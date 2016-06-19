/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
