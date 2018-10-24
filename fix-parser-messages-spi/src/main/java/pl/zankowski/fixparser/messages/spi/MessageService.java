package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryTO;
import pl.zankowski.fixparser.user.api.UserIdTO;

import java.util.List;
import java.util.Set;

public interface MessageService {

    List<FixMessageTO> findMessagesByUser(UserIdTO userDetails, DictionaryDescriptorTO providerDescriptor,
            boolean isPermitted, int lowerLimit, int upperLimit);

    int countUserMessages(UserIdTO userDetails);

    List<FixMessageTO> parseInput(String input);

    List<FixMessageTO> parseInput(DictionaryDescriptorTO providerDescriptor, UserIdTO userID, String input, boolean isPermitted);

    void saveMessages(UserIdTO userDetails, List<FixMessageTO> messages);

    void clearHistory(UserIdTO userID);

    void saveProviderFile(UserIdTO userID, DictionaryDescriptorTO providerDescriptor, byte[] content) throws Exception;

    DictionaryTO getDefinitionProvider(DictionaryDescriptorTO providerDescriptor, UserIdTO userID, boolean isPermitted);

    Set<DictionaryDescriptorTO> getProviders(UserIdTO userID, boolean isPermitted);

    void loadProvidersForUser(UserIdTO userID);

    boolean removeProvider(UserIdTO userID, DictionaryDescriptorTO providerDescriptor);

    boolean isProProvider(DictionaryDescriptorTO providerDescriptor);

}
