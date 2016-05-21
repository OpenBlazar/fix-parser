package com.blazarquant.bfp.web.bean.help;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.data.FixPair;
import com.blazarquant.bfp.fix.data.field.MsgType;
import com.blazarquant.bfp.fix.parser.util.FixUtilities;
import com.blazarquant.bfp.web.util.StyleUtilities;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author Wojciech Zankowski
 */
@ApplicationScoped
@ManagedBean(name = "styleBean")
public class StyleBean {

    public String getStyleForMsgType(FixPair msgType) {
        return StyleUtilities.getStyleForMsgType(msgType);
    }

    public String getStyleForField(int tag) {
        return StyleUtilities.getStyleForField(tag);
    }

    public String getStyleForOrdStatus(FixMessage message) {
        return StyleUtilities.getStyleForOrdStatus(FixUtilities.getOrdStatus(message));
    }

}
