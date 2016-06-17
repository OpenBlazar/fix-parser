package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserSetting;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "profileBean")
@ViewScoped
public class ProfileBean extends AbstractBean {

    private ParserService parserService;
    private UserService userService;

    private ShiroUtilities shiroUtilities;

    private ExecutorService threadService = Executors.newSingleThreadExecutor();

    private Set<ProviderDescriptor> providerDescriptors = new HashSet<>();
    private XMLLoaderType[] loaderTypes = XMLLoaderType.values();

    private String providerName;
    private XMLLoaderType providerLoader = XMLLoaderType.QUICKFIX_LOADER;
    private UploadedFile uploadedFile;

    private ProviderDescriptor defaultProvider;
    private Boolean storeMessages;
    private String permissions;

    private String userMail;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        if (shiroUtilities.isUserAuthenticated()) {
            doReloadProviders();
            doLoadDefaultParameters();
            doLoadPermissions();
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

    private void doLoadDefaultParameters() {
        ProviderDescriptor savedProvider = (ProviderDescriptor) userService.getUserSettingsCache().getObject(shiroUtilities.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER);
        if (savedProvider != null) {
            defaultProvider = savedProvider;
        }

        Boolean storeMessages = userService.getUserSettingsCache().getBoolean(shiroUtilities.getCurrentUserID(), UserSetting.STORE_MESSAGES);
        if (storeMessages != null) {
            this.storeMessages = storeMessages;
        }
    }

    private void doReloadProviders() {
        providerDescriptors = parserService.getProviders(
                shiroUtilities.getCurrentUserID(),
                shiroUtilities.isPermitted(Permission.PRO.name()) || shiroUtilities.isPermitted(Permission.ENTERPRISE.name())
        );
    }

    private void doLoadPermissions() {
        permissions = Arrays.stream(Permission.values())
                .filter(permission -> shiroUtilities.isPermitted(permission.name()))
                .map(Permission::name)
                .collect(Collectors.joining(", "));
    }

    public void doAssignPermission() {
        if (shiroUtilities.isPermitted(Permission.ENTERPRISE.name())) {
            UserDetails userDetails = userService.getUserDetailsByMail(userMail);
            if (userDetails != null) {
                userService.addUserPermission(userDetails.getUserID(), Permission.PRO);
            } else {
                // TODO message
            }
        }
    }

    public void doClearHistory() {
        if (shiroUtilities.isUserAuthenticated()) {
            threadService.submit(() -> parserService.clearHistory(shiroUtilities.getCurrentUserID()));
        }
    }

    public void handleFileUpload() throws Exception {
        Subject currentUser = shiroUtilities.getSubject();
        if (!currentUser.isAuthenticated()) {
            return;
        }

        if (!(shiroUtilities.isPermitted(Permission.PRO.name()) || shiroUtilities.isPermitted(Permission.ENTERPRISE.name()))
                && providerDescriptors.size() >= 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Info", "Custom dictionaries are limited to 1 for BASIC users."));
            return;
        }

        if (uploadedFile != null) {
            UserDetails userDetails = ((UserDetails) currentUser.getPrincipal());
            if (providerName.isEmpty()) {
                providerName = uploadedFile.getFileName();
            }

            ProviderDescriptor providerDescriptor = new ProviderDescriptor(providerName, providerLoader);

            parserService.saveProviderFile(userDetails.getUserID(), providerDescriptor, uploadedFile.getContents());

            doReloadProviders();

            FacesMessage message = new FacesMessage("Successful", providerName + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void doRemoveProvider(ProviderDescriptor providerDescriptor) {
        if (shiroUtilities.isUserAuthenticated()) {
            if (parserService.removeProvider(shiroUtilities.getCurrentUserID(), providerDescriptor)) {
                if (providerDescriptor.equals(defaultProvider)) {
                    userService.getUserSettingsCache().setParameter(shiroUtilities.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER, DefaultFixDefinitionProvider.DESCRIPTOR);
                }

                defaultProvider = DefaultFixDefinitionProvider.DESCRIPTOR;
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Failed to remove " + providerDescriptor + ".");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public boolean isDefaultProvider(ProviderDescriptor providerDescriptor) {
        return !DefaultFixDefinitionProvider.DESCRIPTOR.equals(providerDescriptor) && !parserService.isProProvider(providerDescriptor);
    }

    public Set<ProviderDescriptor> getProviderDescriptors() {
        return providerDescriptors;
    }

    public XMLLoaderType[] getLoaderTypes() {
        return loaderTypes;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public XMLLoaderType getProviderLoader() {
        return providerLoader;
    }

    public void setProviderLoader(XMLLoaderType providerLoader) {
        this.providerLoader = providerLoader;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public ProviderDescriptor getDefaultProvider() {
        return defaultProvider;
    }

    public void setDefaultProvider(ProviderDescriptor defaultProvider) {
        this.defaultProvider = defaultProvider;
        saveParameter(defaultProvider);
    }

    private void saveParameter(ProviderDescriptor defaultProvider) {
        if (shiroUtilities.isUserAuthenticated()) {
            this.userService.getUserSettingsCache().setParameter(shiroUtilities.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER, defaultProvider);
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
        if (shiroUtilities.isUserAuthenticated()) {
            this.userService.getUserSettingsCache().setParameter(shiroUtilities.getCurrentUserID(), UserSetting.STORE_MESSAGES, storeMessages);
        }
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public boolean isEnterprise() {
        return shiroUtilities.isPermitted(Permission.ENTERPRISE.name());
    }
}
