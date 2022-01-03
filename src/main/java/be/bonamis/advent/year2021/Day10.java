package be.bonamis.advent.year2021;

import static be.bonamis.advent.year2021.Day10.ExpectedChar.of;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.DaySolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day10 extends DaySolver<String> {

    public static final Map<Character, ExpectedChar> CHUNK = Map.of('{', of('}', 1197, 3),
            '(', of(')', 3, 1),
            '[', of(']', 57, 2),
            '<', of('>', 25137, 4));

    public Day10(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return this.puzzle
                .stream()
                .map(this::unExpected).filter(Optional::isPresent)
                .map(unExpectedChar -> errorScore(unExpectedChar.get())).reduce(0, Integer::sum);
    }

    Optional<Character> unExpected(String input) {
        Stack<Character> stack = new Stack<>();
        for (char c : input.toCharArray()) {
            if (CHUNK.containsKey(c)) {
                stack.push(c);
            } else {
                Character pop = stack.pop();
                if (CHUNK.get(pop).expected != c) {
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    int errorScore(char c) {
        return CHUNK.values()
                .stream()
                .filter(expectedChar -> expectedChar.expected == c)
                .mapToInt(expectedChar -> expectedChar.unExpectedPoints).findFirst()
                .orElse(0);
    }

    @Override
    public long solvePart02() {
        return middleScore(this.puzzle
                .stream()
                .map(this::inComplete).filter(Optional::isPresent)
                .map(unExpectedChar -> completionClosingCharacters(unExpectedChar.get()))
                .map(expectedChars -> expectedChars.stream().map(ExpectedChar::getCompletionPoints))
                .map(this::completionScore));
    }

    Optional<Deque<Character>> inComplete(String input) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : input.toCharArray()) {
            if (CHUNK.containsKey(c)) {
                stack.push(c);
            } else {
                Character pop = stack.pop();
                if (CHUNK.get(pop).expected != c) {
                    return Optional.empty();
                }
            }
        }
        return stack.isEmpty() ? Optional.empty() : Optional.of(stack);
    }

    public List<ExpectedChar> completionClosingCharacters(Deque<Character> characters) {
        return characters.stream().map(CHUNK::get).collect(Collectors.toList());
    }

    public long completionScore(Stream<Long> scores) {
        return scores.reduce(0L, (a, b) -> 5 * a + b);
    }

    public long middleScore(Stream<Long> scores) {
        List<Long> sortedScored = scores.sorted().collect(Collectors.toList());
        return sortedScored.get(sortedScored.size() / 2);
    }

    @AllArgsConstructor
    @Getter
    static class ExpectedChar {
        private final char expected;
        private final int unExpectedPoints;
        private final long completionPoints;

        public static ExpectedChar of(char c, int unExpectedPoints, int completionPoints) {
            return new ExpectedChar(c, unExpectedPoints, completionPoints);
        }
    }


}
