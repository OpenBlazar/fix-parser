package pl.zankowski.fixparser.auth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zankowski.fixparser.auth.api.JwtTokenTO;
import pl.zankowski.fixparser.auth.api.LoginTO;
import pl.zankowski.fixparser.core.api.FixParserSystemException;

@Service
public class RestAuthClient implements AuthClient {

    private final RestTemplate restTemplate;

    @Autowired
    public RestAuthClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public JwtTokenTO authenticate(final LoginTO login) {
        final ResponseEntity<JwtTokenTO> response = restTemplate
                .postForEntity("/authenticate", login, JwtTokenTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new FixParserSystemException("Failed to authenticate " + response.getStatusCode());
        }
        return response.getBody();
    }

}
