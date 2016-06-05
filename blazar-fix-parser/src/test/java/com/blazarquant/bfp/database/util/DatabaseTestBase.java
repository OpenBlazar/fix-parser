package com.blazarquant.bfp.database.util;

import com.blazarquant.bfp.database.dao.MessageDAO;
import com.blazarquant.bfp.database.dao.ShareDAO;
import com.blazarquant.bfp.database.dao.TrackerDAO;
import com.blazarquant.bfp.database.dao.UserDAO;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.After;
import org.junit.Before;
import org.mybatis.guice.MyBatisJtaModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static org.apache.ibatis.io.Resources.getResourceAsReader;

/**
 * @author Wojciech Zankowski
 */
public class DatabaseTestBase {

    protected Injector injector;

    protected UserDAO userDAO;
    protected MessageDAO messageDAO;
    protected ShareDAO shareDAO;
    protected TrackerDAO trackerDAO;

    @Before
    public void setUp() throws IOException, SQLException {
        injector = Guice.createInjector(new MyBatisJtaModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.HSQLDB_IN_MEMORY_NAMED);
                environmentId("test");

                bindDataSourceProviderType(PooledDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                Names.bindProperties(binder(), createTestProperties());

                addMapperClass(UserDAO.class);
                addMapperClass(MessageDAO.class);
                addMapperClass(ShareDAO.class);
                addMapperClass(TrackerDAO.class);
            }
        });

        // Prepare Database
        Environment environment = this.injector.getInstance(SqlSessionFactory.class).getConfiguration().getEnvironment();
        DataSource dataSource = environment.getDataSource();
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.runScript(getResourceAsReader("com/blazarquant/bfp/database/schema.sql"));
        runner.runScript(getResourceAsReader("com/blazarquant/bfp/database/test-data.sql"));
        runner.closeConnection();

        this.injector.injectMembers(this);
    }

    @Inject
    public void setMessageDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Inject
    public void setShareDAO(ShareDAO shareDAO) {
        this.shareDAO = shareDAO;
    }

    @Inject
    public void setTrackerDAO(TrackerDAO trackerDAO) {
        this.trackerDAO = trackerDAO;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private static Properties createTestProperties() {
        Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "test");
        myBatisProperties.setProperty("JDBC.username", "sa");
        myBatisProperties.setProperty("JDBC.password", "");
        myBatisProperties.setProperty("JDBC.autoCommit", "false");
        return myBatisProperties;
    }

    @After
    public void tearDown() throws SQLException, IOException {
        Environment environment = this.injector.getInstance(SqlSessionFactory.class).getConfiguration().getEnvironment();
        DataSource dataSource = environment.getDataSource();
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.runScript(getResourceAsReader("com/blazarquant/bfp/database/drop-schema.sql"));
        runner.closeConnection();
    }

}
