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
package com.blazarquant.bfp.database;

import com.blazarquant.bfp.core.security.util.SettingsManager;
import com.blazarquant.bfp.database.dao.MessageDAO;
import com.blazarquant.bfp.database.dao.ShareDAO;
import com.blazarquant.bfp.database.dao.TrackerDAO;
import com.blazarquant.bfp.database.dao.UserDAO;
import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

/**
 * @author Wojciech Zankowski
 */
public class DatabaseModule extends MyBatisModule {

    @Override
    protected void initialize() {
        install(JdbcHelper.MariaDB);
        environmentId("development");

        bindDataSourceProviderType(BasicDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        Names.bindProperties(binder(), SettingsManager.getInstance().getProperties());

        addMapperClass(UserDAO.class);
        addMapperClass(MessageDAO.class);
        addMapperClass(ShareDAO.class);
        addMapperClass(TrackerDAO.class);
    }

}
