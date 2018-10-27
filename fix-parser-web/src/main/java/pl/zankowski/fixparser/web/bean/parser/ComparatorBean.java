package pl.zankowski.fixparser.web.bean.parser;

import pl.zankowski.bfp.fix.data.FixMessage;
import pl.zankowski.bfp.fix.data.FixPair;
import pl.zankowski.bfp.web.bean.AbstractBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "comparatorBean")
@RequestScoped
public class ComparatorBean extends AbstractBean {

    private FixMessage selectedMessage_1;
    private FixMessage selectedMessage_2;

    public FixMessage getSelectedMessage_1() {
        return selectedMessage_1;
    }

    public void setSelectedMessage_1(FixMessage selectedMessage_1) {
        this.selectedMessage_1 = selectedMessage_1;
    }

    public FixMessage getSelectedMessage_2() {
        return selectedMessage_2;
    }

    public void setSelectedMessage_2(FixMessage selectedMessage_2) {
        this.selectedMessage_2 = selectedMessage_2;
    }

    public String getRowStyleForSecondMessage(FixPair fixPair) {
        if (selectedMessage_1.getMessageFields().contains(fixPair)) {
            return "null";
        } else {
            return "blazar-comparator-row";
        }
    }

}
