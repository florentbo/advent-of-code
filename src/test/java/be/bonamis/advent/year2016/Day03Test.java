package be.bonamis.advent.year2016;

import static be.bonamis.advent.utils.FileHelper.*;
import static be.bonamis.advent.year2016.Day03.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
class Day03Test {

  @Test
  void solvePart01() {
    String input =
        """
5 10 25
""";
    assertThat(new Day03(inputStream(input)).solvePart01()).isZero();
  }

  @Test
  void parse() {
    assertThat(Triangle.of("5 10 25")).isEqualTo(Triangle.of(5, 10, 25));
  }

  @Test
  void isValid() {
    assertThat(Triangle.of(5, 10, 25).isValid()).isFalse();
  }


}
