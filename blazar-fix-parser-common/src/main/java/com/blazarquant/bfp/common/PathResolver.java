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
package com.blazarquant.bfp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Wojciech Zankowski
 */
public class PathResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathResolver.class);

    private final String appDirectory;

    public PathResolver(String systemProperty) {
        String appDirectory = System.getProperty(systemProperty);
        if (appDirectory == null) {
            appDirectory = "src" + File.separator + "test";
        }
        this.appDirectory = appDirectory;

        File resultFile = new File(appDirectory);
        if (!resultFile.exists()) {
            LOGGER.warn("'{}' does not exist!", appDirectory);
        }
        if (!resultFile.isDirectory()) {
            LOGGER.warn("'{}' is not a directory!", appDirectory);
        }
    }

    public String getAppDirectory() {
        return appDirectory;
    }
}
