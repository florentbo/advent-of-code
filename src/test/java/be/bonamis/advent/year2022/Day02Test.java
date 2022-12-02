package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    public static final String CODE_TXT = "2022/02/2022_02_06_code.txt";

    @Test
    void solvePart01() {
        assertThat(new Day02(getLines(CODE_TXT)).solvePart01()).isEqualTo(15);
    }

    @Test
    void solvePart02() {
        assertThat(new Day02(getLines(CODE_TXT)).solvePart02()).isEqualTo(12);
    }
}
