package pl.zankowski.fixparser.messages;

import pl.zankowski.bfp.fix.data.FixMessage;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import pl.zankowski.fixparser.core.entity.IDAO;
import pl.zankowski.fixparser.user.api.UserIdTO;

import java.util.List;

public interface MessageDAO extends IDAO {

    @SelectProvider(type = MessageSQLProvider.class, method = "buildCountUserMessages")
    int countUserMessages(
            @Param("userId") UserIdTO userId);

    @SelectProvider(type = MessageSQLProvider.class, method = "buildFindMessageByUserId")
    List<String> findMessageByUserId(
            @Param("userId") UserIdTO userId,
            @Param("lowerlimit") int lowerLimit,
            @Param("upperlimit") int upperLimit);

    @InsertProvider(type = MessageSQLProvider.class, method = "buildSaveMessage")
    void saveMessage(
            @Param("userId") UserIdTO userId,
            @Param("message") FixMessage message);

    @DeleteProvider(type = MessageSQLProvider.class, method = "buildClearHistory")
    void clearHistory(
            @Param("userId") UserIdTO userId
    );

}
