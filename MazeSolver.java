import java.util.*;

public class MazeSolver {

    // Directions arrays for moving up, down, left, right
    private static final int[] dRow = {-1, 1, 0, 0};
    private static final int[] dCol = {0, 0, -1, 1};

    public static void main(String[] args) {
        int[][] maze = {
            {0, 1, 0, 0, 0, 0},
            {0, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 0},
            {1, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0}
        };

        int[] start = {0, 0};
        int[] end = {4, 5};

        List<int[]> path = solveMaze(maze, start, end);

        if (path != null) {
            for (int[] p : path) {
                System.out.println(Arrays.toString(p));
            }
        } else {
            System.out.println("No path found");
        }
    }

    public static List<int[]> solveMaze(int[][] maze, int[] start, int[] end) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Map<int[], int[]> parentMap = new HashMap<>();
        Queue<int[]> queue = new LinkedList<>();

        queue.add(start);
        visited[start[0]][start[1]] = true;
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            if (Arrays.equals(current, end)) {
                return constructPath(parentMap, end);
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current[0] + dRow[i];
                int newCol = current[1] + dCol[i];

                if (isValid(maze, visited, newRow, newCol)) {
                    int[] next = {newRow, newCol};
                    queue.add(next);
                    visited[newRow][newCol] = true;
                    parentMap.put(next, current);
                }
            }
        }
        return null; // No path found
    }

    private static boolean isValid(int[][] maze, boolean[][] visited, int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length
                && maze[row][col] == 0 && !visited[row][col];
    }

    private static List<int[]> constructPath(Map<int[], int[]> parentMap, int[] end) {
        List<int[]> path = new LinkedList<>();
        for (int[] at = end; at != null; at = parentMap.get(at)) {
            path.add(0, at);
        }
        return path;
    }
}
