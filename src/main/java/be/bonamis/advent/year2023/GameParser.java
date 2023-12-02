package be.bonamis.advent.year2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameParser {

    public static void main(String[] args) {
        String input = "Game 28: 6 blue, 5 red, 3 green; 5 blue, 1 green; 1 green, 8 red, 1 blue; 2 blue, 4 green; 4 red, 5 blue";
        List<ColorQuantity> colorQuantities = parseInput(input);

        for (ColorQuantity colorQuantity : colorQuantities) {
            System.out.println(colorQuantity);
        }
    }

    record ColorQuantity(String color, int quantity) {
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
}
