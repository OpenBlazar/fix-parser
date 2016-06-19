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
package com.blazarquant.bfp.core.parser;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;

import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProviderFactory {

    private final FixDefinitionProviderManager definitionProviderManager;

    public FixDefinitionProviderFactory(FixDefinitionProviderManager definitionProviderManager) throws Exception {
        this.definitionProviderManager = definitionProviderManager;
    }

    public FixDefinitionProvider getDefinitionProvider() {
        return definitionProviderManager.getDefaultDefinitionProvider();
    }

    public FixDefinitionProvider getProDefinitionProvider(ProviderDescriptor providerDescriptor) {
        if (providerDescriptor == null) {
            return definitionProviderManager.getDefaultDefinitionProvider();
        }
        FixDefinitionProvider definitionProvider = definitionProviderManager.getProDefinitionProviders().get(providerDescriptor);
        if (definitionProvider == null) {
            return definitionProviderManager.getDefaultDefinitionProvider();
        }
        return definitionProvider;
    }

    public FixDefinitionProvider getDefinitionProvider(UserID userID, ProviderDescriptor providerDescriptor) {
        if (userID == null || providerDescriptor == null || providerDescriptor.getProviderName().isEmpty()) {
            return definitionProviderManager.getDefaultDefinitionProvider();
        }
        if (providerDescriptor.getProviderName().equals(DefaultFixDefinitionProvider.NAME)) {
            return definitionProviderManager.getDefaultDefinitionProvider();
        }
        Map<ProviderDescriptor, FixDefinitionProvider> providers = definitionProviderManager.getDefinitionProviders(userID);
        if (providers == null) {
            return definitionProviderManager.getDefaultDefinitionProvider();
        }
        FixDefinitionProvider fixDefinitionProvider = providers.get(providerDescriptor);
        if (fixDefinitionProvider == null) {
            return definitionProviderManager.getDefaultDefinitionProvider();
        }
        return fixDefinitionProvider;
    }

}
