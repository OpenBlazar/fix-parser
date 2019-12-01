package pl.zankowski.fixparser.web.util.converter;

import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "xmlLoaderTypeConverter", forClass = Object.class)
public class XMLLoaderTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return DictionaryLoaderType.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof DictionaryLoaderType) {
            return ((DictionaryLoaderType) value).name();
        } else {
            throw new IllegalArgumentException("Failed to convert XMLLoaderType. Unexpected object type.");
        }
    }
}
