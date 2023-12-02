package be.bonamis.advent.year2015;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day01Test {

    @Test
    void solvePart01() {
        String input = "(())";
        assertThat(new Day01(List.of(input)).solvePart01()).isEqualTo(0);
    }

    @Test
    void solvePart02() {
        String input = "()())";
        String substring = input.substring(0, 2);
        log.info("{}", substring);
        assertThat(new Day01(List.of(substring)).solvePart01()).isEqualTo(0);





        assertThat(new Day01(List.of(input)).solvePart02()).isEqualTo(5);
    }
}
