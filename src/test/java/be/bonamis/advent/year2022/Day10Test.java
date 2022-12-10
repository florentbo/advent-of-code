package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day10Test {

    private static final String CODE_TXT = "2022/10/2022_10_07_code.txt";
    private static final String LARGER_CODE_TXT = "2022/10/2022_10_30_code.txt";

    public static void main(String[] args) {
        List<String> lines2 = getLines("2022/10/2022_10_input.txt");
        log.info("Day10 part 01 result: {}", new ClockCircuit(lines2).run());

    }
    @Test
    @Disabled
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        new ClockCircuit(lines).run();
        assertThat(lines.size()).isEqualTo(3);
    }

    @Test
    void solvePart011() {
        List<String> lines = getLines(LARGER_CODE_TXT);
        assertThat(new ClockCircuit(lines).run()).isEqualTo(13140);
    }

    static class ClockCircuit {

        private final List<String> lines;

        int cycle = 1;
        int xValue = 1;
        int globalResult = 0;

        ClockCircuit(List<String> lines) {
            this.lines = lines;
        }

        public int run() {
            print();
            for (String line : this.lines) {
                if (line.equals("noop")) {
                    cycle += 1;
                    print();
                    //run2();
                } else {
                    int number = Integer.parseInt(line.replace("addx ", ""));
                    cycle += 1;
                    print();
                    //run2();
                    cycle += 1;
                    //print();
                    //run2();
                    xValue += number;
                    print();
                }
            }
            return globalResult;
        }

        private void print() {
            if (isaBoolean(20)
                    || isaBoolean(60)
                    || isaBoolean(100)
                    || isaBoolean(140)
                    || isaBoolean(180)
                    || isaBoolean(220)) {
            }
        }

        private boolean isaBoolean(int x) {
            boolean b = cycle == x;
            if (b) {
                int result = xValue * x;
                globalResult+=result;
                System.out.println("cycle: " + cycle + " and value: " + xValue + " and result: " + result);
            }
            return b;
        }
    }

    @Test
    @Disabled
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(lines.size()).isEqualTo(3);
    }
}
