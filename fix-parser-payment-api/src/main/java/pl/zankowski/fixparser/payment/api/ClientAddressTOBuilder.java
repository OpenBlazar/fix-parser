package pl.zankowski.fixparser.payment.api;

public final class ClientAddressTOBuilder {
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;

    public ClientAddressTOBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public ClientAddressTOBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public ClientAddressTOBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public ClientAddressTOBuilder withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public ClientAddressTOBuilder withCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public ClientAddressTO build() {
        return new ClientAddressTO(address, city, state, postalCode, countryCode);
    }
}
