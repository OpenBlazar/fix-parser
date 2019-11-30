package pl.zankowski.fixparser.user.client;

import pl.zankowski.fixparser.user.api.PasswordResetRequestTO;
import pl.zankowski.fixparser.user.api.PasswordResetTO;
import pl.zankowski.fixparser.user.api.UserActivationTO;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;

public interface UserClient {

    boolean register(UserRegistrationTO registration);

    boolean activateAccount(UserActivationTO activation);

    boolean initPasswordReset(PasswordResetRequestTO passwordResetRequest);

    boolean resetPassword(PasswordResetTO passwordReset);

}
