package pl.zankowski.fixparser.messages.share;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import pl.zankowski.fixparser.messages.spi.share.ShareService;

public class ShareModule extends MyBatisModule {

    @Override
    protected void initialize() {
        install(JdbcHelper.MariaDB);

        bindDataSourceProviderType(BasicDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        addMapperClass(ShareDAO.class);
        bind(ShareService.class).to(ShareServiceImpl.class).asEagerSingleton();
    }
}
