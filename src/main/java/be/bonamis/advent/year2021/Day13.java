package be.bonamis.advent.year2021;

import static be.bonamis.advent.year2021.Day13.Fold.Direction.LEFT;
import static be.bonamis.advent.year2021.Day13.Fold.Direction.UP;
import static be.bonamis.advent.year2021.Day13.Fold.Direction.from;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Grid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day13 extends DaySolver<String> {

	private final List<Point> points;
	private final List<Fold> folds;
	private final Grid grid;

	public Day13(List<String> puzzle) {
		super(puzzle);
		OptionalInt indexOpt = IntStream.range(0, puzzle.size())
				.filter(i -> puzzle.get(i).length() == 0)
				.findFirst();

		if (indexOpt.isPresent()) {
			final var index = indexOpt.getAsInt();
			this.points = IntStream.range(0, index).mapToObj(value -> {
				final var s = puzzle.get(value).split(",");
				return new Point(Integer.parseInt(s[1]), Integer.parseInt(s[0]));
			}).collect(Collectors.toList());

			this.folds = IntStream.range(index + 1, puzzle.size()).mapToObj(value -> new Fold(puzzle.get(value))).collect(Collectors.toList());
			final ToIntFunction<Double> doubleToIntFunction = value -> {
				double data = value;
				return (int) data;
			};
			int height = this.points.stream().map(Point::getX).mapToInt(doubleToIntFunction).max().orElseThrow(NoSuchElementException::new) + 1;
			int wide = this.points.stream().map(Point::getY).mapToInt(doubleToIntFunction).max().orElseThrow(NoSuchElementException::new) + 1;

			log.info("max Y:{} max X:{}", height, wide);

			this.grid = new Grid(new int[height][wide]);
			this.points.forEach(point -> grid.set(point, 1));

		} else {
			this.points = Collections.emptyList();
			this.folds = Collections.emptyList();
			this.grid = null;
		}
	}

	@Override
	public long solvePart01() {
		final var fold = this.folds.get(0);
		log.info("fold: {}", fold);

		Grid foldedGrid = fold(grid, fold);
		return foldedGrid.stream().map(foldedGrid::get).reduce(0, Integer::sum);
	}

	@Override
	public long solvePart02() {
		Grid foldedGrid = this.grid;

		for (final Fold fold : this.folds) {
			log.info("fold: {}", fold);
			foldedGrid = fold(foldedGrid, fold);

		}
		foldedGrid.printArray();

		return foldedGrid.stream().map(foldedGrid::get).reduce(0, Integer::sum);
	}

	Grid fold(Grid grid, Fold fold) {
		int height = grid.getHeight();
		int width = grid.getWidth();
		int[][] input = grid.getData();
		if (fold.direction == UP) {
			return foldUp(fold.linePosition, width, input, height);
		} else if (fold.direction == LEFT) {
			return foldLeft(height, fold.linePosition, input, width);
		} else {
			throw new NoSuchElementException("Wrong direction");
		}
	}

	private Grid foldUp(int height, int width, int[][] input, int actualHeight) {
		int[][] folded = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				folded[i][j] = input[i][j] | input[actualHeight - i - 1][j];
			}
		}
		return new Grid(folded);
	}

	private Grid foldLeft(int height, int width, int[][] input, int actualWidth) {
		int[][] folded = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				folded[i][j] = input[i][j] | input[i][actualWidth - j - 1];
			}
		}
		return new Grid(folded);
	}

	@AllArgsConstructor
	@EqualsAndHashCode
	@ToString
	static class Fold {
		private final Direction direction;
		private final Integer linePosition;

		public Fold(String input) {
			final var split = input.split("along");
			this.direction = from(split[1].split("=")[0].trim());
			this.linePosition = Integer.parseInt(split[1].split("=")[1].trim());
		}

		enum Direction {
			UP,
			LEFT;

			static Direction from(String input) {
				return input.equals("x") ? LEFT : UP;
			}
		}
	}
}
