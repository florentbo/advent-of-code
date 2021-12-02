package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getIntegers;
import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.year2021.Day02.Move.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.Day;

class Day02Test {

	private Day day;

	@BeforeEach
	void setUp() {
		List<String> data = getLines("2021_day02_test.txt");
		day = new Day02(data);
	}

	@Test
	void solvePart01() {
		assertEquals(150, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertEquals(900, day.solvePart02());
	}

	@Test
	void move_construct() {
		Day02.Move move = new Day02.Move("forward 5");
		assertEquals(FORWARD, move.getDirection());
		assertEquals(5, move.getUnits());
	}
}
