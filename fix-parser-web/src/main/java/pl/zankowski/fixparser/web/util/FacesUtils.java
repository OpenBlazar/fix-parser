package pl.zankowski.fixparser.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.IOException;

public class FacesUtils {

    public void setContextAttribute(String attributeName, Object attribute) {
        ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).setAttribute(attributeName, attribute);
    }

    public Object getContextAttribute(String attributeName) {
        return ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttribute(attributeName);
    }

    public void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public void addMessage(FacesMessage.Severity severity, String summary) {
        addMessage(severity, summary, "");
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String details) {
        addMessage(null, severity, summary, details);
    }

    public void addMessage(String clientId, FacesMessage.Severity severity, String summary, String details) {
        FacesContext.getCurrentInstance().addMessage(clientId,
                new FacesMessage(severity, summary, details));
    }

    public String getRequestParameter(String parameterName) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parameterName);
    }

}
