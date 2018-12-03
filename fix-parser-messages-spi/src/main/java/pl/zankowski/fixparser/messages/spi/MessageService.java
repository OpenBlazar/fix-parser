package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.core.exception.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.List;

public interface MessageService {

    List<FixMessageTO> findMessagesByUser(UserId userId, DictionaryDescriptorTO providerDescriptor,
            boolean isPermitted, int lowerLimit, int upperLimit) throws FixParserBusinessException;

    int countUserMessages(UserId userDetails);

    List<FixMessageTO> parseInput(String input) throws FixParserBusinessException;

    String parseInput(FixMessageTO message);

    List<FixMessageTO> parseInput(DictionaryDescriptorTO providerDescriptor, UserId userID, String input,
            boolean isPermitted) throws FixParserBusinessException;

    void saveMessages(UserId userDetails, List<FixMessageTO> messages);

    void clearHistory(UserId userID);

}
