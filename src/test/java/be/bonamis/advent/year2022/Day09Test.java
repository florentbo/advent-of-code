package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day09Test {

    private static final String CODE_TXT = "2022/09/2022_09_05_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(new Day09(lines).solvePart01()).isEqualTo(8);
    }

    @Test
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(new Day09(lines).solvePart02()).isEqualTo(9);
    }
}