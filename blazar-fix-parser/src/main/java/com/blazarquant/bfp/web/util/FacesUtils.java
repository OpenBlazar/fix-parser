/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * @author Wojciech Zankowski
 */
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
