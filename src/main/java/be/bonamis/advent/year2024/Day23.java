package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.tuple.*;

@Slf4j
@Getter
public class Day23 extends TextDaySolver {
  private final Input input;

  public Day23(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day23(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  record Input(List<Pair<String, String>> computerPairs) {

    public static Input of(List<String> puzzle) {
      return new Input(
          puzzle.stream()
              .map(
                  s -> {
                    String[] split = s.split("-");
                    return Pair.of(split[0], split[1]);
                  })
              .toList());
    }

    public Set<Set<String>> interConnectedComputers() {
      Set<Set<String>> list = new HashSet<>();
      for (int i = 0; i < computerPairs().size(); i++) {
        Pair<String, String> pair = computerPairs().get(i);
        var optional = findInterConnectedComputers(pair, i);
        for (Triple<String, String, String> triple : optional) {
          list.add(Set.of(triple.getLeft(), triple.getMiddle(), triple.getRight()));
        }
      }
      return list;
    }

    private List<Triple<String, String, String>> findInterConnectedComputers(
        Pair<String, String> pair, int i) {
      List<Triple<String, String, String>> resultList = new ArrayList<>();
      for (int j = i + 1; j < computerPairs().size(); j++) {

        Pair<String, String> nextPair = computerPairs().get(j);
        log.debug("original pair: {} next pair: {}", pair, nextPair);

        boolean cond01 = nextPair.getLeft().equals(pair.getLeft());
        boolean cond02 = nextPair.getLeft().equals(pair.getRight());
        boolean cond03 = nextPair.getRight().equals(pair.getLeft());
        boolean cond04 = nextPair.getRight().equals(pair.getRight());

        if (cond01 || cond02 || cond03 || cond04) {

          var list =
              List.of(pair.getLeft(), pair.getRight(), nextPair.getLeft(), nextPair.getRight());
          log.debug("list: {}", list);

          List<String> result =
              list.stream().filter(item -> Collections.frequency(list, item) == 1).toList();
          log.debug("result: {}", result);
          var resultPair = Pair.of(result.get(0), result.get(1));

          Optional<Pair<String, String>> first =
              this.computerPairs().stream()
                  .filter(
                      pair1 ->
                          (pair1.getLeft().equals(resultPair.getLeft())
                                  && pair1.getRight().equals(resultPair.getRight()))
                              || (pair1.getLeft().equals(resultPair.getRight())
                                  && pair1.getRight().equals(resultPair.getLeft())))
                  .findFirst();

          List<String> set =
              new ArrayList<>(
                  new HashSet<>(
                      Arrays.asList(
                          pair.getLeft(),
                          pair.getRight(),
                          nextPair.getLeft(),
                          nextPair.getRight())));

          log.debug("first: {}", first);
          first.ifPresent(
              stringStringPair -> {
                log.debug("graal: {}", stringStringPair);
                Triple<String, String, String> stringStringStringTriple =
                    Triple.of(set.get(0), set.get(1), set.get(2));
                log.debug("graal triple: {}", stringStringStringTriple);
              });

          if (first.isPresent()) {
            resultList.add(Triple.of(set.get(0), set.get(1), set.get(2)));
          }
        }
      }
      return resultList;
    }
  }

  @Override
  public long solvePart01() {

    Stream<Set<String>> t =
        this.input.interConnectedComputers().stream()
            .filter(s -> s.stream().anyMatch(ss -> ss.startsWith("t")));
    List<Set<String>> list = t.toList();
    list.forEach(s -> log.debug("s: {}", s));
    return list.size();
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
