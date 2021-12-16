package be.bonamis.advent.year2021;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Grid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day11 extends DaySolver<String> {

    private final Grid grid;

    public Day11(List<String> puzzle) {
        super(puzzle);
        this.grid = new Grid(puzzle.parallelStream().map(l -> l.trim().split(""))
                .map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new));
    }

    long flashCount() {
        return grid.stream().filter(point -> grid.get(point) > 9).count();
    }

    long flashCount2() {
        return grid.stream().filter(point -> grid.get(point) == 0).count();
    }

    @Override
    public long solvePart01() {
        long count = 0;
        for (int i = 0; i < 100; i++) {
            grid.stream().forEach(this::increaseEnergyLevel);
            Set<Point> flashNumbers = new HashSet<>();
            while (flashCount() > 0) {
                count += getCount(flashNumbers);
            }
        }
        return count;
    }

    @Override
    public long solvePart02() {
        int steps = 0;
        while (flashCount2() != 100) {
            grid.stream().forEach(this::increaseEnergyLevel);
            Set<Point> flashNumbers = new HashSet<>();
            while (flashCount() > 0) {
                getCount(flashNumbers);
            }
            steps++;
        }
        return steps;
    }

    private long getCount(Set<Point> flashNumbers) {
        List<Point> flashPoints = grid.stream().filter(point -> grid.get(point) > 9).peek(point -> grid.set(point, 0)).collect(Collectors.toList());
        flashNumbers.addAll(flashPoints);
        flashPoints.stream().flatMap(point -> getAdjacentPoints(point, grid)).forEach(adjacentPoint -> increaseAdjacentPoints(flashNumbers, adjacentPoint));
        return flashPoints.size();
    }

    private void increaseAdjacentPoints(Set<Point> flashNumbers, Point adjacentPoint) {
        if (!flashNumbers.contains(adjacentPoint)) {
            increaseEnergyLevel(adjacentPoint);
        }
    }

    Stream<Point> getAdjacentPoints(Point point, Grid grid) {
        List<Point> adjacentPoints = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0)) {
                    int x = (int) point.getX();
                    int y = (int) point.getY();
                    Point p = new Point(x + i, y + j);
                    Integer value = grid.get(p);
                    if (value != null) {
                        adjacentPoints.add(p);
                    }
                }
            }
        }
        return adjacentPoints.stream();
    }

    public void increaseEnergyLevel(Point point) {
        Integer value = grid.get(point);
        grid.set(point, value + 1);
    }


}
