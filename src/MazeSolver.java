import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;

	// This class encapsulates a value in the Priority Queue
	// It stores the x and y values of the room it represents
	// As well as the shortest distance to it (that has been found so far)
	private static class Pair implements Comparable<Pair> {
		int dist;
		int x;
		int y;

		public Pair(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

		@Override
		public int compareTo(Pair p) {
			return this.dist - p.dist;
		}
	}
	
	private PriorityQueue<Pair> heap;
	// Visited matrix to prevent repeated visits to nodes that have
	// already been traversed
	private boolean[][] visited;

	public MazeSolver() {
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		heap = new PriorityQueue<>();
		visited = new boolean[maze.getRows()][maze.getColumns()];

		// Add starting point
		heap.add(new Pair(startRow, startCol, 0));

		// Perform djikstra algorithm, optimizing for smallest distance
		while (!heap.isEmpty()) {
			Pair next = heap.poll();

			// It is the end point, return the distance (Shortest guaranteed by djikstra)
			if (next.x == endRow && next.y == endCol) {
				return next.dist;
			}
			
			// If visited skip, else set it to visited, dist is the shortest to this node
			if (visited[next.x][next.y]) {
				continue;
			}
			visited[next.x][next.y] = true;

			System.out.println(String.format("%d, %d", next.x, next.y));

			// For all 4 directions get its total distance from this node and add it to the heap
			for (int dir = 0; dir < 4; dir++) {
				int newX = next.x + DELTAS[dir][0], newY = next.y + DELTAS[dir][1];
				int value = WALL_FUNCTIONS.get(dir).apply(maze.getRoom(next.x, next.y));

				// If it is a wall, no need to include it in checks
				if (value == TRUE_WALL) continue;
				// If it is empty, fear increases by 1
				if (value == EMPTY_SPACE) { value = 1; }

				// Add the next node into the priority queue
				heap.add(new Pair(newX, newY, next.dist + value));
			}
		}

		// If the heap is empty and no distance has been returned, it means no path is found
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		heap = new PriorityQueue<>();
		visited = new boolean[maze.getRows()][maze.getColumns()];

		// Add starting point
		heap.add(new Pair(startRow, startCol, 0));

		// Perform djikstra algorithm, optimizing for smallest distance
		while (!heap.isEmpty()) {
			Pair next = heap.poll();

			// It is the end point, return the distance (Shortest guaranteed by djikstra)
			if (next.x == endRow && next.y == endCol) {
				return next.dist;
			}
			
			// If visited skip, else set it to visited, dist is the shortest to this node
			if (visited[next.x][next.y]) {
				continue;
			}
			visited[next.x][next.y] = true;

			//System.out.println(String.format("%d, %d", next.x, next.y));

			// For all 4 directions get its total distance from this node and add it to the heap
			for (int dir = 0; dir < 4; dir++) {
				int newX = next.x + DELTAS[dir][0], newY = next.y + DELTAS[dir][1];
				int value = WALL_FUNCTIONS.get(dir).apply(maze.getRoom(next.x, next.y));

				// If it is a wall, no need to include it in checks
				if (value == TRUE_WALL) continue;
				// If it is empty, fear increases by 1
				if (value == EMPTY_SPACE) { value = next.dist + 1; }
				// Adapted to fit bonus
				else { value = Math.max(value, next.dist); }

				// Add the next node into the priority queue
				heap.add(new Pair(newX, newY, value));
			}
		}

		// If the heap is empty and no distance has been returned, it means no path is found
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		Integer valueFromStart = bonusSearch(startRow, startCol, endRow, endCol);
		Integer valueFromSpecial = bonusSearch(sRow, sCol, endRow, endCol);
		
		if (valueFromSpecial == null) { return valueFromStart; }
		if (valueFromStart == null) { return null; }

		return Math.min(valueFromStart, valueFromSpecial - 1);
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			//System.out.println(solver.pathSearch(0, 1, 1, 4));

			for (int i = 0; i < maze.getRows(); i++) {
				for (int j = 0; j < maze.getColumns(); j++) {
					for (int i2 = 0; i2 < maze.getRows(); i2++) {
						for (int j2 = 0; j2 < maze.getColumns(); j2++) {
							System.out.println(String.format("(%d, %d) to (%d, %d) : %d", i, j, i2, j2, solver.pathSearch(i, j, i2, j2)));
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
