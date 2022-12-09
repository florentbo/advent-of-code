package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Grid;
import cyclops.companion.Streamable;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day09 extends DaySolver<String> {

    public Day09(List<String> puzzle) {
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
