package be.bonamis.advent.year2022;

import be.bonamis.advent.common.Grid;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {

    private static final String CODE_TXT = "2022/08/2022_08_00_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(findSize(lines)).isEqualTo(21);
    }

    @Test
    @Disabled
    void solvePart02() {
        assertThat(new Day07(getLines(CODE_TXT)).solvePart02()).isEqualTo(24);
    }

    int findSize(List<String> lines) {
        Grid grid = new Grid(lines.parallelStream().map(l -> l.trim().split(""))
                .map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new));

        grid.printArray();
        grid.stream().forEach(new Consumer<Point>() {
            @Override
            public void accept(Point point) {
                System.out.println(point + " - " + grid.get(point));
            }
        });

        return lines.size();
    }

    record Directory(String path) {

    }
}
