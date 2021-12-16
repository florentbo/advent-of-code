package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day15Test {

	private Day15 day;

	@BeforeEach
	void setUp() {
		List<String> data = getLines("2021_day15_test.txt");
		day = new Day15(data);
	}

	@Test
	void solvePart01() {
		assertEquals(40, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertEquals(0, day.solvePart02());
	}
}
