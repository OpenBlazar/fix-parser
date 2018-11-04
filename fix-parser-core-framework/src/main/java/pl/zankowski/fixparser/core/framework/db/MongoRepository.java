package pl.zankowski.fixparser.core.framework.db;

import com.google.inject.Inject;
import org.mongodb.morphia.Datastore;
import pl.zankowski.fixparser.core.entity.IRepository;

public abstract class MongoRepository implements IRepository {

    private final Datastore datastore;

    @Inject
    public MongoRepository(final Datastore datastore) {
        this.datastore = datastore;
    }

    public Datastore getDatastore() {
        return datastore;
    }

}
