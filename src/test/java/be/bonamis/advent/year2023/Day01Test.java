package be.bonamis.advent.year2023;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day01Test {

    @Test
    void solvePart01() {
        assertThat(new Day01(List.of("1abc12", "pqr3stu8vwx")).solvePart01()).isEqualTo(50);
    }

    @Test
    void solvePart02() {
        assertThat(new Day01(List.of("two1nine")).solvePart02()).isEqualTo(29);
        assertThat(new Day01(List.of("eightwothree")).solvePart02()).isEqualTo(83);
        assertThat(new Day01(List.of("abcone2threexyz")).solvePart02()).isEqualTo(13);
        assertThat(new Day01(List.of("xtwone3four")).solvePart02()).isEqualTo(24);
        assertThat(new Day01(List.of("4nineeightseven2")).solvePart02()).isEqualTo(42);
        assertThat(new Day01(List.of("zoneight234")).solvePart02()).isEqualTo(14);
        assertThat(new Day01(List.of("7pqrstsixteen")).solvePart02()).isEqualTo(76);
        assertThat(new Day01(List.of("vzqcvvtqjone7one")).solvePart02()).isEqualTo(11);
    }
}
