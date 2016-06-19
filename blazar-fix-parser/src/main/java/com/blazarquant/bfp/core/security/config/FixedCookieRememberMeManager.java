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
package com.blazarquant.bfp.core.security.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

/**
 * https://issues.apache.org/jira/browse/SHIRO-561
 * https://issues.apache.org/jira/browse/SHIRO-441
 *
 * @author Wojciech Zankowski
 */
public class FixedCookieRememberMeManager extends CookieRememberMeManager {

    @Inject
    public FixedCookieRememberMeManager(@Named("shiro.cipherKey") String cipherKey) {
        super();
        setCipherKey(Base64.decode(cipherKey));
    }

}
