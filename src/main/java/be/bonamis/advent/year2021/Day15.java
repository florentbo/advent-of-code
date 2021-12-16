package be.bonamis.advent.year2021;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

	private final Grid grid;

	public Day15(List<String> puzzle) {
		super(puzzle);
		this.grid = new Grid(puzzle.parallelStream().map(l -> l.trim().split(""))
									 .map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
									 .toArray(int[][]::new));
	}

	@Override
	public long solvePart01() {
		var graph = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.grid.consume(graph::addVertex);
		this.grid.consume(point -> addEdge(graph, point));

		ShortestPathAlgorithm<Point, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

		final var source = new Point(0, 0);
		final var sink = new Point(grid.getHeight() - 1, grid.getWidth() - 1);
		return (long) dijkstraShortestPath.getPathWeight(source, sink);
	}

	private void addEdge(DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge> graph, Point point) {
		var value = grid.get(point);
		for (var adjacent : adjacentPoints(point)) {
			var edge = graph.addEdge(adjacent, point);
			graph.setEdgeWeight(edge, value);
		}
	}

	private Collection<Point> adjacentPoints(Point point) {
		var points = new HashSet<Point>();

		addPoint(points, point.x, point.y - 1);
		addPoint(points, point.x, point.y + 1);
		addPoint(points, point.x - 1, point.y);
		addPoint(points, point.x + 1, point.y);

		return points;
	}

	private void addPoint(HashSet<Point> points, int x, int y) {
		final var point = new Point(x, y);
		Integer value = grid.get(point);
		if (value != null) {
			points.add(point);
		}
	}

	@Override
	public long solvePart02() {
		return 0;
	}
}

