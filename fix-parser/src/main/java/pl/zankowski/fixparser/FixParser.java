package pl.zankowski.fixparser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import pl.zankowski.fixparser.web.security.SecurityModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class FixParser extends GuiceServletContextListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.servletContext = servletContextEvent.getServletContext();
        super.contextInitialized(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new FixParserModule(),
                new SecurityModule(servletContext));
    }

}
