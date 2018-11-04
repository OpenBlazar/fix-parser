package pl.zankowski.fixparser.messages.dictionary.loader;

import pl.zankowski.fixparser.core.exception.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;

import java.io.InputStream;
import java.util.Map;

public interface DictionaryLoader {

    Map<Integer, FixFieldDefinition> parseDocument(final InputStream documentFile) throws FixParserBusinessException;

    DictionaryLoaderType getType();

}
