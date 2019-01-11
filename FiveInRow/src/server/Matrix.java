package server;

public class Matrix {
	private int [][] matrix;

	public Matrix() {		// Constructor with matrix initialization to 255 value. 0 is for first player, 1 for second player
		matrix = new int[6][9];
		for(int row = 0; row < 6; row++) {
			for(int column = 0; column < 9; column++) {
				matrix[row][column] = 255;
			}
		}	
	}
		
	public int[][] getMatrix() {
		return matrix;
	}

	public int insertDisk(int column, int disk) {	// insert disk on top of selected column, or return 255 in case the column is full already
		for(int row = 5; row >=0; row--) {
			if(matrix[row][column] == 255) {
				matrix[row][column] = disk;
				return row;
			}
		}
		return 255;
	}
	
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
