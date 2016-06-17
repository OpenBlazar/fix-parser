package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.web.util.FacesUtils;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

/**
 * @author Wojciech Zankowski
 */
@FacesConverter(value = "fixMessageConverter", forClass = Object.class)
public class FixMessageOneMenuConverter implements Converter {

    private static final char DELIMITER = ';';
    private static final char ENTRY_DELIMITER = '#';
    private final FixMessageConverter messageConverter = new FixMessageConverter();

    private FacesUtils facesUtils;
    private ShiroUtils shiroUtils;
    private ParserService parserService;

    public FixMessageOneMenuConverter() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        Injector injector = (Injector) servletContext.getAttribute(Injector.class.getName());
        injector.injectMembers(this);
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int index = value.indexOf(DELIMITER);

        ProviderDescriptor providerDescriptor = null;
        if (shiroUtils.isUserAuthenticated()) {
            providerDescriptor = (ProviderDescriptor) facesUtils.getContextAttribute(
                    shiroUtils.getCurrentUserID().getId() + FixDefinitionProvider.class.getSimpleName());
        }

        return messageConverter.convertToFixMessage(
                value.substring(index + 1),
                String.valueOf(ENTRY_DELIMITER),
                Integer.parseInt(value.substring(0, index)),
                parserService.getDefinitionProvider(
                        providerDescriptor,
                        shiroUtils.getCurrentUserID(),
                        shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name())
                ));
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
