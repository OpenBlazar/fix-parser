package net.openblazar.bfp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.openblazar.bfp.database.DatabaseModule;
import net.openblazar.bfp.core.security.SecurityModule;
import net.openblazar.bfp.services.ServiceModule;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * @author Wojciech Zankowski
 */
public class BlazarFixParser implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();

		Injector injector = Guice.createInjector(
				new DatabaseModule(getProperties()),
				new ServiceModule(),
				new SecurityModule());

		SecurityManager securityManager = injector.getInstance(SecurityManager.class);
		SecurityUtils.setSecurityManager(securityManager);

		servletContext.setAttribute(Injector.class.getName(), injector);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.removeAttribute(Injector.class.getName());
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
