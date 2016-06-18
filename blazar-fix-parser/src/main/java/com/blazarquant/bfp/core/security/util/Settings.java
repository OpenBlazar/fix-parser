package com.blazarquant.bfp.core.security.util;

/**
 * @author Wojciech Zankowski
 */
public enum Settings {

    SHIRO_CIPHER_KEY("shiro.cipherKey");

    private final String property;

    Settings(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

}
