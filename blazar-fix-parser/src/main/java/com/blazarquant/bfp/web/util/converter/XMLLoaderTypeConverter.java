package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Wojciech Zankowski
 */
@FacesConverter(value = "xmlLoaderTypeConverter", forClass = Object.class)
public class XMLLoaderTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return XMLLoaderType.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof XMLLoaderType) {
            return ((XMLLoaderType) value).name();
        } else {
            throw new IllegalArgumentException("Failed to convert XMLLoaderType. Unexpected object type.");
        }
    }
}
