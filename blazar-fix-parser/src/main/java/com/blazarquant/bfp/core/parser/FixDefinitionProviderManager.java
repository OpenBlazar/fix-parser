package com.blazarquant.bfp.core.parser;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProviderManager {

    private final FixDefinitionProvider defaultDefinitionProvider;

    private final Map<UserID, Map<ProviderDescriptor, FixDefinitionProvider>> customDefinitionProviders;
    private final Map<ProviderDescriptor, FixDefinitionProvider> proDefinitionProviders;

    public FixDefinitionProviderManager() throws Exception {
        this.customDefinitionProviders = new ConcurrentHashMap<>();
        this.proDefinitionProviders = new ConcurrentHashMap<>();
        this.defaultDefinitionProvider = new DefaultFixDefinitionProvider();
    }

    public void addDefinitionProvider(UserID userID, ProviderDescriptor providerDescriptor, FixDefinitionProvider fixDefinitionProvider) {
        Map<ProviderDescriptor, FixDefinitionProvider> providers = customDefinitionProviders.get(userID);
        if (providers == null) {
            providers = new HashMap<>();
        }
        providers.put(providerDescriptor, fixDefinitionProvider);
        customDefinitionProviders.put(userID, providers);
    }

    public void addDefinitionProviders(UserID userID, Map<ProviderDescriptor, FixDefinitionProvider> fixDefinitionProviderMap) {
        Map<ProviderDescriptor, FixDefinitionProvider> providers = customDefinitionProviders.get(userID);
        if (providers == null) {
            providers = new HashMap<>();
        }
        providers.putAll(fixDefinitionProviderMap);
        customDefinitionProviders.put(userID, providers);
    }

    public void addProDefinitionProviders(Map<ProviderDescriptor, FixDefinitionProvider> definitionProviderMap) {
        proDefinitionProviders.putAll(definitionProviderMap);
    }

    public FixDefinitionProvider getDefaultDefinitionProvider() {
        return defaultDefinitionProvider;
    }

    public Map<ProviderDescriptor, FixDefinitionProvider> getProDefinitionProviders() {
        return proDefinitionProviders;
    }

    public Map<ProviderDescriptor, FixDefinitionProvider> getDefinitionProviders(UserID userID) {
        Map<ProviderDescriptor, FixDefinitionProvider> providers = customDefinitionProviders.get(userID);
        if (providers == null) {
            return Collections.emptyMap();
        }
        return providers;
    }

    public Set<ProviderDescriptor> getProviderDescriptors(UserID userID) {
        Map<ProviderDescriptor, FixDefinitionProvider> providers = customDefinitionProviders.get(userID);
        if (providers == null) {
            return Collections.emptySet();
        }
        return providers.keySet();
    }

    public void removeDefinitionProvider(UserID userID, ProviderDescriptor providerDescriptor) {
        Map<ProviderDescriptor, FixDefinitionProvider> definitionProviderMap = customDefinitionProviders.get(userID);
        if (definitionProviderMap != null) {
            definitionProviderMap.remove(providerDescriptor);
            if (definitionProviderMap.isEmpty()) {
                definitionProviderMap.remove(userID);
            }
        }
    }

    public void removeDefinitionProviders(UserID userID) {
        customDefinitionProviders.remove(userID);
    }

}
