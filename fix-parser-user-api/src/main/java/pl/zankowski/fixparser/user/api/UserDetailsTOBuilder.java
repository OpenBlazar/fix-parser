package pl.zankowski.fixparser.user.api;

import java.time.Instant;

public final class UserDetailsTOBuilder {
    private UserId userId;
    private String userName;
    private String userMail;
    private String password;
    private UserState userState;
    private Instant registrationDate;
    private Instant lastLogin;

    public UserDetailsTOBuilder withUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public UserDetailsTOBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserDetailsTOBuilder withUserMail(String userMail) {
        this.userMail = userMail;
        return this;
    }

    public UserDetailsTOBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDetailsTOBuilder withUserState(UserState userState) {
        this.userState = userState;
        return this;
    }

    public UserDetailsTOBuilder withRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public UserDetailsTOBuilder withLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public UserDetailsTO build() {
        return new UserDetailsTO(userId, userName, userMail, password, userState, registrationDate, lastLogin);
    }
}
