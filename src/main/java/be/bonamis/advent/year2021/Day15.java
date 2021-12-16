package be.bonamis.advent.year2021;


import java.awt.Point;
import java.util.*;
import java.util.stream.Stream;

import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Grid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day15 extends DaySolver<String> {

	private final int[][] data;

	public Day15(List<String> puzzle) {
		super(puzzle);
		this.data = puzzle.parallelStream().map(l -> l.trim().split(""))
				.map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
				.toArray(int[][]::new);
	}

	@Override
	public long solvePart01() {
		return getShortesPath(new Grid(data));
	}

	@Override
	public long solvePart02() {
		final var enlarge = enlarge(this.data, 5);
		return getShortesPath(new Grid(enlarge));
	}

	private long getShortesPath(Grid grid) {
		var graph = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		grid.consume(graph::addVertex);
		grid.consume(point -> addEdge(graph, point, grid));

		ShortestPathAlgorithm<Point, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

		final var source = new Point(0, 0);
		final var sink = new Point(grid.getHeight() - 1, grid.getWidth() - 1);
		return (long) dijkstraShortestPath.getPathWeight(source, sink);
	}

	private void addEdge(DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge> graph, Point point, Grid grid) {
		var value = grid.get(point);
		for (var adjacent : adjacentPoints(point, grid)) {
			var edge = graph.addEdge(adjacent, point);
			graph.setEdgeWeight(edge, value);
		}
	}

	private Collection<Point> adjacentPoints(Point point, Grid grid) {
		var points = new HashSet<Point>();

		addPoint(points, point.x, point.y - 1, grid);
		addPoint(points, point.x, point.y + 1, grid);
		addPoint(points, point.x - 1, point.y, grid);
		addPoint(points, point.x + 1, point.y, grid);

		return points;
	}

	private void addPoint(HashSet<Point> points, int x, int y, Grid grid) {
		final var point = new Point(x, y);
		Integer value = grid.get(point);
		if (value != null) {
			points.add(point);
		}
	}

	int[][] enlarge(int[][] input, int times) {
		var height = input.length;
		var width = input[0].length;

		final var copy = new int[height * times][width * times];
		columnsCopy(input, times, width, copy);
		linesCopy(input, times, height, copy);
		return copy;
	}

	private void linesCopy(int[][] input, int times, int height, int[][] copy) {
		for (int timeXIndex = 0; timeXIndex < times; timeXIndex++) {
			for (int x = 0; x < input.length; x++) {
				for (int y = 0; y < copy[x].length; y++) {
					final var newValue = copy[x][y] + timeXIndex;
					copy[x + timeXIndex * height][y] = newValue > 9 ? newValue - 9 : newValue;
				}
			}
		}
	}

	private void columnsCopy(int[][] input, int times, int width, int[][] copy) {
		for (int timeXIndex = 0; timeXIndex < times; timeXIndex++) {
			for (int x = 0; x < input.length; x++) {
				for (int y = 0; y < input[x].length; y++) {
					final var newValue = input[x][y] + timeXIndex;
					copy[x][y + timeXIndex * width] = newValue > 9 ? newValue - 9 : newValue;
				}
			}
		}
	}
}

