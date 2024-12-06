package be.bonamis.advent;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.file.*;
import java.text.*;

@Slf4j
public class DayDataRetriever {

  public static void main(String[] args) {
    runCode(System.getenv("YEAR"), System.getenv("DAY"));
  }

  private static InputStream downloadInput(int year, int dayNumber) {
    String puzzleInputUrl = dayUrl(year, dayNumber) + "/input";
    log.info("downloading input from: {}", puzzleInputUrl);
    try {
      HttpURLConnection con = (HttpURLConnection) new URL(puzzleInputUrl).openConnection();
      con.addRequestProperty("Cookie", "session=" + System.getenv("ADVENT_SESSION"));
      return con.getInputStream();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  static InputStream inputFile(int year, int dayNumber) {
    try {
      Path homeDir = Path.of(System.getProperty("user.home") + "/personal-dev/advent-inputs");
      String inputFileName = String.format("input-%02d-%02d.txt", year, dayNumber);
      File inputFile = new File(homeDir + "/" + inputFileName);
      log.info("Looking for input file: {}", inputFile);

      if (inputFile.exists()) {
        log.info("Reading existing input from file: {}", inputFile.getPath());
        return Files.newInputStream(inputFile.toPath());
      } else {
        log.info("No input file found: {}", inputFile.getPath());
        InputStream inputStream = downloadInput(year, dayNumber);
        Path path = Paths.get(inputFile.getPath());
        Files.copy(inputStream, path);
        log.info("Writing input to file: {}", path);
        return Files.newInputStream(path);
      }
    } catch (Exception e) {
      log.error("Error reading input file", e);
      throw new RuntimeException(e);
    }
  }

  private static String dayUrl(int year, int day) {
    return "https://adventofcode.com/" + year + "/day/" + day;
  }

  public static void runCode(String inputYear, String inputDay) {
    log.info("runCode input from day: {} and year: {}", inputDay, inputYear);
    long startTime = System.nanoTime();

    try {
      int year = Integer.parseInt(inputYear);
      int dayNumber = Integer.parseInt(inputDay);

      InputStream inputStream = inputFile(year, dayNumber);

      NumberFormat formatter = new DecimalFormat("00");
      Class<?> clazz =
          Class.forName(
              "be.bonamis.advent.year" + year + "." + "Day" + formatter.format(dayNumber));
      Constructor<?> constructor = clazz.getConstructor(InputStream.class);

      Object instance = constructor.newInstance(inputStream);

      Method solvePart01Method = clazz.getMethod("solvePart01");
      Method solvePart02Method = clazz.getMethod("solvePart02");
      Method solvePart02StringMethod = clazz.getMethod("solvePart02String");

      Object resultPart01 = solvePart01Method.invoke(instance);
      log.info("Execution time: {} ms", (System.nanoTime() - startTime) / 1000000);
      Object resultPart02 = solvePart02Method.invoke(instance);
      log.info("Execution time: {} ms", (System.nanoTime() - startTime) / 1000000);
      Object resultPart03 = solvePart02StringMethod.invoke(instance);

      log.info("Part 1: {}", resultPart01);
      log.info("Part 2: {}", resultPart02);
      log.info("Part 3: {}", resultPart03);
      log.info("Done Execution time: {} ms", (System.nanoTime() - startTime) / 1000000);

    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
