package be.bonamis.advent.year2023;

import static be.bonamis.advent.year2023.Day07.*;
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
    assertThat(day07.solvePart02()).isEqualTo(7);
  }

  @Test
  void isStrongerThan() {
    assertThat(Cards.of("77888").isStrongerThan(Cards.of("77788"))).isTrue();
    assertThat(Cards.of("77778").isStrongerThan(Cards.of("77788"))).isTrue();

    assertThat(Cards.of("QQQJA").isStrongerThan(Cards.of("T55J5"))).isTrue();
    assertThat(Cards.of("T55J5").isStrongerThan(Cards.of("KK677"))).isTrue();
    assertThat(Cards.of("KK677").isStrongerThan(Cards.of("KTJJT"))).isTrue();
    assertThat(Cards.of("KTJJT").isStrongerThan(Cards.of("32T3K"))).isTrue();
  }
}
