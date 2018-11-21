package Unused;
/**Converts the game data to a game board and displays that game board.
 * 
 * @author William Arnold
 * @version 1.0 Septermber 29, 2018
 * 
 */

public class Connect4TextConsole {
	
	/**2D character array that holds the console game board*/
	private char[][] gameBoard;
	
	/**Constructor generates an empty game board with the dividers filled in*/
	public Connect4TextConsole() {
		gameBoard = new char[6][15];
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 15; j+=2) { //Fills rows 0, 2, 4, 6, 8, 10, 12, and 14 with barriers
				gameBoard[i][j] = '|';
			}
		}
	}
	
	/**Method converts the game data from the Connect4 class into the
	 * game board for the user to see
	 * 
	 * @param arr is the arr from Connect4 containing the game data
	 */
	public void convertBoard(char[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				int k = (j * 2) + 1; //k converts the rows from the game data to the rows of the game board
				gameBoard[i][k] = arr[i][j];
			}
		}
	}
	
	/**Method prints the game board into the console*/
	public void printBoard() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 15; j++) {
				System.out.print(gameBoard[i][j]);
			}
			System.out.println();
		}
	}
}
