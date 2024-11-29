package be.bonamis.advent.year2019;

import be.bonamis.advent.TextDaySolver;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class Day01 extends TextDaySolver {

    public Day01(InputStream inputStream) {
        super(inputStream);
    }

    public Day01(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return this.puzzle.stream().mapToInt(Day01::requiredFuel).sum();
    }

    private static int requiredFuel(String s) {
        return requiredFuel(Integer.parseInt(s));
    }

    static int requiredFuel(int mass) {
        return (mass / 3) - 2;
    }

    static int totalFuel(int mass) {
        int sum = 0;
        int requiredFuel = requiredFuel(mass);
        while (requiredFuel > 0) {
            sum += requiredFuel;
            requiredFuel = requiredFuel(requiredFuel);
        }
        return sum;
    }

    @Override
    public long solvePart02() {
        return this.puzzle.stream().mapToInt(Day01::totalFuel).sum();
    }

    private static int totalFuel(String s) {
        return totalFuel(Integer.parseInt(s));
    }
}
