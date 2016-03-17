package net.openblazar.bfp.core.security;

import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import net.openblazar.bfp.core.security.config.BcryptCredentialsMatcher;
import net.openblazar.bfp.core.security.config.DatabaseUserRealm;
import net.openblazar.bfp.core.security.config.FixedCookieRememberMeManager;
import net.openblazar.bfp.core.security.config.ShiroMethodInterceptor;
import net.openblazar.bfp.web.util.BlazarURL;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.SessionManager;
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

        bindConstant().annotatedWith(Names.named("shiro.sessionMode")).to("http");
        bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(1800000L);
        bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/signin");

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresAuthentication.class),
                new ShiroMethodInterceptor());
        bind(RememberMeManager.class).to(FixedCookieRememberMeManager.class).in(Singleton.class);

        addFilterChain(BlazarURL.HOME_URL, ANON);
        addFilterChain(BlazarURL.PARSER_URL, ANON);
        addFilterChain(BlazarURL.FILEPARSER_URL, ANON);
        addFilterChain(BlazarURL.HELP_URL, ANON);
        addFilterChain(BlazarURL.SIGNUP_URL, ANON);
        addFilterChain(BlazarURL.SIGNIN_URL, ANON);

        addFilterChain(BlazarURL.HISTORY_URL, AUTHC);
    }

    @Override
    protected void bindSessionManager(AnnotatedBindingBuilder<SessionManager> bind) {
        bind.to(ServletContainerSessionManager.class).in(Singleton.class);
    }

}
