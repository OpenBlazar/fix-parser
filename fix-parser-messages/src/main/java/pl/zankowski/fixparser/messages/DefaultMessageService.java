package pl.zankowski.fixparser.messages;

import com.google.inject.Inject;
import pl.zankowski.fixparser.core.exception.FixParserBusinessException;
import pl.zankowski.fixparser.core.framework.CodecUtil;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.dictionary.FixDictionaryService;
import pl.zankowski.fixparser.messages.fix.FixMessageConverter;
import pl.zankowski.fixparser.messages.fix.FixMessageMapper;
import pl.zankowski.fixparser.messages.fix.FixParser;
import pl.zankowski.fixparser.messages.spi.MessageService;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultMessageService implements MessageService {

    private final FixParser fixParser;
    private final FixMessageConverter messageConverter;
    private final MessageRepository messageRepository;
    private final FixMessageMapper mapper;
    private final FixDictionaryService dictionaryService;

    @Inject
    public DefaultMessageService(final MessageRepository messageRepository, final FixMessageMapper mapper,
            final FixDictionaryService dictionaryService) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
        this.dictionaryService = dictionaryService;
        this.fixParser = new FixParser();
        this.messageConverter = new FixMessageConverter();
    }

    @Override
    public List<FixMessageTO> findMessagesByUser(final UserId userId, final DictionaryDescriptorTO dictionaryDescriptor,
            final boolean isPermitted, final int lowerLimit, final int upperLimit) throws FixParserBusinessException {
        List<String> messages = messageRepository.findMessageByUserId(userId, lowerLimit, upperLimit)
                .stream()
                .map(CodecUtil::decodeBase64)
                .collect(Collectors.toList());
        return messageConverter.convertToFixMessages(
                messages,
                String.valueOf(FixMessageConverter.ENTRY_DELIMITER),
                dictionaryService.getDefinitionProvider(dictionaryDescriptor));
    }

    @Override
    public int countUserMessages(final UserId userId) {
        return messageRepository.countUserMessages(userId);
    }

    @Override
    public List<FixMessageTO> parseInput(final String input) throws FixParserBusinessException {
        return fixParser.parseInput(input, dictionaryService.getDefinitionProvider(null));
    }

    @Override
    public String parseInput(final FixMessageTO message) {
        return messageConverter.convertToString(message);
    }

    @Override
    public List<FixMessageTO> parseInput(DictionaryDescriptorTO dictionaryDescriptor, UserId userID, String input,
            boolean isPermitted) throws FixParserBusinessException {
        return fixParser.parseInput(
                input,
                dictionaryService.getDefinitionProvider(dictionaryDescriptor)
        );
    }

    @Override
    public void saveMessages(final UserId userId, final List<FixMessageTO> messages) {
        messages.forEach(message -> messageRepository.saveMessage(userId, mapper.map(message)));
    }

    @Override
    public void clearHistory(final UserId userId) {
        messageRepository.clearHistory(userId);
    }

}
