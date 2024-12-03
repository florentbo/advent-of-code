package be.bonamis.advent.year2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    @Test
    void solvePart01() {
    List<String> input =
        List.of("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))");
        assertThat(new Day03(input).solvePart01()).isEqualTo(161);
    }

    @Test
    void solvePart02() {
        List<String> input =
            List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))");
        assertThat(new Day03(input).solvePart02()).isEqualTo(48);
    }

    @ParameterizedTest
    @MethodSource("isSafeTestCases")
    void safeTest(String input, boolean expected) {
        //assertThat(isSafe(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> isSafeTestCases() {
        return Stream.of(
                Arguments.of("7 6 4 2 1", true),
                Arguments.of("1 2 7 8 9", false),
                Arguments.of("9 7 6 2 1", false),
                Arguments.of("1 3 2 4 5", false),
                Arguments.of("8 6 4 4 1", false),
                Arguments.of("1 3 6 7 9", true));
    }
}
