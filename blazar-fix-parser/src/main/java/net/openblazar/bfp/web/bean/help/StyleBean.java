package net.openblazar.bfp.web.bean.help;

import net.openblazar.bfp.fix.data.FixField;
import net.openblazar.bfp.fix.data.FixMessage;
import net.openblazar.bfp.fix.data.field.MsgType;
import net.openblazar.bfp.fix.parser.util.FixUtilities;
import net.openblazar.bfp.web.util.StyleUtilities;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author Wojciech Zankowski
 */
@ApplicationScoped
@ManagedBean(name = "styleBean")
public class StyleBean {

    public String getStyleForMsgType(MsgType msgType) {
        return StyleUtilities.getStyleForMsgType(msgType);
    }

    public String getStyleForField(FixField fixField) {
        return StyleUtilities.getStyleForField(fixField);
    }

    public String getStyleForOrdStatus(FixMessage message) {
        return StyleUtilities.getStyleForOrdStatus(FixUtilities.getOrdStatus(message));
    }

}
