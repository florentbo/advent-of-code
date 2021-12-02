package be.bonamis.advent.utils;

import static java.util.stream.Collectors.toList;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FileHelper {

	private FileHelper() {
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
