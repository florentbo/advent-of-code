package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;

import java.util.*;

public class Day03 extends DaySolver<String> {

    public Day03(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return this.puzzle.stream().map(Day03::getResult).reduce(0L, Long::sum);
    }

    private static long getResult(String line) {
        System.out.println("line: " + line);
        int half = line.length() / 2;
        String part1 = line.substring(0, half);
        System.out.println("part1: " + part1);
        String part2 = line.substring(half);
        System.out.println("part2: " + part2);

        return extracted(part1, part2);
    }

    private static int extracted(String part1, String part2) {
        List<Character> list1 = getCharacters(part1);

        List<Character> list2 = getCharacters(part2);

        List<Character> collect = list1.stream()
                .filter(list2::contains)
                .toList();
        return number(collect);
    }

    private static int number(List<Character> collect) {
        Character common = collect.get(0);
        String alphabet = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.println(common);

        int index1 = Arrays.binarySearch(alphabet.toCharArray(), common) + 1;
        int a = Character.toUpperCase(common) - 65 + 26 + 1;
        return index1 == 0 ? a : index1;
    }


    @Override
    public long solvePart02() {
        int result = 0;
        for (int i = 0; i < this.puzzle.size(); i += 3) {
            int number = getNumber(this.puzzle, i + 2, i);
            System.out.println("number1: " + number);
            result += number;
        }
        return result;
    }

    private static int getNumber(List<String> lines, int halfSize, int start) {
        List<Character> collect = getCharacters(lines.get(start));
        for (int i = start + 1; i <= halfSize; i++) {
            List<Character> list2 = getCharacters(lines.get(i));
            System.out.println("count" + i);
            collect = collect.stream()
                    .filter(list2::contains)
                    .toList();
        }
        return number(collect);
    }

    private static List<Character> getCharacters(String lines) {
        return lines
                .chars()
                .mapToObj(e -> (char) e)
                .toList();
    }
}
