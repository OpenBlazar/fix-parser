package net.openblazar.bfp.web.bean.parser;

import net.openblazar.bfp.fix.data.FixMessage;
import net.openblazar.bfp.web.bean.AbstractBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "comparatorBean")
@ViewScoped
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

}
