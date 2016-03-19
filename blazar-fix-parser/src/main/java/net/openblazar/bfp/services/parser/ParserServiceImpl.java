package net.openblazar.bfp.services.parser;

import com.google.inject.Inject;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.database.dao.MessageDAO;
import net.openblazar.bfp.fix.data.FixMessage;
import net.openblazar.bfp.fix.parser.FixParser;
import net.openblazar.bfp.fix.parser.util.FixMessageConverter;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class ParserServiceImpl implements ParserService {

    private final FixParser fixParser;
    private final FixMessageConverter messageConverter;
    private MessageDAO messageDAO;

    @Inject
    public ParserServiceImpl(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.fixParser = new FixParser();
        this.messageConverter = new FixMessageConverter();
    }

    @Override
    public List<FixMessage> findMessagesByUser(UserDetails userDetails, int lowerLimit, int upperLimit) {
        return messageConverter.convertToFixMessages(
                messageDAO.findMessageByUserID(userDetails.getUserID(), lowerLimit, upperLimit),
                String.valueOf(FixMessageConverter.ENTRY_DELIMITER));
    }

    @Override
    public int countUserMessages(UserDetails userDetails) {
        return messageDAO.countUserMessages(userDetails.getUserID());
    }

    @Override
    public List<FixMessage> parseInput(String input) {
        return fixParser.parseInput(input);
    }

    @Override
    public void saveMessages(UserDetails userDetails, List<FixMessage> messages) {
        for (FixMessage message : messages) {
            messageDAO.saveMessage(userDetails.getUserID(), message);
        }
    }

}
