package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day07 extends DaySolver<String> {

  private static Map<String, Integer> cardsOrder;

  public Day07(List<String> puzzle) {
    super(puzzle);
    List<String> stringList =
        Arrays.stream("A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(","))
            .map(String::trim)
            .toList();
    cardsOrder =
        IntStream.range(0, stringList.size())
            .boxed()
            .collect(
                Collectors.toMap(
                    // Key: position (index) in the list
                    stringList::get,
                    index -> stringList.size() - index // Value: corresponding string
                    ));
    log.debug("cardorders: {}", stringList);
    log.debug("cardorders: {}", cardsOrder);
  }

  @Override
  public long solvePart01() {

    List<Game> games = new ArrayList<>(this.puzzle.stream().map(this::parse).toList());
    Game game = games.get(0);
    HandValueType type = handType(game.cards);
    log.debug("type: {}", type);
    log.debug("games: {}", games);

    Collections.sort(games);

    log.debug("games after sorting : {}", games);

    return IntStream.range(0, games.size())
        .mapToLong(index -> games.get(index).bid * (index + 1))
        .reduce(0L, Long::sum);
  }

  static HandValueType handType(Cards cards) {
    log.debug("cards: {}", cards);
    if (isFiveOfAKind(cards)) {
      return HandValueType.FIVE_OF_A_KIND;
    }
    if (isFourOfAKind(cards)) {
      return HandValueType.FOUR_OF_A_KIND;
    }
    if (isFullHouse(cards)) {
      return HandValueType.FULL_HOUSE;
    }
    if (isThreeOfAKind(cards)) {
      return HandValueType.THREE_OF_A_KIND;
    }

    if (isTwoPair(cards)) {
      return HandValueType.TWO_PAIRS;
    }

    if (isOnePair(cards)) {
      return HandValueType.ONE_PAIR;
    }

    return HandValueType.HIGH_CARD;
  }

  private static boolean isFiveOfAKind(Cards cards) {
    return cards.inventory().containsValue(5L);
  }

  private static boolean isFourOfAKind(Cards cards) {
    return cards.inventory().containsValue(4L);
  }

  private static boolean isFullHouse(Cards cards) {
    return cards.inventory().containsValue(3L) && cards.inventory().containsValue(2L);
  }

  private static boolean isThreeOfAKind(Cards cards) {
    return cards.inventory().containsValue(3L);
  }

  private static boolean isTwoPair(Cards cards) {
    return cards.inventory().values().stream().filter(value -> value == 2L).count() == 2;
  }

  private static boolean isOnePair(Cards cards) {
    return cards.inventory().containsValue(2L);
  }

  private Game parse(String s) {
    String[] game = s.split(" ");
    // Function<String, Card> card = ss -> new Card(ss, cardsOrder.get(ss.length()).length());
    List<String> cards = parseCards(game[0]);
    return new Game(new Cards(cards), Long.parseLong(game[1]));
  }

  List<String> parseCards(String input) {
    String[] split = input.split("");
    return Arrays.stream(split).toList();
  }

  public record Game(Cards cards, Long bid) implements Comparable<Game> {

    @Override
    public int compareTo(Game other) {
      return this.cards.compareTo(other.cards());
    }
  }

  public record Cards(List<String> cards) implements Comparable<Cards> {

    public static Cards of(String input) {
      return new Cards(Arrays.stream(input.split("")).toList());
    }

    public HandValueType type() {
      return handType(this);
    }

    public boolean isStrongerThan(Cards other) {
      return this.compareTo(other) > 0;
    }

    public Map<String, Long> inventory() {
      return this.cards().stream()
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public int compareTo(Cards other) {
      int type = handType(this).getValue();
      int otherType = handType(other).getValue();

      if (type == otherType) {

        OptionalInt firstDifference =
            IntStream.range(0, this.cards().size())
                .filter(i -> !this.cards().get(i).equals(other.cards.get(i)))
                .findFirst();
        if (firstDifference.isPresent()) {
          int index = firstDifference.getAsInt();
          Integer value01 = cardsOrder.get(this.cards.get(index));
          Integer value02 = cardsOrder.get(other.cards().get(index));
          // String s2 = other.cards().get(value);
          return value01.compareTo(value02);

        } else {
          return 0;
        }
      }

      return type - otherType;
    }
  }

  public record Card(String label, int value) {}

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/07/2023_07_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day07 day = new Day07(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
