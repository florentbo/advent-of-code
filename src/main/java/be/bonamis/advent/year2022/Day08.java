package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;

import java.util.List;

public class Day08 extends DaySolver<String> {

    public Day08(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return this.puzzle.size();
    }

    @Override
    public long solvePart02() {
        return this.puzzle.size() + 1;
    }
}
