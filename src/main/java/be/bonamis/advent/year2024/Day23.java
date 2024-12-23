package be.bonamis.advent.year2024;

import static be.bonamis.advent.year2024.Day13.LineEquation.*;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.*;

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
      // for (int i = 0; i < 2; i++) {
      for (int i = 0; i < computerPairs().size(); i++) {
        Pair<String, String> pair = computerPairs().get(i);
        Optional<Triple<String, String, String>> optional = findInterConnectedComputers(pair, i);
        log.debug("optional: {}", optional);
        optional.ifPresent(e -> list.add(Set.of(e.getLeft(), e.getMiddle(), e.getRight())));
      }

      return list;
    }

    private Optional<Triple<String, String, String>> findInterConnectedComputers(
        Pair<String, String> pair, int i) {
      for (int j = i + 1; j < computerPairs().size(); j++) {
        Pair<String, String> nextPair = computerPairs().get(j);
        log.debug("original pair: {} next pair: {}", pair, nextPair);

        boolean cond01 = nextPair.getLeft().equals(pair.getLeft());
        boolean cond02 = nextPair.getLeft().equals(pair.getRight());
        boolean cond03 = nextPair.getRight().equals(pair.getLeft());
        boolean cond04 = nextPair.getRight().equals(pair.getRight());

        if (cond01 || cond02 || cond03 || cond04) {
          List<String> set =
              new ArrayList<>(
                  new HashSet<>(
                      Arrays.asList(
                          pair.getLeft(),
                          pair.getRight(),
                          nextPair.getLeft(),
                          nextPair.getRight())));
          log.debug("set: {}", set);
          return Optional.of(Triple.of(set.get(0), set.get(1), set.get(2)));
        }
      }
      return Optional.empty();
    }
  }

  @Override
  public long solvePart01() {

    Stream<Set<String>> t = this.input.interConnectedComputers().stream()
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
