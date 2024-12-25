package be.bonamis.advent.year2024;

import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.year2024.Day25.Inputs.KeyLock;
import org.junit.jupiter.api.*;

import java.util.List;

class Day25Test {

  private static final String INPUT =
      """
   #####
   .####
   .####
   .####
   .#.#.
   .#...
   .....

   #####
   ##.##
   .#.##
   ...##
   ...#.
   ...#.
   .....

   .....
   #....
   #....
   #...#
   #.#.#
   #.###
   #####

   .....
   .....
   #.#..
   ###..
   ###.#
   ###.#
   #####

   .....
   .....
   .....
   #....
   #.#..
   #.#.#
   #####
   """;

  @Test
  void solvePart01() {
    assertThat(new Day25(INPUT).solvePart01()).isEqualTo(3);
  }

  @Test
  void parse() {
    Day25.Inputs inputs = new Day25(INPUT).getInputs();
    List<KeyLock> keyLocks = inputs.inputs();
    assertThat(keyLocks).hasSize(5);

    KeyLock firstKeyLock = keyLocks.get(0);
    assertThat(firstKeyLock.isLock()).isTrue();
    assertThat(firstKeyLock.isKey()).isFalse();

    KeyLock thirdKeyLock = keyLocks.get(2);
    assertThat(thirdKeyLock.isLock()).isFalse();
    assertThat(thirdKeyLock.isKey()).isTrue();

    assertThat(inputs.locks()).hasSize(2);
    assertThat(inputs.keys()).hasSize(3);

    assertThat(firstKeyLock.pinHeights()).containsExactly(0, 5, 3, 4, 3);
  }

  @Test
  void fitTest() {
    Day25.Inputs inputs = new Day25(INPUT).getInputs();
    List<KeyLock> keyLocks = inputs.inputs();
    assertThat(keyLocks).hasSize(5);

    KeyLock firstKeyLock = keyLocks.get(0);
    assertThat(firstKeyLock.pinHeights()).containsExactly(0, 5, 3, 4, 3);

    KeyLock lastKeyLock = keyLocks.get(4);
    assertThat(lastKeyLock.pinHeights()).containsExactly(3, 0, 2, 0, 1);

    assertThat(firstKeyLock.fit(lastKeyLock)).isTrue();
  }
}
