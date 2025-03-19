import java.util.*;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private Map<Integer, Integer> counts;

	public MazeSolver() {
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		counts = new HashMap<>();

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		boolean[][] visited = new boolean[maze.getRows()][maze.getColumns()];
		Queue<int[]> queue = new LinkedList<>();
		int[][][] fromArr = new int[maze.getRows()][maze.getColumns()][2];

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				maze.getRoom(i, j).onPath = false;
			}
		}

		queue.add(new int[] {startRow, startCol, 0});
		visited[startRow][startCol] = true;
		boolean found = false;
		int output = 0;

		while (!queue.isEmpty()) {
			int[] current = queue.poll();

			int row = current[0], col = current[1], dist = current[2];
			counts.put(dist, counts.getOrDefault(dist, 0)+1);

			for (int direction = 0; direction < 4; ++direction) {
				int newRow = row + DELTAS[direction][0], newCol = col + DELTAS[direction][1];

				if (canGo(row, col, direction, visited)) { 
					fromArr[newRow][newCol] = new int[] { row, col };
					if (newRow == endRow && newCol == endCol) {
						//System.out.println(Arrays.deepToString(fromArr));
						setPath(startRow, startCol, newRow, newCol, fromArr);
						output = dist + 1;
						found = true;
					}
					
					queue.add(new int[] {newRow, newCol, dist + 1});
					visited[newRow][newCol] = true;
				}
			}
		}

		if (startRow == endRow && startCol == endCol) {
			maze.getRoom(startRow, startCol).onPath = true;
			return 0;
		}

		if (!found) {
			return null;
		}

		return output;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (!counts.containsKey(0)) {
			pathSearch(0, 0, maze.getRows() - 1, maze.getColumns() - 1);
		}
		return counts.getOrDefault(k, 0);
	}

	private void setPath(int startRow, int startCol, int currRow, int currCol, int[][][] fromArr) {
		//System.out.println(String.format("Set %d,%d", currRow, currCol));

		maze.getRoom(currRow, currCol).onPath = true;

		if (currRow == startRow && currCol == startCol) {
			return;
		}

		int nextRow = fromArr[currRow][currCol][0], nextCol = fromArr[currRow][currCol][1];
		setPath(startRow, startCol, nextRow, nextCol, fromArr);
	}

	private boolean canGo(int row, int col, int dir, boolean[][] visited) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;
		if (visited[row + DELTAS[dir][0]][col + DELTAS[dir][1]]) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	public static void main(String[] args) {
		// Do remember to remove any references to ImprovedMazePrinter before submitting
		// your code!
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			for (int x = 0; x < maze.getRows(); x++) {
				for (int y = 0; y < maze.getColumns(); y++) {
					for (int x2 = 0; x2 < maze.getRows(); x2++) {
						for (int y2 = 0; y2 < maze.getColumns(); y2++) {
							System.out.println(solver.pathSearch(x, y, x2, y2));
							MazePrinter.printMaze(maze);
							for (int i = 0; i <= 9; ++i) {
								System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
