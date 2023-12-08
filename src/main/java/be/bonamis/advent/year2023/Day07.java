package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day07 extends DaySolver<String> {

  private final Map<String, Integer> cardsOrder;
  private final Map<String, Integer> cardsOrderWithJoker;

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

    games.sort(new GameComparator(false, this.cardsOrder));

    log.debug("games after sorting : {}", games);

    return IntStream.range(0, games.size())
        .mapToLong(index -> games.get(index).bid * (index + 1L))
        .reduce(0L, Long::sum);
  }

  private Game parse(String s) {
    String[] game = s.split(" ");
    List<String> cards = parseCards(game[0]);
    return new Game(new Cards(cards), Integer.parseInt(game[1]));
  }

  List<String> parseCards(String input) {
    String[] split = input.split("");
    return Arrays.stream(split).toList();
  }

  public record Game(Cards cards, int bid) {}

  public record Cards(List<String> cards) {

    Integer value(Map<String, Integer> cardsOrder, int index) {
      return cardsOrder.get(this.cards.get(index));
    }

    public static Cards of(String input) {
      return new Cards(Arrays.stream(input.split("")).toList());
    }

    HandType handType(boolean playWithJoker) {
      Map<String, Long> inventory = inventory();
      if (isFiveOfAKind()) {
        return HandType.FIVE_OF_A_KIND;
      }
      Long jokerCount = inventory.getOrDefault("J", 99L);

      if (isFourOfAKind()) {
        if (playWithJoker && (jokerCount.equals(1L) || jokerCount.equals(4L))) {
          return HandType.FIVE_OF_A_KIND;
        }
        return HandType.FOUR_OF_A_KIND;
      }

      if (isFullHouse()) {
        if (playWithJoker && (jokerCount.equals(2L) || jokerCount.equals(3L))) {
          return HandType.FIVE_OF_A_KIND;
        }
        return HandType.FULL_HOUSE;
      }

      if (isThreeOfAKind()) {
        if (playWithJoker && (jokerCount.equals(1L) || jokerCount.equals(3L))) {
          return HandType.FOUR_OF_A_KIND;
        }
        return HandType.THREE_OF_A_KIND;
      }
      if (isTwoPair()) {
        if (playWithJoker) {
          if (jokerCount.equals(1L)) {
            return HandType.FULL_HOUSE;
          }
          if (jokerCount.equals(2L)) {
            return HandType.FOUR_OF_A_KIND;
          }
        }
        return HandType.TWO_PAIRS;
      }

      if (isOnePair()) {
        if (playWithJoker && (jokerCount.equals(1L) || jokerCount.equals(2L))) {
          return HandType.THREE_OF_A_KIND;
        }
        return HandType.ONE_PAIR;
      }

      HandType highCardHandType = jokerCount.equals(1L) ? HandType.ONE_PAIR : HandType.HIGH_CARD;
      return playWithJoker ? highCardHandType : HandType.HIGH_CARD;
    }

    boolean isFiveOfAKind() {
      return inventory().containsValue(5L);
    }

    boolean isFourOfAKind() {
      return inventory().containsValue(4L);
    }

    boolean isFullHouse() {
      return inventory().containsValue(3L) && inventory().containsValue(2L);
    }

    boolean isThreeOfAKind() {
      return inventory().containsValue(3L);
    }

    boolean isTwoPair() {
      return inventory().values().stream().filter(value -> value == 2L).count() == 2;
    }

    boolean isOnePair() {
      return inventory().containsValue(2L);
    }

    private int typeValue(boolean playWithJoker) {
      HandType type = handType(playWithJoker);
      return type.getValue();
    }

    public boolean isStrongerThan(
        Cards other, boolean playWithJoker, Map<String, Integer> cardsOrder) {
      return this.compareTo(other, playWithJoker, cardsOrder) > 0;
    }

    private Map<String, Long> inventory() {
      return this.cards().stream()
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public int compareTo(Cards other, boolean playWithJoker, Map<String, Integer> cardsOrder) {
      int type = this.typeValue(playWithJoker);
      int otherType = other.typeValue(playWithJoker);

      if (type == otherType) {

        OptionalInt firstDifference =
            IntStream.range(0, this.cards().size())
                .filter(i -> !this.cards().get(i).equals(other.cards.get(i)))
                .findFirst();

        Optional<Integer> optional =
            firstDifference.isPresent()
                ? Optional.of(firstDifference.getAsInt())
                : Optional.empty();
        return optional
            .map(index -> value(cardsOrder, index).compareTo(other.value(cardsOrder, index)))
            .orElse(0);
      }

      return type - otherType;
    }
  }

  @Override
  public long solvePart02() {
    List<Game> games = new ArrayList<>(this.puzzle.stream().map(this::parse).toList());
    log.debug("games: {}", games);

    games.sort(new GameComparator(true, this.cardsOrderWithJoker));

    games.stream().map(Game::cards).forEach(cards -> log.debug("card: {}", cards));

    return IntStream.range(0, games.size())
        .mapToLong(index -> games.get(index).bid * (index + 1L))
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
    private final Map<String, Integer> cardsOrder;

    @Override
    public int compare(Game g1, Game g2) {
      return g1.cards().compareTo(g2.cards(), playWithJoker, cardsOrder);
    }
  }

  @Getter
  @AllArgsConstructor
  public enum HandType {
    FIVE_OF_A_KIND(8),
    FOUR_OF_A_KIND(7),
    FULL_HOUSE(6),
    THREE_OF_A_KIND(3),
    TWO_PAIRS(2),
    ONE_PAIR(1),
    HIGH_CARD(0);

    private final int value;
  }
}
