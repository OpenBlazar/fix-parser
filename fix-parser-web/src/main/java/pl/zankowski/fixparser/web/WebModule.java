package pl.zankowski.fixparser.web;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import org.apache.shiro.guice.web.GuiceShiroFilter;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.ShiroUtils;

public class WebModule extends ServletModule {

    @Override
    protected void configureServlets() {
        filter("/*").through(GuiceShiroFilter.class);

        binder().bind(ShiroUtils.class).in(Singleton.class);
        binder().bind(FacesUtils.class).in(Singleton.class);
    }
}
