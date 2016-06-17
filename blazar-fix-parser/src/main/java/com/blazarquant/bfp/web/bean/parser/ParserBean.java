package com.blazarquant.bfp.web.bean.parser;

import com.blazarquant.bfp.core.share.exception.ShareException;
import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.data.user.UserSetting;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.util.FixParserConstants;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.share.ShareService;
import com.blazarquant.bfp.services.tracker.TrackerService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.FacesUtilities;
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "parserBean")
@ViewScoped
public class ParserBean extends AbstractBean {

    public static final String FAILED_TO_SHARE = "Failed to share message.";

    private static final String SHARE_PARAM = "share";
    private static final String SHARE_URL = "http://www.blazarquant.com/parser?" + SHARE_PARAM + "=";

    private final static Logger LOGGER = LoggerFactory.getLogger(ParserBean.class);

    private ShiroUtilities shiroUtilities;
    private FacesUtilities facesUtilities;

    private ParserService parserService;
    private TrackerService trackerService;
    private ShareService shareService;
    private UserService userService;

    private List<FixMessage> messages = new ArrayList<>();
    private List<ProviderDescriptor> providers = Arrays.asList(DefaultFixDefinitionProvider.DESCRIPTOR);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    protected ProviderDescriptor selectedProvider = DefaultFixDefinitionProvider.DESCRIPTOR;
    private FixMessage selectedMessage;
    private String shareKey;
    private String input;

    @Inject
    public void setShiroUtilities(ShiroUtilities shiroUtilities) {
        this.shiroUtilities = shiroUtilities;
    }

    @Inject
    public void setFacesUtilities(FacesUtilities facesUtilities) {
        this.facesUtilities = facesUtilities;
    }

    @Inject
    public void setParserService(ParserService parserService) {
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
        if (shiroUtilities.isUserAuthenticated()) {
            UserID userID = shiroUtilities.getCurrentUserID();
            providers = new ArrayList<>();
            providers.addAll(parserService.getProviders(
                    userID,
                    shiroUtilities.isPermitted(Permission.PRO.name()) || shiroUtilities.isPermitted(Permission.ENTERPRISE.name())
            ));
        }
    }

    private void doLoadShared() {
        String shareKey = facesUtilities.getRequestParameter(SHARE_PARAM);
        if (shareKey == null) {
            return;
        }
        try {
            synchronized (messages) {
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
        if (shiroUtilities.isUserAuthenticated()) {
            ProviderDescriptor savedProvider = (ProviderDescriptor) userService.getUserSettingsCache().getObject(shiroUtilities.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER);
            if (savedProvider != null) {
                selectedProvider = savedProvider;
            }
        }
    }

    public void doParse(String input) {
        synchronized (messages) {
            selectedMessage = null;
            if (shiroUtilities.isUserAuthenticated()) {
                messages = new ArrayList<>(parserService.parseInput(
                        selectedProvider,
                        shiroUtilities.getCurrentUserID(),
                        input,
                        shiroUtilities.isPermitted(Permission.PRO.name()) || shiroUtilities.isPermitted(Permission.ENTERPRISE.name())
                ));
            } else {
                messages = new ArrayList<>(parserService.parseInput(input));
            }
            trackerService.inputParsed(messages.size());
            doSaveMessages(messages);
        }
    }

    protected void doSaveMessages(List<FixMessage> messages) {
        if (shiroUtilities.isUserAuthenticated()) {
            UserDetails userDetails = shiroUtilities.getCurrentUserDetails();
            Boolean storeMessages = userService.getUserSettingsCache().getBoolean(userDetails.getUserID(), UserSetting.STORE_MESSAGES);
            if (userDetails != null && storeMessages) {
                executorService.submit(() -> {
                    parserService.saveMessages(userDetails, messages);
                });
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
            facesUtilities.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
            LOGGER.error(FAILED_TO_SHARE, e);
        } catch (Exception e) {
            facesUtilities.addMessage(FacesMessage.SEVERITY_ERROR, FAILED_TO_SHARE);
            LOGGER.error(FAILED_TO_SHARE, e);
        }
    }

    private void setProviderToContext(ProviderDescriptor selectedProvider) {
        if (shiroUtilities.isUserAuthenticated()) {
            facesUtilities.setContextAttribute(
                    shiroUtilities.getCurrentUserID().getId() + FixDefinitionProvider.class.getSimpleName(),
                    selectedProvider);
        }
    }

    public String getShareLink() {
        return shareKey == null ? "" : SHARE_URL + shareKey;
    }

    public List<FixMessage> getMessages() {
        return messages;
    }

    public FixMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(FixMessage selectedMessage) {
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
