package pl.zankowski.fixparser.messages;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import pl.zankowski.fixparser.messages.spi.MessageService;

public class MessageModule extends MyBatisModule {

    @Override
    protected void initialize() {
        install(JdbcHelper.MariaDB);

        bindDataSourceProviderType(BasicDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        addMapperClass(MessageDAO.class);

        bind(MessageService.class).to(MessageServiceImpl.class).asEagerSingleton();
    }
}
