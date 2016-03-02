package net.openblazar.bfp.web;

import com.google.inject.servlet.ServletModule;
import org.apache.shiro.guice.web.GuiceShiroFilter;

/**
 * @author Wojciech Zankowski
 */
public class WebModule extends ServletModule {

    @Override
    protected void configureServlets() {
        filter("/*").through(GuiceShiroFilter.class);
    }
}
