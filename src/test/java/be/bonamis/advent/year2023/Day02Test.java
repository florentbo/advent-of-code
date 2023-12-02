package be.bonamis.advent.year2023;

import be.bonamis.advent.utils.FileHelper;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day02Test {

    @Test
    void solvePart01() {
        String line = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
        int all = Day02.lineCheck(line);
        System.out.println(all);

        assertThat(all).isEqualTo(1);
        String content = FileHelper.content("2023/02/2023_02_03_code.txt");
        List<String> puzzle = Arrays.asList(content.split("\n"));

        assertThat(Day02.solve(puzzle)).isEqualTo(8);


    }


    private static Map<String, Integer> convertMultimapToMap(Multimap<String, Integer> multimap) {
        return multimap.entries()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> replacement
                ));
    }

    @Test
    void solvePart02() {

        assertThat(new Day02(List.of("1abc12", "pqr3stu8vwx")).solvePart02()).isEqualTo(3);
    }
}
