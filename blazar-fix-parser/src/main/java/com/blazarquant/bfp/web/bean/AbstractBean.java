package com.blazarquant.bfp.web.bean;

import com.google.inject.Injector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.Serializable;

/**
 * @author Wojciech Zankowski
 */
public abstract class AbstractBean implements Serializable {

    private Injector injector;

    public Injector getInjector() {
        if (injector == null) {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            injector = (Injector) servletContext.getAttribute(Injector.class.getName());
        }
        return injector;
    }

    public void init() {
        getInjector().injectMembers(this);
    }

    protected void facesError(String message, Exception exception) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
    }

}
