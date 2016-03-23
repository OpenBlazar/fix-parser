package com.blazarquant.bfp.web.bean.help;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.util.FixUtilities;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author Wojciech Zankowski
 */
@ApplicationScoped
@ManagedBean(name = "fixBean")
public class FixBean {

    public String getSender(FixMessage message) {
        return FixUtilities.getSender(message);
    }

    public String getReceiver(FixMessage message) {
        return FixUtilities.getReceiver(message);
    }

    public String getSendingTime(FixMessage message) {
        return FixUtilities.getSendingTime(message);
    }

    public String getOrdStatusDescription(FixMessage message) {
        return FixUtilities.getOrdStatusDescription(message);
    }

}
