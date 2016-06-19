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

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Wojciech Zankowski
 */
public class BcryptCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String storedBcryptPassword;
        if (info.getCredentials() instanceof char[]) {
            storedBcryptPassword = new String((char[]) info.getCredentials());
        } else {
            storedBcryptPassword = info.getCredentials().toString();
        }
        final String assertedPlaintextPassword = new String(upToken.getPassword());
        return BCrypt.checkpw(assertedPlaintextPassword, storedBcryptPassword);
    }

}
