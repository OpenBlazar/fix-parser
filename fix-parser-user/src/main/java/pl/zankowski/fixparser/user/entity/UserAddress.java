package pl.zankowski.fixparser.user.entity;

import pl.zankowski.fixparser.core.entity.IEntity;

import java.util.Objects;

public class UserAddress implements IEntity {

    private final String address;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String countryCode;

    public UserAddress(final String address, final String city, final String state, final String postalCode,
            final String countryCode) {
        Objects.requireNonNull(address);
        Objects.requireNonNull(city);
        Objects.requireNonNull(state);
        Objects.requireNonNull(postalCode);
        Objects.requireNonNull(countryCode);

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
        final UserAddress that = (UserAddress) o;
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
        return "UserAddress{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    public static class Builder {

        private String address = "";
        private String city = "";
        private String state = "";
        private String postalCode = "";
        private String countryCode = "";

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder countrycode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public UserAddress build() {
            return new UserAddress(
                    address, city, state, postalCode, countryCode
            );
        }

    }

}
