package be.bonamis.advent.year2016;

import be.bonamis.advent.year2016.Day04.Room;
import be.bonamis.advent.year2016.Day04.Room.LetterCount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static be.bonamis.advent.year2016.Day04.Room.LetterCount.letterCountComparator;
import static org.assertj.core.api.Assertions.assertThat;

class Day04Test {

  private static final Room FIRST_ROOM =
      new Room(List.of("aaaaa", "bbb", "z", "y", "x"), 123, "abxyz");

  @Test
  void roomFrom() {
    Room room = Room.from("aaaaa-bbb-z-y-x-123[abxyz]");
    assertThat(room).isEqualTo(FIRST_ROOM);
  }

  @Test
  void letterCounts() {
    Room room = Room.from("aaaaa-bbb-z-y-x-123[abxyz]");
    assertThat(room.letterCounts())
        .contains(
            new LetterCount("a", 5),
            new LetterCount("b", 3),
            new LetterCount("x", 1),
            new LetterCount("y", 1),
            new LetterCount("z", 1));
  }

  @Test
  void letterCountsSorting() {
    List<LetterCount> letterCounts =
        List.of(
            new LetterCount("a", 5),
            new LetterCount("y", 1),
            new LetterCount("x", 1),
            new LetterCount("b", 3),
            new LetterCount("z", 1));
    assertThat(letterCounts.stream().sorted(letterCountComparator).toList())
        .containsExactly(
            new LetterCount("a", 5),
            new LetterCount("b", 3),
            new LetterCount("x", 1),
            new LetterCount("y", 1),
            new LetterCount("z", 1));
  }

  @Test
  void sortedLetterCounts() {
    Room room = Room.from("aaaaa-bbb-z-y-x-123[abxyz]");
    assertThat(room.sortedLetterCounts())
        .containsExactly(
            new LetterCount("a", 5),
            new LetterCount("b", 3),
            new LetterCount("x", 1),
            new LetterCount("y", 1),
            new LetterCount("z", 1));
  }

  @ParameterizedTest
  @MethodSource("isSafeTestCases")
  void safeTest(Room input, boolean expected) {
    assertThat(input.isReal()).isEqualTo(expected);
  }

  private static Stream<Arguments> isSafeTestCases() {
    return Stream.of(
        Arguments.of(Room.from("aaaaa-bbb-z-y-x-123[abxyz]"), true),
        Arguments.of(Room.from("a-b-c-d-e-f-g-h-987[abcde]"), true),
        Arguments.of(Room.from("not-a-real-room-404[oarel]"), true),
        Arguments.of(Room.from("totally-real-room-200[decoy]"), false));
  }

  @Test
  void solvePart01() {
    assertThat(
            new Day04(
                    List.of(
                        "aaaaa-bbb-z-y-x-123[abxyz]",
                        "a-b-c-d-e-f-g-h-987[abcde]",
                        "not-a-real-room-404[oarel]",
                        "totally-real-room-200[decoy]"))
                .solvePart01())
        .isEqualTo(1514);
  }

  @Test
  void solvePart02() {
    assertThat(new Day04(List.of()).solvePart02()).isEqualTo(201502);
  }

  @Test
  void charDecrypt() {
    assertThat(Room.decrypt('q', 343)).isEqualTo('v');
  }

  @Test
  void roomDecrypt() {
    Room room = Room.from("qzmt-zixmtkozy-ivhz-343[qq]");
    assertThat(room.shiftCipherDecrypt()).isEqualTo("very encrypted name");
  }
}
