package pl.zankowski.fixparser.messages;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import pl.zankowski.fixparser.core.framework.db.MongoModule;
import pl.zankowski.fixparser.messages.spi.MessageService;

public class MessageModule extends MyBatisModule {

    @Override
    protected void initialize() {
        install(JdbcHelper.MariaDB);
        install(new MongoModule());

        bindDataSourceProviderType(BasicDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        addMapperClass(MessageRepository.class);

        bind(MessageService.class).to(DefaultMessageService.class).asEagerSingleton();
    }
}
