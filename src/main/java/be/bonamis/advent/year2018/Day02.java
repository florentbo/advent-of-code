package be.bonamis.advent.year2018;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

@Slf4j
public class Day02 extends DaySolver<String> {

  public Day02(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    long left = toPair(this.puzzle).mapToInt(Pair::getValue0).reduce(0, Integer::sum);
    long right = toPair(this.puzzle).mapToInt(Pair::getValue1).reduce(0, Integer::sum);

    return left * right;
  }

  Stream<Pair<Integer, Integer>> toPair(List<String> lines) {
    return lines.stream().map(this::lettersInventory).map(this::map2);
  }

  Pair<Integer, Integer> map2(Map<String, Long> lettersInventory) {
    return Pair.with(find(lettersInventory, 2), find(lettersInventory, 3));
  }

  Integer find(Map<String, Long> lettersInventory, int i) {
    return lettersInventory.values().stream().filter(s -> s == i).findFirst().map(s -> 1).orElse(0);
  }

  Map<String, Long> lettersInventory(String line) {
    return Arrays.stream(line.split(""))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  @Override
  public long solvePart02() {
    return 999L;
  }

  public String solvePart02Bis() {

    Set<Set<String>> combinations = Sets.combinations(Sets.newTreeSet(this.puzzle), 2);
    for (Set<String> combination : combinations) {
      List<String> list = Lists.newArrayList(combination);
      List<String> common = new ArrayList<>();
      for (int i = 0; i < list.get(0).split("").length; i++) {
        String s = list.get(0).split("")[i];
        String s1 = list.get(1).split("")[i];
        if (s.equals(s1)) {
          common.add(s);
        }
      }
      if (list.get(0).split("").length - common.size() == 1) {
        String join = String.join("", common);
        log.info("common: {}", join);
        return join;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2018/02/2018_02_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day02 day = new Day02(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02Bis());
  }
}
