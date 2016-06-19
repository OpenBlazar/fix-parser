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
package com.blazarquant.bfp.core.security.util;

/**
 * @author Wojciech Zankowski
 */
public enum Settings {

    SHIRO_CIPHER_KEY("shiro.cipherKey");

    private final String property;

    Settings(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

}
