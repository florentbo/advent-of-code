package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;

import java.util.List;

public class Day01 extends DaySolver<String> {

    public Day01(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return puzzle.size();
    }


    @Override
    public long solvePart02() {
        return 0;
    }
}
