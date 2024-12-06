package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day05 extends TextDaySolver {

  private final Day05Input input;

  public Day05(InputStream inputStream) {
    super(inputStream);
    input = Day05Input.from(puzzle);
  }

  public Day05(List<String> puzzle) {
    super(puzzle);
    input = Day05Input.from(puzzle);
  }

  record Rule(int a, int b) {}

  record Updates(List<Integer> updates) {
    int middle() {
      return updates.get(updates.size() / 2);
    }
  }

  record Day05Input(List<Rule> rules, List<Updates> updates) {
    static Day05Input from(List<String> puzzle) {
      Integer blank =
          IntStream.range(0, puzzle.size())
              .filter(i -> puzzle.get(i).isEmpty())
              .boxed()
              .findFirst()
              .orElseThrow();
      log.debug("first: {}", blank);

      List<Rule> ruleList =
          IntStream.range(0, blank)
              .mapToObj(
                  i -> {
                    String line = puzzle.get(i);
                    String[] parts = line.split("\\|");
                    return new Rule(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                  })
              .toList();

      log.debug("rules: {}", ruleList);
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
      return new Day05Input(ruleList, updatesList);
    }
  }

  @Override
  public long solvePart01() {
    Map<Integer, List<Integer>> collect =
        this.input.rules.stream()
            .collect(
                Collectors.groupingBy(Rule::a, Collectors.mapping(Rule::b, Collectors.toList())));

    log.debug("collect: {}", collect);

    return this.input.updates.stream()
        .filter(updates -> updateCheck(updates, collect))
        .map(Updates::middle)
        .reduce(0, Integer::sum);
  }

  private boolean updateCheck(Updates updates1, Map<Integer, List<Integer>> collect) {
    List<Integer> updates2 = updates1.updates();
    for (int i = 0; i < updates2.size() - 1; i++) {
      Integer number = updates2.get(i);
      List<Integer> collectGet = collect.get(number);
      List<Integer> nextNumbers =
          IntStream.range(i + 1, updates2.size()).mapToObj(updates2::get).toList();

      for (Integer nextNumber : nextNumbers) {
        if (collectGet == null || !collectGet.contains(nextNumber)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public long solvePart02() {
    List<Rule> rules = this.input.rules;
    Map<Integer, List<Integer>> collect =
        rules.stream()
            .collect(
                Collectors.groupingBy(Rule::a, Collectors.mapping(Rule::b, Collectors.toList())));

    return this.input.updates.stream()
        .filter(updates -> !updateCheck(updates, collect))
        .map(updates -> reOrder(updates, collect, rules))
        .map(Updates::middle)
        .reduce(0, Integer::sum);
  }

  private Updates reOrder(Updates updates, Map<Integer, List<Integer>> collect, List<Rule> rules) {
    Updates updatesToReOrder = updates;
    while (!updateCheck(updatesToReOrder, collect)) {
      log.debug("updateCheck false: {}", updatesToReOrder);
      updatesToReOrder = order(updatesToReOrder, collect, rules);
    }
    return updatesToReOrder;
  }

  private Updates order(Updates updates, Map<Integer, List<Integer>> collect, List<Rule> rules) {
    Rule rule = findRule(updates, collect, rules);
    Updates reOrder = reOrder(updates, rule);
    log.debug("reOrder: {} update check {}", reOrder, updateCheck(reOrder, collect));
    return reOrder;
  }

  private Updates reOrder(Updates updates, Rule rule) {
    List<Integer> updates2 = new ArrayList<>(updates.updates());
    updates2.set(updates2.indexOf(rule.a), rule.b);
    updates2.set(updates2.indexOf(rule.b), rule.a);
    return new Updates(updates2);
  }

  private Rule findRule(Updates updates1, Map<Integer, List<Integer>> collect, List<Rule> rules) {
    List<Integer> updates2 = updates1.updates();
    for (int i = 0; i < updates2.size() - 1; i++) {
      Integer number = updates2.get(i);
      List<Integer> collectGet = collect.get(number);
      List<Integer> nextNumbers =
          IntStream.range(i + 1, updates2.size()).mapToObj(updates2::get).toList();

      for (Integer nextNumber : nextNumbers) {
        if (collectGet == null || !collectGet.contains(nextNumber)) {
          return rules.stream()
              .filter(rule -> rule.a == nextNumber && rule.b == number)
              .findFirst()
              .orElseThrow();
        }
      }
    }
    throw new IllegalStateException("Should not get here");
  }
}
