package pl.zankowski.fixparser.web;

import com.sun.faces.config.ConfigureListener;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.ocpsoft.rewrite.servlet.impl.RewriteServletRequestListener;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.zankowski.fixparser.web.filter.CharacterFilter;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "pl.zankowski.fixparser" })
public class FixParserApplication extends SpringBootServletInitializer implements
        ServletContextAware, WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(FixParserApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new FacesServlet(), "*.xhtml");
    }

    @Bean
    public FilterRegistrationBean rewriteFilter() {
        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR));
        rwFilter.addUrlPatterns("/*");
        return rwFilter;
    }

    @Bean
    public FilterRegistrationBean characterFilter() {
        FilterRegistrationBean charFilter = new FilterRegistrationBean(new CharacterFilter());
        charFilter.addUrlPatterns("/*");
        return charFilter;
    }

    @Bean
    public FilterRegistrationBean fileUploadFilter(
            final ServletRegistrationBean servletRegistrationBean) {
        FilterRegistrationBean charFilter = new FilterRegistrationBean(new FileUploadFilter());
        charFilter.addInitParameter("thresholdSize", "51200");
        charFilter.addInitParameter("uploadDirectory", "/uploads");
        charFilter.addServletRegistrationBeans(servletRegistrationBean);
        return charFilter;
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<>(new ConfigureListener());
    }

    @Bean
    public ServletListenerRegistrationBean<FileCleanerCleanup> fileUploadCleanListener() {
        return new ServletListenerRegistrationBean<>(new FileCleanerCleanup());
    }

    @Bean
    public ServletListenerRegistrationBean<RewriteServletRequestListener> rewriteServletRequestListener() {
        return new ServletListenerRegistrationBean<>(new RewriteServletRequestListener());
    }

//    @Bean
//    public ServletListenerRegistrationBean<RewriteServletContextListener> rewriteServletContextListener() {
//        return new ServletListenerRegistrationBean<>(new RewriteServletContextListener());
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/pages/public/parser.xhtml");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");
        servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING", Boolean.FALSE.toString());
        servletContext.setInitParameter("primefaces.UPLOADER", "auto");
        servletContext.setInitParameter("primefaces.THEME", "omega");
        servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

}
