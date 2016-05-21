package com.blazarquant.bfp.fix.parser.definition.data;

/**
 * @author Wojciech Zankowski
 */
public enum XMLLoaderType {

    QUICKFIX_LOADER("QF");

    private final String tag;

    XMLLoaderType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static XMLLoaderType getXMLLoaderTypeFromTag(String tag) {
        for (XMLLoaderType loaderType : values()) {
            if (loaderType.getTag().equals(tag)) {
                return loaderType;
            }
        }
        throw new IllegalArgumentException("Illegal XML Loader tag - " + tag + ".");
    }
}
