package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.utils.EqualArrays;
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
		assertEquals(315, day.solvePart02());
	}

	@Test
	void enlarge() {
		int[][] input = {
				{8, 9},
				{4, 1}};

		int[][] input2 = {
				{8, 9},
				{4, 1}};

		int[][] enlargeResult = {
				{8, 9, 9, 1},
				{4, 1, 5, 2},
				{9, 1, 1, 2},
				{5, 2, 6, 3}};

		assertTrue(EqualArrays.equal(input, input2));
		assertTrue(EqualArrays.equal(enlargeResult, day.enlarge(input, 2)));
	}
}
