package net.openblazar.bfp.web.bean.parser;

import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.web.bean.AbstractBean;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "comparatorBean")
@ViewScoped
public class ComparatorBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(ComparatorBean.class);

    private final Map<String,Object> options;

    private FixMessage selectedMessage_1;
    private FixMessage selectedMessage_2;

    public ComparatorBean() {
        options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
    }

    @PostConstruct
    public void init() {
        System.out.println();
    }

    public void doCompare() {
        LOGGER.info("Comparing messages: " + selectedMessage_1 + ", " + selectedMessage_2);
        RequestContext.getCurrentInstance().openDialog("/components/comparator.xhtml", options, null);
    }

    public FixMessage getSelectedMessage_1() {
        LOGGER.info(selectedMessage_1 == null ? "null " : selectedMessage_1.toString());
        return selectedMessage_1;
    }

    public void setSelectedMessage_1(FixMessage selectedMessage_1) {
        LOGGER.info(selectedMessage_1 == null ? "null " : selectedMessage_1.toString());
        this.selectedMessage_1 = selectedMessage_1;
    }

    public FixMessage getSelectedMessage_2() {
        return selectedMessage_2;
    }

    public void setSelectedMessage_2(FixMessage selectedMessage_2) {
        this.selectedMessage_2 = selectedMessage_2;
    }

}
