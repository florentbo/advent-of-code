package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    private static final String CODE_TXT = "2022/10/2022_10_07_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        int xValue = 1;
        int cycle = 0;
        new ClockCircuit(lines).run(xValue, cycle);
        assertThat(lines.size()).isEqualTo(3);
    }

    static class ClockCircuit {

        private final List<String> lines;

        ClockCircuit(List<String> lines) {
            this.lines = lines;
        }

        public void run(int xValue, int cycle) {
            System.out.println("cycle: " + cycle + " and value: " + xValue);
            for (String line : this.lines) {
                if (cycle > 10) {
                    break;
                }
                cycle++;
                if (line.equals("noop")) {
                    cycle += 1;
                    run(xValue, cycle);
                } else {
                    int number = Integer.parseInt(line.replace("addx ", ""));
                    cycle += 1;
                    run(xValue, cycle);
                    cycle += 1;
                    run(xValue, cycle);
                    xValue += number;
                }
            }
        }

    }

    @Test
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(lines.size()).isEqualTo(3);
    }
}
