package com.blazarquant.bfp.services;

import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.blazarquant.bfp.core.security.util.SecurityUtilImpl;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.parser.ParserServiceImpl;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.services.user.UserServiceImpl;

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
