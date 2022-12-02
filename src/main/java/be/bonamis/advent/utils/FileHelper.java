package be.bonamis.advent.utils;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FileHelper {

    private FileHelper() {
    }

    public static IntStream getColumn(int[][] matrix, int column) {
        return Arrays.stream(matrix).mapToInt(ints -> ints[column]);
    }

    public static List<String> getLines(String name) {
        try {
            try (Stream<String> lines = Files.lines(getPath(name))) {
                return lines.collect(toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static String content(String name) {
        try {
            return Files.readString(getPath(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> getIntegers(String name) {
        try {
            try (Stream<String> lines = Files.lines(getPath(name))) {
                return lines.map(Integer::parseInt).collect(toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static Path getPath(String name) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(name).toURI());
    }
}
