package com.blazarquant.bfp.web.util;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * @author Wojciech Zankowski
 */
public class FacesUtilities {

    public void setContextAttribute(String attributeName, Object attribute) {
        ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).setAttribute(attributeName, attribute);
    }

    public Object getContextAttribute(String attributeName) {
        return ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttribute(attributeName);
    }

}
