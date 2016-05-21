package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import org.ocpsoft.rewrite.annotation.Convert;

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
        return ((XMLLoaderType) value).name();
    }
}
