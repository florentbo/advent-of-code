package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.util.Arrays;
import java.util.List;

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
    // Split the string into two parts based on the "|"
    String[] parts = split[1].split("\\|");

    // Process each part and create lists of integers
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
    long count = card.numbers.stream().filter(card.winningNumbers::contains).count();
    log.debug("count: {}", count);
    long power = (long) Math.pow(2, count - 1);
    log.debug("power: {}", power);
    return power;
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
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
