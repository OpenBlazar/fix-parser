package pl.zankowski.fixparser.core.framework.db;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Objects;

public class MongoConfig {

    private final String databaseName;
    private final String hostname;
    private final int port;

    @Inject
    public MongoConfig(
            @Named("MONGO.database") final String databaseName,
            @Named("MONGO.host") final String hostname,
            @Named("MONGO.port") final int port) {
        this.databaseName = databaseName;
        this.hostname = hostname;
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MongoConfig that = (MongoConfig) o;
        return port == that.port &&
                Objects.equals(databaseName, that.databaseName) &&
                Objects.equals(hostname, that.hostname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseName, hostname, port);
    }

    @Override
    public String toString() {
        return "MongoConfig{" +
                "databaseName='" + databaseName + '\'' +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }

}
