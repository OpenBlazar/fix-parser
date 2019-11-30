package pl.zankowski.fixparser.auth.client;

import pl.zankowski.fixparser.auth.api.JwtTokenTO;
import pl.zankowski.fixparser.auth.api.LoginTO;

public interface AuthClient {

    JwtTokenTO authenticate(LoginTO login);

}
