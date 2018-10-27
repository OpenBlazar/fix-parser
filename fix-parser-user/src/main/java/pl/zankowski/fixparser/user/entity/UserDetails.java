package pl.zankowski.fixparser.user.entity;

import pl.zankowski.fixparser.core.entity.IEntity;
import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.api.UserState;

import java.time.Instant;
import java.util.Objects;

public class UserDetails implements IEntity {

    private static final long serialVersionUID = 3187485280700741057L;

    private final UserId userId;
    private final String userName;
    private final String userMail;
    private final String password;
    private final UserState userState;
    private final Instant registrationDate;
    private final Instant lastLogin;

    public UserDetails(final UserId userId, final String userName, final String userMail, final String password,
            final UserState isActive, final Instant registrationDate, final Instant lastLogin) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(userName);
        Objects.requireNonNull(userMail);
        Objects.requireNonNull(password);
        Objects.requireNonNull(isActive);
        Objects.requireNonNull(registrationDate);
        Objects.requireNonNull(lastLogin);

        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.password = password;
        this.userState = isActive;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getPassword() {
        return password;
    }

    public UserState getUserState() {
        return userState;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserDetails that = (UserDetails) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userMail, that.userMail) &&
                Objects.equals(password, that.password) &&
                userState == that.userState &&
                Objects.equals(registrationDate, that.registrationDate) &&
                Objects.equals(lastLogin, that.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userMail, password, userState, registrationDate, lastLogin);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", password='" + password + '\'' +
                ", userState=" + userState +
                ", registrationDate=" + registrationDate +
                ", lastLogin=" + lastLogin +
                '}';
    }

}