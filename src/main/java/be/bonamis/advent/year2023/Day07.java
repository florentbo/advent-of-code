package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day07 extends DaySolver<String> {

  private static Map<String, Integer> cardsOrder;
  private static Map<String, Integer> cardsOrderWithJoker;

  public Day07(List<String> puzzle) {
    super(puzzle);
    List<String> stringList =
        Arrays.stream("A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(","))
            .map(String::trim)
            .toList();

    List<String> cardsWithJoker =
        Arrays.stream("A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(","))
            .map(String::trim)
            .toList();
    cardsOrder = cardsOrder(stringList);
    cardsOrderWithJoker = cardsOrder(cardsWithJoker);
    log.debug("stringList: {}", stringList);
    log.debug("cardsOrder: {}", cardsOrder);
    log.debug("cardsOrderWithJoker: {}", cardsOrderWithJoker);
  }

  private Map<String, Integer> cardsOrder(List<String> stringList) {
    return IntStream.range(0, stringList.size())
        .boxed()
        .collect(Collectors.toMap(stringList::get, index -> stringList.size() - index));
  }

  @Override
  public long solvePart01() {
    List<Game> games = new ArrayList<>(this.puzzle.stream().map(this::parse).toList());
    log.debug("games: {}", games);

    games.sort(new GameComparator(false));

    log.debug("games after sorting : {}", games);

    return IntStream.range(0, games.size())
        .mapToLong(index -> games.get(index).bid * (index + 1))
        .reduce(0L, Long::sum);
  }

  static boolean isFiveOfAKind(Cards cards) {
    return cards.inventory().containsValue(5L);
  }

  static boolean isFourOfAKind(Cards cards) {
    return cards.inventory().containsValue(4L);
  }

  static boolean isFullHouse(Cards cards) {
    return cards.inventory().containsValue(3L) && cards.inventory().containsValue(2L);
  }

  static boolean isThreeOfAKind(Cards cards) {
    return cards.inventory().containsValue(3L);
  }

  static boolean isTwoPair(Cards cards) {
    return cards.inventory().values().stream().filter(value -> value == 2L).count() == 2;
  }

  static boolean isOnePair(Cards cards) {
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

  public record Game(Cards cards, Long bid) {}

  public record Cards(List<String> cards) {

    public static Cards of(String input) {
      return new Cards(Arrays.stream(input.split("")).toList());
    }

    public int typeValue(boolean playWithJoker) {
      HandValueType type = GameComparator.handType(this, playWithJoker);
      return type.getValue();
    }

    public boolean isStrongerThan(Cards other, boolean playWithJoker) {
      return this.compareTo(other, playWithJoker) > 0;
    }

    public Map<String, Long> inventory() {
      return this.cards().stream()
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public int compareTo(Cards other, boolean playWithJoker) {
      int type = this.typeValue(playWithJoker);
      int otherType = other.typeValue(playWithJoker);

      if (type == otherType) {

        OptionalInt firstDifference =
            IntStream.range(0, this.cards().size())
                .filter(i -> !this.cards().get(i).equals(other.cards.get(i)))
                .findFirst();
        if (firstDifference.isPresent()) {
          int index = firstDifference.getAsInt();
          Map<String, Integer> order = playWithJoker ? cardsOrderWithJoker : cardsOrder;
          return order.get(this.cards.get(index)).compareTo(order.get(other.cards().get(index)));

        } else {
          return 0;
        }
      }

      return type - otherType;
    }
  }

  @Override
  public long solvePart02() {
    List<Game> games = new ArrayList<>(this.puzzle.stream().map(this::parse).toList());
    log.debug("games: {}", games);

    games.sort(new GameComparator(true));

    games.stream().map(Game::cards).forEach(cards -> log.debug("card: {}", cards));

    return IntStream.range(0, games.size())
        .mapToLong(index -> games.get(index).bid * (index + 1))
        .reduce(0L, Long::sum);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/07/2023_07_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day07 day = new Day07(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  @AllArgsConstructor
  static class GameComparator implements Comparator<Game> {
    private final boolean playWithJoker;

    static HandValueType handType(Cards cards, boolean playWithJoker) {
      Map<String, Long> inventory = cards.inventory();
      if (isFiveOfAKind(cards)) {
        return HandValueType.FIVE_OF_A_KIND;
      }
      Long jokerCount = inventory.getOrDefault("J", 99L);
      if (isFourOfAKind(cards)) {
        if (playWithJoker && jokerCount.equals(1L)) {
          return HandValueType.FIVE_OF_A_KIND;
        }
        return HandValueType.FOUR_OF_A_KIND;
      }
      if (isFullHouse(cards)) {
        if (playWithJoker)
          if (jokerCount.equals(2L) || jokerCount.equals(3L)) {
            return HandValueType.FIVE_OF_A_KIND;
          }
        return HandValueType.FULL_HOUSE;
      }
      if (isThreeOfAKind(cards)) {
        if (playWithJoker && jokerCount.equals(1L)) {
          return HandValueType.FOUR_OF_A_KIND;
        }
        return HandValueType.THREE_OF_A_KIND;
      }

      if (isTwoPair(cards)) {
        if (playWithJoker) {
          if (jokerCount.equals(1L)) {
            return HandValueType.FULL_HOUSE;
          }
          if (jokerCount.equals(2L)) {
            return HandValueType.FOUR_OF_A_KIND;
          }
        }
        return HandValueType.TWO_PAIRS;
      }

      if (isOnePair(cards)) {
        return HandValueType.ONE_PAIR;
      }

      return HandValueType.HIGH_CARD;
    }

    @Override
    public int compare(Game g1, Game g2) {
      return g1.cards().compareTo(g2.cards(), playWithJoker);
    }
  }
}
