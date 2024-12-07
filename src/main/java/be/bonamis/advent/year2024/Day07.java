package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day07 extends TextDaySolver {

  public Day07(InputStream inputStream) {
    super(inputStream);
  }

  public Day07(List<String> puzzle) {
    super(puzzle);
  }

  public static boolean canBeMade(Equation input) {
    return canBeMade(input, OPERATORS);
  }

  public static boolean canBeMadeWithConcatenation(Equation input) {
    return canBeMade(input, OPERATORS_PART2);
  }

  public static boolean canBeMade(Equation input, List<String> operators) {
    int positions = input.numbers().size() - 1;
    List<List<String>> lists = generateOperatorCombinations(positions, operators);
    return check(input, lists);
  }

  private static boolean check(Equation input, List<List<String>> combinations) {
    return combinations.stream()
        .anyMatch(
            operators -> {
              long result = input.numbers().get(0);
              for (int i = 0; i < operators.size(); i++) {
                result = calculateOperation(result, input.numbers().get(i + 1), operators.get(i));
              }
              return result == input.result();
            });
  }

  private static long calculateOperation(long a, long b, String operator) {
    return switch (operator) {
      case "+" -> a + b;
      case "*" -> a * b;
      case "||" -> concat(a, b);
      default -> throw new IllegalArgumentException("Unknown operator: " + operator);
    };
  }

  private static long concat(long calculusResult, long nextNum) {
    String part0 = String.valueOf(calculusResult);
    String part1 = String.valueOf(nextNum);
    String concatenation = part0 + part1;
    return Long.parseLong(concatenation);
  }

  static List<List<String>> generateOperatorCombinations(int positions, List<String> operators) {
    List<List<String>> result = new ArrayList<>();
    generateCombinations(new ArrayList<>(), positions, result, operators);
    return result;
  }

  private static final List<String> OPERATORS = List.of("+", "*");

  private static final List<String> OPERATORS_PART2 = List.of("+", "*", "||");

  private static void generateCombinations(
      List<String> current, int remaining, List<List<String>> result, List<String> operators) {
    if (remaining == 0) {
      result.add(new ArrayList<>(current));
      return;
    }

    for (String op : operators) {
      current.add(op);
      generateCombinations(current, remaining - 1, result, operators);
      current.remove(current.size() - 1);
    }
  }

  public record Equation(long result, List<Long> numbers) {
    static Equation of(String line) {
      String[] parts = line.split(": ");
      log.debug("part 0: {}", parts[0]);
      log.debug("part 1: {}", parts[1]);
      List<Long> numbers = Arrays.stream(parts[1].split(" ")).map(Long::parseLong).toList();
      return new Equation(Long.parseLong(parts[0]), numbers);
    }
  }

  public record Equations(List<Equation> equations) {
    static Equations of(List<String> input) {
      return new Equations(input.stream().map(Equation::of).toList());
    }
  }

  @Override
  public long solvePart01() {
    Equations equations = Equations.of(this.puzzle);
    return equations.equations().stream()
        .filter(Day07::canBeMade)
        .map(Equation::result)
        .reduce(0L, Long::sum);
  }

  @Override
  public long solvePart02() {
    Equations equations = Equations.of(this.puzzle);
    return equations.equations().stream()
        .filter(Day07::canBeMadeWithConcatenation)
        .map(Equation::result)
        .reduce(0L, Long::sum);
  }
}
