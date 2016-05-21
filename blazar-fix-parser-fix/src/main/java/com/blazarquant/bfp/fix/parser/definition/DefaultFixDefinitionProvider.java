package com.blazarquant.bfp.fix.parser.definition;

import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import com.blazarquant.bfp.fix.parser.definition.loader.QuickFixXMLLoader;

/**
 * @author Wojciech Zankowski
 */
public class DefaultFixDefinitionProvider extends AbstractFixDefinitionProvider {

    public static final String NAME = "Default";
    public static final XMLLoaderType LOADER_TYPE = XMLLoaderType.QUICKFIX_LOADER;
    public static final ProviderDescriptor DESCRIPTOR = new ProviderDescriptor(NAME, LOADER_TYPE);

    private static final String documentFileName = "FIX50SP2.xml";

    public DefaultFixDefinitionProvider() throws Exception {
        super(new QuickFixXMLLoader().parseDocument(DefaultFixDefinitionProvider.class.getClassLoader().getResourceAsStream(documentFileName)));
    }

}
