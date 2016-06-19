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

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;

import java.util.List;
import java.util.Set;

/**
 * @author Wojciech Zankowski
 */
public interface ParserService {

    List<FixMessage> findMessagesByUser(UserDetails userDetails, ProviderDescriptor providerDescriptor, boolean isPermitted, int lowerLimit, int upperLimit);

    int countUserMessages(UserDetails userDetails);

    List<FixMessage> parseInput(String input);

    List<FixMessage> parseInput(ProviderDescriptor providerDescriptor, UserID userID, String input, boolean isPermitted);

    void saveMessages(UserDetails userDetails, List<FixMessage> messages);

    void clearHistory(UserID userID);

    void saveProviderFile(UserID userID, ProviderDescriptor providerDescriptor, byte[] content) throws Exception;

    FixDefinitionProvider getDefinitionProvider(ProviderDescriptor providerDescriptor, UserID userID, boolean isPermitted);

    Set<ProviderDescriptor> getProviders(UserID userID, boolean isPermitted);

    void loadProvidersForUser(UserID userID);

    boolean removeProvider(UserID userID, ProviderDescriptor providerDescriptor);

    boolean isProProvider(ProviderDescriptor providerDescriptor);

}
