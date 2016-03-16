package net.openblazar.bfp.web.util;

import net.openblazar.bfp.core.parser.util.FixMessageConverter;
import net.openblazar.bfp.data.fix.FixMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Wojciech Zankowski
 */
@FacesConverter(value = "fixMessageConverter", forClass = Object.class)
public class FixMessageOneMenuConverter implements Converter {

    private static final char DELIMITER = ';';
    private static final char ENTRY_DELIMITER = '#';
    private final FixMessageConverter messageConverter = new FixMessageConverter();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int index = value.indexOf(DELIMITER);
        return messageConverter.convertToFixMessage(
                value.substring(index + 1),
                String.valueOf(ENTRY_DELIMITER),
                Integer.parseInt(value.substring(0, index)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            FixMessage fixMessage = (FixMessage) value;
            return fixMessage.getMessageID().toString() + DELIMITER + messageConverter.convertToString(fixMessage, ENTRY_DELIMITER);
        }
    }

}
