package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day12Test {

    private static final String CODE_TXT = "2022/12/2022_12_13_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/12/2022_12_input.txt");
        log.info("Day11 part 01 result: {}", solvePart01(lines));
        //log.info("Day11 part 02 result: {}", solvePart02(lines));
    }

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        long result = solvePart01(lines);
        assertThat(result).isEqualTo(5);
        Graph<String, DefaultEdge> g
                = new SimpleGraph<>(DefaultEdge.class);
        g.addVertex("v1");
        g.addVertex("v2");
        g.addEdge("v1", "v2");
    }

    @Test
    @Disabled
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);

    }



    private static long solvePart01(List<String> lines) {
        return lines.size();
    }



}
