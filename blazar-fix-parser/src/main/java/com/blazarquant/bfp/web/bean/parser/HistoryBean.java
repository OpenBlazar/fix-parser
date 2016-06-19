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
package com.blazarquant.bfp.web.bean.parser;

import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserSetting;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.model.FixMessageLazyDataModel;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "historyBean")
@ViewScoped
public class HistoryBean extends AbstractBean {

    private ParserService parserService;
    private UserService userService;
    private ShiroUtils shiroUtils;

    private LazyDataModel<FixMessage> messagesModel;
    private FixMessage selectedMessage;
    private int messageCount;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        if (shiroUtils.isUserAuthenticated()) {
            UserDetails userDetails = shiroUtils.getCurrentUserDetails();
            if (userDetails != null) {
                ProviderDescriptor providerDescriptor = (ProviderDescriptor) userService.getUserSettingsCache().getObject(userDetails.getUserID(), UserSetting.DEFAULT_PROVIDER);
                messageCount = parserService.countUserMessages(userDetails);
                messagesModel = new FixMessageLazyDataModel(
                        parserService,
                        providerDescriptor,
                        userDetails,
                        shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name())
                );
                messagesModel.setRowCount(messageCount);
            }
        }
    }

    @Inject
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    public FixMessage getSelectedMessage() {
        return selectedMessage;
    }

    public LazyDataModel<FixMessage> getMessagesModel() {
        return messagesModel;
    }

    public void setSelectedMessage(FixMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public int getMessageCount() {
        return messageCount;
    }
}
