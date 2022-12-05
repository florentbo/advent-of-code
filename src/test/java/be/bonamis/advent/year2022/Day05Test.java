package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day05Test {

    private static final String CODE_TXT = "2022/05/2022_05_00_code.txt";

    @Test
    void solvePart01() {
        //assertThat(new Day05(getLines(CODE_TXT)).solvePart01String()).isEqualTo("CMZ");
        assertThat(new Day05(getLines(CODE_TXT)).solvePart01String()).isEqualTo("MCD");
    }

    @Test@Disabled
    void solvePart02() {
        assertThat(new Day05(getLines(CODE_TXT)).solvePart02()).isEqualTo(7);
    }


}
