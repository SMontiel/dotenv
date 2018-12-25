package com.salvadormontiel.dotenv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Salvador Montiel on 23/dic/2018.
 */
public class DotEnv {
    private static DotEnv INSTANCE = null;

    public static DotEnv load() {
        if (INSTANCE ==  null) {
            Pattern isWhitespace = Pattern.compile("^\\s*$");
            Pattern parseLine = Pattern.compile("^\\s*([\\w.\\-]+)\\s*(=)s*(.*)?s*$");
            Map<String, String> map = read().map(String::trim)
                    .filter(s -> !isWhitespace.matcher(s).matches())
                    .filter(s -> !(s.startsWith("#") || s.startsWith("//")))
                    .filter(s -> parseLine.matcher(s).matches())
                    .map(s -> s.split("="))
                    .collect(Collectors.toMap(s -> s[0], s -> s[1]));
            INSTANCE = new DotEnv(map);
        }
        return INSTANCE;
    }

    public static String get(String key) {
        return load().getVar(key);
    }

    /**
     * Reads the contents of the .env file
     */
    private static Stream<String> read() {
        Path path = Paths.get(".env");
        if (path.toFile().exists()) {
            try {
                return Files.lines(path);
            } catch (IOException e) {
                e.printStackTrace();
                return Stream.empty();
            }
        } else throw new DotEnvException("The file '.env' does not exist on '" + path.toAbsolutePath().getParent().toString()
                + "' directory. Please create one.");
    }

    private final Map<String, String> vars;

    private DotEnv(Map<String, String> map) {
        vars = map;
    }

    private String getVar(String key) {
        String envVar = System.getenv(key);
        return envVar != null ? envVar : vars.get(key);
    }
}
