package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.year2023.GameParser.ColorQuantity;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.bonamis.advent.year2023.Day02.GameParser.parseGameIds;
import static be.bonamis.advent.year2023.GameParser.parseInput;

@Slf4j
public class Day02 extends DaySolver<String> {

    public Day02(List<String> puzzle) {
        super(puzzle);
    }

    public static int solve(List<String> puzzle) {
        return puzzle.stream().mapToInt(Day02::lineCheck).sum();
    }

    public static int lineCheck(String line) {
        int i = parseGameIds(line);
        log.info("parseGameIds {} ", i);
        log.info("line {} ", line);
        List<ColorQuantity> games = parseInput(line);
        log.info("games {} ", games);


        return games.stream().allMatch(Day02::check) ? i : 0;
    }

    private static boolean check(ColorQuantity colorQuantity) {
        return max(colorQuantity.color()) >= colorQuantity.quantity();
    }


    public static int max(String color) {
        return switch (color) {
            case "red" -> 12;
            case "green" -> 13;
            case "blue" -> 14;
            default -> throw new IllegalStateException("color is not ok" + color);
        };
    }

    @Override
    public long solvePart01() {
        return solve(this.puzzle);
    }

    @Override
    public long solvePart02() {
        return this.puzzle.size() + 1;
    }

    public static void main(String[] args) {
        String content = FileHelper.content("2023/02/2023_02_input.txt");
        List<String> puzzle = Arrays.asList(content.split("\n"));
        Day02 day = new Day02(puzzle);
        log.info("solution part 1: {}", day.solvePart01());
        log.info("solution part 2: {}", day.solvePart02());
    }

    public static class GameParser {

        public static int parseGameIds(String input) {
            String regex = "Game\\s*(\\d+):";

            Matcher matcher = Pattern.compile(regex).matcher(input);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }

            return 0;
        }


    }
}
