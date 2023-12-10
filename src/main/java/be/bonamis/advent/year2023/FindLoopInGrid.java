package be.bonamis.advent.year2023;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FindLoopInGrid {
    public static void main(String[] args) {
        // Example grid
        char[][] grid = {
                {'.', '.', '.', '.', '.'},
                {'.', 'S', '-', '7', '.'},
                {'.', '|', '.', '|', '.'},
                {'.', 'L', '-', 'J', '.'},
                {'.', '.', '.', '.', '.'}
        };

        // Find the loop
        List<Point> loop = findLoop(grid);

        // Print the loop coordinates
        if (loop != null) {
            System.out.println("Loop Coordinates:");
            for (Point point : loop) {
                System.out.println("(" + point.x + "," + point.y + ")");
            }
        } else {
            System.out.println("No loop found.");
        }
    }

    private static List<Point> findLoop(char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 'S') {
                    List<Point> path = new ArrayList<>();
                    if (dfs(grid, x, y, x, y, path, new boolean[grid.length][grid[0].length])) {
                        return path;
                    }
                }
            }
        }
        return null;
    }

    private static boolean dfs(char[][] grid, int x, int y, int startX, int startY, List<Point> path, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= grid[0].length || y >= grid.length) {
            return false;
        }

        char currentChar = grid[y][x];
        if (currentChar == '.') {
            return false;
        }

        if (visited[y][x]) {
            // Loop found
            path.add(new Point(x, y));
            return x == startX && y == startY;
        }

        visited[y][x] = true;
        path.add(new Point(x, y));

        // Check all four directions
        if (dfs(grid, x + 1, y, startX, startY, path, visited) ||
                dfs(grid, x - 1, y, startX, startY, path, visited) ||
                dfs(grid, x, y + 1, startX, startY, path, visited) ||
                dfs(grid, x, y - 1, startX, startY, path, visited)) {
            return true;
        }

        // If none of the directions leads to a solution, backtrack
        path.remove(path.size() - 1);
        visited[y][x] = false;
        return false;
    }
}
