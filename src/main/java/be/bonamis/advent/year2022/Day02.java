package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day02 extends DaySolver<String> {

    public Day02(List<String> puzzle) {
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
