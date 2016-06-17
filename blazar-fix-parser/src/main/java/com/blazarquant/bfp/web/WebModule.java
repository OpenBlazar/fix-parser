package com.blazarquant.bfp.web;

import com.blazarquant.bfp.web.util.FacesUtils;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import org.apache.shiro.guice.web.GuiceShiroFilter;

/**
 * @author Wojciech Zankowski
 */
public class WebModule extends ServletModule {

    @Override
    protected void configureServlets() {
        filter("/*").through(GuiceShiroFilter.class);

        binder().bind(ShiroUtils.class).in(Singleton.class);
        binder().bind(FacesUtils.class).in(Singleton.class);
    }
}
