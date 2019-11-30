package pl.zankowski.fixparser.messages.client;

import org.springframework.data.domain.Pageable;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserBaseRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;

import java.util.List;

public interface FixMessageClient {

    List<FixMessageTO> getMessages(
            final String providerName,
            final DictionaryLoaderType loaderType,
            final Pageable pageable);

    Integer countUserMessages();

    List<FixMessageTO> parseInput(FixParserBaseRequestTO input);

    void clearHistory();

}
