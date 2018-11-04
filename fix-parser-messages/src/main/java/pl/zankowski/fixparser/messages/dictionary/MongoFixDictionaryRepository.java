package pl.zankowski.fixparser.messages.dictionary;

import com.google.inject.Inject;
import org.mongodb.morphia.Datastore;
import pl.zankowski.fixparser.core.framework.db.MongoRepository;
import pl.zankowski.fixparser.messages.entity.dictionary.DictionaryDescriptor;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.List;
import java.util.Optional;

public class MongoFixDictionaryRepository extends MongoRepository implements FixDictionaryRepository {

    @Inject
    public MongoFixDictionaryRepository(final Datastore datastore) {
        super(datastore);
    }

    @Override
    public Optional<FixDictionary> findById(final DictionaryDescriptor dictionaryDescriptor) {
        return null;
    }

    @Override
    public List<FixDictionary> findByUserId(final UserId userId) {
        return null;
    }

    @Override
    public void save(final FixDictionary dictionary) {

    }

}
