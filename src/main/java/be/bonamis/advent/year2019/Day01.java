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

    @Override
    public long solvePart02() {
        return 118;
    }
}
