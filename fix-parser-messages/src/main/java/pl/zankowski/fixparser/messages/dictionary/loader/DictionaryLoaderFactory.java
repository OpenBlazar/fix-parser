package pl.zankowski.fixparser.messages.dictionary.loader;

import com.google.common.collect.Maps;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;

import java.util.List;
import java.util.Map;

public class DictionaryLoaderFactory {

    private final Map<DictionaryLoaderType, DictionaryLoader> loaderMap = Maps.newHashMap();

    public DictionaryLoaderFactory(final List<DictionaryLoader> dictionaryLoaderList) {
        for (final DictionaryLoader dictionaryLoader : dictionaryLoaderList) {
            this.loaderMap.put(dictionaryLoader.getType(), dictionaryLoader);
        }
    }

    public DictionaryLoader getDictionaryLoader(final DictionaryLoaderType loaderType) {
        return loaderMap.get(loaderType);
    }

}
