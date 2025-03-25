import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	Maze maze;
	boolean[][][] visited;
	int[][] dists;
	int[] target;
	int shortestDist;
	Room[] shortestPath;
	Map<Integer, Integer> counts;

	public MazeSolverWithPower() {
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		return pathSearch(startRow, startCol, endRow, endCol, 0);
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		return counts.getOrDefault(k, 0);
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		this.visited = new boolean[superpowers+1][maze.getRows()][maze.getColumns()];
		this.dists = new int[maze.getRows()][maze.getColumns()];
		this.target = new int[] { endRow, endCol };
		this.shortestDist = Integer.MAX_VALUE;
		this.shortestPath = null;
		this.counts = new HashMap<>();

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				maze.getRoom(i, j).onPath = false;
				dists[i][j] = Integer.MAX_VALUE;
			}
		}

		dfs(startRow, startCol, 0, superpowers, new ArrayList<>());

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				counts.put(dists[i][j], counts.getOrDefault(dists[i][j], 0) + 1);
			}
		}

		if (startRow == endRow && startCol == endCol) {
			maze.getRoom(startRow, startCol).onPath = true;
			return 0;
		}

		if (shortestPath == null) {
			return null;
		}

		for (int i = 0; i < shortestPath.length; i++) {
			shortestPath[i].onPath = true;
		}

		return shortestDist;
	}

	public void dfs(int row, int col, int dist, int remain_sp, ArrayList<Room> rooms) {
		if (row < 0 || row >= maze.getRows()) return;
		if (col < 0 || col >= maze.getColumns()) return;

		if (visited[remain_sp][row][col]) return;

		//System.out.println(String.format("%d, %d", row, col));

		if (dists[row][col] > dist) {
			dists[row][col] = dist;
		}

		Room currRoom = maze.getRoom(row, col);

		rooms.add(currRoom);
		int prevSP = 0;

		for (int i = 0; i <= remain_sp; i++) {
			if (visited[i][row][col]) {
				prevSP = i;
			} else {
				visited[i][row][col] = true;
			}
		}

		if (this.target[0] == row && this.target[1] == col) {

			Room[] path = rooms.toArray(new Room[rooms.size()]);
			
			// for (int i = 0; i < path.length; i++) {
			// 	path[i].onPath = true;
			// }
			// MazePrinter.printMaze(maze);
			// for (int i = 0; i < path.length; i++) {
			// 	path[i].onPath = false;
			// }

			if (dist < shortestDist) {
				shortestDist = dist;
				shortestPath = path;
			}
		}

		for (int direction = 0; direction < 4; ++direction) {
			int newRow = row + DELTAS[direction][0], newCol = col + DELTAS[direction][1];

			if (hasDirWall(currRoom, direction)) {
				if (remain_sp > 0) {
					dfs(newRow, newCol, dist + 1, remain_sp - 1, rooms);
				}
			} else {
				dfs(newRow, newCol, dist + 1, remain_sp, rooms);
			}
		}

		for (int i = prevSP; i <= remain_sp; i++) {
			visited[i][row][col] = false;
		}

		rooms.removeLast();
	}

	public boolean hasDirWall(Room room, int dir) {
		switch (dir) {
			case NORTH:
				return room.hasNorthWall();
			case SOUTH:
				return room.hasSouthWall();
			case EAST:
				return room.hasEastWall();
			case WEST:
				return room.hasWestWall();
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 4, 3, 2));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
