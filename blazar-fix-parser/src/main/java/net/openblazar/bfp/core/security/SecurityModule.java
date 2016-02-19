package net.openblazar.bfp.core.security;

import com.google.inject.name.Names;
import net.openblazar.bfp.core.security.matcher.BcryptCredentialsMatcher;
import net.openblazar.bfp.core.security.realm.DatabaseUserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.guice.ShiroModule;

/**
 * @author Wojciech Zankowski
 */
public class SecurityModule extends ShiroModule {

    @Override
    protected void configureShiro() {
        bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);

        bindRealm().to(DatabaseUserRealm.class);
        bind(CredentialsMatcher.class).to(BcryptCredentialsMatcher.class);
    }
}
