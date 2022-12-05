package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05 extends DaySolver<String> {

    private final CharGrid charGrid;

    public Day05(List<String> puzzle) {
        super(puzzle);
        charGrid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
    }


    public static Stream<Character> getColumn2(char[][] matrix, int column) {
        return Arrays.stream(matrix).map(ints -> ints[column]);
    }

    public String solvePart01String() {

        List<StackInput> stackInputs = List.of(
                new StackInput(1, "ZN"),
                new StackInput(2, "MCD"),
                new StackInput(3, "P")
        );
        Map<Integer, Stack<String>> map = new LinkedHashMap<>();
        for (StackInput stackInput : stackInputs) {
            Stack<String> stack = new Stack<>();
            stack.addAll(stackInput.stack());
            map.put(stackInput.number(), stack);
        }

        this.puzzle.stream().skip(4).forEach(System.out::println);

        System.out.println("map before move 1 " + map);



        List<Instruction> instructions = this.puzzle.stream().skip(5).map(Instruction::fromLine).toList();
        for (Instruction instruction : instructions) {
            System.out.println("instruction: " + instruction);
            for (int i = 0; i < instruction.numberToMove; i++) {
                int origin = instruction.origin();
                Stack<String> old = map.get(origin);
                String pop = old.pop();
                System.out.println("old: " + old);
                map.get(instruction.destination()).push(pop);
                System.out.println("map after move  " + i + " map " + map);
            }
        }
        System.out.println("map after final move " + map);
        String strings = map.values().stream().map(Stack::peek).collect(Collectors.joining(""));

        System.out.println("final result " + strings);



        return strings;
    }

    @Override
    public long solvePart01() {
        return 0;
    }

    @Override
    public long solvePart02() {
        return this.puzzle.size() + 1;
    }

    record StackInput(int number, String data) {
        List<String> stack() {
            return Arrays.stream(data.split("")).toList();
        }
    }

    record Instruction(int numberToMove, int origin, int destination) {
        public static Instruction fromLine(String line) {
            System.out.println("line: " + line);
            String input = "move 1 from 2 to 1";
            System.out.println("inpu: " + input);
            List<Integer> matches2 = Pattern.compile("\\d+")
                    .matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .map(Integer::parseInt)
                    .toList();

            return new Instruction(matches2.get(0), matches2.get(1), matches2.get(2));
        }
    }
}
