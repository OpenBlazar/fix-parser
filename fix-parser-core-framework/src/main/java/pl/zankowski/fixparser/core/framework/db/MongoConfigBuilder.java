package pl.zankowski.fixparser.core.framework.db;

public final class MongoConfigBuilder {

    private String databaseName;
    private String hostname;
    private int port;

    public MongoConfigBuilder withDatabaseName(final String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public MongoConfigBuilder withHostname(final String hostname) {
        this.hostname = hostname;
        return this;
    }

    public MongoConfigBuilder withPort(final int port) {
        this.port = port;
        return this;
    }

    public MongoConfig build() {
        return new MongoConfig(databaseName, hostname, port);
    }

}
