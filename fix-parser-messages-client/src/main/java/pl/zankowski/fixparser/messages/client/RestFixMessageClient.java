package pl.zankowski.fixparser.messages.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zankowski.fixparser.core.api.FixParserSystemException;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserBaseRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;

import java.util.List;

@Service
public class RestFixMessageClient implements FixMessageClient {

    private final RestTemplate restTemplate;

    @Autowired
    public RestFixMessageClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FixMessageTO> getMessages(final String providerName,
            final DictionaryLoaderType loaderType, final Pageable pageable) {
        final ResponseEntity<FixMessageTO[]> response = restTemplate
                .getForEntity("/message?providerName={providerName}&loaderType={loaderType}",
                        FixMessageTO[].class, ImmutableMap.builder()
                                .put("providerName", providerName)
                                .put("loaderType", loaderType)
                                .build());
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new FixParserSystemException("Failed to get messages");
        }
        return response.getBody() == null
                ? Lists.newArrayList()
                : Lists.newArrayList(response.getBody());
    }

    @Override
    public Integer countUserMessages() {
        final ResponseEntity<Integer> response = restTemplate
                .getForEntity("/message/count", Integer.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new FixParserSystemException("Failed to count messages");
        }
        return response.getBody();
    }

    @Override
    public List<FixMessageTO> parseInput(final FixParserBaseRequestTO input) {
        final ResponseEntity<FixMessageTO[]> response = restTemplate
                .postForEntity("/message/parse", input, FixMessageTO[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new FixParserSystemException("Failed to get messages");
        }
        return response.getBody() == null
                ? Lists.newArrayList()
                : Lists.newArrayList(response.getBody());
    }

    @Override
    public void clearHistory() {
        restTemplate.delete("/message/clear");
    }
}
