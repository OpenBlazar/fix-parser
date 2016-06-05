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
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

    private FixMessage selectedMessage;
    private ParserService parserService;
    private UserService userService;
    private ShiroUtilities shiroUtilities;
    private LazyDataModel<FixMessage> messagesModel;
    private int messageCount;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) currentUser.getPrincipal();
            if (userDetails != null) {
                ProviderDescriptor providerDescriptor = (ProviderDescriptor) userService.getUserSettingsCache().getObject(userDetails.getUserID(), UserSetting.DEFAULT_PROVIDER);
                messageCount = parserService.countUserMessages(userDetails);
                messagesModel = new FixMessageLazyDataModel(
                        parserService,
                        providerDescriptor,
                        userDetails,
                        shiroUtilities.isPermitted(Permission.PRO.name()) || shiroUtilities.isPermitted(Permission.ENTERPRISE.name())
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
    public void setShiroUtilities(ShiroUtilities shiroUtilities) {
        this.shiroUtilities = shiroUtilities;
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
