package net.openblazar.bfp.database.dao;

import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.data.user.UserID;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface MessageDAO {

    List<FixMessage> findMessageByUserID(UserID userID, int limit);

    void saveMessage(FixMessage message);

}
