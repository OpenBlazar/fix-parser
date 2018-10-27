package pl.zankowski.fixparser.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

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
