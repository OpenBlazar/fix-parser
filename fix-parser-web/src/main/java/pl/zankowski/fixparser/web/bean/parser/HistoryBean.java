package pl.zankowski.fixparser.web.bean.parser;

import com.google.inject.Inject;
import org.primefaces.model.LazyDataModel;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.spi.MessageService;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.spi.UserService;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.model.FixMessageLazyDataModel;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "historyBean")
@ViewScoped
public class HistoryBean extends AbstractBean {

    private MessageService parserService;
    private UserService userService;
    private ShiroUtils shiroUtils;

    private LazyDataModel<FixMessageTO> messagesModel;
    private FixMessageTO selectedMessage;
    private int messageCount;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        if (shiroUtils.isUserAuthenticated()) {
            UserDetailsTO userDetails = shiroUtils.getCurrentUserDetails();
            if (userDetails != null) {
                DictionaryDescriptorTO providerDescriptor = (DictionaryDescriptorTO) userService.getUserSettingsCache().getObject(userDetails.getUserID(), UserSetting.DEFAULT_PROVIDER);
                messageCount = parserService.countUserMessages(userDetails.getUserId());
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
    public void setParserService(MessageService parserService) {
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

    public FixMessageTO getSelectedMessage() {
        return selectedMessage;
    }

    public LazyDataModel<FixMessageTO> getMessagesModel() {
        return messagesModel;
    }

    public void setSelectedMessage(FixMessageTO selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public int getMessageCount() {
        return messageCount;
    }
}
