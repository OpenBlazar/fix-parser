package com.blazarquant.bfp;

import com.blazarquant.bfp.core.security.SecurityModule;
import com.blazarquant.bfp.database.DatabaseModule;
import com.blazarquant.bfp.services.ServiceModule;
import com.blazarquant.bfp.web.WebModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.shiro.guice.aop.ShiroAopModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author Wojciech Zankowski
 */
public class BlazarFixParser extends GuiceServletContextListener {

	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		this.servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(
				new DatabaseModule(),
				new ServiceModule(),
				new SecurityModule(servletContext),
				new ShiroAopModule(),
				new WebModule());
	}

}
