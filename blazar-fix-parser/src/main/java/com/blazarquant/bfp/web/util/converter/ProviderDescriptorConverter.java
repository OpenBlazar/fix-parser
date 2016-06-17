package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.core.parser.FixDefinitionProvidersFileUtility;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Wojciech Zankowski
 */
@FacesConverter(value = "providerDescriptorConverter", forClass = Object.class)
public class ProviderDescriptorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return FixDefinitionProvidersFileUtility.resolveProviderFileName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof ProviderDescriptor) {
            return FixDefinitionProvidersFileUtility.createProviderFileName((ProviderDescriptor) value);
        } else {
            throw new IllegalArgumentException("Failed to convert ProviderDescriptor. Unexpected object type.");
        }
    }

}
