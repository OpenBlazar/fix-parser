package pl.zankowski.fixparser.web.bean.parser;

import pl.zankowski.bfp.data.user.Permission;
import pl.zankowski.bfp.data.user.UserDetails;
import pl.zankowski.bfp.data.user.UserSetting;
import pl.zankowski.bfp.fix.data.FixMessage;
import pl.zankowski.bfp.fix.parser.definition.data.ProviderDescriptor;
import pl.zankowski.bfp.services.parser.ParserService;
import pl.zankowski.bfp.services.user.UserService;
import pl.zankowski.bfp.web.bean.AbstractBean;
import pl.zankowski.bfp.web.model.FixMessageLazyDataModel;
import pl.zankowski.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
