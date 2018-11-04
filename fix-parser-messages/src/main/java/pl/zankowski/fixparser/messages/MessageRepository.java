package pl.zankowski.fixparser.messages;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import pl.zankowski.fixparser.core.entity.IRepository;
import pl.zankowski.fixparser.messages.entity.parser.FixMessage;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.List;

public interface MessageRepository extends IRepository {

    @SelectProvider(type = MessageSQLProvider.class, method = "buildCountUserMessages")
    int countUserMessages(
            @Param("userId") UserId userId);

    @SelectProvider(type = MessageSQLProvider.class, method = "buildFindMessageByUserId")
    List<String> findMessageByUserId(
            @Param("userId") UserId userId,
            @Param("lowerlimit") int lowerLimit,
            @Param("upperlimit") int upperLimit);

    @InsertProvider(type = MessageSQLProvider.class, method = "buildSaveMessage")
    void saveMessage(
            @Param("userId") UserId userId,
            @Param("message") FixMessage message);

    @DeleteProvider(type = MessageSQLProvider.class, method = "buildClearHistory")
    void clearHistory(
            @Param("userId") UserId userId
    );

}
