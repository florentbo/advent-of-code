package be.bonamis.advent.year2025;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import be.bonamis.advent.common.CharGrid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day06 extends TextDaySolver {

  private final Input input;
  private final CharGrid grid;

  public Day06(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
    this.grid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
  }

  public Day06(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
    this.grid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
  }

  record Input(NumberLines numberLines, Operands operands) {
    record NumberLines(List<Numbers> lines) {}

    record Numbers(List<Long> numbers) {

      static Numbers of(String input) {
        String[] split = input.trim().split("\\s+");
        return new Numbers(Arrays.stream(split).map(Long::parseLong).toList());
      }
    }

    public enum Operand {
      ADD("+"),
      MULTIPLY("*");

      public final String symbol;

      Operand(String symbol) {
        this.symbol = symbol;
      }
    }

    record Operands(List<Operand> operands) {
      static Operands of(String symbols) {
        String[] split = symbols.trim().split("\\s+");

        List<Operand> operandList =
            Arrays.stream(split)
                .map(
                    s ->
                        switch (s) {
                          case "+" -> Operand.ADD;
                          case "*" -> Operand.MULTIPLY;
                          default -> throw new IllegalArgumentException("Unknown operand: " + s);
                        })
                .toList();
        return new Operands(operandList);
      }
    }

    public static Input of(List<String> puzzle) {
      int size = puzzle.size();
      Operands operands = Operands.of(puzzle.get(size - 1));
      List<Numbers> list =
          IntStream.range(0, size - 1).mapToObj(i -> Numbers.of(puzzle.get(i))).toList();
      return new Input(new NumberLines(list), operands);
    }
  }

  @Override
  public long solvePart01() {
    int size = this.input.operands().operands().size();
    Stream<Number> stream = IntStream.range(0, size).mapToObj(getIntFunction());
    List<Number> results = stream.toList();
    log.debug("Results: {}", results);
    return results.stream().mapToLong(Number::longValue).sum();
  }

  private IntFunction<Number> getIntFunction() {
    return i -> {
      Input.Operand operand = this.input.operands().operands().get(i);
      final LongStream columns =
          this.input.numberLines().lines().stream().mapToLong(numbers -> numbers.numbers().get(i));
      return executeOperand(operand, columns);
    };
  }

  private long executeOperand(Input.Operand operand, LongStream columns) {
    switch (operand) {
      case ADD -> {
        return columns.sum();
      }
      case MULTIPLY -> {
        return columns.reduce(1L, (a, b) -> a * b);
      }
      default -> throw new IllegalArgumentException("Unknown operand: " + operand);
    }
  }

  @Override
  public long solvePart02() {
    CharGrid flo = new CharGrid(this.puzzle.subList(0, this.puzzle.size() - 1));
    List<String> columns = flo.rowsAsLines2();
    log.debug("Columns: {}", columns);
    List<Integer> emptyColumns =
        IntStream.range(0, columns.size())
            .filter(i -> columns.get(i).trim().isEmpty())
            .boxed()
            .toList();
    log.debug("Empty columns at indices: {}", emptyColumns);
    List<List<String>> splitColumns = splitBySeparators(columns, emptyColumns);
    log.debug("Split columns: {}", splitColumns);
    Input.Operands operands = this.input.operands();
    log.debug("Operands: {}", operands);

    var list =
        IntStream.range(0, operands.operands().size())
            .mapToObj(
                i -> {
                  Input.Operand operand = operands.operands().get(i);
                  var column = splitColumns.get(i);
                  LongStream longStream = column.stream().mapToLong(s -> Long.parseLong(s.trim()));
                  log.debug("Processing operand {} for column {}", operand, column);
                  return executeOperand(operand, longStream);
                });
    log.debug("List: {}", list);

    return list.mapToLong(s -> s).sum();
  }

  private static <T> List<List<T>> splitBySeparators(List<T> list, List<Integer> separators) {
    List<List<T>> parts = new ArrayList<>();
    int start = 0;
    for (int sep : separators) {
      parts.add(list.subList(start, sep));
      start = sep + 1;
    }
    parts.add(list.subList(start, list.size()));
    return parts;
  }
}
