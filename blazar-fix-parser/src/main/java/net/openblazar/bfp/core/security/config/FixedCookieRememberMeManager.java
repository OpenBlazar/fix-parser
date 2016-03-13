package net.openblazar.bfp.core.security.config;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

/**
 * https://issues.apache.org/jira/browse/SHIRO-561
 *
 * @author Wojciech Zankowski
 */
public class FixedCookieRememberMeManager extends CookieRememberMeManager {

    public FixedCookieRememberMeManager() {
        super();
        setCipherKey(new AesCipherService().generateNewKey().getEncoded());
    }

}
