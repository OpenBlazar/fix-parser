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
