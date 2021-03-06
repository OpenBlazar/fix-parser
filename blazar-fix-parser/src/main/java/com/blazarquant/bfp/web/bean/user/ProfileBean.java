/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import com.blazarquant.bfp.web.util.FacesUtils;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

    public static final String DICTIONARIES_LIMIT = "Custom dictionaries are limited to 1 for BASIC users.";
    public static final String PROVIDER_UPLOADED = "%s has been successfully uploaded";
    public static final String FAILED_TO_REMOVE = "Failed to remove %s.";

    private ParserService parserService;
    private UserService userService;

    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

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
        if (shiroUtils.isUserAuthenticated()) {
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
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    private void doLoadDefaultParameters() {
        ProviderDescriptor savedProvider = (ProviderDescriptor) userService.getUserSettingsCache().getObject(shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER);
        if (savedProvider != null) {
            defaultProvider = savedProvider;
        }

        Boolean storeMessages = userService.getUserSettingsCache().getBoolean(shiroUtils.getCurrentUserID(), UserSetting.STORE_MESSAGES);
        if (storeMessages != null) {
            this.storeMessages = storeMessages;
        }
    }

    private void doReloadProviders() {
        providerDescriptors = parserService.getProviders(
                shiroUtils.getCurrentUserID(),
                shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name())
        );
    }

    private void doLoadPermissions() {
        permissions = Arrays.stream(Permission.values())
                .filter(permission -> shiroUtils.isPermitted(permission.name()))
                .map(Permission::name)
                .collect(Collectors.joining(", "));
    }

    public void doAssignPermission() {
        if (shiroUtils.isPermitted(Permission.ENTERPRISE.name())) {
            UserDetails userDetails = userService.getUserDetailsByMail(userMail);
            if (userDetails != null) {
                userService.addUserPermission(userDetails.getUserID(), Permission.PRO);
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
            UserDetails userDetails = ((UserDetails) currentUser.getPrincipal());
            if (providerName.isEmpty()) {
                providerName = uploadedFile.getFileName();
            }

            ProviderDescriptor providerDescriptor = new ProviderDescriptor(providerName, providerLoader);

            parserService.saveProviderFile(userDetails.getUserID(), providerDescriptor, uploadedFile.getContents());

            doReloadProviders();

            facesUtils.addMessage(FacesMessage.SEVERITY_INFO, String.format(PROVIDER_UPLOADED, providerName));
        }
    }

    public void doRemoveProvider(ProviderDescriptor providerDescriptor) {
        if (shiroUtils.isUserAuthenticated()) {
            if (parserService.removeProvider(shiroUtils.getCurrentUserID(), providerDescriptor)) {
                if (providerDescriptor.equals(defaultProvider)) {
                    userService.getUserSettingsCache().setParameter(shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER, DefaultFixDefinitionProvider.DESCRIPTOR);
                }

                defaultProvider = DefaultFixDefinitionProvider.DESCRIPTOR;
            } else {
                facesUtils.addMessage(FacesMessage.SEVERITY_WARN, String.format(FAILED_TO_REMOVE, providerDescriptor));
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
        return Arrays.copyOf(loaderTypes, loaderTypes.length);
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
        if (shiroUtils.isUserAuthenticated()) {
            this.userService.getUserSettingsCache().setParameter(shiroUtils.getCurrentUserID(), UserSetting.DEFAULT_PROVIDER, defaultProvider);
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
            this.userService.getUserSettingsCache().setParameter(shiroUtils.getCurrentUserID(), UserSetting.STORE_MESSAGES, storeMessages);
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
