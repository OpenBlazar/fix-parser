package pl.zankowski.fixparser.web.bean.help;

import pl.zankowski.bfp.fix.data.FixMessage;
import pl.zankowski.bfp.fix.data.FixPair;
import pl.zankowski.bfp.fix.parser.util.FixUtilities;
import pl.zankowski.bfp.web.util.StyleUtilities;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "styleBean")
@ApplicationScoped
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
