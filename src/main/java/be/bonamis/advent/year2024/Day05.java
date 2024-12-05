package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day05 extends TextDaySolver {

  public Day05(InputStream inputStream) {
    super(inputStream);

    /*int blank = puzzle.indexOf("");
    log.info("blank: {}", blank);*/
  }

  private final List<Rule> rules = new ArrayList<>();
  private final List<Integer> updates = new ArrayList<>();

  public Day05(List<String> puzzle) {
    super(puzzle);
  }

  /*
  """
      47|53
      97|13
      97|61
      97|47
      75|29
      61|13
      75|53
      29|13
      97|29
      53|29
      61|53
      97|53
      61|29
      47|13
      75|47
      97|75
      47|61
      75|61
      47|29
      75|13
      53|13

      75,47,61,53,29
      97,61,53,29,13
      75,29,13
      75,97,47,61,53
      61,13,29
      97,13,75,29,47
      """;
   */

  record Rule(int a, int b) {}

  record Updates(List<Integer> updates) {
    int middle() {
      return updates.get(updates.size() / 2);
    }
  }

  @Override
  public long solvePart01() {

    Integer blank =
        IntStream.range(0, puzzle.size())
            .filter(i -> puzzle.get(i).isEmpty())
            .boxed()
            .findFirst()
            .orElseThrow();
    log.info("first: {}", blank);

    List<Rule> ruleList =
        IntStream.range(0, blank)
            .mapToObj(
                i -> {
                  String line = puzzle.get(i);
                  String[] parts = line.split("\\|");
                  return new Rule(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
            .toList();

    Map<Integer, List<Integer>> collect =
        ruleList.stream()
            .collect(
                Collectors.groupingBy(Rule::a, Collectors.mapping(Rule::b, Collectors.toList())));

    Map<Integer, List<Integer>> collectb =
        ruleList.stream()
            .collect(
                Collectors.groupingBy(Rule::b, Collectors.mapping(Rule::a, Collectors.toList())));

    log.info("rules: {}", ruleList);
    List<Updates> updatesList =
        IntStream.range(blank + 1, puzzle.size())
            .mapToObj(
                i -> {
                  String line = puzzle.get(i);
                  String[] parts = line.split(",");
                  List<Integer> updates = Arrays.stream(parts).map(Integer::parseInt).toList();
                  return new Updates(updates);
                })
            .toList();
    log.info("updates: {}", updatesList);

    log.info("collect: {}", collect);
    log.info("collectb: {}", collectb);
    log.info("++++++++++++++++++++++++++++ ");

    Updates updates1 = updatesList.get(4);

    boolean updateCheck = updateCheck(updates1, collect);
    log.info("updateCheck: {}", updateCheck);

    //return 257;
    return updatesList.stream().filter(updates -> updateCheck(updates, collect)).map(Updates::middle).reduce(0, Integer::sum);

  }

  private boolean updateCheck(Updates updates1, Map<Integer, List<Integer>> collect) {
    List<Integer> updates2 = updates1.updates();
    for (int i = 0; i < updates2.size() - 1; i++) {
      Integer number = updates2.get(i);
      List<Integer> collectGet = collect.get(number);
      List<Integer> nextNumbers =
          IntStream.range(i + 1, updates2.size()).mapToObj(updates2::get).toList();

      for (Integer nextNumber : nextNumbers) {
        log.info("nextNumber: {} collectGet: {}", nextNumber, collectGet);
        if (collectGet == null) {
          log.info("updateCheck: {}", false);
          return false;
        }
        boolean contains = collectGet.contains(nextNumber);
        if (!contains) {
          log.info("updateCheck: {}", false);
          return false;
        }
      }
    }
    log.info("updateCheck: {}", true);
    return true;
  }

  @Override
  public long solvePart02() {
    return 2;
  }
}
