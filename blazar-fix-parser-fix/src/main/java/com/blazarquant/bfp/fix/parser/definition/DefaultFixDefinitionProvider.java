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
