package com.blazarquant.bfp.web.bean.parser;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.util.FixParserConstants;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.share.ShareService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "parserBean")
@ViewScoped
public class ParserBean extends AbstractBean {

    public static final String SHARE_PARAM = "share";
    public static final String SHARE_URL = "http://www.blazarquant.com/parser?" + SHARE_PARAM + "=";

    private final static Logger LOGGER = LoggerFactory.getLogger(ParserBean.class);

    protected ParserService parserService;
    protected ShareService shareService;
    protected List<FixMessage> messages = new ArrayList<>();
    protected FixMessage selectedMessage;
    protected String shareKey;
    protected String input;

    @Inject
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Inject
    public void setShareService(ShareService shareService) {
        this.shareService = shareService;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        doLoadShared();
    }

    public void doLoadShared() {
        String shareKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(SHARE_PARAM);
        if (shareKey == null) {
            return;
        }
        try {
            input = shareService.getMessageFromKey(shareKey);
            messages = new ArrayList<>(parserService.parseInput(input));
        } catch (Exception e) {
            // TODO Handle
            LOGGER.error("Failed to load shared message.", e);
        }
    }

    public void doParse(String input) {
        messages = new ArrayList<>(parserService.parseInput(input));
        doSaveMessages(messages);
    }

    protected void doSaveMessages(List<FixMessage> messages) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) currentUser.getPrincipal();
            if (userDetails != null) {
                parserService.saveMessages(userDetails, messages);
            }
        }
    }

    public void doInjectSampleData() {
        input = FixParserConstants.SAMPLE_DATA;
    }

    public void doShare() {
        try {
            shareKey = shareService.shareMessage(input);
        } catch (Exception e) {
            // TODO Handle
            LOGGER.error("Failed to share message.", e);
        }
    }

    public String getShareLink() {
        return shareKey == null ? "" : SHARE_URL + shareKey;
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
