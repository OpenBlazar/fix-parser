package com.blazarquant.bfp.database;

import com.blazarquant.bfp.database.dao.UserDAO;
import com.blazarquant.bfp.database.utils.DatabasePropertiesLoader;
import com.google.inject.name.Names;
import com.blazarquant.bfp.database.dao.MessageDAO;
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
		Names.bindProperties(binder(), DatabasePropertiesLoader.loadProperties());

		addMapperClass(UserDAO.class);
		addMapperClass(MessageDAO.class);
	}

}
