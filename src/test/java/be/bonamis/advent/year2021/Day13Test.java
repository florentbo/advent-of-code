package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.year2021.Day13.Fold.Direction.LEFT;
import static be.bonamis.advent.year2021.Day13.Fold.Direction.UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.common.Grid;
import be.bonamis.advent.year2021.Day13.Fold;

class Day13Test {

	private Day13 day;

	@BeforeEach
	void setUp() {
		List<String> data = getLines("2021_day13_test.txt");
		day = new Day13(data);
	}

	@Test
	void solvePart01() {
		assertEquals(17, day.solvePart01());
	}

	@Test
	void solvePart02() {
		assertEquals(16, day.solvePart02());
	}

	@Test
	void fold_constructor() {
		assertEquals(new Fold(UP, 7), new Fold("fold along y=7"));
		assertEquals(new Fold(LEFT, 5), new Fold("fold along x=5"));
	}

	@Test
	void day_constructor() {
		assertThat(day.getPoints()).hasSize(18);
		assertThat(day.getFolds()).hasSize(2);
		assertThat(day.getGrid().getHeight()).isEqualTo(15);
		assertThat(day.getGrid().getWidth()).isEqualTo(11);
	}

	@Test
	void fold() {
		int[][] input = {
				{1, 0, 0, 1, 0},
				{0, 1, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{1, 0, 0, 0, 0}};

		System.out.println(input.length);
		System.out.println(input[0].length);

		Grid grid = new Grid(input);
		grid.printArray();
		System.out.println();
		System.out.println("next copy the stuff");
		Grid grid2 = day.fold(grid, new Fold(LEFT, 2));
		grid2.printArray();
	}
}
