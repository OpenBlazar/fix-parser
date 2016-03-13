package net.openblazar.bfp.database;

import com.google.inject.name.Names;
import net.openblazar.bfp.database.dao.MessageDAO;
import net.openblazar.bfp.database.dao.UserDAO;
import net.openblazar.bfp.database.utils.DatabasePropertiesLoader;
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
