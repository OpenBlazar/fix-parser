package pl.zankowski.fixparser.web.bean.payment;

import pl.zankowski.bfp.core.payments.SubscriptionPlan;
import pl.zankowski.bfp.core.payments.exception.PaymentException;
import pl.zankowski.bfp.data.payments.paypal.PayPalCountryCodes;
import pl.zankowski.bfp.data.user.UserAddress;
import pl.zankowski.bfp.services.payment.PaymentService;
import pl.zankowski.bfp.web.bean.AbstractBean;
import pl.zankowski.bfp.web.util.FacesUtils;
import pl.zankowski.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class SubscriptionBean extends AbstractBean {

    private PaymentService paymentService;
    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

    private String type;
    private SubscriptionPlan subscriptionPlan;

    private String address;
    private String city;
    private String state;
    private String postalCode;
    private PayPalCountryCodes countryCode;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Inject
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    public void doSubscribe() throws PaymentException, IOException {
        if (shiroUtils.isUserAuthenticated()) {
            String acceptUrl = this.paymentService.subscribe(subscriptionPlan, new UserAddress(address, city, state, postalCode, countryCode.getCountryCode()));
            facesUtils.redirect(acceptUrl);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public PayPalCountryCodes getCountryCode() {
        return countryCode;
    }

    public PayPalCountryCodes[] getCountryCodes() {
        return PayPalCountryCodes.values();
    }

    public void setCountryCode(PayPalCountryCodes countryCode) {
        this.countryCode = countryCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        if (type != null && !type.isEmpty()) {
            subscriptionPlan = SubscriptionPlan.valueOf(type);
        }
    }

    public boolean isPro() {
        return SubscriptionPlan.valueOf(type) == SubscriptionPlan.PRO;
    }

    public boolean isEnterprise() {
        return SubscriptionPlan.valueOf(type) == SubscriptionPlan.ENTERPRISE;
    }

    public String getSummaryPrice() {
        return subscriptionPlan == SubscriptionPlan.PRO ? "$2" : "$30";
    }
}
