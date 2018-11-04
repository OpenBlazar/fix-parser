package pl.zankowski.fixparser.web.bean.parser;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.zankowski.fixparser.core.exception.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.share.ShareException;
import pl.zankowski.fixparser.messages.spi.DictionaryService;
import pl.zankowski.fixparser.messages.spi.MessageService;
import pl.zankowski.fixparser.messages.spi.ShareService;
import pl.zankowski.fixparser.tracker.api.TrackerDataTOBuilder;
import pl.zankowski.fixparser.tracker.spi.TrackerService;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.api.UserSetting;
import pl.zankowski.fixparser.user.spi.UserService;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.FixParserConstants;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Named("parserBean")
@ViewScoped
public class ParserBean extends AbstractBean {

    public static final String FAILED_TO_SHARE = "Failed to share message.";

    private static final String SHARE_PARAM = "share";
    private static final String SHARE_URL = "http://www.blazarquant.com/parser?" + SHARE_PARAM + "=";

    private final static Logger LOGGER = LoggerFactory.getLogger(ParserBean.class);

    private static final long serialVersionUID = 6160698503177122700L;

    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

    private MessageService parserService;
    private DictionaryService dictionaryService;
    private TrackerService trackerService;
    private ShareService shareService;
    private UserService userService;

    private List<FixMessageTO> messages = new ArrayList<>();
    private List<DictionaryDescriptorTO> providers = Lists.newArrayList();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    protected DictionaryDescriptorTO selectedProvider;
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

    @Inject
    public void setDictionaryService(final DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
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
            providers.addAll(dictionaryService.getDictionaryDescriptors(userID));
            selectedProvider = providers.isEmpty() ? null : providers.get(0);
        }
    }

    private void doLoadShared() {
        String shareKey = facesUtils.getRequestParameter(SHARE_PARAM);
        if (shareKey == null) {
            return;
        }
        try {
            synchronized (this) {
                input = shareService.getMessage(shareKey);
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
            DictionaryDescriptorTO savedProvider =
                    (DictionaryDescriptorTO) userService.getParameter(shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER);
            if (savedProvider != null) {
                selectedProvider = savedProvider;
            }
        }
    }

    public void doParse(String input) throws FixParserBusinessException {
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

            trackerService.track(new TrackerDataTOBuilder()
                    .withMessageNumber(messages.size())
                    .withParseDate(Instant.now())
                    .build());

            doSaveMessages(messages);
        }
    }

    protected void doSaveMessages(List<FixMessageTO> messages) {
        if (shiroUtils.isUserAuthenticated()) {
            UserDetailsTO userDetails = shiroUtils.getCurrentUserDetails();
            if (userDetails != null) {
                Boolean storeMessages = (Boolean) userService.getParameter(userDetails.getUserId(), UserSetting.STORE_MESSAGES);
                if (storeMessages) {
                    executorService.submit(() -> {
                        parserService.saveMessages(userDetails.getUserId(), messages);
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

    private void setProviderToContext(DictionaryDescriptorTO selectedProvider) {
        if (shiroUtils.isUserAuthenticated()) {
            facesUtils.setContextAttribute(
                    shiroUtils.getCurrentUserID().getId() + DictionaryDescriptorTO.class.getSimpleName(),
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

    public DictionaryDescriptorTO getSelectedProvider() {
        return selectedProvider;
    }

    public void setSelectedProvider(DictionaryDescriptorTO selectedProvider) {
        this.selectedProvider = selectedProvider;
        setProviderToContext(selectedProvider);
    }

    public List<DictionaryDescriptorTO> getProviders() {
        return providers;
    }

    public void setProviders(List<DictionaryDescriptorTO> providers) {
        this.providers = providers;
    }
}
