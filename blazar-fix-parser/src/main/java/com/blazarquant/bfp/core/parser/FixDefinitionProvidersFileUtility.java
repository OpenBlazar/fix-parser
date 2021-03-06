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

import com.blazarquant.bfp.core.security.util.SettingsManager;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;

import java.io.File;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProvidersFileUtility {

    private static final String MAIN_DIR = SettingsManager.getInstance().getPathResolver().getAppDirectory() + File.separator + "dictionaries";
    private static final String DELIMITER = "#";

    private static final int PROVIDER_LOADER_TYPE = 0;
    private static final int PROVIDER_NAME = 1;

    private static final String PRO = "pro";

    public static String createProviderFileName(ProviderDescriptor providerDescriptor) {
        return createProviderFileName(providerDescriptor.getLoaderType(), providerDescriptor.getProviderName());
    }

    public static String createProviderFileName(XMLLoaderType loaderType, String providerName) {
        return loaderType.getTag() + DELIMITER + providerName.replaceAll(DELIMITER, "");
    }

    public static String getMainProvidersDir() {
        return MAIN_DIR;
    }

    public static String getProProvidersDir() {
        return MAIN_DIR + File.separator + PRO;
    }

    public static String getUserProvidersDir(UserID userID) {
        return MAIN_DIR + File.separator + userID.getId();
    }

    public static ProviderDescriptor resolveProviderFileName(String fileName) {
        String[] elements = validateFileName(fileName.split(DELIMITER));
        return new ProviderDescriptor(
                elements[PROVIDER_NAME],
                XMLLoaderType.getXMLLoaderTypeFromTag(elements[PROVIDER_LOADER_TYPE])
        );
    }

    private static String[] validateFileName(String[] elements) {
        if (elements.length != 2) {
            throw new IllegalArgumentException("Illegal file name. Missing XML Loader Type or provider name.");
        }
        return elements;
    }

}
