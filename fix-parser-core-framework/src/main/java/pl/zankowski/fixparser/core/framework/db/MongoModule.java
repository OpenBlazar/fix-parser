package pl.zankowski.fixparser.core.framework.db;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mongodb.WriteConcern.MAJORITY;

public class MongoModule extends AbstractModule {

    private final static Logger LOGGER = LoggerFactory.getLogger(MongoModule.class);

    @Provides
    public MongoClient provideMongoClientSingleton(@Named("nonsingleton") MongoClient mongoClient) {
        return mongoClient;
    }

    @Provides
    @Named("nonsingleton")
    public MongoClient provideMongoClient(final MongoConfig mongoConfig) throws UnknownHostException {
        // useful info about this here: http://stackoverflow.com/questions/6520439/how-to-configure-mongodb-java-driver-mongooptions-for-production-use
        final MongoClientOptions options = MongoClientOptions.builder()
                .threadsAllowedToBlockForConnectionMultiplier(100)
                .connectionsPerHost(60)
                .connectTimeout(5000)
                .socketTimeout(5000)
                .writeConcern(MAJORITY.withWTimeout(5000, TimeUnit.MILLISECONDS)
                        .withJournal(false))
                .build();
        final List<ServerAddress> addrs = parseServerAddress(mongoConfig.getHostname(), mongoConfig.getPort());

        LOGGER.info("----------");
        LOGGER.info("Creating Mongo client for: " + mongoConfig);
        LOGGER.info("----------");

        return new MongoClient(addrs, options);
    }

    private static List<ServerAddress> parseServerAddress(String host, int port) throws UnknownHostException {
        final List<ServerAddress> address = Lists.newArrayList();
        final InetAddress[] inetAddresses = InetAddress.getAllByName(host);
        for (final InetAddress inetAddress : inetAddresses) {
            address.add(new ServerAddress(inetAddress.getHostAddress(), port));
        }
        return address;
    }

    @Provides
    public Datastore provideDatastoreSingleton(@Named("nonsingleton") final Datastore datastore) {
        return datastore;
    }

    @Provides
    @Named("nonsingleton")
    public Datastore provideDatastore(final MongoClient mongoClient, final MongoConfig mongoConfig) {
        final Morphia morphia = new Morphia();
        return morphia.createDatastore(mongoClient, mongoConfig.getDatabaseName());
    }

}
