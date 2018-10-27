package pl.zankowski.fixparser.web.bean.parser;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.share.ShareException;
import pl.zankowski.fixparser.messages.spi.MessageService;
import pl.zankowski.fixparser.messages.spi.ShareService;
import pl.zankowski.fixparser.tracker.spi.TrackerService;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.spi.UserService;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ManagedBean(name = "parserBean")
@ViewScoped
public class ParserBean extends AbstractBean {

    public static final String FAILED_TO_SHARE = "Failed to share message.";

    private static final String SHARE_PARAM = "share";
    private static final String SHARE_URL = "http://www.blazarquant.com/parser?" + SHARE_PARAM + "=";

    private final static Logger LOGGER = LoggerFactory.getLogger(ParserBean.class);

    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

    private MessageService parserService;
    private TrackerService trackerService;
    private ShareService shareService;
    private UserService userService;

    private List<FixMessageTO> messages = new ArrayList<>();
    private List<ProviderDescriptor> providers = Arrays.asList(DefaultFixDefinitionProvider.DESCRIPTOR);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    protected ProviderDescriptor selectedProvider = DefaultFixDefinitionProvider.DESCRIPTOR;
    private FixMessageTO selectedMessage;
    private String shareKey;
    private String input;

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    @Inject
    public void setParserService(MessageService parserService) {
        this.parserService = parserService;
    }

    @Inject
    public void setTrackerService(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @Inject
    public void setShareService(ShareService shareService) {
        this.shareService = shareService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        doLoadShared();
        doLoadProviders();
        doLoadDefaultProvider();
        setProviderToContext(selectedProvider);
    }

    private void doLoadProviders() {
        if (shiroUtils.isUserAuthenticated()) {
            UserId userID = shiroUtils.getCurrentUserID();
            providers = new ArrayList<>();
            providers.addAll(parserService.getProviders(
                    userID,
                    shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name())
            ));
        }
    }

    private void doLoadShared() {
        String shareKey = facesUtils.getRequestParameter(SHARE_PARAM);
        if (shareKey == null) {
            return;
        }
        try {
            synchronized (this) {
                input = shareService.getMessageFromKey(shareKey);
                // TODO hack, inputTextArea eats \u0001, why? // FIXME: 21.04.2016
                input = input.replaceAll("\u0001", "#");
                messages = new ArrayList<>(parserService.parseInput(input));
            }
        } catch (Exception e) {
            // TODO Handle
            LOGGER.error("Failed to load shared message.", e);
        }
    }

    private void doLoadDefaultProvider() {
        if (shiroUtils.isUserAuthenticated()) {
            ProviderDescriptor savedProvider = (ProviderDescriptor) userService.getUserSettingsCache().getObject(shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER);
            if (savedProvider != null) {
                selectedProvider = savedProvider;
            }
        }
    }

    public void doParse(String input) {
        synchronized (this) {
            selectedMessage = null;
            if (shiroUtils.isUserAuthenticated()) {
                messages = new ArrayList<>(parserService.parseInput(
                        selectedProvider,
                        shiroUtils.getCurrentUserID(),
                        input,
                        shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name())
                ));
            } else {
                messages = new ArrayList<>(parserService.parseInput(input));
            }
            trackerService.inputParsed(messages.size());
            doSaveMessages(messages);
        }
    }

    protected void doSaveMessages(List<FixMessageTO> messages) {
        if (shiroUtils.isUserAuthenticated()) {
            UserDetails userDetails = shiroUtils.getCurrentUserDetails();
            if (userDetails != null) {
                Boolean storeMessages = userService.getUserSettingsCache().getBoolean(userDetails.getUserID(), UserSetting.STORE_MESSAGES);
                if (storeMessages) {
                    executorService.submit(() -> {
                        parserService.saveMessages(userDetails, messages);
                    });
                }
            }
        }
    }

    public void doInjectSampleData() {
        input = FixParserConstants.SAMPLE_DATA;
    }

    public void doShare() {
        try {
            shareKey = shareService.shareMessage(input);
        } catch (ShareException e) {
            facesUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
            LOGGER.error(FAILED_TO_SHARE, e);
        } catch (Exception e) {
            facesUtils.addMessage(FacesMessage.SEVERITY_ERROR, FAILED_TO_SHARE);
            LOGGER.error(FAILED_TO_SHARE, e);
        }
    }

    private void setProviderToContext(ProviderDescriptor selectedProvider) {
        if (shiroUtils.isUserAuthenticated()) {
            facesUtils.setContextAttribute(
                    shiroUtils.getCurrentUserID().getId() + FixDefinitionProvider.class.getSimpleName(),
                    selectedProvider);
        }
    }

    public String getShareLink() {
        return shareKey == null ? "" : SHARE_URL + shareKey;
    }

    public List<FixMessageTO> getMessages() {
        return messages;
    }

    public FixMessageTO getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(FixMessageTO selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ProviderDescriptor getSelectedProvider() {
        return selectedProvider;
    }

    public void setSelectedProvider(ProviderDescriptor selectedProvider) {
        this.selectedProvider = selectedProvider;
        setProviderToContext(selectedProvider);
    }

    public List<ProviderDescriptor> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderDescriptor> providers) {
        this.providers = providers;
    }
}
