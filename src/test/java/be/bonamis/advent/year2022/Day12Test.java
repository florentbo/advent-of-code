package be.bonamis.advent.year2022;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.List;

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
        CharGrid charGrid = new CharGrid(lines.stream().map(String::toCharArray).toArray(char[][]::new));



       /* var graph = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        Set<Point> points = new HashSet<>();
        final var source = new Point(0, 0);
        final var sink = new Point(2,5);


        points.add(source);
        points.add(sink);
        charGrid.consume(graph::addVertex);
        charGrid.consume(point -> addEdge(graph, point, charGrid, points));

        *//*Set<DefaultWeightedEdge> defaultWeightedEdges = graph.edgeSet();
        for (DefaultWeightedEdge defaultWeightedEdge : defaultWeightedEdges) {
            System.out.println(defaultWeightedEdge);
        }*//*


        ShortestPathAlgorithm<Point, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        charGrid.printArray();

        System.out.println(charGrid.get(source));
        System.out.println(charGrid.get(sink));
        long pathWeight = (long) dijkstraShortestPath.getPathWeight(source, sink);*/

        Point source = new Point(0, 0);
        Point dest = new Point(2, 5);

        int dist = bfs(charGrid.getData(), source, dest);
        System.out.println(dist);

        assertThat(dist).isEqualTo(31);
    }

    final static int[][] d = {
            {0, 1},  //right
            {1, 0},  //down
            {0, -1}, //left
            {-1, 0}  //up
    };

    public static int bfs(char[][] maze, Point src, Point dest) {
        int minDist = Integer.MAX_VALUE;

        char srcChar = maze[src.x][src.y];
        char destChar = maze[dest.x][dest.y];
      /*  if (srcChar != 1 || destChar != 1)
            return minDist;*/

        int h = maze.length;
        int w = maze[0].length;
        boolean[][] visited = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                visited[i][j] = false;
            }
        }

        Queue<Node> q = new LinkedList<>();
        Node s = new Node(src, 0);
        q.add(s);

        while (!q.isEmpty()) {
            Node curr = q.poll();
            Point pt = curr.pt;
            char currPointValue = maze[pt.x][pt.y];
            if (pt.x == dest.x && pt.y == dest.y)
                return curr.dist;
            for (int i = 0; i < 4; i++) {
                int row = pt.x + d[i][0];
                int col = pt.y + d[i][1];
                if (isValid(maze, visited, h, w, row, col, currPointValue)) {
                    visited[row][col] = true;
                    Node adjCell = new Node(new Point(row, col), curr.dist + 1);
                    q.add(adjCell);
                }
            }
        }
        return minDist;
    }

    private static boolean isValid(char[][] maze, boolean[][] visited, int width, int height, int row, int col, char pt) {
        return (row >= 0) && (row < width) && (col >= 0) && (col < height) && canGoToThatCell(col, maze[row], pt) && !visited[row][col];
    }

    private static boolean canGoToThatCell(int col, char[] maze, char originPointCharacter) {
        char character = maze[col];
        boolean test = (1 >= (character - originPointCharacter));
        return originPointCharacter == 'S' || character == 'E' || test;
    }


    @Test
    @Disabled
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);

    }


    private static long solvePart01(List<String> lines) {
        return lines.size();
    }


    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Node {
        Point pt;
        int dist;

        public Node(Point pt, int dist) {
            this.pt = pt;
            this.dist = dist;
        }
    }
}
