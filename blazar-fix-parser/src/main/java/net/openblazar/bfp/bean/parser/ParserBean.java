package net.openblazar.bfp.bean.parser;

import com.google.inject.Inject;
import net.openblazar.bfp.bean.AbstractBean;
import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.services.parser.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "parserBean")
@ViewScoped
public class ParserBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParserBean.class);

    private ParserService parserService;
    private List<FixMessage> messages;
    private FixMessage selectedMessage;
    private String input;

    @Inject
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostConstruct
    public void init() {
        super.init();
    }

    public void doParse(String input) {
        messages = parserService.parseInput(input);
    }

    public List<FixMessage> getMessages() {
        return messages;
    }

    public FixMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(FixMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

}
