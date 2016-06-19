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
package com.blazarquant.bfp.services.mail;

import com.blazarquant.bfp.core.mail.MailEngine;
import com.blazarquant.bfp.core.mail.util.MailMessageFactory;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;

/**
 * @author Wojciech Zankowski
 */
public class MailServiceImpl implements MailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    private final MailEngine mailEngine;

    @Inject
    public MailServiceImpl(MailEngine mailEngine) throws MessagingException {
        this.mailEngine = mailEngine;
        this.mailEngine.start();
    }

    @Override
    public void sendConfirmationLink(String confirmationKey, String userMail) {
        sendMessage(MailMessageFactory.confirmationMessage(confirmationKey), MailMessageFactory.CONFIRMATION_MESSAGE_SUBJECT, userMail);
    }

    @Override
    public void sendMessage(String message, String subject, String userMail) {
        mailEngine.sendMessage(message, subject, userMail);
    }

}
