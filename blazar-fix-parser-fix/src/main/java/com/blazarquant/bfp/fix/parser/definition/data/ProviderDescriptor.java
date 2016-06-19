/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.fix.parser.definition.data;

/**
 * @author Wojciech Zankowski
 */
public class ProviderDescriptor implements Comparable<ProviderDescriptor> {

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
    public int compareTo(ProviderDescriptor providerDescriptor) {
        return this.getProviderName().compareTo(providerDescriptor.getProviderName());
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
