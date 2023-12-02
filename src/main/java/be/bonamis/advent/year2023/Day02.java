package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static be.bonamis.advent.year2023.Day02.GameParser.parseGameIds;
import static be.bonamis.advent.year2023.Day02.GameParser.parseGames;

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
        List<List<GameParser.ColorQuantity>> games = parseGames(line);
        Multimap<String, Integer> colors = ArrayListMultimap.create();
        for (List<GameParser.ColorQuantity> game : games) {
            for (GameParser.ColorQuantity colorQuantity : game) {
                log.info("colorQuantity {} ", colorQuantity);
                colors.put(colorQuantity.color(), colorQuantity.quantity());
            }
        }

        log.info("colors as map {} ", colors.asMap());

        log.info("parseGameIds {} ", i);
        log.info("line {} ", line);

        Map<String, Integer> integerMap = colors.entries()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));
        log.info("colors {}", integerMap);
        boolean red = integerMap.get("red") <= 12;
        boolean green = integerMap.get("green") <= 13;
        boolean blue = integerMap.get("blue") <= 14;
        boolean all = red && green && blue;
        return all ? i : 0;
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
        record ColorQuantity(String color, int quantity) {
        }

        public static void main(String[] args) {
            String input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";

            List<List<ColorQuantity>> games = parseGames(input);
            printGames(games);
            System.out.println(parseGameIds(input));
        }

        public static int parseGameIds(String input) {
            // Define the regex pattern to match game IDs
            String regex = "Game\\s*(\\d+):";
            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }

            return 0;
        }

        public static List<List<ColorQuantity>> parseGames(String input) {
            List<List<ColorQuantity>> gamesList = new ArrayList<>();

            // Define the regex pattern to match color quantities
            String regex = "(\\d+)\\s*(\\w+)";
            Pattern pattern = Pattern.compile(regex);

            // Split input by semicolon to separate games
            String[] games = input.split(";");

            // Process each game
            for (String game : games) {
                List<ColorQuantity> colors = new ArrayList<>();

                // Extract color quantities using regex
                Matcher matcher = pattern.matcher(game);
                while (matcher.find()) {
                    String group = matcher.group(1);
                    int quantity = Integer.parseInt(group);
                    String color = matcher.group(2);
                    colors.add(new ColorQuantity(color, quantity));
                }

                gamesList.add(colors);
            }

            return gamesList;
        }

        public static void printGames(List<List<ColorQuantity>> games) {
            for (int i = 0; i < games.size(); i++) {
                System.out.println("Game " + (i + 1) + ":");
                for (ColorQuantity colorQuantity : games.get(i)) {
                    System.out.println(colorQuantity.quantity() + " " + colorQuantity.color());
                }
                System.out.println();
            }
        }
    }
}
