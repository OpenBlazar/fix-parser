package pl.zankowski.fixparser.messages.dictionary;

import pl.zankowski.fixparser.core.entity.IRepository;
import pl.zankowski.fixparser.messages.entity.dictionary.DictionaryDescriptor;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.List;
import java.util.Optional;

public interface FixDictionaryRepository extends IRepository {

    Optional<FixDictionary> findById(final DictionaryDescriptor dictionaryDescriptor);

    List<FixDictionary> findByUserId(final UserId userId);

    void save(FixDictionary dictionary);

}
