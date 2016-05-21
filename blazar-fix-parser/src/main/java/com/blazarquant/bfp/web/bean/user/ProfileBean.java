package com.blazarquant.bfp.web.bean.user;

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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "profileBean")
@ViewScoped
public class ProfileBean extends AbstractBean {

    private ParserService parserService;
    private UserService userService;

    private ShiroUtilities shiroUtilities;

    private Set<ProviderDescriptor> providerDescriptors = new HashSet<>();
    private XMLLoaderType[] loaderTypes = XMLLoaderType.values();

    private String providerName;
    private XMLLoaderType providerLoader;
    private UploadedFile uploadedFile;

    private ProviderDescriptor defaultProvider;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        if (shiroUtilities.isUserAuthenticated()) {
            providerDescriptors.add(DefaultFixDefinitionProvider.DESCRIPTOR);
            providerDescriptors.addAll(parserService.getProviders(shiroUtilities.getCurrentUserID()));

            ProviderDescriptor savedProvider = (ProviderDescriptor) userService.getUserSettingsCache().getObject(shiroUtilities.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER);
            if (savedProvider != null) {
                defaultProvider = savedProvider;
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

    public void handleFileUpload() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return;
        }

        if (uploadedFile != null) {
            UserDetails userDetails = ((UserDetails) subject.getPrincipal());
            if (providerName.isEmpty()) {
                providerName = uploadedFile.getFileName();
            }

            ProviderDescriptor providerDescriptor = new ProviderDescriptor(providerName, XMLLoaderType.QUICKFIX_LOADER);

            parserService.saveProviderFile(userDetails.getUserID(), providerDescriptor, uploadedFile.getContents());

            FacesMessage message = new FacesMessage("Succesful", providerName + " is uploaded.");
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
        return !DefaultFixDefinitionProvider.DESCRIPTOR.equals(providerDescriptor);
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
}
