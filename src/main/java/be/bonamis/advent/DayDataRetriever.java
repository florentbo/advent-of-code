package be.bonamis.advent;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@Slf4j
public class DayDataRetriever {

  public static void main(String[] args) {
    runCode();
  }

  public static InputStream downloadInput(String puzzleInputUrl) {
    try {
      HttpURLConnection con = (HttpURLConnection) new URL(puzzleInputUrl).openConnection();
      String cookie = System.getenv("ADVENT_SESSION");
      con.addRequestProperty("Cookie", "session=" + cookie);
      return con.getInputStream();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String dayUrl(int year, int day) {
    return "https://adventofcode.com/" + year + "/day/" + day;
  }

  public static void runCode() {
    try {
      int year = Integer.parseInt(System.getenv("YEAR"));
      int dayNumber = Integer.parseInt(System.getenv("DAY"));

      NumberFormat formatter = new DecimalFormat("00");
      String puzzleInputUrl = dayUrl(year, dayNumber) + "/input";
      InputStream inputStream = downloadInput(puzzleInputUrl);

      Class<?> clazz =
          Class.forName(
              "be.bonamis.advent.year" + year + "." + "Day" + formatter.format(dayNumber));
      Constructor<?> constructor = clazz.getConstructor(InputStream.class);

      Object instance = constructor.newInstance(inputStream);

      Method solvePart01Method = clazz.getMethod("solvePart01");
      Method solvePart02Method = clazz.getMethod("solvePart02");

      Object resultPart01 = solvePart01Method.invoke(instance);
      Object resultPart02 = solvePart02Method.invoke(instance);

      log.info("Part 1: {}", resultPart01);
      log.info("Part 2: {}", resultPart02);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
