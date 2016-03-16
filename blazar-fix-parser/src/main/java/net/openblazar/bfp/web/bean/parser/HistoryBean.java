package net.openblazar.bfp.web.bean.parser;

import com.google.inject.Inject;
import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.services.parser.ParserService;
import net.openblazar.bfp.web.bean.AbstractBean;
import net.openblazar.bfp.web.model.FixMessageLazyDataModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "historyBean")
@ViewScoped
public class HistoryBean extends AbstractBean {

    private List<FixMessage> messages = new ArrayList<>();
    private FixMessage selectedMessage;
    private ParserService parserService;
    private LazyDataModel<FixMessage> messagesModel;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) currentUser.getPrincipal();
            if (userDetails != null) {
                messagesModel = new FixMessageLazyDataModel(parserService, userDetails);
                messagesModel.setRowCount(parserService.countUserMessages(userDetails));
                messages = parserService.findMessagesByUser(userDetails, 0, 100);
            }
        }
    }

    @Inject
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    public String getSender(FixMessage message) {
        return parserService.getSender(message);
    }

    public String getReceiver(FixMessage message) {
        return parserService.getReceiver(message);
    }

    public String getSendingTime(FixMessage message) {
        return parserService.getSendingTime(message);
    }

    @RequiresAuthentication
    public List<FixMessage> getMessages() {
        return messages;
    }

    public FixMessage getSelectedMessage() {
        return selectedMessage;
    }

    public LazyDataModel<FixMessage> getMessagesModel() {
        return messagesModel;
    }

    public void setSelectedMessage(FixMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

}
