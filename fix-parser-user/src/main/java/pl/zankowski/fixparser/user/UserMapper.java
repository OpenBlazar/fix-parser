package pl.zankowski.fixparser.user;

import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserDetailsTOBuilder;
import pl.zankowski.fixparser.user.entity.UserDetails;

public class UserMapper {

    public UserDetailsTO map(final UserDetails userDetails) {
        return new UserDetailsTOBuilder()
                .withUserId(userDetails.getUserId())
                .withUserName(userDetails.getUserName())
                .withUserMail(userDetails.getUserMail())
                .withPassword(userDetails.getPassword())
                .withUserState(userDetails.getUserState())
                .withRegistrationDate(userDetails.getRegistrationDate())
                .withLastLogin(userDetails.getLastLogin())
                .build();
    }

}
