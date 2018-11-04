package pl.zankowski.fixparser.payment.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.zankowski.fixparser.core.ITransferObject;

import java.util.Objects;

public class ClientAddressTO implements ITransferObject {

    private static final long serialVersionUID = -4650717543022792088L;

    private final String address;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String countryCode;

    @JsonCreator
    public ClientAddressTO(
            @JsonProperty("address") final String address,
            @JsonProperty("city") final String city,
            @JsonProperty("state") final String state,
            @JsonProperty("postalCode") final String postalCode,
            @JsonProperty("countryCode") final String countryCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClientAddressTO that = (ClientAddressTO) o;
        return Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(countryCode, that.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, city, state, postalCode, countryCode);
    }

    @Override
    public String toString() {
        return "ClientAddressTO{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

}
