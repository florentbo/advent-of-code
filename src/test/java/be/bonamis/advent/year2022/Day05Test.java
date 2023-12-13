package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day05Test {

  private static final String CODE_TXT = "2022/05/2022_05_00_code.txt";

  @ParameterizedTest
  @CsvSource({"mjqjpqmgbljsphdztnvjfqwrcgsmlb,7,19"
    // , "bvwbjplbgvbhsrlpgdmjqwftvncz,5",
    // "nppdvjthqldpwncqszvftbrmjlhg,6", "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10",
    // "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11"
  })
  void aTobogganIsOnSymbolAfterSlopes(String input, long count4, long count14) {
    assertThat(detect(input, 4)).isEqualTo(count4);
    assertThat(detect(input, 14)).isEqualTo(count14);
  }

  int detect(String input, int distinctCharacters) {
    char[] chars = input.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (i > distinctCharacters - 1) {
        String substr = input.substring(i - distinctCharacters + 1, i + 1);
        if (new HashSet<>(Arrays.stream(substr.split("")).toList()).size() == distinctCharacters) {
          System.out.println("substr: " + substr);
          return i + 1;
        }
      }
    }
    return 0;
  }

  @Test
  void solvePart02() {
    List<String> lines = getLines(CODE_TXT);
    assertThat(detect(lines.get(0), 4)).isEqualTo(1042);
    assertThat(detect(lines.get(0), 14)).isEqualTo(2980);
  }
}
