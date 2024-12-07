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

  private static final List<String> OPERATORS = List.of("+", "*");

  public static boolean canBeMade(Equation input) {
    int positions = input.numbers().size() - 1;
    List<List<String>> lists = generateOperatorCombinations(positions);
    return check(input, lists);
  }

  private static boolean check(Equation input, List<List<String>> combinations) {
    log.debug("input: {}", input);
    List<Long> numbers = input.numbers();

    for (List<String> operators : combinations) {
      log.debug("operators: {}", operators);

      long calculusResult = numbers.get(0);
      for (int i = 0; i < operators.size(); i++) {
        String op = operators.get(i);
        long nextNum = numbers.get(i + 1);

        if (op.equals("+")) {
          calculusResult += nextNum;
        } else if (op.equals("*")) {
          calculusResult *= nextNum;
        }
      }

      if (calculusResult == input.result()) {
        return true;
      }
    }

    return false;
  }

  static List<List<String>> generateOperatorCombinations(int positions) {
    List<List<String>> result = new ArrayList<>();
    generateCombinations(new ArrayList<>(), positions, result);
    return result;
  }

  private static void generateCombinations(
      List<String> current, int remaining, List<List<String>> result) {
    if (remaining == 0) {
      result.add(new ArrayList<>(current));
      return;
    }

    for (String op : OPERATORS) {
      current.add(op);
      generateCombinations(current, remaining - 1, result);
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
    return 1;
  }
}
