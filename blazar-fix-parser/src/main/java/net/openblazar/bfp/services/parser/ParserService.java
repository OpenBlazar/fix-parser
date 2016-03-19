package net.openblazar.bfp.services.parser;

import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.fix.data.FixMessage;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface ParserService {

    List<FixMessage> findMessagesByUser(UserDetails userDetails, int lowerLimit, int upperLimit);

    int countUserMessages(UserDetails userDetails);

    List<FixMessage> parseInput(String input);

    void saveMessages(UserDetails userDetails, List<FixMessage> messages);

}
