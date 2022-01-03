package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2021.Day21.PlayerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day21Test {

    private Day21 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day21_test.txt");
        day = new Day21(data);
    }

    @Test
    void solvePart01() {
        assertEquals(739785, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(3, day.solvePart02());
    }

    @Test
    void moveToSpace() {
        PlayerState playerState = new PlayerState(1, 1);
        assertThat(playerState.moveToSpace(1, 10)).isEqualTo(1);
        assertThat(playerState.moveToSpace(4 + 1 + 2 + 3, 10)).isEqualTo(10);
        assertThat(playerState.moveToSpace(8 + 4 + 5 + 6, 10)).isEqualTo(3);
    }

    @Test
    void constructor() {
        assertThat(day.getPositionPlayer1()).isEqualTo(4);
        assertThat(day.getPositionPlayer2()).isEqualTo(8);
    }
}
