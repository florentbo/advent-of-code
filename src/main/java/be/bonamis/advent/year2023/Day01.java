package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day01 extends DaySolver<String> {

    public Day01(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return this.puzzle.stream().map(s -> {
            long nextNumber = findFirstDigit(s);
            long nextNumber2 = findLastDigit(s);
            String text = nextNumber + "" + nextNumber2;
            return Integer.parseInt(text);
        }).reduce(0, Integer::sum);
    }

    private static long findFirstDigit(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return Long.parseLong(String.valueOf(c));

            }
        }
        return '\0'; // return a default value if no digit is found
    }

    private static long findLastDigit(String input) {
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                return Long.parseLong(String.valueOf(c));

            }
        }
        return '\0'; // return a default value if no digit is found
    }

    @Override
    public long solvePart02() {
        return this.puzzle.stream().map(s -> {
            List<Day01Number> numbersAndPositions = findNumbersAndPositions(s);
            //log.info("numbersAndPositions: {}", numbersAndPositions);
            List<Day01Number> numbersAndPositions2 = findDigitAndPositions(s);
            //log.info("numbersAndPositions2: {}", numbersAndPositions2);
            numbersAndPositions.addAll(numbersAndPositions2);
            //log.info("numbersAndPositions: {}", numbersAndPositions);
            Day01Number smallest = numbersAndPositions.stream().min(Comparator.comparing(Day01Number::position)).orElseThrow();
            // log.info("smallest: {}", smallest);
            Day01Number biggest = numbersAndPositions.stream().max(Comparator.comparing(Day01Number::position)).orElseThrow();


            long nextNumber = smallest.number;
            long nextNumber2 = biggest.number;
            String text = nextNumber + "" + nextNumber2;
            return Integer.parseInt(text);
        }).reduce(0, Integer::sum);
    }

    record Day01Number(long number, int position) {
    }

    private List<Day01Number> findDigitAndPositions(String input) {
        List<Day01Number> day01Numbers = new ArrayList<>();
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (Character.isDigit(c)) {
                day01Numbers.add(new Day01Number(Long.parseLong(String.valueOf(c)), i));
            }
        }
        return day01Numbers;
    }

    private static List<Day01Number> findNumbersAndPositions(String input) {
        List<Day01Number> day01Numbers = new ArrayList<>();
        List<String> numbers = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        for (String number : numbers) {
            int index = input.indexOf(number);
            while (index != -1) {
                day01Numbers.add(new Day01Number(textToDigit(number), index));
                index = input.indexOf(number, index + 1);
            }
        }
        return day01Numbers;
    }


    private static long textToDigit(String text) {
        return switch (text) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        String content = FileHelper.content("2023/01/2023_01_input.txt");
        List<String> puzzle = Arrays.asList(content.split("\n"));
        Day01 day = new Day01(puzzle);
        log.info("solution part 1: {}", day.solvePart01());
        log.info("solution part 2: {}", day.solvePart02());
    }
}
