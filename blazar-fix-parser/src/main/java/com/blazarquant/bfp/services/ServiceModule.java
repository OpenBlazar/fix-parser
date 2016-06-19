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
