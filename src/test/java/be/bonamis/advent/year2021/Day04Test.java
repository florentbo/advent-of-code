package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.Day;

class Day04Test {

	private Day day;

	@BeforeEach
	void setUp() {
		List<String> data = getLines("2021_day04_test.txt");
		day = new Day04(data);
	}

	@Test
	void solvePart01() {
		assertEquals(4512, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertEquals(1924, day.solvePart02());
	}
}
