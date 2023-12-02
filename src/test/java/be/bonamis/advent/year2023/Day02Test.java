package be.bonamis.advent.year2023;

import be.bonamis.advent.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day02Test {

    @Test
    void solvePart01() {
        assertThat(Day02.lineCheck("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")).isEqualTo(1);
        assertThat(Day02.lineCheck("Game 28: 6 blue, 5 red, 3 green; 5 blue, 1 green; 1 green, 8 red, 1 blue; 2 blue, 4 green; 4 red, 5 blue")).isEqualTo(28);


        String content = FileHelper.content("2023/02/2023_02_03_code.txt");
        List<String> puzzle = Arrays.asList(content.split("\n"));

        assertThat(new Day02(puzzle).solvePart01()).isEqualTo(8);
    }


    @Test
    void solvePart02() {
        String line = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";

        assertThat(new Day02(List.of(line)).solvePart02()).isEqualTo(48);

        String content = FileHelper.content("2023/02/2023_02_03_code.txt");
        List<String> puzzle = Arrays.asList(content.split("\n"));

        assertThat(new Day02(puzzle).solvePart02()).isEqualTo(2286);
    }
}
