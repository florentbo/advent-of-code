package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.year2021.Day06.initResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day06Test {

	private Day06 day;

	@BeforeEach
	void setUp() {
		List<String> data = getLines("2021_day06_test.txt");
		day = new Day06(data);
	}

	@Test
	void solvePart01() {
		assertEquals(5934, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertEquals(26984457539L, day.solvePart02());
	}

	@Test
	void oneIterationWithWhile() {
		final var fishes = List.of(3, 4, 3, 1, 2);
		final var fishesAfterDay01 = new ArrayList<>(fishes);
		day.updateList(fishesAfterDay01, 1);
		assertEquals(List.of(2, 3, 2, 0, 1), fishesAfterDay01);
		assertThat(fishesAfterDay01).hasSize(5);
	}

	@Test
	void twoIterationWithWhile() {
		final var fishes = List.of(3, 4, 3, 1, 2);
		final var fishesAfterDay02 = new ArrayList<>(fishes);
		day.updateList(fishesAfterDay02, 2);
		assertEquals(List.of(1, 2, 1, 6, 0, 8), fishesAfterDay02);
		assertThat(fishesAfterDay02).hasSize(6);
	}

	@Test
	void having_18_iterationWithWhile() {
		final var fishes = List.of(3, 4, 3, 1, 2);
		final var fishesAfterDay18 = new ArrayList<>(fishes);
		day.updateList(fishesAfterDay18, 18);
		assertThat(fishesAfterDay18).hasSize(26);
	}

	@Test
	void oneIterationWithMap() {
		final var fishes = List.of(3, 4, 3, 1, 2);
		Map<Integer, Long> map = initResult();
		for (Integer fish : fishes) {
			final var value = map.get(fish);
			map.put(fish, value + 1);
		}
		assertThat(day.updateMap(map, 1)).containsAnyOf(entry(2, 2L),
														entry(3, 1L),
														entry(0, 1L),
														entry(1, 1L));
	}

	@Test
	void twoIterationWithMap() {
		final var fishes = List.of(3, 4, 3, 1, 2);
		Map<Integer, Long> map = initResult();
		for (Integer fish : fishes) {
			final var value = map.get(fish);
			map.put(fish, value + 1);
		}        //1, 2, 1, 6, 0, 8
		assertThat(day.updateMap(map, 2)).containsAnyOf(entry(1, 2L),
														entry(2, 1L),
														entry(6, 1L),
														entry(0, 1L),
														entry(8, 1L));
	}
}
