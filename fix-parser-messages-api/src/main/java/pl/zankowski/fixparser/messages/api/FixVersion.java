package pl.zankowski.fixparser.messages.api;

public enum FixVersion {

    FIX_40("FIX.4.0"),
    FIX_41("FIX.4.1"),
    FIX_42("FIX.4.2"),
    FIX_43("FIX.4.3"),
    FIX_44("FIX.4.4"),
    FIX_50("FIXT.1.1"),
    UNKNOWN("");

    private String code;

    FixVersion(String code) {
        this.code = code;
    }

    public static FixVersion getFixVersionFromCode(String code) {
        for (FixVersion version : values()) {
            if (version.getCode().equals(code)) {
                return version;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

}
