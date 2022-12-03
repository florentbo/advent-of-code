package be.bonamis.advent.year2022;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.year2022.Day03.*;
import static be.bonamis.advent.year2022.Day03.getIntersection;
import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    public static final String CODE_TXT = "2022/03/2022_03_02_code.txt";

    @Test
    void solvePart01() {
        assertThat(new Day03(getLines(CODE_TXT)).solvePart01()).isEqualTo(157);
    }

    @Test
    void solvePart02() {
        assertThat(new Day03(getLines(CODE_TXT)).solvePart02()).isEqualTo(70);
    }

    @ParameterizedTest
    @CsvSource({"b,2", "c,3", "B,28", "C,29"})
    void alphabetPosition_ReturnsThePositionOfTheLetterInTheAlphabet(char input, int expected) {
        assertThat(alphabetPosition(input)).isEqualTo(expected);
    }

    @Test
    void intersection() {
        Set<Integer> set01 = Set.of(1, 2, 3);
        Set<Integer> set02 = Set.of(2, 3, 4);
        Set<Integer> set03 = Set.of(3, 4, 5);
        List<Set<Integer>> listOfSets = List.of(set01, set02, set03);
        Set<Integer> intersection = getIntersection(listOfSets);
        assertThat(intersection).containsExactly(3);
    }

    @Test
    void commonItems() {
        Set<Character> set01 = charactersSet("vJrwpWtwJgWr");
        Set<Character> set02 = charactersSet("hcsFMMfFFhFp");
        List<Set<Character>> listOfSets = List.of(set01, set02);
        Set<Character> intersection = getIntersection(listOfSets);
        assertThat(intersection).containsExactly('p');
    }
}
