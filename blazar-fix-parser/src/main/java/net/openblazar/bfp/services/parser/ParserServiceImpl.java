package net.openblazar.bfp.services.parser;

import com.google.inject.Inject;
import net.openblazar.bfp.core.parser.FixParser;
import net.openblazar.bfp.core.parser.util.FixMessageConverter;
import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.database.dao.MessageDAO;

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
    public List<FixMessage> findMessagesById(UserDetails userDetails) {
        return messageConverter.convertToFixMessages(
                messageDAO.findMessageByUserID(userDetails.getUserID(), 100),
                String.valueOf(FixMessageConverter.ENTRY_DELIMITER));
    }

    @Override
    public List<FixMessage> parseInput(String input) {
        return fixParser.parseInput(input);
    }

    @Override
    public void saveMessages(UserDetails userDetails, List<FixMessage> messages) {
        System.out.println(userDetails);
        System.out.println(messages);
        for (FixMessage message : messages) {
                messageDAO.saveMessage(userDetails.getUserID(), message);
        }
    }



}
