package net.openblazar.bfp.database;

import com.google.inject.Singleton;
import com.google.inject.name.Names;
import net.openblazar.bfp.database.dao.UserDAO;
import net.openblazar.bfp.services.UserService;
import net.openblazar.bfp.services.UserServiceImpl;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.Properties;

/**
 * @author Wojciech Zankowski
 */
public class DatabaseModule extends MyBatisModule {

	private final Properties properties;

	public DatabaseModule(Properties properties) {
		this.properties = properties;
	}


	@Override
	protected void initialize() {
		install(JdbcHelper.MariaDB);
		environmentId("development");

		bindDataSourceProviderType(BasicDataSourceProvider.class);
		bindTransactionFactoryType(JdbcTransactionFactory.class);
		Names.bindProperties(binder(), properties);


		bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);

		addMapperClass(UserDAO.class);
	}

}
