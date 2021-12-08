package be.bonamis.advent.year2021;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.bonamis.advent.DaySolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day07 extends DaySolver<String> {

    private final List<Integer> crabPositions;

    public Day07(List<String> puzzle) {
        super(puzzle);
        this.crabPositions = Arrays.stream(this.puzzle.get(0).trim().split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public long solvePart01() {
        return cheapestCost(increment -> crabPositions.parallelStream().mapToLong(crab -> getSmallCost(increment, crab)).sum());
    }

    @Override
    public long solvePart02() {
        return cheapestCost(increment -> crabPositions.parallelStream().mapToLong(crabPosition -> getBigCost(increment, crabPosition)).sum());
    }

    private long cheapestCost(IntToLongFunction crabPositionToCost) {
        int min = crabPositions.stream().min(Integer::compare).orElse(0);
        int max = crabPositions.stream().max(Integer::compare).orElse(0);

        var costs = IntStream.range(min, max).mapToLong(crabPositionToCost);
        return costs.min().orElse(0L);
    }

    int getSmallCost(int increment, Integer crabPosition) {
        return Math.abs(crabPosition - increment);
    }

    int getBigCost(int increment, Integer crabPosition) {
        IntStream intStream = IntStream.range(1, Math.abs(crabPosition - increment) + 1);
        return intStream.reduce(0, Integer::sum);
    }
}
