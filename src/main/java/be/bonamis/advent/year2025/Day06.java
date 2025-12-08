package be.bonamis.advent.year2025;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day06 extends TextDaySolver {

  private final Input input;

  public Day06(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day06(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
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
      log.debug("Line: {}", size);
      for (String s : puzzle) {
        log.debug(
            "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug("Line: {}", s);
      }
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
      switch (operand) {
        case ADD -> {
          long sum = columns.sum();
          log.debug("Sum for column {}: {}", i, sum);
          return sum;
        }
        case MULTIPLY -> {
          long product = columns.reduce(1L, (a, b) -> a * b);
          log.debug("Product for column {}: {}", i, product);
          return product;
        }
        default -> throw new IllegalArgumentException("Unknown operand: " + operand);
      }
    };
  }

  @Override
  public long solvePart02() {
    return 1000;
  }
}
