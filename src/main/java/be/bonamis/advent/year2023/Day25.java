package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;
import java.util.HashSet;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.*;

@Slf4j
@Getter
public class Day25 extends DaySolver<String> {

  public Day25(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    var graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    this.puzzle.forEach(
        line -> {
          String[] parts = line.split(":");
          String name = parts[0];
          graph.addVertex(name);
          String[] ingredients = parts[1].trim().split(" ");
          for (String ingredient : ingredients) {
            graph.addVertex(ingredient);
            graph.addEdge(name, ingredient);
          }
        });

    var stoerWagnerMinimumCut = new StoerWagnerMinimumCut<>(graph);

    Set<Object> minCut = stoerWagnerMinimumCut.minCut();
    log.info("minCut: {}", minCut);
    Set<Object> vertexSet = graph.vertexSet();
    Set<Object> diff = new HashSet<>(vertexSet);
    diff.removeAll(minCut);
    log.info("diff: {}", diff);

    String line = "jqt: rhn xhk nvd";
    String[] parts = line.split(":");
    String name = parts[0];
    log.info("name: {}", name);
    String[] ingredients = parts[1].trim().split(" ");
    log.info("ingredients: {}", Arrays.toString(ingredients));

    // grid.consume(graph::addVertex);
    // grid.consume(point -> addEdge(graph, point, grid));
    return (long) minCut.size() * diff.size();
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/25/2023_25_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day25 day = new Day25(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
