package pl.zankowski.fixparser.messages.dictionary;

import com.google.inject.Inject;
import pl.zankowski.fixparser.core.exception.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DefinitionProviderNotFoundException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTOBuilder;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryTO;
import pl.zankowski.fixparser.messages.dictionary.loader.DictionaryLoader;
import pl.zankowski.fixparser.messages.dictionary.loader.DictionaryLoaderFactory;
import pl.zankowski.fixparser.messages.entity.dictionary.DictionaryDescriptorBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionaryBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;
import pl.zankowski.fixparser.user.api.UserId;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class DefaultFixDictionaryService implements FixDictionaryService {

    private DictionaryLoaderFactory dictionaryLoaderFactory;
    private FixDictionaryRepository fixDictionaryRepository;

    @Inject
    public DefaultFixDictionaryService(final DictionaryLoaderFactory dictionaryLoaderFactory,
            final FixDictionaryRepository fixDictionaryRepository) {
        this.dictionaryLoaderFactory = dictionaryLoaderFactory;
        this.fixDictionaryRepository = fixDictionaryRepository;
    }

    @Override
    public Set<DictionaryDescriptorTO> getDictionaryDescriptors(final UserId userId) {
        return fixDictionaryRepository.findByUserId(userId).stream()
                .map(this::toDictionaryDescriptor)
                .collect(toSet());
    }

    private DictionaryDescriptorTO toDictionaryDescriptor(final FixDictionary fixDictionary) {
        return new DictionaryDescriptorTOBuilder()
                .withUserId(fixDictionary.getDictionaryDescriptor().getUserId())
                .withLoaderType(fixDictionary.getDictionaryDescriptor().getLoaderType())
                .withProviderName(fixDictionary.getDictionaryDescriptor().getProviderName())
                .build();
    }

    @Override
    public void saveDictionary(final DictionaryTO dictionary)
            throws FixParserBusinessException {
        final DictionaryLoader dictionaryLoader = dictionaryLoaderFactory.getDictionaryLoader(
                dictionary.getDictionaryDescriptor().getLoaderType());
        final Map<Integer, FixFieldDefinition> parsedDictionary = dictionaryLoader.parseDocument(
                new ByteArrayInputStream(dictionary.getContent()));

        fixDictionaryRepository.save(new FixDictionaryBuilder()
                .withDictionaryDescriptorEntity(new DictionaryDescriptorBuilder(dictionary.getDictionaryDescriptor())
                        .build())
                .withDictionaryMap(parsedDictionary)
                .build());
    }

    @Override
    public FixDefinitionProvider getDefinitionProvider(final DictionaryDescriptorTO providerDescriptor)
            throws FixParserBusinessException {
        return fixDictionaryRepository.findById(new DictionaryDescriptorBuilder(providerDescriptor).build())
                .map(DefaultFixDefinitionProvider::new)
                .orElseThrow(DefinitionProviderNotFoundException::new);
    }
}
