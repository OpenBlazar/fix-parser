package com.blazarquant.bfp.services.parser;

import com.blazarquant.bfp.core.parser.FixDefinitionProviderFactory;
import com.blazarquant.bfp.core.parser.FixDefinitionProviderManager;
import com.blazarquant.bfp.core.parser.FixDefinitionProvidersFileStore;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.security.util.SecurityUtilImpl;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.google.inject.Inject;
import com.blazarquant.bfp.database.dao.MessageDAO;
import com.blazarquant.bfp.fix.parser.FixParser;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;
import com.google.inject.Provider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wojciech Zankowski
 */
public class ParserServiceImpl implements ParserService {

    private final FixParser fixParser;
    private final FixMessageConverter messageConverter;
    private final SecurityUtil securityUtil = new SecurityUtilImpl();

    private final FixDefinitionProviderManager definitionProviderManager;
    private final FixDefinitionProviderFactory definitionProviderFactory;
    private final FixDefinitionProvidersFileStore definitionProvidersFileStore;

    private MessageDAO messageDAO;

    @Inject
    public ParserServiceImpl(MessageDAO messageDAO) throws Exception {
        this.messageDAO = messageDAO;
        this.fixParser = new FixParser();
        this.messageConverter = new FixMessageConverter();

        this.definitionProviderManager = new FixDefinitionProviderManager();
        this.definitionProviderFactory = new FixDefinitionProviderFactory(definitionProviderManager);
        this.definitionProvidersFileStore = new FixDefinitionProvidersFileStore(definitionProviderFactory);
    }

    @Override
    public List<FixMessage> findMessagesByUser(UserDetails userDetails, ProviderDescriptor providerDescriptor, int lowerLimit, int upperLimit) {
        List<String> messages = messageDAO.findMessageByUserID(userDetails.getUserID(), lowerLimit, upperLimit)
                .stream()
                .map(s -> securityUtil.decodeMessage(s))
                .collect(Collectors.toList());
        return messageConverter.convertToFixMessages(
                messages,
                String.valueOf(FixMessageConverter.ENTRY_DELIMITER),
                definitionProviderFactory.getDefinitionProvider(userDetails.getUserID(), providerDescriptor));
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
    public List<FixMessage> parseInput(ProviderDescriptor providerDescriptor, UserID userID, String input) {
        return fixParser.parseInput(
                input,
                definitionProviderFactory.getDefinitionProvider(userID, providerDescriptor)
        );
    }

    @Override
    public void saveMessages(UserDetails userDetails, List<FixMessage> messages) {
        for (FixMessage message : messages) {
            messageDAO.saveMessage(userDetails.getUserID(), message);
        }
    }

    @Override
    public void saveProviderFile(UserID userID, ProviderDescriptor providerDescriptor, byte[] content) throws Exception {
        definitionProvidersFileStore.saveProviderFile(userID, providerDescriptor, content);
        FixDefinitionProvider definitionProvider = definitionProvidersFileStore.loadCustomProvider(userID, providerDescriptor);
        definitionProviderManager.addDefinitionProvider(userID, providerDescriptor, definitionProvider);
    }

    @Override
    public FixDefinitionProvider getDefinitionProvider(ProviderDescriptor providerDescriptor, UserID userID) {
        return definitionProviderFactory.getDefinitionProvider(userID, providerDescriptor);
    }

    @Override
    public Set<ProviderDescriptor> getProviders(UserID userID) {
        return definitionProviderManager.getProviderDescriptors(userID);
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
}
