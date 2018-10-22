package pl.zankowski.fixparser.mail.connection;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class MailConnectionCredentials {

    private final String host;
    private final int port;
    private final String username;
    private final String password;

    @Inject
    public MailConnectionCredentials(
            @Named("mail.host") final String host,
            @Named("mail.port") final int port,
            @Named("mail.username") final String username,
            @Named("mail.password") final String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
