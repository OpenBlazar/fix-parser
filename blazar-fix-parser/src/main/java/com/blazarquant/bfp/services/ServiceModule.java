package com.blazarquant.bfp.services;

import com.blazarquant.bfp.core.mail.MailEngine;
import com.blazarquant.bfp.core.mail.connection.MailConnection;
import com.blazarquant.bfp.core.mail.connection.MailConnectionImpl;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.security.util.SecurityUtilImpl;
import com.blazarquant.bfp.services.mail.MailService;
import com.blazarquant.bfp.services.mail.MailServiceImpl;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.parser.ParserServiceImpl;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.services.user.UserServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Wojciech Zankowski
 */
public class ServiceModule extends AbstractModule {

    private static final String MAIL_CONFIG_PATH = System.getProperty("jboss.server.base.dir")+"/config/mail.properties";

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        bind(SecurityUtil.class).to(SecurityUtilImpl.class);

        bind(ParserService.class).to(ParserServiceImpl.class).in(Singleton.class);

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(MAIL_CONFIG_PATH));
            Names.bindProperties(binder(), properties);
        } catch (FileNotFoundException e) {
            System.out.println("The configuration file Test.properties can not be found");
        } catch (IOException e) {
            System.out.println("I/O Exception during loading configuration");
        }
        bind(MailConnection.class).to(MailConnectionImpl.class);
        bind(MailEngine.class);
        bind(MailService.class).to(MailServiceImpl.class).in(Singleton.class);
    }

}
