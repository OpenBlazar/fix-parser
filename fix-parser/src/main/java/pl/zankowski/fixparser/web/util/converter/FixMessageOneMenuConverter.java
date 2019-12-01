package pl.zankowski.fixparser.web.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixParserBaseRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.client.FixMessageClient;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;
import java.util.List;

@FacesConverter(value = "fixMessageConverter", forClass = Object.class)
public class FixMessageOneMenuConverter implements Converter {

    private static final char DELIMITER = ';';
    private static final char ENTRY_DELIMITER = '#';

    @Autowired
    private FixMessageClient fixMessageClient;

    public FixMessageOneMenuConverter() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int index = value.indexOf(DELIMITER);

        final List<FixMessageTO> messages = fixMessageClient
                .parseInput(ImmutableFixParserBaseRequestTO.builder()
                        .input(value.substring(index + 1))
                        .dictionaryDescriptor(ImmutableDictionaryDescriptorTO.builder()
                                .loaderType(DictionaryLoaderType.QUICKFIX_LOADER)
                                .build())
                        .build());

        return messages.stream().findFirst().orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            FixMessageTO fixMessage = (FixMessageTO) value;
            return "";
//            return fixMessage.getMessageId().toString() + DELIMITER +
//                    fixMessageClient.parseInput(fixMessage);
        }
    }

}
