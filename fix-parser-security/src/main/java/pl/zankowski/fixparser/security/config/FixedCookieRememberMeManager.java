package pl.zankowski.fixparser.security.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

/**
 * https://issues.apache.org/jira/browse/SHIRO-561
 * https://issues.apache.org/jira/browse/SHIRO-441
 */
public class FixedCookieRememberMeManager extends CookieRememberMeManager {

    @Inject
    public FixedCookieRememberMeManager(@Named("shiro.cipherKey") String cipherKey) {
        super();
        setCipherKey(Base64.decode(cipherKey));
    }

}
