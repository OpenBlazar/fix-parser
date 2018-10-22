package pl.zankowski.fixparser.user.entity;

import pl.zankowski.fixparser.core.entity.IEntity;

import java.time.Instant;
import java.util.Objects;

public class UserDetails implements IEntity {

    private static final long serialVersionUID = 3187485280700741057L;

    private final UserID userID;
    private final String userName;
    private final String userMail;
    private final String password;
    private final UserState userState;
    private final Instant registrationDate;
    private final Instant lastLogin;

    public UserDetails(final UserID userID, final String userName, final String userMail, final String password,
            final UserState isActive, final Instant registrationDate, final Instant lastLogin) {
        Objects.requireNonNull(userID);
        Objects.requireNonNull(userName);
        Objects.requireNonNull(userMail);
        Objects.requireNonNull(password);
        Objects.requireNonNull(isActive);
        Objects.requireNonNull(registrationDate);
        Objects.requireNonNull(lastLogin);

        this.userID = userID;
        this.userName = userName;
        this.userMail = userMail;
        this.password = password;
        this.userState = isActive;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }

    public UserID getUserID() {
        return userID;
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
        return Objects.equals(userID, that.userID) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userMail, that.userMail) &&
                Objects.equals(password, that.password) &&
                userState == that.userState &&
                Objects.equals(registrationDate, that.registrationDate) &&
                Objects.equals(lastLogin, that.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userName, userMail, password, userState, registrationDate, lastLogin);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", password='" + password + '\'' +
                ", userState=" + userState +
                ", registrationDate=" + registrationDate +
                ", lastLogin=" + lastLogin +
                '}';
    }

}