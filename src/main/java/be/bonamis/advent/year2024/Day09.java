package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.stream.*;

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
    log.debug("line: {}", line);

    Day09Input[] day09Inputs = compactToArray(line);
    int lefterDotPosition2 = lefterDotPosition(day09Inputs);

    return IntStream.range(0, lefterDotPosition2)
        .mapToObj(
            i -> {
              long value = day09Inputs[i].value();
              log.debug("value: {} at index: {}", value, i);
              return value * i;
            })
        .reduce(0L, Long::sum);
  }

  interface Day09Input {
    boolean isDot();

    boolean isNumber();

    Long value();

    String valueAsString();
  }

  record Number(Long value) implements Day09Input {

    static Number of(int input) {
      return new Number((long) input);
    }

    @Override
    public boolean isDot() {
      return false;
    }

    @Override
    public boolean isNumber() {
      return true;
    }

    @Override
    public String valueAsString() {
      return value.toString();
    }
  }

  static class Dot implements Day09Input {
    @Override
    public boolean isDot() {
      return true;
    }

    @Override
    public boolean isNumber() {
      return false;
    }

    @Override
    public Long value() {
      return null;
    }

    @Override
    public String valueAsString() {
      return ".";
    }
  }

  static List<String> compact(String line) {
    Day09Input[] inputsArray = compactToArray(line);

    return print(inputsArray);
  }

  private static List<String> print(Day09Input[] inputsArray) {
    List<String> list = Arrays.stream(inputsArray).map(Day09Input::valueAsString).toList();
    log.debug("list: {}", list);
    return list;
  }

  static Day09Input[] compactToArray(String line) {
    var inputsArray = parse(line);

    int lefterDotPosition = lefterDotPosition(inputsArray);
    int righterNumberPosition = righterNumberPosition(inputsArray);

    while (lefterDotPosition < righterNumberPosition) {
      Day09Input day09Input = inputsArray[righterNumberPosition];
      log.debug("day09Input: {}", day09Input);
      inputsArray[lefterDotPosition] = day09Input;
      inputsArray[righterNumberPosition] = new Dot();
      print(inputsArray);
      lefterDotPosition = lefterDotPosition(inputsArray);
      righterNumberPosition = righterNumberPosition(inputsArray);
    }

    return inputsArray;
  }

  private static Day09Input[] parse(String line) {
    List<Integer> list = line.chars().mapToObj(Character::toString).map(Integer::parseInt).toList();

    var inputLists =
        IntStream.range(0, list.size())
            .mapToObj(
                index -> {
                  Integer value = list.get(index);
                  if (index % 2 == 0) {
                    int evenValueIndex = index / 2;
                    return IntStream.range(0, value)
                        .mapToObj(i -> Number.of(evenValueIndex))
                        .toList();
                  } else {
                    return IntStream.range(0, value).mapToObj(i -> new Dot()).toList();
                  }
                })
            .toList();

    var inputs = inputLists.stream().flatMap(List::stream).toList();

    var inputsArray = inputs.toArray(new Day09Input[0]);
    return inputsArray;
  }

  static int lefterDotPosition(Day09Input[] line) {
    return IntStream.range(0, line.length)
        .boxed()
        .filter(i -> line[i].isDot())
        .findFirst()
        .orElseThrow();
  }

  static int righterNumberPosition(Day09Input[] line) {
    return IntStream.range(0, line.length)
        .mapToObj(i -> line.length - 1 - i) // reverse indices
        .filter(i -> line[i].isNumber())
        .findFirst()
        .orElseThrow();
  }

  @Override
  public long solvePart02() {
    return 4;
  }
}
