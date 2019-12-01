package pl.zankowski.fixparser.web.bean.parser;

import pl.zankowski.fixparser.web.bean.AbstractBean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("historyBean")
@ViewScoped
public class HistoryBean extends AbstractBean {
//
//    private MessageService parserService;
//    private UserService userService;
//    private ShiroUtils shiroUtils;
//
//    private LazyDataModel<FixMessageTO> messagesModel;
//    private FixMessageTO selectedMessage;
//    private int messageCount;
//
//    @PostConstruct
//    @Override
//    public void init() {
//        super.init();
//        if (shiroUtils.isUserAuthenticated()) {
//            UserDetailsTO userDetails = shiroUtils.getCurrentUserDetails();
//            if (userDetails != null) {
//                DictionaryDescriptorTO providerDescriptor = (DictionaryDescriptorTO) userService.getParameter(
//                        userDetails.getUserId(), UserSetting.DEFAULT_PROVIDER);
//                messageCount = parserService.countUserMessages(userDetails.getUserId());
//                messagesModel = new FixMessageLazyDataModel(
//                        parserService,
//                        providerDescriptor,
//                        userDetails,
//                        shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name())
//                );
//                messagesModel.setRowCount(messageCount);
//            }
//        }
//    }
//
//    @Inject
//    public void setParserService(MessageService parserService) {
//        this.parserService = parserService;
//    }
//
//    @Inject
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Inject
//    public void setShiroUtils(ShiroUtils shiroUtils) {
//        this.shiroUtils = shiroUtils;
//    }
//
//    public FixMessageTO getSelectedMessage() {
//        return selectedMessage;
//    }
//
//    public LazyDataModel<FixMessageTO> getMessagesModel() {
//        return messagesModel;
//    }
//
//    public void setSelectedMessage(FixMessageTO selectedMessage) {
//        this.selectedMessage = selectedMessage;
//    }
//
//    public int getMessageCount() {
//        return messageCount;
//    }
}
