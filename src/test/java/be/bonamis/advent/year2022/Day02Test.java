package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Test;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    @Test
    void solvePart01() {
        assertThat(new Day02(getLines("2022/01/2022_01_00_code.txt")).solvePart01()).isEqualTo(14);
    }

    @Test
    void solvePart02() {
        assertThat(new Day02(getLines("2022/01/2022_01_00_code.txt")).solvePart02()).isEqualTo(15);
    }
}
