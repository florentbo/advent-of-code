package be.bonamis.advent.year2020;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
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
    return groups.sumAnyOneSayYesCounts();
  }

  @Override
  public long solvePart02() {
    Groups groups = groups(this.puzzle);
    return groups.sumEveryOneSayYesCounts();
  }

  record Group(List<String> answers) {
    static Group of(List<String> input) {
      return new Group(input);
    }

    public int countAnyOneSayYes() {
      Stream<IntStream> allAnswers = allAnswers();
      return merge(allAnswers).size();
    }

    private Stream<IntStream> allAnswers() {
      return this.answers.stream().map(String::chars);
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

    public int countEveryOneSayYes() {
      Stream<IntStream> allAnswers = allAnswers();
      return intersection(allAnswers).size();
    }

    private Collection<Integer> intersection(Stream<IntStream> allAnswers) {
      Stream<Set<Integer>> sets = allAnswers.map(toSet());
      return intersect(sets);
    }

    private Function<IntStream, Set<Integer>> toSet() {
      return intStream -> intStream.boxed().collect(Collectors.toSet());
    }
  }

  public static <T, C extends Collection<T>> Collection<T> intersect(Stream<C> stream) {
    final Iterator<C> allLists = stream.iterator();

    if (!allLists.hasNext()) return Collections.emptySet();

    final Set<T> result = new HashSet<>(allLists.next());
    while (allLists.hasNext()) {
      result.retainAll(new HashSet<>(allLists.next()));
    }
    return result;
  }

  record Groups(List<Group> groups) {
    static Groups of(List<Group> groups) {
      return new Groups(groups);
    }

    int sumAnyOneSayYesCounts() {
      return this.groups.stream().mapToInt(Group::countAnyOneSayYes).sum();
    }

    int sumEveryOneSayYesCounts() {
      return this.groups.stream().mapToInt(Group::countEveryOneSayYes).sum();
    }
  }
}
