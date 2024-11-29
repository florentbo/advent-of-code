package be.bonamis.advent.year2020;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void solvePart01() {
        assertThat(new Day01(List.of("1721", "979", "366", "299")).solvePart01()).isEqualTo(514579);
    }

    @Test
    void solvePart02() {
        assertThat(new Day01(List.of("1721", "979", "366", "299", "675")).solvePart02()).isEqualTo(241861950);
    }
}
