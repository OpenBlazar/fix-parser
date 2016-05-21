package com.blazarquant.bfp.core.parser;

import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.loader.QuickFixXMLLoader;
import com.blazarquant.bfp.fix.parser.definition.loader.XMLLoader;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProvidersLoaderFactory {

    private final XMLLoader quickFixLoader;

    public FixDefinitionProvidersLoaderFactory() {
        this.quickFixLoader = new QuickFixXMLLoader();
    }

    public XMLLoader getLoader(ProviderDescriptor providerDescriptor) {
        switch (providerDescriptor.getLoaderType()) {
            case QUICKFIX_LOADER:
                return quickFixLoader;
            default:
                throw new IllegalArgumentException("Illegal loader type.");
        }
    }

}
