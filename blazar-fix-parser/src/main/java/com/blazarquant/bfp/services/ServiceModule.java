package com.blazarquant.bfp.services;

import com.blazarquant.bfp.core.mail.MailEngine;
import com.blazarquant.bfp.core.mail.connection.MailConnection;
import com.blazarquant.bfp.core.mail.connection.MailConnectionImpl;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.security.util.SecurityUtilImpl;
import com.blazarquant.bfp.core.security.util.SettingsManager;
import com.blazarquant.bfp.services.mail.MailService;
import com.blazarquant.bfp.services.mail.MailServiceImpl;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.parser.ParserServiceImpl;
import com.blazarquant.bfp.services.payment.PaymentService;
import com.blazarquant.bfp.services.payment.PaymentServiceImpl;
import com.blazarquant.bfp.services.share.ShareService;
import com.blazarquant.bfp.services.share.ShareServiceImpl;
import com.blazarquant.bfp.services.tracker.TrackerService;
import com.blazarquant.bfp.services.tracker.TrackerServiceImpl;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.services.user.UserServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/**
 * @author Wojciech Zankowski
 */
public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        bind(SecurityUtil.class).to(SecurityUtilImpl.class);

        bind(ParserService.class).to(ParserServiceImpl.class).in(Singleton.class);

        bind(MailConnection.class).to(MailConnectionImpl.class);
        bind(MailEngine.class);
        bind(MailService.class).to(MailServiceImpl.class).in(Singleton.class);

        bind(ShareService.class).to(ShareServiceImpl.class).in(Singleton.class);

        bind(TrackerService.class).to(TrackerServiceImpl.class).in(Singleton.class);

        bind(PaymentService.class).to(PaymentServiceImpl.class).in(Singleton.class);
    }

}
