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
package com.blazarquant.bfp.fix.parser.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wojciech Zankowski
 */
public class FixDelimiterResolver {

    private static final Pattern tag9Pattern = Pattern.compile("[^0-9]9=[0-9]+");
    private static final Pattern tag35Pattern = Pattern.compile("[^0-9]35=[0-9A-Za-z]+");

    public int getTag9Index(String input) {
        Matcher matcher = tag9Pattern.matcher(input);
        if (matcher.find()) {
            return matcher.start();
        }
        throw new IllegalArgumentException("Failed to find body length value.");
    }

    public int getTag9Length(String input) {
        Matcher matcher = tag9Pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group().length();
        }
        throw new IllegalArgumentException("Failed to find body length value.");
    }

    public int getTag35Index(String input) {
        Matcher matcher = tag35Pattern.matcher(input);
        if (matcher.find()) {
            return matcher.start();
        }
        throw new IllegalArgumentException("Failed to find message type value.");
    }

}
