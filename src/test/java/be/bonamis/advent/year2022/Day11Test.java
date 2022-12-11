package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day11Test {

    private static final String CODE_TXT = "2022/11/2022_11_00_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/11/2022_11_input.txt");
        //log.info("Day10 part 01 result: {}", new ClockCircuit(lines).run());

    }
    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        //new ClockCircuit(lines).run();
        assertThat(lines.size()).isEqualTo(27);
    }
}
