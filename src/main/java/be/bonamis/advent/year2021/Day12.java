package be.bonamis.advent.year2021;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Graph;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day12 extends DaySolver<String> {

	private static final String START_VERTEX = "start";
	private static final String END_VERTEX = "end";

	public Day12(List<String> puzzle) {
		super(puzzle);
	}

	@Override
	public long solvePart01() {
		Multimap<String, String> map = cavesMap();
		return simplePaths(START_VERTEX, END_VERTEX, map.asMap(), false).size();
	}

	@Override
	public long solvePart02() {
		Multimap<String, String> map = cavesMap();
		return simplePaths(START_VERTEX, END_VERTEX, map.asMap(), true).size();
	}

	private Multimap<String, String> cavesMap() {
		Multimap<String, String> map = ArrayListMultimap.create();
		this.puzzle
				.stream()
				.map(Graph.Node::parse)
				.forEach(node -> {
					if (node.getDestination().equals(START_VERTEX)) {
						map.put(node.getDestination(), node.getSource());
					} else {
						map.put(node.getSource(), node.getDestination());
						if (!node.getSource().equals(START_VERTEX) && !node.getDestination().equals(END_VERTEX)) {
							map.put(node.getDestination(), node.getSource());
						}
					}

				});
		return map;
	}

	public List<List<String>> simplePaths(String start, String end, Map<String, Collection<String>> graph, boolean smallCaves) {
		List<String> visited = new ArrayList<>();
		List<List<String>> simplePaths = new ArrayList<>();
		List<String> path = new ArrayList<>();
		return getSimplePaths(start, end, graph, visited, simplePaths, path, smallCaves);
	}

	public List<List<String>> getSimplePaths(String start, String end, Map<String, Collection<String>> graph,
											 List<String> visited, List<List<String>> simplePaths, List<String> path,
											 boolean smallCaves) {
		if (graph.containsKey(start)) {
			Collection<String> neighbours = graph.get(start);
			for (String neighbour : neighbours) {
				if (!(canVisitCave(neighbour, visited, smallCaves))) {
					visited.add(neighbour);
					path.add(neighbour);
					if (neighbour.equals(end)) {
						simplePaths.add(new ArrayList<>(path));
					} else {
						getSimplePaths(neighbour, end, graph, visited, simplePaths, path, smallCaves);
					}
					path.remove(path.size() - 1);
					visited.remove(neighbour);
				}
			}
		}
		return simplePaths;
	}

	boolean canVisitCave(String node, List<String> isVisited, boolean smallCaves) {
		return smallCaves ? isSmallCaveCannotBeVisited(node, isVisited) : isSmallCave(node) &&
																		  Collections.frequency(isVisited, node) == 1;
	}

	boolean isSmallCaveCannotBeVisited(String node, List<String> isVisited) {
		return (isSmallCave(node) && Collections.frequency(isVisited, node) > 1) || (isSmallCave(node) &&
																					 hasMoreThanTwoSmallCaves(node, isVisited));
	}

	boolean hasMoreThanTwoSmallCaves(String node, final Collection<String> isVisited) {
		List<String> newList = new ArrayList<>(isVisited);
		newList.add(node);
		Map<String, Long> counts =
				newList.stream()
						.filter(this::isSmallCave)
						.collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		return (counts.values().stream().filter(i -> i > 1).count() > 1);
	}

	private boolean isSmallCave(String node) {
		return (!node.equals(START_VERTEX) && !node.equals(END_VERTEX)) && node.toLowerCase().equals(node);
	}
}
