package pl.zankowski.fixparser.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zankowski.fixparser.user.api.PasswordResetRequestTO;
import pl.zankowski.fixparser.user.api.PasswordResetTO;
import pl.zankowski.fixparser.user.api.UserActivationTO;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;

@Service
public class RestUserClient implements UserClient {

    private final RestTemplate restTemplate;

    @Autowired
    public RestUserClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean register(final UserRegistrationTO registration) {
        final ResponseEntity<Object> response = restTemplate
                .postForEntity("/account/register", registration, Object.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean activateAccount(final UserActivationTO activation) {
        final ResponseEntity<Object> response = restTemplate
                .postForEntity("/account/activation", activation, Object.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean initPasswordReset(final PasswordResetRequestTO passwordResetRequest) {
        final ResponseEntity<Object> response = restTemplate
                .postForEntity("/account/password-reset/init", passwordResetRequest, Object.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean resetPassword(final PasswordResetTO passwordReset) {
        final ResponseEntity<Object> response = restTemplate
                .postForEntity("/account/password-reset", passwordReset, Object.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

}
