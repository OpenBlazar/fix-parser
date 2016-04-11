package com.blazarquant.bfp.web.bean.parser;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.web.bean.AbstractBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author Wojciech Zankowski
 */
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

}
