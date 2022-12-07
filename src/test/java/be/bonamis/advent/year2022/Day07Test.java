package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Test;

import java.util.*;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day07Test {

    private static final String CODE_TXT = "2022/07/2022_07_01_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(findSize(lines)).isEqualTo(95437);
    }

    @Test
    void solvePart02() {
        assertThat(new Day07(getLines(CODE_TXT)).solvePart02()).isEqualTo(24);
    }

    int findSize(List<String> lines){
        List<Directory> directories = new ArrayList<>();
        for (String line : lines) {
            String test = "$ cd /\n" +
                    "$ ls";

        }
        return 0;
    }

    record Directory(){

    }
}
