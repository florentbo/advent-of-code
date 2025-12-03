package be.bonamis.advent.year2025;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day03 extends TextDaySolver {

  private final Input input;

  public Day03(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day03(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  record Input(List<Bank> banks) {
    record Bank(List<Integer> batteries) {
      static Bank of(String line) {
        String[] split = line.split("");
        List<Integer> batteries = Arrays.stream(split).map(Integer::valueOf).toList();
        return new Bank(batteries);
      }

      int largestJoltage() {
        int largest =
            batteries.stream()
                .limit(batteries.size() - 1)
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
        List<Integer> largestIndexes =
            IntStream.range(0, batteries.size())
                .filter(i -> batteries.get(i) == largest)
                .boxed()
                .toList();

        List<Integer> list =
            largestIndexes.stream()
                .map(
                    i -> {
                      List<Integer> integers = batteries.subList(i + 1, batteries.size());
                      return integers.stream().mapToInt(Integer::intValue).max().orElse(0);
                    })
                .toList();
        int max = list.stream().mapToInt(Integer::intValue).max().orElseThrow();
        return Integer.parseInt(largest + "" + max);
      }
    }

    static Input of(List<String> lines) {
      List<Bank> banks = lines.stream().map(Bank::of).toList();
      return new Input(banks);
    }
  }

  @Override
  public long solvePart01() {
    Stream<Integer> integerStream =
        this.getInput().banks().stream().map(Input.Bank::largestJoltage);
    return integerStream.mapToLong(Integer::longValue).sum();
  }

  @Override
  public long solvePart02() {
    return 101L;
  }
}
