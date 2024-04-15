package be.bonamis.advent.year2020;

import static be.bonamis.advent.DayDataRetriever.*;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day06 extends TextDaySolver {

  public Day06(InputStream sample) {
    super(sample);
  }

  static Groups groups(List<String> input) {
    Stream<Integer> blankLines =
        IntStream.range(0, input.size()).filter(value -> input.get(value).trim().isEmpty()).boxed();
    List<Integer> indexes =
        Stream.concat(Stream.concat(Stream.of(-1), blankLines), Stream.of(input.size())).toList();
    List<Group> groups =
        IntStream.range(0, indexes.size() - 1)
            .mapToObj(index -> group(input, indexes.get(index), indexes.get(index + 1)))
            .toList();

    return Groups.of(groups);
  }

  private static Group group(List<String> input, Integer start, Integer end) {
    return Group.of(IntStream.range(start + 1, end).mapToObj(input::get).toList());
  }

  @Override
  public long solvePart01() {
    Groups groups = groups(this.puzzle);
    return groups.sumCounts();
  }

  @Override
  public long solvePart02() {
    return 777;
  }

  record Group(List<String> answers) {
    static Group of(List<String> input) {
      return new Group(input);
    }

    public int countAnswers() {
      Stream<IntStream> intStreamStream = this.answers.stream().map(String::chars);
      return merge(intStreamStream).size();
    }

    private Set<Integer> merge(Stream<IntStream> intStreamStream) {
      Set<Integer> merge = new HashSet<>();
      intStreamStream.forEach(
          intStream -> {
            Set<Integer> boxed = intStream.boxed().collect(Collectors.toSet());
            merge.addAll(boxed);
          });

      return merge;
    }
  }

  record Groups(List<Group> groups) {
    static Groups of(List<Group> groups) {
      return new Groups(groups);
    }

    int sumCounts() {
      return this.groups.stream().mapToInt(Group::countAnswers).sum();
    }
  }

  public static void main(String[] args) {
    String puzzleInputUrl = dayUrl(2020, 6) + "/input";
    InputStream inputStream = downloadInput(puzzleInputUrl);
    Day06 day05 = new Day06(inputStream);
    System.out.println("Day 06");
    System.out.println("Part 1: " + day05.solvePart01());
    System.out.println("Part 2: " + day05.solvePart02());
  }
}
