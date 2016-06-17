package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.data.payments.paypal.PayPalCountryCodes;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Wojciech Zankowski
 */
@FacesConverter(value = "payPalCountryCodeConverter", forClass = Object.class)
public class PayPalCountryCodeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return PayPalCountryCodes.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof PayPalCountryCodes) {
            return ((PayPalCountryCodes) value).name();
        } else {
            throw new IllegalArgumentException("Failed to convert PayPalCountryCode. Unexpected object type.");
        }
    }
}
