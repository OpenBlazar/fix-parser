package pl.zankowski.fixparser.common;

public enum Directories {

    CONFIG("config");

    private final String dirName;

    Directories(String dirName) {
        this.dirName = dirName;
    }

    public String getDirName() {
        return dirName;
    }

}
