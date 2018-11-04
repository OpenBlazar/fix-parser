package pl.zankowski.fixparser.common;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PathUtils {

    public static String joinPath(String... args) {
        return Arrays.stream(args).collect(Collectors.joining(File.separator));
    }

}
