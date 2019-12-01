package pl.zankowski.fixparser.web.bean.help;

import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.web.util.FixUtilities;
import pl.zankowski.fixparser.web.util.StyleUtilities;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

@Named("styleBean")
@ApplicationScoped
public class StyleBean {

    public String getStyleForMsgType(FixPairTO msgType) {
        return StyleUtilities.getStyleForMsgType(msgType);
    }

    public String getStyleForField(int tag) {
        return StyleUtilities.getStyleForField(tag);
    }

    public String getStyleForOrdStatus(FixMessageTO message) {
        return StyleUtilities.getStyleForOrdStatus(FixUtilities.getOrdStatus(message));
    }

}
