package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;
import java.util.HashMap;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day04 extends DaySolver<String> {

  private final List<Card> cards;

  public Day04(List<String> puzzle) {
    super(puzzle);
    this.cards = parse(this.puzzle);
    log.debug("cards: {}", cards);
  }

  List<Card> parse(List<String> puzzle) {
    return puzzle.stream().map(this::parse2).toList();
  }

  private Card parse2(String input) {
    String[] split = input.split(":");
    String carNumber = split[0];
    log.debug("card number: {}", carNumber);
    String[] parts = split[1].split("\\|");

    List<Integer> list1 =
        Arrays.stream(parts[0].trim().split("\\s+")).map(Integer::parseInt).toList();

    List<Integer> list2 =
        Arrays.stream(parts[1].trim().split("\\s+")).map(Integer::parseInt).toList();
    return new Card(
        Integer.parseInt(carNumber.substring("Card".length() + 1).trim()), list1, list2);
  }

  @Override
  public long solvePart01() {
    return this.cards.stream().map(this::solve).reduce(Long::sum).orElseThrow();
  }

  private long solve(Card card) {
    long count = winningCount(card);
    long power = (long) Math.pow(2, count - 1);
    log.debug("power: {}", power);
    return power;
  }

  private long winningCount(Card card) {
    return card.numbers.stream().filter(card.winningNumbers::contains).count();
  }

  @Override
  public long solvePart02() {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < this.cards.size(); i++) {
      map.put(i, 1);
    }
    log.debug("initial map: {}", map);

    for (int i = 0; i < cards.size(); i++) {
      long count = winningCount(cards.get(i));
      log.debug("card: {} count: {}", i, count);
      Integer actualValue = map.get(i);

      for (int j = 1; j <= count; j++) {
        map.put(i + j, map.get(i + j) + actualValue);
      }
      log.debug("map: {}", map);
    }

    return map.values().stream().reduce(Integer::sum).orElseThrow();
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/04/2023_04_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day04 day = new Day04(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  record Card(int id, List<Integer> winningNumbers, List<Integer> numbers) {}
}
