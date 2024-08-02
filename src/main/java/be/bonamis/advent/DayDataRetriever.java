package be.bonamis.advent;

import be.bonamis.advent.year2017.Day04;
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
    runCode(System.getenv("YEAR"), System.getenv("DAY"), System.getenv("ADVENT_SESSION"));
  }

  public static InputStream downloadInput(String puzzleInputUrl, String cookie) {
    try {
      HttpURLConnection con = (HttpURLConnection) new URL(puzzleInputUrl).openConnection();
      con.addRequestProperty("Cookie", "session=" + cookie);
      return con.getInputStream();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String dayUrl(int year, int day) {
    return "https://adventofcode.com/" + year + "/day/" + day;
  }

  public static void runCode(Class<Day04> day04Class) {
    String year = String.valueOf(extractYear(day04Class));
    String dayNumber = String.valueOf(extractDay(day04Class));
    runCode(year, dayNumber, System.getenv("ADVENT_SESSION"));
  }

  public static void runCode(String inputYear, String inputDay, String cookie) {
    try {
      int year = Integer.parseInt(inputYear);
      int dayNumber = Integer.parseInt(inputDay);

      NumberFormat formatter = new DecimalFormat("00");
      String puzzleInputUrl = dayUrl(year, dayNumber) + "/input";
      InputStream inputStream = downloadInput(puzzleInputUrl, cookie);

      Class<?> clazz =
          Class.forName(
              "be.bonamis.advent.year" + year + "." + "Day" + formatter.format(dayNumber));
      Constructor<?> constructor = clazz.getConstructor(InputStream.class);

      Object instance = constructor.newInstance(inputStream);

      Method solvePart01Method = clazz.getMethod("solvePart01");
      Method solvePart02Method = clazz.getMethod("solvePart02");
      Method solvePart02StringMethod = clazz.getMethod("solvePart02String");

      Object resultPart01 = solvePart01Method.invoke(instance);
      Object resultPart02 = solvePart02Method.invoke(instance);
      Object resultPart03 = solvePart02StringMethod.invoke(instance);

      log.info("Part 1: {}", resultPart01);
      log.info("Part 2: {}", resultPart02);
      log.info("Part 3: {}", resultPart03);

    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static int extractYear(Class<?> clazz) {
    Package pkg = clazz.getPackage();
    String packageName = pkg.getName();
    String yearPart = packageName.replaceAll("[^0-9]", "");
    return Integer.parseInt(yearPart);
  }

  public static int extractDay(Class<?> clazz) {
    String className = clazz.getSimpleName();
    String dayPart = className.replaceAll("[^0-9]", "");
    return Integer.parseInt(dayPart);
  }
}
