package be.bonamis.advent.year2017;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

@Slf4j
class Day05Test {

  private Day05 day;

  @BeforeEach
  void setUp() {
    String sampleInput =
        """
                0
                3
                0
                1
                -3
                """;
    ;
    InputStream inputStream = new ByteArrayInputStream(sampleInput.getBytes());
    day = new Day05(inputStream);
  }

  /*@ParameterizedTest
  @MethodSource
  void isPassphraseValid(String input, boolean expected) {
    assertThat(day.isPassphraseValid(input)).isEqualTo(expected);
  }

  private static List<Arguments> isPassphraseValid() {
    return List.of(of(LINE01_TXT, true), of(LINE02_TXT, false), of(LINE03_TXT, true));
  }*/

  @Test
  void streamTest() {
    List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    assertThat(list.stream().filter(i -> i % 2 == 0).findFirst().orElseThrow()).isEqualTo(2);
    IntStream intStream = IntStream.range(0, 10);
    intStream.mapToObj(list::get).filter(i -> i % 2 == 0).findFirst().orElseThrow();
  }

  @Test
  void solvePart01() {
    assertThat(day.solvePart01()).isEqualTo(99);
  }
}
