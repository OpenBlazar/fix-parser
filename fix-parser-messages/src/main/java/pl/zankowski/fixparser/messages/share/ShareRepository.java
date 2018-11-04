package pl.zankowski.fixparser.messages.share;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import pl.zankowski.fixparser.core.entity.IRepository;

public interface ShareRepository extends IRepository {

    @SelectProvider(type = ShareSQLProvider.class, method = "buildFindMessageByKey")
    String findMessageByKey(
            @Param(value = "shareKey") String key);

    @InsertProvider(type = ShareSQLProvider.class, method = "buildSaveSharedMessage")
    void saveSharedMessage(
            @Param(value = "shareKey") String key,
            @Param(value = "shareMessage") String message);

}
