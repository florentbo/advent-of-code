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

      long findLargestN(int n) {
        List<Integer> result = new ArrayList<>();
        int currentPosition = -1;

        for (int i = 0; i < n; i++) {
          int remainingNeeded = n - i - 1;

          int searchStart = currentPosition + 1;
          int searchEnd = batteries.size() - remainingNeeded - 1;

          int largest =
              batteries.subList(searchStart, searchEnd + 1).stream()
                  .mapToInt(Integer::intValue)
                  .max()
                  .orElseThrow();

          int largestIndex =
              IntStream.range(searchStart, searchEnd + 1)
                  .filter(ii -> batteries.get(ii) == largest)
                  .findFirst()
                  .orElseThrow();

          result.add(largest);
          currentPosition = largestIndex;
        }

        String resultStr = result.stream().map(String::valueOf).collect(Collectors.joining());
        return Long.parseLong(resultStr);
      }

      int largestJoltage() {
        return (int) findLargestN(2);
      }

	  long largestJoltagePart2() {
        return findLargestN(12);
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
	  Stream<Long> integerStream =
			  this.getInput().banks().stream().map(Input.Bank::largestJoltagePart2);
	  return integerStream.mapToLong(Long::longValue).sum();
  }
}
