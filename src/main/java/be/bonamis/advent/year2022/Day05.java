package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day05 extends DaySolver<String> {


    public Day05(List<String> puzzle) {
        super(puzzle);
    }

    public String solvePart01String() {
        Map<Integer, Deque<String>> map = readInput(this.puzzle);

        this.puzzle.stream().skip(map.size()).forEach(System.out::println);

        System.out.println("map before move 1 " + map);

        List<Instruction> instructions = this.puzzle.stream().skip(map.size() + 2).map(Instruction::fromLine).toList();
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.numberToMove; i++) {
                int origin = instruction.origin();
                String last = map.get(origin).removeLast();
                map.get(instruction.destination()).addLast(last);
            }
            System.out.println("map after move  map " + map);
        }
        return map.values().stream().map(Deque::removeLast).collect(Collectors.joining(""));
    }

    private Map<Integer, Deque<String>> readInput(List<String> puzzle) {
        int blankLineSeparatorIndex = puzzle.indexOf("");
        int cravesInputWidth = puzzle.get(blankLineSeparatorIndex - 1).length();
        System.out.println(cravesInputWidth);
        int cravesNumber = (cravesInputWidth + 1) / 4;
        System.out.println("cravesNumber: " + cravesNumber);
        Map<Integer, Deque<String>> map = new LinkedHashMap<>();
        for (int i = 0; i < cravesNumber; i++) {
            map.put(i + 1, new ArrayDeque<>());
        }

        for (int i = 0; i < blankLineSeparatorIndex - 1; i++) {
            String[] line = puzzle.get(i).split("");
            for (int c = 0; c <= cravesNumber; c++) {
                int columnNumber = c * 4 + 1;
                if (line.length > columnNumber) {
                    String crate = line[columnNumber];
                    if (!crate.isBlank()) {
                        map.get(c + 1).addFirst(crate);
                    }
                }
            }
        }


        return map;
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
            /*System.out.println("line: " + line);
            String input = "move 1 from 2 to 1";
            System.out.println("inpu: " + input);*/
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
