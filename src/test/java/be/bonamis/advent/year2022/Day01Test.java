package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Test;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day01Test {

    @Test
    void solvePart01() {
        assertThat(new Day01(getLines("2021/01/2021_01_00_code.txt")).solvePart01()).isEqualTo(10);
    }
}
