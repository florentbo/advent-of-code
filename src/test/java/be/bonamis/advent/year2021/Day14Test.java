package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day14Test {

	private Day14 day;

	@BeforeEach
	void setUp() {
		List<String> data = getLines("2021_day14_test.txt");
		day = new Day14(data);
	}

	@Test
	void solvePart01() {
		assertEquals(1588, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertThat(day.solvePart02()).isGreaterThan(10000);
	}

	@Test
	void constructor() {
		assertThat(day.getPolymerTemplate()).isEqualTo("NNCB");
		assertThat(day.getRules()).hasSize(16);
	}

	@Test
	void characters_count_after_step_1() {
		final var steps = day.naiveSteps(1);
		log.info("steps: {}", steps);
		final var count = day.naiveCount(steps);
		log.info("count: {}", count);
		assertThat(count).contains(entry('B', 2), entry('H', 1));
	}

	@Test
	void characters_count_after_step_2() {
		final var steps = day.naiveSteps(2);
		log.info("steps: {}", steps);
		final var count = day.naiveCount(steps);
		log.info("count: {}", count);
		assertThat(count).contains(entry('B', 6), entry('H', 1));
	}

	@Test
	void characters_count_before_step_second_method() {
		final var steps2 = day.steps(0);
		log.info("steps2: {}", steps2);
		final var count = day.count(steps2);
		log.info("count: {}", count);
		assertThat(count).containsOnly(entry('N', 2L), entry('C', 1L), entry('B', 1L));
	}

	@Test
	void stepsResult_one_run() {
		final var steps = day.naiveSteps(1);
		assertThat(steps).isEqualTo("NCNBCHB");
		System.out.println(day.naiveCount(steps));
	}

	@Test
	void stepsResult_after_2_runs() {
		assertThat(day.naiveSteps(2)).isEqualTo("NBCCNBBBCBHCB");
	}

	@Test
	void stepsResult_multiple_runs() {
		assertThat(day.naiveSteps(3)).isEqualTo("NBBBCNCCNBBNBNBBCHBHHBCHB");
		assertThat(day.naiveSteps(4)).isEqualTo("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB");
	}

	@Test
	void characters_count_after_step_1_second_method() {
		final var steps2 = day.steps(1);
		log.info("steps2: {}", steps2);
		final var count = day.count(steps2);
		log.info("count: {}", count);
		assertThat(count).contains(entry('B', 2L), entry('H', 1L));
	}

	@Test
	void characters_count_after_step_2_second_method() {
		final var steps2 = day.steps(2);
		log.info("steps2: {}", steps2);
		final var count = day.count(steps2);
		log.info("count: {}", count);
		assertThat(count).contains(entry('B', 6L), entry('H', 1L));
	}

	@Test
	void characters_count_after_step_10() {
		final var steps = day.naiveSteps(10);
		log.info("steps: {}", steps);
		assertThat(day.naiveCount(steps)).contains(entry('B', 1749), entry('H', 161));
	}
}
