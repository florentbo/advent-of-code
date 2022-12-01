package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day01 extends DaySolver<String> {

    public Day01(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return getElves().stream()
                .mapToLong(Elves::getTotal)
                .max().orElseThrow();
    }

    @Override
    public long solvePart02() {
        return getElves().stream()
                .map(Elves::getTotal)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(0L, Long::sum);
    }

    private List<Elves> getElves() {
        List<Elves> list = new ArrayList<>();
        List<Integer> calories = new ArrayList<>();
        for (String line : puzzle) {
            if (line.isEmpty()) {
                list.add(new Elves(calories));
                calories = new ArrayList<>();
            } else {
                int calorie = Integer.parseInt(line);
                calories.add(calorie);
            }
        }
        list.add(new Elves(calories));
        return list;
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
