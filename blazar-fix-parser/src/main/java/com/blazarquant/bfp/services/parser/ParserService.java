package com.blazarquant.bfp.services.parser;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Wojciech Zankowski
 */
public interface ParserService {

    List<FixMessage> findMessagesByUser(UserDetails userDetails, ProviderDescriptor providerDescriptor, int lowerLimit, int upperLimit);

    int countUserMessages(UserDetails userDetails);

    List<FixMessage> parseInput(String input);

    List<FixMessage> parseInput(ProviderDescriptor providerDescriptor, UserID userID, String input);

    void saveMessages(UserDetails userDetails, List<FixMessage> messages);

    void saveProviderFile(UserID userID, ProviderDescriptor providerDescriptor, byte[] content) throws Exception;

    FixDefinitionProvider getDefinitionProvider(ProviderDescriptor providerDescriptor, UserID userID);

    Set<ProviderDescriptor> getProviders(UserID userID);

    void loadProvidersForUser(UserID userID);

    boolean removeProvider(UserID userID, ProviderDescriptor providerDescriptor);

}
