package com.blazarquant.bfp.core.parser;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.parser.definition.CustomFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProvidersFileStore {

    private final static Logger LOGGER = LoggerFactory.getLogger(FixDefinitionProvidersFileStore.class);

    private final FixDefinitionProviderFactory providerFactory;
    private final FixDefinitionProvidersLoaderFactory providerLoaderFactory;

    public FixDefinitionProvidersFileStore(FixDefinitionProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
        this.providerLoaderFactory = new FixDefinitionProvidersLoaderFactory();
    }

    public Map<ProviderDescriptor, FixDefinitionProvider> loadProProviders() {
        File file = new File(FixDefinitionProvidersFileUtility.getProProvidersDir());
        Map<ProviderDescriptor, FixDefinitionProvider> definitionProviderMap = new HashMap<>();
        if (file.exists()) {
            loadProviders(definitionProviderMap, file);
        }
        return definitionProviderMap;
    }

    public Map<ProviderDescriptor, FixDefinitionProvider> loadUserProviders(UserID userID) {
        File file = new File(FixDefinitionProvidersFileUtility.getUserProvidersDir(userID));
        Map<ProviderDescriptor, FixDefinitionProvider> definitionProviderMap = new HashMap<>();
        if (file.exists()) {
            loadProviders(definitionProviderMap, file);
        }
        return definitionProviderMap;
    }

    private void loadProviders(Map<ProviderDescriptor, FixDefinitionProvider> definitionProviderMap, File file) {
        if (file.isDirectory()) {
            File[] filesList = file.listFiles();
            if (filesList != null) {
                for (File tempFile : filesList) {
                    loadProviders(definitionProviderMap, tempFile);
                }
            }
        } else {
            try {
                ProviderDescriptor providerDescriptor = FixDefinitionProvidersFileUtility.resolveProviderFileName(file.getName());
                definitionProviderMap.put(providerDescriptor, loadCustomProvider(file, providerDescriptor));
            } catch (Exception e) {
                LOGGER.error("Failed to load file {}.", file, e);
            }
        }
    }

    public FixDefinitionProvider loadCustomProvider(File dictionaryFile, ProviderDescriptor providerDescriptor) throws Exception {
        FixDefinitionProvider definitionProvider = null;
        try (InputStream inputStream = new FileInputStream(dictionaryFile)) {
            definitionProvider = new CustomFixDefinitionProvider(
                    providerLoaderFactory.getLoader(providerDescriptor).parseDocument(
                            inputStream
                    ));
        }
        return definitionProvider;
    }

    public FixDefinitionProvider loadCustomProvider(UserID userID, ProviderDescriptor providerDescriptor) throws Exception {
        InputStream inputStream = null;
        FixDefinitionProvider definitionProvider = null;
        try {
            inputStream = new FileInputStream(new File(
                    FixDefinitionProvidersFileUtility.getUserProvidersDir(userID),
                    FixDefinitionProvidersFileUtility.createProviderFileName(providerDescriptor)));
            definitionProvider = new CustomFixDefinitionProvider(
                    providerLoaderFactory.getLoader(providerDescriptor).parseDocument(
                            inputStream
                    )
            );
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return definitionProvider;
    }

    public void saveProviderFile(UserID userID, ProviderDescriptor providerDescriptor, byte[] content) throws IOException {
        File savedFile = new File(
                FixDefinitionProvidersFileUtility.getUserProvidersDir(userID),
                FixDefinitionProvidersFileUtility.createProviderFileName(providerDescriptor.getLoaderType(), providerDescriptor.getProviderName())
        );
        savedFile.getParentFile().mkdirs();
        FileUtils.writeByteArrayToFile(savedFile, content);
    }

    public boolean deleteProviderFile(UserID userID, ProviderDescriptor providerDescriptor) {
        return FileUtils.deleteQuietly(new File(
                FixDefinitionProvidersFileUtility.getUserProvidersDir(userID),
                FixDefinitionProvidersFileUtility.createProviderFileName(providerDescriptor.getLoaderType(), providerDescriptor.getProviderName()))
        );
    }

}
