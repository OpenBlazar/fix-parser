package pl.zankowski.fixparser.web.util.converter;

import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "providerDescriptorConverter", forClass = Object.class)
public class ProviderDescriptorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return resolveProviderFileName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof DictionaryDescriptorTO) {
            return createProviderFileName((DictionaryDescriptorTO) value);
        } else {
            throw new IllegalArgumentException("Failed to convert ProviderDescriptor. Unexpected object type.");
        }
    }

    private String resolveProviderFileName(String value) {
        return value;
    }

    private String createProviderFileName(DictionaryDescriptorTO dictionaryDescriptor) {
        return dictionaryDescriptor.toString();
    }

}
