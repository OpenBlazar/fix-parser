package pl.zankowski.fixparser.web.bean.help;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "fixBean")
@ApplicationScoped
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
