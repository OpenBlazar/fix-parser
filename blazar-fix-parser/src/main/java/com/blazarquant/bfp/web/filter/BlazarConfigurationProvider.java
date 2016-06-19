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
package com.blazarquant.bfp.web.filter;

import com.blazarquant.bfp.web.util.BlazarURL;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

/**
 * @author Wojciech Zankowski
 */
@RewriteConfiguration
public class BlazarConfigurationProvider extends HttpConfigurationProvider {

    @Override
    public Configuration getConfiguration(ServletContext servletContext) {
        return ConfigurationBuilder.begin()
                .addRule(Join.path(BlazarURL.PARSER_URL).to(BlazarURL.PARSER_FULL_URL))
                .addRule(Join.path(BlazarURL.SIGNIN_URL).to(BlazarURL.SIGNIN_FULL_URL))
                .addRule(Join.path(BlazarURL.SIGNUP_URL).to(BlazarURL.SIGNUP_FULL_URL))
                .addRule(Join.path(BlazarURL.FILEPARSER_URL).to(BlazarURL.FILEPARSER_FULL_URL))
                .addRule(Join.path(BlazarURL.HISTORY_URL).to(BlazarURL.HISTORY_FULL_URL))
                .addRule(Join.path(BlazarURL.FAQ_URL).to(BlazarURL.FAQ_FULL_URL))
                .addRule(Join.path(BlazarURL.CONFIRMATION_URL).to(BlazarURL.CONFIRMATION_FULL_URL))
                .addRule(Join.path(BlazarURL.ADMIN_URL).to(BlazarURL.ADMIN_FULL_URL))
                .addRule(Join.path(BlazarURL.PROFILE_URL).to(BlazarURL.PROFILE_FULL_URL))
                .addRule(Join.path(BlazarURL.INDEX_URL).to(BlazarURL.HOME_FULL_URL))
                .addRule(Join.path(BlazarURL.SUBSCRIPTION_URL).to(BlazarURL.SUBSCRIPTION_FULL_URL))
                .addRule(Join.path(BlazarURL.PAYMENT_URL).to(BlazarURL.PAYMENT_FULL_URL));
    }

    @Override
    public int priority() {
        return 0;
    }
}
