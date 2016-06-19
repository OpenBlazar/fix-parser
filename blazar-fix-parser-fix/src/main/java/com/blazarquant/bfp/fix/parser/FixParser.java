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
package com.blazarquant.bfp.fix.parser;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.util.FixDelimiterResolver;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wojciech Zankowski
 */
public class FixParser {

    private static final Pattern FIX_PATTERN = Pattern.compile("[^0-9a-zA-Z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?\\s]*8=FIX(.*?)[^0-9]10=\\d{3}.?");

    private final FixDelimiterResolver delimiterResolver = new FixDelimiterResolver();
    private final FixMessageConverter messageConverter = new FixMessageConverter();

    public List<FixMessage> parseInput(String input, FixDefinitionProvider definitionProvider) {
        List<String> textMessages = extractFixMessages(input);
        if (textMessages.isEmpty()) {
            return Collections.emptyList();
        }

        String delimiter = resolveMessageDelimiter(textMessages.get(0));
        return messageConverter.convertToFixMessages(textMessages, delimiter, definitionProvider);
    }

    protected List<String> extractFixMessages(String input) {
        List<String> messages = new ArrayList<>();
        Matcher matcher = FIX_PATTERN.matcher(input);
        while (matcher.find()) {
            messages.add(matcher.group());
        }
        return messages;
    }

    protected String resolveMessageDelimiter(String input) {
        int tag9Start = delimiterResolver.getTag9Index(input);
        int tag9Length = delimiterResolver.getTag9Length(input);
        int tag35Start = delimiterResolver.getTag35Index(input);
        return input.substring(tag9Start + tag9Length, tag35Start + 1);
    }

}
