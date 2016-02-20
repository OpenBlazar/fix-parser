package net.openblazar.bfp.services;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import net.openblazar.bfp.core.security.util.SecurityUtil;
import net.openblazar.bfp.core.security.util.SecurityUtilImpl;
import net.openblazar.bfp.services.parser.ParserService;
import net.openblazar.bfp.services.parser.ParserServiceImpl;
import net.openblazar.bfp.services.user.UserService;
import net.openblazar.bfp.services.user.UserServiceImpl;

/**
 * @author Wojciech Zankowski
 */
public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        bind(SecurityUtil.class).to(SecurityUtilImpl.class);

        bind(ParserService.class).to(ParserServiceImpl.class).in(Singleton.class);
    }

}
