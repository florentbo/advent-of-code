package be.bonamis.advent;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.*;
import java.nio.file.*;

@Slf4j
public class DayDataRetriever {

  private static final Path CACHE_DIR =
      Path.of(System.getProperty("user.home"), "personal-dev", "advent-inputs");

  public record PuzzleId(int year, int day) {
    public String inputUrl() {
      return "https://adventofcode.com/" + year + "/day/" + day + "/input";
    }

    public String inputFileName() {
      return String.format("input-%02d-%02d.txt", year, day);
    }

    public String solverClassName() {
      return String.format("be.bonamis.advent.year%d.Day%02d", year, day);
    }
  }

  public static void main(String[] args) {
    PuzzleId id = new PuzzleId(
        Integer.parseInt(System.getenv("YEAR")),
        Integer.parseInt(System.getenv("DAY")));
    runCode(id);
  }

  static byte[] loadInput(PuzzleId id) throws IOException {
    Files.createDirectories(CACHE_DIR);
    Path cached = CACHE_DIR.resolve(id.inputFileName());
    log.info("Looking for input file: {}", cached);
    if (Files.exists(cached)) {
      log.info("Reading existing input from file: {}", cached);
      return Files.readAllBytes(cached);
    }
    log.info("No input file found: {}", cached);
    byte[] fetched = downloadInput(id);
    Files.write(cached, fetched);
    log.info("Writing input to file: {}", cached);
    return fetched;
  }

  private static byte[] downloadInput(PuzzleId id) throws IOException {
    log.info("downloading input from: {}", id.inputUrl());
    HttpURLConnection con = (HttpURLConnection) new URL(id.inputUrl()).openConnection();
    con.addRequestProperty("Cookie", "session=" + System.getenv("ADVENT_SESSION"));
    try (InputStream in = con.getInputStream()) {
      return in.readAllBytes();
    }
  }

  public static void runCode(PuzzleId id) {
    log.info("runCode input from day: {} and year: {}", id.day(), id.year());
    long startTime = System.nanoTime();
    try {
      byte[] input = loadInput(id);
      Day<?> solver = instantiateSolver(id, input);

      log.info("Part 1: {}", solver.solvePart01());
      log.info("Execution time: {} ms", (System.nanoTime() - startTime) / 1_000_000);
      log.info("Part 2: {}", solver.solvePart02());
      log.info("Execution time: {} ms", (System.nanoTime() - startTime) / 1_000_000);
      solver.solvePart02String().ifPresent(s -> log.info("Part 3: {}", s));

      log.info("Done Execution time: {} ms", (System.nanoTime() - startTime) / 1_000_000);
    } catch (Exception e) {
      throw new IllegalStateException("failed to run " + id, e);
    }
  }

  private static Day<?> instantiateSolver(PuzzleId id, byte[] input) throws ReflectiveOperationException {
    Class<?> clazz = Class.forName(id.solverClassName());
    Constructor<?> constructor = clazz.getConstructor(InputStream.class);
    return (Day<?>) constructor.newInstance(new ByteArrayInputStream(input));
  }
}
