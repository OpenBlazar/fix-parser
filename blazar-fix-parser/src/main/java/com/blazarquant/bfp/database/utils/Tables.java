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
package com.blazarquant.bfp.database.utils;

/**
 * @author Wojciech Zankowski
 */
public class Tables {

    public static final String USERS_TABLE = "bfp_users";
    public static final String ROLES_TABLE = "bfp_roles";
    public static final String USER_ROLES_TABLE = "bfp_userroles";
    public static final String PERMISSIONS_TABLE = "bfp_permissions";
    public static final String USER_PERMISSIONS_TABLE = "bfp_userpermissions";
    public static final String USER_PARAMETERS = "bfp_userparameters";
    public static final String MESSAGES = "bfp_messages";
    public static final String SHARED_MESSAGES = "bfp_sharedmessages";
    public static final String TRACKER_TABLE = "bfp_tracker";

    private Tables() {
        // no constructor
    }

}
