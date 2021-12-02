package be.bonamis.advent.utils;

import static be.bonamis.advent.utils.FileHelper.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class FileHelperTest {

	@Test
	void getIntegers_returnListOfNumbers() {
		final var name = "2020_day1_test.txt";
		List<Integer> data = getIntegers(name);
		assertEquals(List.of(1721,979,366,299,675,1456), data);
	}

	@Test
	void getLines_returnListOfStrings() {
		final var name = "2020_day1_test.txt";
		List<String> data = getLines(name);
		assertEquals(List.of("1721","979","366","299","675","1456"), data);
	}
}
