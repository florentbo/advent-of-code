package be.bonamis.advent.year2021;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;

import be.bonamis.advent.DaySolver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day17 extends DaySolver<String> {
	public Day17(List<String> puzzle) {
		super(puzzle);
	}

	@Override
	public long solvePart01() {
		final ToIntFunction<Double> doubleToIntFunction = value -> {
			double data = value;
			return (int) data;
		};
		final var points = getPoints(puzzle.get(0));
		int maxY = points.stream().map(Point::getY).mapToInt(doubleToIntFunction).max().orElseThrow();
		return (long) maxY * (maxY + 1) / 2;
	}

	@Override
	public long solvePart02() {
		return getPoints(puzzle.get(0)).size();
	}

	private Set<Point> getPoints(String input) {
		TargetArea targetArea = new TargetArea(input);

		int maxX = targetArea.xLimit.max;
		int minY = targetArea.yLimit.min;

		Set<Point> points = new HashSet<>();
		for (int i = 1; i <= maxX; i++) {
			for (int j = minY; j <= -minY; j++) {
				Point movingPoint = new Point(0, 0);
				int speedX = i;
				int speedY = j;
				while (movingPoint.x <= maxX && movingPoint.y >= minY) {
					int newPointX = movingPoint.x + speedX;
					int newPointY = movingPoint.y + speedY;
					movingPoint = new Point(newPointX, newPointY);
					if (targetArea.contains(movingPoint)) {
						points.add(new Point(i, j));
						break;
					}
					speedY -= 1;
					speedX = speedX > 0 ? speedX - 1 : 0;
				}
			}
		}
		return points;
	}

	@Getter
	static class TargetArea {

		private static final String DOUBLE_DOT_REGEX = "\\.\\.";
		private final int[] xLimits;
		private final int[] yLimits;
		private final Limit xLimit;
		private final Limit yLimit;

		public TargetArea(String input) {
			String limits = input.substring("target area: ".length());
			final var split = limits.split(", ");
			this.xLimits = getLimits(split[0].substring(2));
			this.yLimits = getLimits(split[1].substring(2));

			this.xLimit = new Limit(split[0].substring(2));
			this.yLimit = new Limit(split[1].substring(2));
		}

		private int[] getLimits(String s) {
			return new int[]{Integer.parseInt(s.split(DOUBLE_DOT_REGEX)[0]), Integer.parseInt(s.split(DOUBLE_DOT_REGEX)[1])};
		}

		public boolean contains(Point point) {
			return this.yLimit.min <= point.y && this.yLimit.max >= point.y && this.xLimit.min <= point.x && this.xLimit.max >= point.x;
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@ToString
		static class Limit {
			private final int min;
			private final int max;

			public Limit(String limit) {
				final var first = Integer.parseInt(limit.split(DOUBLE_DOT_REGEX)[0]);
				final var second = Integer.parseInt(limit.split(DOUBLE_DOT_REGEX)[1]);
				this.min = Math.min(first, second);
				this.max = Math.max(first, second);
			}
		}
	}
}

