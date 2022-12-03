package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day03 extends DaySolver<String> {

    public Day03(List<String> puzzle) {
        super(puzzle);
    }

    public static int alphabetPosition(char input) {
        return Character.isUpperCase(input) ? input - 'A' + 27 : input - 'a' + 1;
    }

    @Override
    public long solvePart01() {
        return this.puzzle.stream().map(line -> {
            int half = line.length() / 2;
            String part1 = line.substring(0, half);
            String part2 = line.substring(half);
            return alphabetPosition(getIntersection(List.of(charactersSet(part1), charactersSet(part2))).iterator().next());
        }).reduce(0, Integer::sum);
    }

    @Override
    public long solvePart02() {
        return IntStream.range(0, this.puzzle.size())
                .filter(idx -> idx % 3 == 0)
                .map(i -> {
                    Set<Character> intersection = getIntersection(List.of(
                            charactersSet(this.puzzle.get(i)),
                            charactersSet(this.puzzle.get(i + 1)),
                            charactersSet(this.puzzle.get(i + 2))));
                    return alphabetPosition(intersection.iterator().next());
                })
                .sum();
    }

    public static <T> Set<T> getIntersection(List<Set<T>> listOfSets) {
        return listOfSets.stream().skip(1)
                .collect(() -> new HashSet<>(listOfSets.get(0)), Set::retainAll, Set::retainAll);
    }

    public static Set<Character> charactersSet(String input) {
        return input
                .chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toSet());
    }

    public static List<Character> charactersList(String input) {
        return input
                .chars()
                .mapToObj(e -> (char) e)
                .toList();
    }
}
