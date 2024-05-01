package be.bonamis.advent.utils;

import lombok.extern.slf4j.Slf4j;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class FileHelper {

    private FileHelper() {
    }

    public static IntStream getColumn(int[][] matrix, int column) {
        return Arrays.stream(matrix).mapToInt(ints -> ints[column]);
    }

    public static List<String> getLines(String name) {
        try {
            try (Stream<String> lines = Files.lines(getPath(name))) {
                return lines.toList();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static String content(String name) {
        try {
            return Files.readString(getPath(name));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static List<Integer> getIntegers(String name) {
        try {
            try (Stream<String> lines = Files.lines(getPath(name))) {
                return lines.map(Integer::parseInt).toList();
            }
        } catch (Exception e) {
           throw new IllegalArgumentException();
        }
    }

    private static Path getPath(String name) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(name).toURI());
    }

    public static String content() {
      Class<?> aClass = MethodHandles.lookup().lookupClass();
      String name = aClass.getSimpleName();
      int length = "day".length();
      String dayAsString = name.substring(length, length + 2);
      String packageName = aClass.getPackage().getName();
      int packageLength = packageName.length();
      String year = packageName.substring(packageLength - 4, packageLength);

      return content(String.format("%1$s/%2$s/%1$s_%2$s_input.txt", year, dayAsString));
    }

    public static InputStream inputStream(String input) {
      return new ByteArrayInputStream(input.getBytes());
    }
}
