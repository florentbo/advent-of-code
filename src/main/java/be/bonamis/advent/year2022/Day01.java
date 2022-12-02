package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Day01 extends DaySolver<String> {

    public Day01(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return getElves().mapToLong(Elves::getTotal).max().orElseThrow();
    }

    @Override
    public long solvePart02() {
        return getElves().map(Elves::getTotal).sorted(Comparator.reverseOrder()).limit(3).reduce(0L, Long::sum);
    }

    private Stream<Elves> getElves() {
        String content = FileHelper.content("2022/01/2022_01_00_code.txt");
        Stream<Stream<Integer>> streamStream =
                Arrays.stream(content.split("\n\n"))
                        .map(s -> Arrays.stream(s.split("\n"))
                        .map(Integer::parseInt));
        return streamStream.map(integerStream -> new Elves(integerStream.collect(toList())));
    }

    @ToString
    @Getter
    static class Elves {
        private final List<Integer> calories;

        private final long total;

        Elves(List<Integer> calories) {
            this.calories = calories;
            this.total = calories.stream().reduce(0, Integer::sum);
        }
    }
}
