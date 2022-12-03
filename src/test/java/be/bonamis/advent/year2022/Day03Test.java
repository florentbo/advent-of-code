package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Test;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    public static final String CODE_TXT = "2022/03/2022_03_02_code.txt";

    @Test
    void solvePart01() {
        assertThat(new Day03(getLines(CODE_TXT)).solvePart01()).isEqualTo(157);
    }

    @Test
    void solvePart02() {
        assertThat(new Day03(getLines(CODE_TXT)).solvePart02()).isEqualTo(70);
    }
}
