package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getIntegers;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.Day;

class Day01AnotherTest {

	private Day day;

	@BeforeEach
	void setUp() {
		List<Integer> data = getIntegers("2021_day1_test.txt");
		day = new Day01Another(data);
	}

	@Test
	void solvePart01() {
		assertEquals(7, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertEquals(5, day.solvePart02());
	}

}
