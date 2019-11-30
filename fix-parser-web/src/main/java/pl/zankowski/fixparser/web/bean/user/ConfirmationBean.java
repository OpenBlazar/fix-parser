package pl.zankowski.fixparser.web.bean.user;

import pl.zankowski.fixparser.web.bean.AbstractBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("confirmationBean")
@RequestScoped
public class ConfirmationBean extends AbstractBean {

//    public static final String FAILED_TO_CONFIRM = "Failed to confirm registration. Invalid confirmation key.";
//    public static final String CONFIRMATION_SUCCEDED = "Congratulations, registration has been successful. Your account is active right now. Please sign in.";
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationBean.class);
//
//    private UserService userService;
//    private FacesUtils facesUtils;
//
//    private String confirmationKey;
//
//    @PostConstruct
//    @Override
//    public void init() {
//        super.init();
//    }
//
//    @Inject
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Inject
//    public void setFacesUtils(FacesUtils facesUtils) {
//        this.facesUtils = facesUtils;
//    }
//
//    public String getConfirmationKey() {
//        return confirmationKey;
//    }
//
//    public void setConfirmationKey(String confirmationKey) {
//        this.confirmationKey = confirmationKey;
//    }
//
//    public void doConfirmation() {
//        try {
//            boolean confirmed = userService.confirmUser(confirmationKey);
//            if (confirmed) {
//                facesUtils.addMessage(FacesMessage.SEVERITY_INFO, CONFIRMATION_SUCCEDED);
//            } else {
//                facesUtils.addMessage(FacesMessage.SEVERITY_WARN, FAILED_TO_CONFIRM);
//            }
//        } catch (Exception e) {
//            facesUtils.addMessage(FacesMessage.SEVERITY_WARN, FAILED_TO_CONFIRM);
//            LOGGER.error(FAILED_TO_CONFIRM, e);
//        }
//    }

}
