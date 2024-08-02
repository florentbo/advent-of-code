package be.bonamis.advent.year2017;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

@Slf4j
class Day04Test {

  private static final String LINE01_TXT = "aa bb cc dd ee";
  private static final String LINE02_TXT = "aa bb cc dd aa";
  private static final String LINE03_TXT = "aa bb cc dd aaa";

  private Day04 day;

  @BeforeEach
  void setUp() {
    String sampleInput =
        """
                %s
                %s
                %s
                """
            .formatted(LINE01_TXT, LINE02_TXT, LINE03_TXT);
    InputStream inputStream = new ByteArrayInputStream(sampleInput.getBytes());
    day = new Day04(inputStream);
  }

  @ParameterizedTest
  @MethodSource
  void isPassphraseValid(String input, boolean expected) {
    assertThat(day.isPassphraseValid(input)).isEqualTo(expected);
  }

  private static List<Arguments> isPassphraseValid() {
    return List.of(of(LINE01_TXT, true), of(LINE02_TXT, false), of(LINE03_TXT, true));
  }

  @Test
  void part01() {
    assertThat(day.solvePart01()).isEqualTo(2);
  }

  @Test
  void anagram() {
    assertThat(day.isAnagram("abc", "bca")).isTrue();
  }

  private static final String PART_TWO_LINE01_TXT = "abcde fghij";

  private static final String PART_TWO_LINE02_TXT = "abcde xyz ecdab";
  private static final String PART_TWO_LINE03_TXT = "a ab abc abd abf abj";
  private static final String PART_TWO_LINE04_TXT = "iiii oiii ooii oooi oooo";
  private static final String PART_TWO_LINE05_TXT = "oiii ioii iioi iiio";

  @ParameterizedTest
  @MethodSource
  void isPart02PassphraseValid(String input, boolean expected) {
    assertThat(day.isPart02PassphraseValid(input)).isEqualTo(expected);
  }

  private static List<Arguments> isPart02PassphraseValid() {
    return List.of(
        of(PART_TWO_LINE01_TXT, true),
        of(PART_TWO_LINE02_TXT, false),
        of(PART_TWO_LINE03_TXT, true),
        of(PART_TWO_LINE04_TXT, true),
        of(PART_TWO_LINE05_TXT, false));
  }

  @Test
  void part02() {
    String sampleInput =
        """
                    %s
                    %s
                    %s
                    %s
                    %s
                    """
            .formatted(
                PART_TWO_LINE01_TXT,
                PART_TWO_LINE02_TXT,
                PART_TWO_LINE03_TXT,
                PART_TWO_LINE04_TXT,
                PART_TWO_LINE05_TXT);
    InputStream inputStream = new ByteArrayInputStream(sampleInput.getBytes());
    day = new Day04(inputStream);
    assertThat(day.solvePart02()).isEqualTo(3);
  }
}
