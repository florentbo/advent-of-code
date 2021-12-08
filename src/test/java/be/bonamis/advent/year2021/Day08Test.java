package be.bonamis.advent.year2021;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class Day08Test {

	private Day08 day;

	@BeforeEach
	void setUp() {
		List<String> data = getLines("2021_day08_test.txt");
		day = new Day08(data);
	}

	@Test
	void solvePart01() {
		assertEquals(26, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertEquals(61229, day.solvePart02());
	}

	@Test
	void getDigit_for_the_starting_example() {
		//Number					0		   1    2	      3		  4		  5			6		7       8         9
		String[] givenExample = {"abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"};

		assertThat(day.getDigit("cf", givenExample)).isEqualTo(1);
		assertThat(day.getDigit("bcdf", givenExample)).isEqualTo(4);
		assertThat(day.getDigit("acf", givenExample)).isEqualTo(7);
		assertThat(day.getDigit("abcdefg", givenExample)).isEqualTo(8);

		assertThat(day.getDigit("abcdfg", givenExample)).isEqualTo(9);
		assertThat(day.getDigit("abcefg", givenExample)).isZero();
		assertThat(day.getDigit("abdefg", givenExample)).isEqualTo(6);
		assertThat(day.getDigit("acdfg", givenExample)).isEqualTo(3);
		assertThat(day.getDigit("abdfg", givenExample)).isEqualTo(5);
		assertThat(day.getDigit("acdeg", givenExample)).isEqualTo(2);
	}

	@Test
	void getOutPutNumber() {
		String[] inputLine = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb".split("\\s+");

		assertThat(day.getDigit("acedgfb", inputLine)).isEqualTo(8);

		String[] output = {"fdgacbe", "cefdb", "cefbgd", "gcbe"};
		assertThat(day.getOutPutNumber(inputLine, output)).isEqualTo(8394);
	}
}
