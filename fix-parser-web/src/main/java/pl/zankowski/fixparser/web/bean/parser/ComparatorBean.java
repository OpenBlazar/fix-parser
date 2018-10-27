package pl.zankowski.fixparser.web.bean.parser;

import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.web.bean.AbstractBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "comparatorBean")
@RequestScoped
public class ComparatorBean extends AbstractBean {

    private FixMessageTO selectedMessage_1;
    private FixMessageTO selectedMessage_2;

    public FixMessageTO getSelectedMessage_1() {
        return selectedMessage_1;
    }

    public void setSelectedMessage_1(FixMessageTO selectedMessage_1) {
        this.selectedMessage_1 = selectedMessage_1;
    }

    public FixMessageTO getSelectedMessage_2() {
        return selectedMessage_2;
    }

    public void setSelectedMessage_2(FixMessageTO selectedMessage_2) {
        this.selectedMessage_2 = selectedMessage_2;
    }

    public String getRowStyleForSecondMessage(FixPairTO fixPair) {
        if (selectedMessage_1.getMessageFields().contains(fixPair)) {
            return "null";
        } else {
            return "blazar-comparator-row";
        }
    }

}
