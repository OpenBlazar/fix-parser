package com.blazarquant.bfp.fix.parser.definition.data;

/**
 * @author Wojciech Zankowski
 */
public class ProviderDescriptor {

    private final String providerName;
    private final XMLLoaderType loaderType;

    public ProviderDescriptor(String providerName, XMLLoaderType loaderType) {
        this.providerName = providerName;
        this.loaderType = loaderType;
    }

    public String getProviderName() {
        return providerName;
    }

    public XMLLoaderType getLoaderType() {
        return loaderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderDescriptor that = (ProviderDescriptor) o;

        if (providerName != null ? !providerName.equals(that.providerName) : that.providerName != null) return false;
        return loaderType == that.loaderType;

    }

    @Override
    public int hashCode() {
        int result = providerName != null ? providerName.hashCode() : 0;
        result = 31 * result + (loaderType != null ? loaderType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProviderDescriptor{" +
                "providerName='" + providerName + '\'' +
                ", loaderType=" + loaderType +
                '}';
    }

}
