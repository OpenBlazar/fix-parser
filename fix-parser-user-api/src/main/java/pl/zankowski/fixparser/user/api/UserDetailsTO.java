package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.zankowski.fixparser.core.ITransferObject;

import java.time.Instant;
import java.util.Objects;

@JsonPropertyOrder({"userId", "userName", "userMail", "password", "userState",
        "registrationDate", "lastLogin"})
public class UserDetailsTO implements ITransferObject {

    private static final long serialVersionUID = -8137126699903885416L;

    private final UserId userId;
    private final String userName;
    private final String userMail;
    private final String password;
    private final UserState userState;
    private final Instant registrationDate;
    private final Instant lastLogin;

    @JsonCreator
    public UserDetailsTO(
            @JsonProperty("userId") final UserId userId,
            @JsonProperty("userName") final String userName,
            @JsonProperty("userMail") final String userMail,
            @JsonProperty("password") final String password,
            @JsonProperty("userState") final UserState userState,
            @JsonProperty("registrationDate") final Instant registrationDate,
            @JsonProperty("lastLogin") final Instant lastLogin) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.password = password;
        this.userState = userState;
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
        final UserDetailsTO that = (UserDetailsTO) o;
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
        return "UserDetailsTO{" +
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
