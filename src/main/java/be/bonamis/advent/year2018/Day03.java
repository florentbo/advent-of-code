package be.bonamis.advent.year2018;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.year2021.Day05.LineSegment.Point;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day03 extends DaySolver<String> {

  public Day03(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    List<Claim> claims = this.puzzle.stream().map(this::extractedNumbers).toList();
    Map<Point, Collection<Integer>> map = claims(claims);

    return map.values().stream().filter(value -> value.size() > 1).count();
  }

  private Map<Point, Collection<Integer>> claims(List<Claim> claims) {
    Multimap<Point, Integer> multimap2 = ArrayListMultimap.create();

    for (Claim numbers : claims) {
      addPoints(
          multimap2,
          numbers.xCoordinate(),
          numbers.yCoordinate(),
          numbers.width(),
          numbers.height(),
          numbers.id());
    }

    // log.info("multimap: {}", multimap2);
    Map<Point, Collection<Integer>> map = multimap2.asMap();
    /*for (Map.Entry<Point, Collection<Integer>> pointCollectionEntry : map.entrySet()) {
      log.info("pointCollectionEntry: {}", pointCollectionEntry);
    }

    Collection<Collection<Integer>> values = map.values();
    log.info("value size: {}", values.size());
    for (Collection<Integer> value : values) {
      log.info("value: {}", value);
    }*/
    return map;
  }

  static int getNextNumber(Matcher matcher) {
    return matcher.find() ? Integer.parseInt(matcher.group()) : 999;
  }

  Claim extractedNumbers(String line1) {
    Matcher matcher = Pattern.compile("\\d+").matcher(line1);
    return new Claim(
        getNextNumber(matcher),
        getNextNumber(matcher),
        getNextNumber(matcher),
        getNextNumber(matcher),
        getNextNumber(matcher));
  }

  void addPoints(
      Multimap<Point, Integer> multimap2, int startX, int startY, int wide, int tall, int pointId) {
    Point point = new Point(startX, startY);
    int x = point.getX();
    int y = point.getY();
    for (int i = x; i < x + wide; i++) {
      for (int j = y; j < y + tall; j++) {
        multimap2.put(new Point(i, j), pointId);
      }
    }
  }

  @Override
  public long solvePart02() {
    List<Claim> claims = this.puzzle.stream().map(this::extractedNumbers).toList();

    Map<Integer, Long> collect =
        claims(claims).values().stream()
            .filter(value -> value.size() == 1)
            .map(list -> list.stream().toList().get(0))
            .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

    log.info("collect: {}", collect);
    Claim claimm = claims.get(2);

    Optional<Map.Entry<Integer, Long>> first =
        collect.entrySet().stream()
            .filter(
                entry -> {
                  Claim claim = claims.get(entry.getKey() - 1);
                  return entry.getValue() == (long) claim.width * claim.height;
                })
            .findFirst();

    log.info("claim size: {} empty: {}", claimm.width * claimm.height, collect.get(3));

    long count = claims(claims).values().stream().filter(value -> value.size() == 1).count();

    log.info("count: {}", count);

    return first.map(Map.Entry::getKey).orElseThrow();
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2018/03/2018_03_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day03 day = new Day03(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  record Claim(int id, int xCoordinate, int yCoordinate, int width, int height) {}
}
