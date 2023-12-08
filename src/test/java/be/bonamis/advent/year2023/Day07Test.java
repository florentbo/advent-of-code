package be.bonamis.advent.year2023;

import static be.bonamis.advent.year2023.Day07.Cards.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
class Day07Test {

  private Day07 day07;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/07/2023_07_28_code.txt");
    day07 = new Day07(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    assertThat(day07.solvePart01()).isEqualTo(6440);
  }

  @Test
  void solvePart02() {
    assertThat(day07.solvePart02()).isEqualTo(5905);
  }

  @Test
  void isStrongerThan() {
    Map<String, Integer> cardsOrder = day07.getCardsOrder();
    assertThat(of("77888").isStrongerThan(of("77788"), false, cardsOrder)).isTrue();
    assertThat(of("77778").isStrongerThan(of("77788"), false, cardsOrder)).isTrue();

    assertThat(of("QQQJA").isStrongerThan(of("T55J5"), false, cardsOrder)).isTrue();
    assertThat(of("T55J5").isStrongerThan(of("KK677"), false, cardsOrder)).isTrue();
    assertThat(of("KK677").isStrongerThan(of("KTJJT"), false, cardsOrder)).isTrue();
    assertThat(of("KTJJT").isStrongerThan(of("32T3K"), false, cardsOrder)).isTrue();

    assertThat(of("KK677").isStrongerThan(of("KTJJT"), true, cardsOrder)).isFalse();

    assertThat(of("KTJJT").isStrongerThan(of("QQQJA"), true, cardsOrder)).isTrue();
    assertThat(of("QQQJA").isStrongerThan(of("T55J5"), true, cardsOrder)).isTrue();
    assertThat(of("T55J5").isStrongerThan(of("KK677"), true, cardsOrder)).isTrue();
    assertThat(of("KK677").isStrongerThan(of("32T3K"), true, cardsOrder)).isTrue();
  }
}
