package com.blazarquant.bfp.web;

import com.blazarquant.bfp.web.util.FacesUtilities;
import com.blazarquant.bfp.web.util.ShiroUtilities;
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

        binder().bind(ShiroUtilities.class).in(Singleton.class);
        binder().bind(FacesUtilities.class).in(Singleton.class);
    }
}
