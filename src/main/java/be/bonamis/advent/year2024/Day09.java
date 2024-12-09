package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day09 extends TextDaySolver {

  public Day09(InputStream inputStream) {
    super(inputStream);
  }

  public Day09(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    log.info("size: {}", this.puzzle.size());
    String line = this.puzzle.get(0);
    log.info("line: {}", line);
    int length = line.length();
    log.debug("line length: {}", length);

    List<String> compacted = compact(line);
    log.info("compacted: {}", compacted);
    log.info("compacted size: {}", compacted.size());


    String joined = String.join("", compacted);
    int lefterDotPosition = lefterDotPosition(joined);
    log.debug("multiplying:+++++++++++ till lefterDotPosition: {}", lefterDotPosition);
    var list = IntStream.range(0, lefterDotPosition).mapToObj(i -> {
      int value = Integer.parseInt(String.valueOf(joined.charAt(i)));
      log.debug("value: {}", value);
        //log.debug("result: {}", result);
      return value * i;
    }).reduce(0, Integer::sum);
    log.debug("list: {}", list);

    return list;
  }

  static List<String> compact(String line) {
    List<Integer> list = line.chars().mapToObj(Character::toString).map(Integer::parseInt).toList();

    List<String> list1 =
        IntStream.range(0, list.size())
            .mapToObj(
                index -> {
                  Integer value = list.get(index);
                  //log.debug("value: {}", value);
                  if (index % 2 == 0) {
                    int evenValueIndex = index / 2;
                    //log.debug("evenValueIndex: {}", evenValueIndex);
                    String numbers =
                        String.join("", Collections.nCopies(value, evenValueIndex + ""));
                    //log.debug("numbers: {}", numbers);
                    return numbers;
                  } else {
                    String dots = String.join("", Collections.nCopies(value, "."));
                    //log.debug("dots: {}", dots);
                    return dots;
                  }
                })
            .toList();
    log.info("list1: {}", list1);
/*
0..111....22222
 */
    String joined = String.join("", list1);
    log.debug("joined: {}", joined);
    List<String> list2 = Arrays.asList(joined.split(""));
    int righterNumberPosition = righterNumberPosition(joined);
    log.debug("righterNumberPosition: {}", righterNumberPosition);
    int lefterDotPosition = lefterDotPosition(joined);
    log.debug("lefterDotPosition: {}", lefterDotPosition);
    while (righterNumberPosition > lefterDotPosition) {
      String element = list2.get(righterNumberPosition);
      list2.set(lefterDotPosition, element);
      list2.set(righterNumberPosition, ".");
      joined = String.join("", list2);
      log.debug("joined: {}", joined);
      righterNumberPosition = righterNumberPosition(joined);
      log.info("righterNumberPosition: {}", righterNumberPosition);
      lefterDotPosition = lefterDotPosition(joined);
      log.info("lefterDotPosition: {}", lefterDotPosition);
    }

    return list2;
  }

  static int righterNumberPosition(String line) {
    return IntStream.range(0, line.length())
        .mapToObj(i -> line.length() - 1 - i) // reverse indices
        .filter(i -> Character.isDigit(line.charAt(i)))
        .findFirst()
        .orElseThrow();
  }

  static int lefterDotPosition(String line) {
    return IntStream.range(0, line.length())
        .boxed()
        .filter(i -> line.charAt(i) == '.')
        .findFirst()
        .orElseThrow();
  }

  @Override
  public long solvePart02() {
    return 4;
  }
}
