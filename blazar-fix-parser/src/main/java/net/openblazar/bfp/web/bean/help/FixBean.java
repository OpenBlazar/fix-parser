package net.openblazar.bfp.web.bean.help;

import net.openblazar.bfp.core.parser.util.FixUtilities;
import net.openblazar.bfp.data.fix.FixMessage;

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
