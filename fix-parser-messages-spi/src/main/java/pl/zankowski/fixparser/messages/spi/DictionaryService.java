package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.core.exception.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryTO;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.Set;

public interface DictionaryService {

    Set<DictionaryDescriptorTO> getDictionaryDescriptors(final UserId userId);

    void saveDictionary(final DictionaryTO dictionary) throws FixParserBusinessException;

    boolean removeDictionary(final DictionaryDescriptorTO descriptor);

}
