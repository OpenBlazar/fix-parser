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
package com.blazarquant.bfp.services.user;

import com.blazarquant.bfp.core.security.exception.DecodingException;
import com.blazarquant.bfp.core.user.UserSettingsCache;
import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.Role;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface UserService {

    UserDetails getUserDetails(String userName);

    UserDetails getUserDetailsByMail(String userMail);

    List<UserDetails> getUsers();

    List<Role> getUserRoles(UserID userID);

    void addUserPermission(UserID userID, Permission permission);

    boolean isUserNameExists(String userName);

    boolean isUserMailExists(String userMail);

    boolean isUserActive(String userName);

    boolean registerUser(String userName, String userMail, char[] password);

    boolean confirmUser(String confirmationKey) throws DecodingException;

    void loginUser(UserID userID);

    UserSettingsCache getUserSettingsCache();

}
