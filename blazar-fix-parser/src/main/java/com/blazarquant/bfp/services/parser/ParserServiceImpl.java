/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.services.parser;

import com.blazarquant.bfp.core.parser.FixDefinitionProviderFactory;
import com.blazarquant.bfp.core.parser.FixDefinitionProviderManager;
import com.blazarquant.bfp.core.parser.FixDefinitionProvidersFileStore;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.database.dao.MessageDAO;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.FixParser;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;
import com.google.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Wojciech Zankowski
 */
public class ParserServiceImpl implements ParserService {

    private final FixParser fixParser;
    private final FixMessageConverter messageConverter;
    private final SecurityUtil securityUtil;

    private final FixDefinitionProviderManager definitionProviderManager;
    private final FixDefinitionProviderFactory definitionProviderFactory;
    private final FixDefinitionProvidersFileStore definitionProvidersFileStore;

    private MessageDAO messageDAO;

    @Inject
    public ParserServiceImpl(MessageDAO messageDAO, SecurityUtil securityUtil) throws Exception {
        this.messageDAO = messageDAO;
        this.securityUtil = securityUtil;
        this.fixParser = new FixParser();
        this.messageConverter = new FixMessageConverter();

        this.definitionProviderManager = new FixDefinitionProviderManager();
        this.definitionProviderFactory = new FixDefinitionProviderFactory(definitionProviderManager);
        this.definitionProvidersFileStore = new FixDefinitionProvidersFileStore(definitionProviderFactory);
        this.definitionProviderManager.addProDefinitionProviders(this.definitionProvidersFileStore.loadProProviders());
    }

    @Override
    public List<FixMessage> findMessagesByUser(UserDetails userDetails, ProviderDescriptor providerDescriptor, boolean isPermitted, int lowerLimit, int upperLimit) {
        List<String> messages = messageDAO.findMessageByUserID(userDetails.getUserID(), lowerLimit, upperLimit)
                .stream()
                .map(s -> securityUtil.decodeMessage(s))
                .collect(Collectors.toList());
        return messageConverter.convertToFixMessages(
                messages,
                String.valueOf(FixMessageConverter.ENTRY_DELIMITER),
                getDefinitionProvider(providerDescriptor, userDetails.getUserID(), isPermitted));
    }

    @Override
    public int countUserMessages(UserDetails userDetails) {
        return messageDAO.countUserMessages(userDetails.getUserID());
    }

    @Override
    public List<FixMessage> parseInput(String input) {
        return fixParser.parseInput(input, definitionProviderFactory.getDefinitionProvider());
    }

    @Override
    public List<FixMessage> parseInput(ProviderDescriptor providerDescriptor, UserID userID, String input, boolean isPermitted) {
        return fixParser.parseInput(
                input,
                getDefinitionProvider(providerDescriptor, userID, isPermitted)
        );
    }

    @Override
    public void saveMessages(UserDetails userDetails, List<FixMessage> messages) {
        for (FixMessage message : messages) {
            messageDAO.saveMessage(userDetails.getUserID(), message);
        }
    }

    @Override
    public void clearHistory(UserID userID) {
        messageDAO.clearHistory(userID);
    }

    @Override
    public void saveProviderFile(UserID userID, ProviderDescriptor providerDescriptor, byte[] content) throws Exception {
        definitionProvidersFileStore.saveProviderFile(userID, providerDescriptor, content);
        FixDefinitionProvider definitionProvider = definitionProvidersFileStore.loadCustomProvider(userID, providerDescriptor);
        definitionProviderManager.addDefinitionProvider(userID, providerDescriptor, definitionProvider);
    }

    @Override
    public FixDefinitionProvider getDefinitionProvider(ProviderDescriptor providerDescriptor, UserID userID, boolean isPermitted) {
        if (isPermitted && providerDescriptor != null
                && definitionProviderManager.getProDefinitionProviders().keySet().contains(providerDescriptor)) {
            return definitionProviderFactory.getProDefinitionProvider(providerDescriptor);
        } else {
            return definitionProviderFactory.getDefinitionProvider(userID, providerDescriptor);
        }
    }

    @Override
    public Set<ProviderDescriptor> getProviders(UserID userID, boolean isPermitted) {
        Set<ProviderDescriptor> providerDescriptors = new TreeSet<>();
        providerDescriptors.add(DefaultFixDefinitionProvider.DESCRIPTOR);
        providerDescriptors.addAll(definitionProviderManager.getProviderDescriptors(userID));
        if (isPermitted) {
            providerDescriptors.addAll(definitionProviderManager.getProDefinitionProviders().keySet());
        }
        return providerDescriptors;
    }

    @Override
    public void loadProvidersForUser(UserID userID) {
        Map<ProviderDescriptor, FixDefinitionProvider> definitionProviderMap = definitionProvidersFileStore.loadUserProviders(userID);
        definitionProviderManager.addDefinitionProviders(userID, definitionProviderMap);
    }

    @Override
    public boolean removeProvider(UserID userID, ProviderDescriptor providerDescriptor) {
        if (definitionProvidersFileStore.deleteProviderFile(userID, providerDescriptor)) {
            definitionProviderManager.removeDefinitionProvider(userID, providerDescriptor);
            return true;
        }
        return false;
    }

    @Override
    public boolean isProProvider(ProviderDescriptor providerDescriptor) {
        return definitionProviderManager.getProDefinitionProviders().keySet().contains(providerDescriptor);
    }
}
