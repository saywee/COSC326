import java.util.*;

// https://g2384.github.io/tents-and-trees-puzzle-solver/
public class TandT {
	public Character[][] grid;
	public ArrayList<String> rule = new ArrayList<>();
	public ArrayList<String> map = new ArrayList<>();
	int treeCount = 0;
	static final int left = 1;
	static final int right = 2;
	static final int up = 3;
	static final int down = 4;
	boolean finished = false;

	public static void main(String[] args) {
		TandT t = new TandT();
		t.getMap();
	}

	public void getMap() {
		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String str = sc.nextLine();
			if (str.trim().isEmpty()) {
				int row = map.size();
				grid = new Character[row][row];
				for (int i = 0; i < grid.length; i++) {

					String currentString = map.get(i);
					for (int j = 0; j < grid[i].length; j++) {

						grid[i][j] = currentString.charAt(j);
						if (currentString.charAt(j) == 'T') {
							treeCount++;
						}
					}
				}
				//removeSpot(grid);
				removeZero(grid);
				ArrayList<Integer[][]> q = treeCords(grid);
				runSolution(grid, 0, q);
				map.clear();
				rule.clear();
				treeCount = 0;
				grid = null;
				if (finished == false) {
					System.out.println("Impossible");
				}
				finished = false;
				System.out.println();
			} else {
				char ch = str.charAt(0);
				if (!Character.isDigit(ch)) {
					map.add(str);
				} else {
					rule.add(str);
				}
			}
		}
		try {
			int row = map.size();
			grid = new Character[row][row];
			for (int i = 0; i < grid.length; i++) {
				String currentString = map.get(i);
				for (int j = 0; j < grid[i].length; j++) {
					grid[i][j] = currentString.charAt(j);
					if (currentString.charAt(j) == 'T') {
						treeCount++;
					}
				}
			}
			removeSpot(grid);
			removeZero(grid);

			ArrayList<Integer[][]> q = treeCords(grid);
			runSolution(grid, 0, q);
			if (finished == false) {
				System.out.println("Impossible");
			}
		} catch (Exception e) {

		}
		sc.close();
	}

	public void print2Darr(Character[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Character ch = grid[i][j];
				if(ch == 't') System.out.print("T");
				else if(ch == 'x') System.out.print(".");
				else{
					System.out.print(grid[i][j]);
				}
			}
			System.out.println();
		}
	}

	public void removeSpot(Character[][] game) {
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				if (game[i][j] == '.' && i == 0) {
					if (j == 0) {
						if ((game[i + 1][j] != 'T' && game[i][j + 1] != 'T') || game[i + 1][j + 1] == 'T') {
							game[i][j] = 'x';
						}
					} else if (j == game[i].length - 1) {
						if ((game[i][j - 1] != 'T' && game[i + 1][j] != 'T') || game[i + 1][j - 1] == 'T') {
							game[i][j] = 'x';
						}
					} else {
						if ((game[i][j - 1] != 'T' && game[i][j + 1] != 'T' && game[i + 1][j] != 'T')
								&& (game[i + 1][j - 1] == 'T' || game[i + 1][j + 1] == 'T'))
							game[i][j] = 'x';
					}
				} else if (game[i][j] == '.' && i == game.length - 1) {
					if (j == 0) {
						if ((game[i - 1][j] != 'T' && game[i][j + 1] != 'T') || game[i - 1][j + 1] == 'T') {
							game[i][j] = 'x';
						}
					} else if (j == game[i].length - 1) {
						if ((game[i][j - 1] != 'T' && game[i - 1][j] != 'T') || game[i - 1][j - 1] == 'T') {
							game[i][j] = 'x';
						}
					} else {
						if ((game[i][j - 1] != 'T' && game[i][j + 1] != 'T' && game[i - 1][j] != 'T')
								&& (game[i - 1][j - 1] == 'T' || game[i - 1][j + 1] == 'T'))
							game[i][j] = 'x';
					}
				} else if (game[i][j] == '.' && i > 0 && i < game.length - 1) {
					if (j == 0) {
						if ((game[i - 1][j] != 'T' && game[i][j + 1] != 'T' && game[i + 1][j] != 'T')
								&& (game[i - 1][j + 1] == 'T' || game[i + 1][j + 1] == 'T')) {
							game[i][j] = 'x';
						}
					} else if (j == game[i].length - 1) {
						if ((game[i][j - 1] != 'T' && game[i - 1][j] != 'T' && game[i + 1][j] != 'T')
								&& (game[i - 1][j - 1] == 'T' || game[i + 1][j - 1] == 'T')) {
							game[i][j] = 'x';
						}
					} else {
						if ((game[i][j - 1] != 'T' && game[i][j + 1] != 'T' && game[i - 1][j] != 'T'
								&& game[i + 1][j] != 'T') /*
															 * && (game[i-1][j-1]=='T' || game[i-1][j+1]=='T' ||
															 * game[i+1][j-1]=='T' || game[i+1][j+1]=='T')
															 */) {
							game[i][j] = 'x';
						}
					}
				}
			}
		}
	}

	public void removeZero(Character[][] game) {

		for (int i = 0; i < game.length; i++) {

			String[] row = rule.get(0).split(" ");
			Collections.reverse(Arrays.asList(row));
			String[] column = rule.get(1).split(" ");
			for (int j = 0; j < game[i].length; j++) {
				try {
					int checkRow = Integer.parseInt(row[i]);
					int chkCol = Integer.parseInt(column[j]);
					if (checkRow == 0) {
						if (game[i][j] != 'T') {
							game[i][j] = 'x';
						}
					} else if (chkCol == 0) {
						if (game[i][j] != 'T') {
							game[i][j] = 'x';
						}
					}
				} catch (Exception e) {
					System.out.println("Has to be numerical");
				}
			}
		}
	}

	public void runSolution(Character[][] grid, int index, ArrayList<Integer[][]> cords) {

		if (finished == true) {
			return;
		}

		if (isFinished(grid)) { // change this to wrong finish for the finished game but row/cols dont match up
			print2Darr(grid);
			finished = true;
			return;
		}

		if (index >= cords.size()) {
			return;
		}
		
		Integer[][] cord = cords.get(index);
		int row = cord[0][0];
		int col = cord[0][1];
		if (grid[row][col] == 'T') {
			if (possibleMove(row, col, left, grid) && isAdjacent(row, col - 1, grid)){
				Character[][] currGrid = copyArray(grid);
				currGrid[row][col] = 't';
				currGrid[row][col - 1] = 'C';
				//print2Darr(currGrid); System.out.println();
				runSolution(currGrid, index+1, cords);
			}
			if (possibleMove(row, col, right, grid) && isAdjacent(row, col + 1, grid)) {
				Character[][] currGrid = copyArray(grid);
				currGrid[row][col] = 't';
				currGrid[row][col + 1] = 'C';
				//print2Darr(currGrid); System.out.println();
				runSolution(currGrid, index+1, cords);
			}
			if (possibleMove(row, col, down, grid) && isAdjacent(row + 1, col, grid)) {
				Character[][] currGrid = copyArray(grid);
				currGrid[row][col] = 't';
				currGrid[row + 1][col] = 'C';
				//print2Darr(currGrid); System.out.println();
				runSolution(currGrid, index+1, cords);
			}
			if (possibleMove(row, col, up, grid) && isAdjacent(row - 1, col, grid)) {
				Character[][] currGrid = copyArray(grid);
				currGrid[row][col] = 't';
				currGrid[row - 1][col] = 'C';
				//print2Darr(currGrid); System.out.println();
				runSolution(currGrid, index+1, cords);
			}
		}
	}

	private boolean possibleMove(int row, int col, int move, Character[][] game) {
		int topBound = 0;
		int leftBound = 0;
		int rightBound = game.length - 1;
		int botBound = game[0].length - 1;

		// left move checks if the move is possible then if any tents are adjacent
		if (move == left && col != leftBound && game[row][col - 1] == '.'){
			return true;
		}

		// right
		if (move == right && col != rightBound && game[row][col + 1] == '.'){
			return true;
		}

		// up
		if (move == up && row != topBound && game[row - 1][col] == '.'){
			return true;
		}

		// down
		if (move == down && row != botBound && game[row + 1][col] == '.'){
			return true;
		}

		return false;
	}

	private boolean isAdjacent(int row, int col, Character[][] game) {
		int topBound = 0;
		int leftBound = 0;
		int rightBound = game.length - 1;
		int botBound = game[0].length - 1;

		// NW adj
		if (row != topBound && col != leftBound && game[row - 1][col - 1] == 'C')
			return false;

		// N adj
		if (row != topBound && game[row - 1][col] == 'C')
			return false;

		// NE adj
		if (row != topBound && col != rightBound && game[row - 1][col + 1] == 'C')
			return false;

		// E adj
		if (col != rightBound && game[row][col + 1] == 'C')
			return false;

		// SE adj
		if (row != botBound && col != rightBound && game[row + 1][col + 1] == 'C')
			return false;

		// S adj
		if (row != botBound && game[row + 1][col] == 'C')
			return false;

		// SW adj
		if (row != botBound && col != leftBound && game[row + 1][col - 1] == 'C')
			return false;

		// W adj
		if (col != leftBound && game[row][col - 1] == 'C')
			return false;

		return true;
	}

	private boolean isFinished(Character[][] game) {
		String[] ruleset = rule.get(0).split(" ");
		Collections.reverse(Arrays.asList(ruleset));
		String[] rulesetCol = rule.get(1).split(" ");

		for (int row = 0; row < game.length; row++) {
			int tentCounts = 0;
			for (int col = 0; col < game[row].length; col++) {
				if (game[row][col] == 'C') {
					tentCounts++;
				}
			}
			int limit = Integer.parseInt(ruleset[row]);
			if (tentCounts != limit)
				return false;
		}
		for (int row = 0; row < game.length; row++) {
			int tentCounts = 0;
			for (int col = 0; col < game[row].length; col++) {
				if (game[col][row] == 'C') {
					tentCounts++;
				}
			}
			int limit = Integer.parseInt(rulesetCol[row]);
			if (tentCounts != limit)
				return false;
		}

		return true;
	}

	private boolean checkRow(int row){
		String[] ruleset = rule.get(0).split(" ");
		int limit = Integer.parseInt(ruleset[row]);
		int tentCount = 0;
		for (int col = 0; col < grid[row].length; col++) {
			if (grid[row][col] == 'C') {
				tentCount++;
			}
		}
		if(tentCount >= limit){
			return false;
		}
		return true;
	}

	private boolean wrongFinished(Character[][] grid) {
		int tentCount = 0;

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 'C') {
					tentCount++;
				}
			}
		}

		if (tentCount == treeCount) {
			return true;
		}

		return false;
	}

	private ArrayList<Integer[][]> treeCords(Character[][] grid) {
		ArrayList<Integer[][]> list = new ArrayList<>();

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 'T') {
					Integer[][] cord = new Integer[1][2];
					cord[0][0] = row;
					cord[0][1] = col;
					list.add(cord);
				}
			}
		}
		Collections.reverse(list);

		return list;
	}

	private static Character[][] copyArray(Character[][] arr) {
		Character[][] newArr = new Character[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = Arrays.copyOf(arr[i], arr[i].length);
		}
		return newArr;
	}

}