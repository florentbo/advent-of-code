package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.CollectionsHelper;
import be.bonamis.advent.utils.FileHelper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.bonamis.advent.year2023.Day02.GameParser.parseGameIds;

@Slf4j
public class Day02 extends DaySolver<String> {

    public Day02(List<String> puzzle) {
        super(puzzle);
    }

    public static int lineCheck(String line) {
        int i = parseGameIds(line);
        log.debug("parseGameIds {} ", i);
        log.debug("line {} ", line);
        List<ColorQuantity> games = parseInput(line);
        log.debug("games {} ", games);
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

    public static List<ColorQuantity> parseInput(String input) {
        List<ColorQuantity> colorQuantities = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)\\s(\\w+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));
            String color = matcher.group(2);
            colorQuantities.add(new ColorQuantity(color, quantity));
        }

        return colorQuantities;
    }

    @Override
    public long solvePart01() {
        return this.puzzle.stream().mapToInt(Day02::lineCheck).sum();
    }

    @Override
    public long solvePart02() {
        return this.puzzle.stream().map(Day02::toProduce).reduce(0, Integer::sum);
    }

    private static int toProduce(String line) {
        List<ColorQuantity> games = parseInput(line);
        Multimap<String, Integer> colors = ArrayListMultimap.create();
        for (ColorQuantity game : games) {
            colors.put(game.color(), game.quantity());
        }
        return colors.asMap().values().stream().map(CollectionsHelper::max).reduce(1, (a, b) -> a * b);
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

    record ColorQuantity(String color, int quantity) {
    }
}
