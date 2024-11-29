package be.bonamis.advent.year2020;

import be.bonamis.advent.TextDaySolver;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.*;

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
        List<Pair<Integer, Integer>> allCombinations =
                IntStream.range(0, puzzle.size())
                        .boxed()
                        .flatMap(i -> IntStream.range(0, puzzle.size()).boxed()
                        .filter(j -> !i.equals(j))
                        .map(j -> Pair.of(Integer.parseInt(puzzle.get(i)), Integer.parseInt(puzzle.get(j))))).toList();
        log.debug("allCombinations Pair: {}", allCombinations);
        Pair<Integer, Integer> first = allCombinations
                .stream().filter(p -> p.getLeft() + p.getRight() == 2020)
                .findFirst().orElseThrow();

        return (long) first.getLeft() * first.getRight();
    }

    @Override
    public long solvePart02() {
        List<Triple<Integer, Integer, Integer>> allCombinations =
                IntStream.range(0, puzzle.size())
                        .boxed()
                        .flatMap(i -> IntStream.range(0, puzzle.size()).boxed()
                                .filter(j -> !i.equals(j))
                                .flatMap(j -> IntStream.range(0, puzzle.size()).boxed()
                                        .filter(k -> !i.equals(k) && !j.equals(k))
                                        .map(k -> Triple.of(Integer.parseInt(puzzle.get(i)), Integer.parseInt(puzzle.get(j)), Integer.parseInt(puzzle.get(k)))))).toList();
        log.debug("allCombinations Triple: {}", allCombinations);
        Triple<Integer, Integer, Integer> first = allCombinations
                .stream().filter(p -> p.getLeft() + p.getMiddle() + p.getRight() == 2020)
                .findFirst().orElseThrow();

        return (long) first.getLeft() * first.getMiddle() * first.getRight();
    }
}
