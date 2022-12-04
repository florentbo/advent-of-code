package be.bonamis.advent.year2022;

import be.bonamis.advent.year2022.Day04.Elf;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day04Test {

    private static final String CODE_TXT = "2022/04/2022_04_00_code.txt";

    @Test
    void solvePart01() {
        assertThat(new Day04(getLines(CODE_TXT)).solvePart01()).isEqualTo(2);
    }

    @Test
    void solvePart02() {
        assertThat(new Day04(getLines(CODE_TXT)).solvePart02()).isEqualTo(4);
    }

    @Test
    void containsAll() {
        List<Integer> list01 = List.of(2, 3, 4, 5, 6, 7, 8);
        List<Integer> list02 = List.of(4, 5, 6);
        assertThat(list01.containsAll(list02)).isTrue();
        assertThat(list02.containsAll(list01)).isFalse();
    }

    @Test
    void oneRangeFullyContainsTheOther() {
        assertThat(new Elf("2-8,3-7").oneRangeFullyContainsTheOther()).isTrue();
        assertThat(new Elf("6-6,4-6").oneRangeFullyContainsTheOther()).isTrue();
        assertThat(new Elf("2-6,4-8").oneRangeFullyContainsTheOther()).isFalse();
    }

    @Test
    void oneRangeOverLapTheOther() {
        assertThat(new Elf("2-3,4-5").oneRangeOverLapTheOther()).isFalse();
        assertThat(new Elf("5-7,7-9").oneRangeOverLapTheOther()).isTrue();
    }
}
