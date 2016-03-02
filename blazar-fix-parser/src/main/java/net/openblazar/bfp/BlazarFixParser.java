package net.openblazar.bfp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import net.openblazar.bfp.core.security.SecurityModule;
import net.openblazar.bfp.database.DatabaseModule;
import net.openblazar.bfp.services.ServiceModule;
import net.openblazar.bfp.web.WebModule;
import org.apache.shiro.guice.aop.ShiroAopModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.Properties;

/**
 * @author Wojciech Zankowski
 */
public class BlazarFixParser extends GuiceServletContextListener {

	private final static Logger LOGGER = LoggerFactory.getLogger(BlazarFixParser.class);

	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		this.servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(
				new DatabaseModule(getProperties()),
				new ServiceModule(),
				new SecurityModule(servletContext),
				new ShiroAopModule(),
				new WebModule());
	}

	private Properties getProperties() {
		Properties properties = new Properties();

		properties.setProperty("JDBC.host", "localhost");
		properties.setProperty("JDBC.port", "3306");
		properties.setProperty("JDBC.schema", "blazarfixparser");

		properties.setProperty("JDBC.username", "root");
		properties.setProperty("JDBC.password", "werty56");
		properties.setProperty("JDBC.autoCommit", "false");

		return properties;
	}

}
