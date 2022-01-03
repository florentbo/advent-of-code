package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2021.Day10.ExpectedChar;

class Day10Test {

    private Day10 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day10_test.txt");
        day = new Day10(data);
    }

    @Test
    void solvePart01() {
        assertEquals(26397, day.solvePart01());
    }

    @Test
    void solvePart01Puzzle() {
        List<String> data = getLines("2021_day10_prod.txt");
        day = new Day10(data);
        assertEquals(339477, day.solvePart01());
    }

    @Test
    void unExpected() {
        assertThat(day.unExpected("(]")).hasValue(']');
        assertThat(day.unExpected("{()()()>")).hasValue('>');
        assertThat(day.unExpected("(((()))}")).hasValue('}');
        assertThat(day.unExpected("{([(<{}[<>[]}>{[]{[(<()>")).hasValue('}');
    }

    @Test
    void errorScore() {
        assertEquals(3, day.errorScore(')'));
        assertEquals(57, day.errorScore(']'));
        assertEquals(1197, day.errorScore('}'));
        assertEquals(25137, day.errorScore('>'));
    }

    @Test
    void inComplete() {
        assertThat(day.inComplete("(]")).isEmpty();

        Optional<Deque<Character>> actual = day.inComplete("[({(<(())[]>[[{[]{<()<>>");
        assertThat(actual.isPresent()).isTrue();
        //[({(<(())[]>[[{[]{<()<>> - Complete by adding }}]])})].
        assertThat(new ArrayList<>(actual.get()))
                                    .isEqualTo(List.of('{', '{', '[', '[', '(', '{', '(', '['));
    }

    @Test
    void completionClosingCharacters() {
        ArrayDeque<Character> characters = new ArrayDeque<>(List.of('{', '{', '[', '[', '(', '{', '(', '['));
        List<ExpectedChar> expectedChars = day.completionClosingCharacters(characters);
        List<Character> characterList = expectedChars.stream().map(ExpectedChar::getExpected).collect(toList());
        assertThat(characterList).isEqualTo(List.of('}', '}', ']', ']', ')', '}', ')', ']'));
    }

    @Test
    void completionScore() {
        assertThat(day.completionScore(Stream.of(2L,1L,3L,4L))).isEqualTo(294L);
    }

    @Test
    void middleScore() {
        assertThat(day.middleScore(Stream.of(2L,1L,3L,4L,5L))).isEqualTo(3L);
    }

    @Test
    void solvePart02() {
        assertEquals(288957, day.solvePart02());
    }

    @Test
    void solvePart02Puzzle() {
        List<String> data = getLines("2021_day10_prod.txt");
        day = new Day10(data);
        assertEquals(3049320156L, day.solvePart02());
    }
}
