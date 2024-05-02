package be.bonamis.advent.year2017;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
@Getter
public class Day02 extends TextDaySolver {

  public Day02(InputStream sample) {
    super(sample);
  }

  @Override
  public long solvePart01() {
    return puzzle.stream().map(Row::of).mapToInt(Row::difference).sum();
  }

  @Override
  public long solvePart02() {
    return puzzle.stream().map(Row::of).mapToInt(Row::evenlyDivisibleValuesDifference).sum();
  }

  record Row(List<Integer> values) {
    static Row of(String line) {
      String lineWithOnlyNumbers = line.replaceAll("\\s+", " ");
      return new Row(Stream.of(lineWithOnlyNumbers.split(" ")).map(Integer::parseInt).toList());
    }

    public int min() {
      return values.stream().mapToInt(Integer::intValue).min().orElseThrow();
    }

    public int max() {
      return values.stream().mapToInt(Integer::intValue).max().orElseThrow();
    }

    public int difference() {
      return max() - min();
    }

    public Stream<Pair<Integer, Integer>> combinations() {
      return combinations(values).stream().map(pair -> Pair.of(pair.get(0), pair.get(1)));
    }

    public static List<List<Integer>> combinations(List<Integer> numbers) {
      List<List<Integer>> combinations = new ArrayList<>();
      for (int i = 0; i < numbers.size(); i++) {
        for (int j = i + 1; j < numbers.size(); j++) {
          combinations.add(Arrays.asList(numbers.get(i), numbers.get(j)));
        }
      }
      return combinations;
    }

    public Pair<Integer, Integer> evenlyDivisibleValues() {
      return combinations()
          .filter(
              pair ->
                  pair.getLeft() % pair.getRight() == 0 || pair.getRight() % pair.getLeft() == 0)
          .findFirst()
          .orElseThrow();
    }

    public int evenlyDivisibleValuesDifference() {
      Pair<Integer, Integer> pair = evenlyDivisibleValues();
      return Math.max(pair.getLeft(), pair.getRight()) / Math.min(pair.getLeft(), pair.getRight());
    }
  }
}
