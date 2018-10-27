package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryTO;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.List;
import java.util.Set;

public interface MessageService {

    List<FixMessageTO> findMessagesByUser(UserId userDetails, DictionaryDescriptorTO providerDescriptor,
            boolean isPermitted, int lowerLimit, int upperLimit);

    int countUserMessages(UserId userDetails);

    List<FixMessageTO> parseInput(String input);

    List<FixMessageTO> parseInput(DictionaryDescriptorTO providerDescriptor, UserId userID, String input, boolean isPermitted);

    void saveMessages(UserId userDetails, List<FixMessageTO> messages);

    void clearHistory(UserId userID);

    void saveProviderFile(UserId userID, DictionaryDescriptorTO providerDescriptor, byte[] content) throws Exception;

    DictionaryTO getDefinitionProvider(DictionaryDescriptorTO providerDescriptor, UserId userID, boolean isPermitted);

    Set<DictionaryDescriptorTO> getProviders(UserId userID, boolean isPermitted);

    void loadProvidersForUser(UserId userID);

    boolean removeProvider(UserId userID, DictionaryDescriptorTO providerDescriptor);

    boolean isProProvider(DictionaryDescriptorTO providerDescriptor);

}
