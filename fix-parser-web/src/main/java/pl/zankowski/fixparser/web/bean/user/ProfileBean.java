package pl.zankowski.fixparser.web.bean.user;

import com.google.inject.Inject;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.UploadedFile;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryTOBuilder;
import pl.zankowski.fixparser.messages.spi.DictionaryService;
import pl.zankowski.fixparser.messages.spi.MessageService;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserSetting;
import pl.zankowski.fixparser.user.spi.UserService;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Named("profileBean")
@ViewScoped
public class ProfileBean extends AbstractBean {

    public static final String DICTIONARIES_LIMIT = "Custom dictionaries are limited to 1 for BASIC users.";
    public static final String PROVIDER_UPLOADED = "%s has been successfully uploaded";
    public static final String FAILED_TO_REMOVE = "Failed to remove %s.";

    private MessageService parserService;
    private UserService userService;
    private DictionaryService dictionaryService;

    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

    private ExecutorService threadService = Executors.newSingleThreadExecutor();

    private Set<DictionaryDescriptorTO> providerDescriptors = new HashSet<>();
    private DictionaryLoaderType[] loaderTypes = DictionaryLoaderType.values();

    private String providerName;
    private DictionaryLoaderType providerLoader = DictionaryLoaderType.QUICKFIX_LOADER;
    private UploadedFile uploadedFile;

    private DictionaryDescriptorTO defaultProvider;
    private Boolean storeMessages;
    private String permissions;

    private String userMail;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        if (shiroUtils.isUserAuthenticated()) {
            doReloadProviders();
            doLoadDefaultParameters();
            doLoadPermissions();
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
    public void setDictionaryService(final DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    private void doLoadDefaultParameters() {
        DictionaryDescriptorTO savedProvider = (DictionaryDescriptorTO) userService.getParameter(
                shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER);
        if (savedProvider != null) {
            defaultProvider = savedProvider;
        }

        Boolean storeMessages = (Boolean) userService.getParameter(shiroUtils.getCurrentUserID(),
                UserSetting.STORE_MESSAGES);
        if (storeMessages != null) {
            this.storeMessages = storeMessages;
        }
    }

    private void doReloadProviders() {
        providerDescriptors = dictionaryService.getDictionaryDescriptors(shiroUtils.getCurrentUserID());
    }

    private void doLoadPermissions() {
        permissions = Arrays.stream(Permission.values())
                .filter(permission -> shiroUtils.isPermitted(permission.name()))
                .map(Permission::name)
                .collect(Collectors.joining(", "));
    }

    public void doAssignPermission() {
        if (shiroUtils.isPermitted(Permission.ENTERPRISE.name())) {
            UserDetailsTO userDetails = userService.getUserDetailsByMail(userMail);
            if (userDetails != null) {
                userService.addUserPermission(userDetails.getUserId(), Permission.PRO);
            } else {
                // TODO message
            }
        }
    }

    public void doClearHistory() {
        if (shiroUtils.isUserAuthenticated()) {
            threadService.submit(() -> parserService.clearHistory(shiroUtils.getCurrentUserID()));
        }
    }

    public void handleFileUpload() throws Exception {
        Subject currentUser = shiroUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            return;
        }

        if (!(shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name()))
                && providerDescriptors.size() >= 2) {
            facesUtils.addMessage(FacesMessage.SEVERITY_INFO, DICTIONARIES_LIMIT);
            return;
        }

        if (uploadedFile != null) {
            UserDetailsTO userDetails = ((UserDetailsTO) currentUser.getPrincipal());
            if (providerName.isEmpty()) {
                providerName = uploadedFile.getFileName();
            }

            DictionaryDescriptorTO providerDescriptor = new DictionaryDescriptorTO(
                    userDetails.getUserId(), providerName, providerLoader);

            dictionaryService.saveDictionary(new DictionaryTOBuilder()
                    .withContent(uploadedFile.getContents())
                    .withDictionaryDescriptor(providerDescriptor)
                    .build());

            doReloadProviders();

            facesUtils.addMessage(FacesMessage.SEVERITY_INFO, String.format(PROVIDER_UPLOADED, providerName));
        }
    }

    public void doRemoveProvider(DictionaryDescriptorTO providerDescriptor) {
        if (shiroUtils.isUserAuthenticated()) {
            if (dictionaryService.removeDictionary(providerDescriptor)) {
                if (providerDescriptor.equals(defaultProvider)) {
                    userService.setParameter(shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER, null);
                }

                defaultProvider = null;
            } else {
                facesUtils.addMessage(FacesMessage.SEVERITY_WARN, String.format(FAILED_TO_REMOVE, providerDescriptor));
            }
        }
    }

    public boolean isDefaultProvider(DictionaryDescriptorTO providerDescriptor) {
//        return !DefaultFixDefinitionProvider.DESCRIPTOR.equals(providerDescriptor) && !parserService.isProProvider(providerDescriptor);
        return true;
    }

    public Set<DictionaryDescriptorTO> getProviderDescriptors() {
        return providerDescriptors;
    }

    public DictionaryLoaderType[] getLoaderTypes() {
        return Arrays.copyOf(loaderTypes, loaderTypes.length);
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public DictionaryLoaderType getProviderLoader() {
        return providerLoader;
    }

    public void setProviderLoader(DictionaryLoaderType providerLoader) {
        this.providerLoader = providerLoader;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public DictionaryDescriptorTO getDefaultProvider() {
        return defaultProvider;
    }

    public void setDefaultProvider(DictionaryDescriptorTO defaultProvider) {
        this.defaultProvider = defaultProvider;
        saveParameter(defaultProvider);
    }

    private void saveParameter(DictionaryDescriptorTO defaultProvider) {
        if (shiroUtils.isUserAuthenticated()) {
            this.userService.setParameter(shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER, defaultProvider);
        }
    }

    public Boolean getStoreMessages() {
        return storeMessages;
    }

    public void setStoreMessages(Boolean storeMessages) {
        this.storeMessages = storeMessages;
        saveParameter(storeMessages);
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    private void saveParameter(Boolean storeMessages) {
        if (shiroUtils.isUserAuthenticated()) {
            this.userService.setParameter(shiroUtils.getCurrentUserID(), UserSetting.STORE_MESSAGES, storeMessages);
        }
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public boolean isEnterprise() {
        return shiroUtils.isPermitted(Permission.ENTERPRISE.name());
    }
}
