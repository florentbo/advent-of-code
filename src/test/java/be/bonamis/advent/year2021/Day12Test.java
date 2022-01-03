package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import be.bonamis.advent.common.Graph.Node;

class Day12Test {

	private Day12 day;

	private static Stream<Arguments> part01TestCases() {
		return Stream.of(
				Arguments.of("2021_day12_test_01.txt", 10),
				Arguments.of("2021_day12_test_02.txt", 19),
				Arguments.of("2021_day12_test_03.txt", 226)
		);
	}

	private static Stream<Arguments> part02TestCases() {
		return Stream.of(
				Arguments.of("2021_day12_test_01.txt", 36),
				Arguments.of("2021_day12_test_02.txt", 103),
				Arguments.of("2021_day12_test_03.txt", 3509)
		);
	}

	@ParameterizedTest
	@MethodSource("part01TestCases")
	void solve01_test(String input, int expected) {
		List<String> data = getLines(input);
		day = new Day12(data);
		assertEquals(expected, day.solvePart01());
	}

	@ParameterizedTest
	@MethodSource("part02TestCases")
	void solve02_test(String input, int expected) {
		List<String> data = getLines(input);
		day = new Day12(data);
		assertEquals(expected, day.solvePart02());
	}

	@Test
	void solve01_puzzle() {
		List<String> data = getLines("2021_day12.txt");
		day = new Day12(data);
		assertEquals(3779, day.solvePart01());
	}

	@Test
	void solve02_puzzle() {
		List<String> data = getLines("2021_day12.txt");
		day = new Day12(data);
		assertEquals(96988, day.solvePart02());
	}

	@Test
	void hasMoreThanTwoSmallCaves() {
		assertTrue(test("c", List.of("start", "A", "c", "A", "c", "A")));
		assertFalse(test("c", List.of("start", "A", "c", "A", "A")));

		assertTrue(new Day12(Collections.emptyList()).
						   hasMoreThanTwoSmallCaves("b", List.of("start", "A", "c", "A", "c", "A", "b")));
	}

	public boolean test(String node, Collection<String> isVisited) {
		boolean isSmallCave = (!node.equals("start") && !node.equals("end")) && node.toLowerCase().equals(node);
		int occurrences = Collections.frequency(isVisited, node);
		return isSmallCave && occurrences > 1;
	}

	@Test
	void node_parse() {
		Node node = Node.parse("start-A");
		assertEquals(new Node("start", "A"), node);
	}
}
