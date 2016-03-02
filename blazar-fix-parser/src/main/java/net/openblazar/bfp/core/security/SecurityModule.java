package net.openblazar.bfp.core.security;

import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.matcher.Matchers;
import net.openblazar.bfp.core.security.interceptor.ShiroMethodInterceptor;
import net.openblazar.bfp.core.security.matcher.BcryptCredentialsMatcher;
import net.openblazar.bfp.core.security.realm.DatabaseUserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;

import javax.servlet.ServletContext;

/**
 * @author Wojciech Zankowski
 */
public class SecurityModule extends ShiroWebModule {

    public SecurityModule(ServletContext servletContext) {
        super(servletContext);
    }

    @Override
    protected void configureShiroWeb() {
        bind(CredentialsMatcher.class).to(BcryptCredentialsMatcher.class);
        bindRealm().to(DatabaseUserRealm.class).asEagerSingleton();

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresAuthentication.class),
                new ShiroMethodInterceptor());
        bind(RememberMeManager.class).to(CookieRememberMeManager.class).in(Singleton.class);
        addFilterChain("/**", ANON);
    }

    @Override
    protected void bindSessionManager(AnnotatedBindingBuilder<SessionManager> bind) {
        bind.to(ServletContainerSessionManager.class).in(Singleton.class);
    }

}
