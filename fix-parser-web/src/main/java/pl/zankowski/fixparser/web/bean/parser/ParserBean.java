package pl.zankowski.fixparser.web.bean.parser;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixParserBaseRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.client.FixMessageClient;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.FixParserConstants;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Named("parserBean")
@ViewScoped
public class ParserBean extends AbstractBean {

    public static final String FAILED_TO_SHARE = "Failed to share message.";

    private static final String SHARE_PARAM = "share";
    private static final String SHARE_URL = "http://www.blazarquant.com/parser?" + SHARE_PARAM + "=";

    private final static Logger LOGGER = LoggerFactory.getLogger(ParserBean.class);

    private static final long serialVersionUID = 6160698503177122700L;

    private FacesUtils facesUtils;

    private FixMessageClient fixMessageClient;

    private List<FixMessageTO> messages = new ArrayList<>();
    private List<DictionaryDescriptorTO> providers = Lists.newArrayList();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    protected DictionaryDescriptorTO selectedProvider;
    private FixMessageTO selectedMessage;
    private String shareKey;
    private String input;

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    @Inject
    public void setFixMessageClient(FixMessageClient fixMessageClient) {
        this.fixMessageClient = fixMessageClient;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    public void doParse(String input) {
        synchronized (this) {
            selectedMessage = null;

            fixMessageClient.parseInput(ImmutableFixParserBaseRequestTO.builder()
                    .dictionaryDescriptor(ImmutableDictionaryDescriptorTO.builder()
                            .loaderType(DictionaryLoaderType.QUICKFIX_LOADER)
                            .build())
                    .input(input)
                    .build());

            messages = new ArrayList<>();

        }
    }

    public void doInjectSampleData() {
        input = FixParserConstants.SAMPLE_DATA;
    }

    public String getShareLink() {
        return shareKey == null ? "" : SHARE_URL + shareKey;
    }

    public List<FixMessageTO> getMessages() {
        return messages;
    }

    public FixMessageTO getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(FixMessageTO selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<DictionaryDescriptorTO> getProviders() {
        return providers;
    }

    public void setProviders(List<DictionaryDescriptorTO> providers) {
        this.providers = providers;
    }
}
