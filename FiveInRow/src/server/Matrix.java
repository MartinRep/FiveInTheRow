package server;

/**
 * The Class Matrix.
 * Holds board matrix and takes care of inserting players disk in available column and checks for winning condition. 
 */
public class Matrix {
	
	/** The matrix. */
	private int [][] matrix;

	/**
	 * Instantiates a new matrix to 255 value. 0 is for first player, 1 for second player
	 */
	public Matrix() {		
		matrix = new int[6][9];
		for(int row = 0; row < 6; row++) {
			for(int column = 0; column < 9; column++) {
				matrix[row][column] = 255;
			}
		}	
	}
		
	/**
	 * Gets the matrix 2d Integer array.
	 *
	 * @return the matrix
	 */
	public int[][] getMatrix() {
		return matrix;
	}

	/**
	 * Insert player's specific disk to chosen column. The disk is "dropped" in the column and "lands" on top of the stack.
	 * If the column is full function return 255 as error.     
	 *
	 * @param column. The column player decided to place they disk. 
	 * @param disk. Player 1 represented as 0 (X), Player 2 is represented as 1 (O).
	 * @return Integer. Row where disk was places.  
	 */
	public int insertDisk(int column, int disk) {	// insert disk on top of selected column, or return 255 in case the column is full already
		for(int row = 5; row >=0; row--) {
			if(matrix[row][column] == 255) {
				matrix[row][column] = disk;
				return row;
			}
		}
		return 255;
	}
	
	/**
	 * Check winning condition (5 identical disks in the row) in horizontal, vertical and both diagonal directions.
	 * Starts at row, column position and counts how many disks are in the row. Increase sum variable for each identical disk until it finds different disk.
	 * Do the same in opposite direction. if then sum is >= 5 return true, else reset sum and continues checking in other directions.   
	 *
	 * @param row. The row position of disk to check winning condition.
	 * @param column.  The column position of disk to check winning condition.
	 * @return true, if winner condition are met, 5 disks in row.
	 */
	public boolean checkWinner(int row, int column) {
		int x = row;
		int y = column;
		int sum = 0;
		int disk = matrix[row][column];	//Gets latest Disk added
		// check horizontal
		// loops trough the columns of the row in decrement
		do {
			if(matrix[x][column] == disk) sum++;	// if matching disk is found increases the count
			else break;
			x--;
		} while (x >= 0);
		x = row + 1;	// prevents double adding of latest Disk
		while(x <= 5) {			// loops trough the columns of the row in increment
			if(matrix[x][column] == disk) sum++;	// if matching disk is found increases the count
			else break;
			x++;
		}
		if (sum >= 5) return true;	// Check for winning condition
		else sum = 0;	// otherwise reset the count 
		// check vertical
		// loops trough the rows of the column in decrement
		do {
			if(matrix[row][y] == disk) sum++;	// if matching disk is found increases the count
			else break;
			y--;
		} while (y >= 0);
		y = column + 1;	// prevents double adding of latest Disk
		while(y <= 8) {			// loops trough the rows of the column in increment
			if(matrix[row][y] == disk) sum++;	// if matching disk is found increases the count
			else break;
			y++;
		}
		if (sum >= 5) return true;	// Check for winning condition
		else sum = 0;	// otherwise reset the count
		// check diagonal
		// loops through first diagonal in decrement
		x = row;
		y = column;
		do {
			if(matrix[x][y] == disk) sum++;	// if matching disk is found increases the count
			else break;
			x--;
			y--;
		} while (x >= 0 && y >= 0);
		// Reset positions and prevents double adding of latest Disk
		x = row + 1;
		y = column + 1;
		// loops through first diagonal in increment
		while(x <= 5 && y <= 8) {	
			if(matrix[x][y] == disk) sum++;	// if matching disk is found increases the count
			else break;
			x++;
			y++;
		}
		if (sum >= 5) return true;	// Check for winning condition
		else sum = 0;	// otherwise reset the count
		// loops through second diagonal in decrement
		x = row;
		y = column;
		do {
			if(matrix[x][y] == disk) sum++;	// if matching disk is found increases the count
			else break;
			x--;
			y++;
		} while (x >= 0 && y < 9);
		// Reset positions and prevents double adding of latest Disk
		x = row + 1;
		y = column - 1;
		// loops through second diagonal in increment
		while(x <= 5 && y >= 0) {	
			if(matrix[x][y] == disk) sum++;	// if matching disk is found increases the count
			else break;
			x++;
			y--;
		}
		if (sum >= 5) return true;	// Check for winning condition
		else return false;
	}
	
	/** 
	 * @return Matrix representation as board. [ ] for empty slot, [X] for player 1 disk and [O] for Player 2 disk.
	 **/
	@Override
	public String toString() {
		StringBuilder boardBuilder = new StringBuilder();
		for(int row = 0; row < 6; row++) {
			for(int column = 0; column < 9; column++) {
				switch(matrix[row][column]) {
				case 255:
					boardBuilder.append("[ ]");
					break;
				case 0:
					boardBuilder.append("[X]");
					break;
				case 1:
					boardBuilder.append("[O]");
					break;
				}
			}
			boardBuilder.append("|");
		}
		return boardBuilder.toString();
	}
}
