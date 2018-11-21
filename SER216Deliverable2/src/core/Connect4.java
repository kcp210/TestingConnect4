package core;
/**Controls the game mechanics of the Connect 4 game server.
 * 
 * @author William Arnold
 * @version 5.0 November 20, 2018
 * 
 */

public class Connect4 {

	/**2D character array that holds the game data */
	private char[][] game;
	
	/**Constructor generates a 2D array with 42 elements for the 42 possible moves in the game*/
	public Connect4() {
		game = new char[6][7]; //6 rows 7 columns
	}
	
	/**Get method returns the game data
	 * 
	 * @return returns the 2D character array
	 */
	public char[][] getGame() {
		return game;
	}
	
	/**Get method returns the content of an array element
	 * 
	 * @return someChar is the character in the game space
	 */
	public char getElement(int i, int j) {
		char someChar = '?';

		if((i >= 0 && i <= 6) && (j >= 0 && j <= 6)) {
			someChar = game[i][j];
		}
		
		return someChar;
	}
	
	/**Method places an X at the lowest available slot in the selected column
	 * 
	 * @param col is the column selected by Player X
	 */
	public void dropX(int col) {
		for(int i = 5; i >= 0; i--) {
			if(game[i][col] != 'X' && game[i][col] != 'O') { //if space is empty
				game[i][col] = 'X';
				break;
			}
		}
	}
	
	/**Method places an X at the lowest available slot in a 
	 * selected column and returns the row where it rests.
	 * 
	 * @param col is the column selected by Player X
	 * @return return the row where the piece rests or -1 if the row is full
	 */
	public int dropXInt(int col) {
		for(int i = 5; i >= 0; i--) {
			if(game[i][col] != 'X' && game[i][col] != 'O') { //if space is empty
				game[i][col] = 'X';
				return i;
			}
		}
		return -1;
	}
	
	/**Method places an X at the lowest available slot in the selected column
	 * 
	 * @param col is the column selected by Player O
	 */
	public void dropO(int col) {
		for(int i = 5; i >= 0; i--) {
			if(game[i][col] != 'X' && game[i][col] != 'O') { //if space is empty
				game[i][col] = 'O';
				break;
			}
		}
	}
	
	/**Method places an O at the lowest available slot in a 
	 * selected column and returns the row where it rests.
	 * 
	 * @param col is the column selected by Player O
	 * @return return the row where the piece rests or -1 if the row is full
	 */
	public int dropOInt(int col) {
		for(int i = 5; i >= 0; i--) {
			if(game[i][col] != 'X' && game[i][col] != 'O') { //if space is empty
				game[i][col] = 'O';
				return i;
			}
		}
		return -1;
	}
	
	/**Checks if the column selected by the user has available
	 * spaces and is within the game board. Exception alerts user
	 * integer was invalid.
	 * 
	 * @param col is the column selected by the player
	 * @return returns true if the column is available
	 */
	public boolean isValid(int col) { //handles the exception if integer is out of the array bounds
		try {
			if(col < 0 || col > 6) //out of bounds column
				throw new ArrayIndexOutOfBoundsException("Number must be between 1 and 7.");
		}
		catch(Exception ex) {
			System.out.println("Number must be between 1 and 7.");
			System.out.println("'" + (col + 1) + "' is not a valid number. Try again.");
			return false;
		}
		
		if(game[0][col] == 'X' || game[0][col] == 'O') { //column is full
			return false;
		}
		
		return true;
	}
	
	/**Boolean method checks straight across, then up and down,
	 * and then diagonals for a string of 5 consecutive X markers
	 * 
	 * @return returns true if Player X won the game
	 */
	public boolean checkForWinX() {
		int count = 0;
		
		for(int i = 0; i < 6; i++) { //search across
			for(int j = 0; j < 7; j++) {
				if(game[i][j] == 'X') {
					count++;
				}
				else {
					count = 0;
				}
				
				if(count == 4) {
					return true;
				}
			}
		}
		
		count = 0;
		
		for(int i = 0; i < 7; i++) { //search up and down
			for(int j = 0; j < 6; j++) {
				if(game[j][i] == 'X') {
					count++;
				}
				else {
					count = 0;
				}
				
				if(count == 4) {
					return true;
				}
			}
		}
		
		count = 0;
		
		for(int i = 4; i >= 0; i--) { //diagonal 1
			int j = 0;
			if(game[i][j] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 0; i--) { //diagonal 2
			int j = 0;
			if(game[i][j] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 0; i--) { //diagonal 3
			int j = 0;
			if(game[i][j] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 1; i--) { //diagonal 4
			int j = 0;
			if(game[i][j] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 4; i >= 0; i--) { //diagonal 5
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 0; i--) { //diagonal 6
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 0; i--) { //diagonal 7
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 1; i--) { //diagonal 8
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		
		return false;
	}
	
	/**Boolean method checks straight across, then up and down,
	 * and then diagonals for a string of 5 consecutive O markers
	 * 
	 * @return returns true if Player O won the game
	 */
	public boolean checkForWinO() {
		int count = 0;
		
		for(int i = 0; i < 6; i++) { //search across
			for(int j = 0; j < 7; j++) {
				if(game[i][j] == 'O') {
					count++;
				}
				else {
					count = 0;
				}
				
				if(count == 4) {
					return true;
				}
			}
		}
		
		count = 0;
		
		for(int i = 0; i < 7; i++) { //search up and down
			for(int j = 0; j < 6; j++) {
				if(game[j][i] == 'O') {
					count++;
				}
				else {
					count = 0;
				}
				
				if(count == 4) {
					return true;
				}
			}
		}
		
		count = 0;
		
		for(int i = 4; i >= 0; i--) { //diagonal 1
			int j = 0;
			if(game[i][j] == 'O') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		for(int i = 5; i >= 0; i--) { //diagonal 2
			int j = 0;
			if(game[i][j] == 'O') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		for(int i = 5; i >= 0; i--) { //diagonal 3
			int j = 0;
			if(game[i][j] == 'O') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 5) {
				return true;
			}
			
			j++;
		}
		for(int i = 5; i >= 1; i--) { //diagonal 4
			int j = 0;
			if(game[i][j] == 'O') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 4; i >= 0; i--) { //diagonal 5
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 0; i--) { //diagonal 6
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 0; i--) { //diagonal 7
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		count = 0;
		for(int i = 5; i >= 1; i--) { //diagonal 8
			int j = 0;
			if(game[j][i] == 'X') {
				count++;
			}
			else {
				count = 0;
			}
			
			if(count == 4) {
				return true;
			}
			
			j++;
		}
		
		
		return false;
	}
	
	/**Print method utilized for testing the validity of the 
	 * 2D array conversion method in the TextConsole
	 */
	public void printGame() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				System.out.print(game[i][j]);
			}
			System.out.println();
		}
	}
	
	/**checks to see if all elements of the board are full.
	 * 
	 * @return if all elements are full true is returned
	 */
	public boolean isBoardFull() {
		int count = 0;
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(game[i][j] == 'X' || game[i][j] == 'O') {
					count++;
				}
			}
		}
		
		if(count == 42) {
			return true;
		}
		
		return false;
	}
	
	/** Creates a string with the consecutive 42 characters of the game
	 * 
	 * @return str is the string of game characters
	 */
	public String toString() {
		String str = "";
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				str = str + game[i][j];
			}
		}
		
		return str;
	}
	
	/**Fills the game board with elements for testing.
	 * 
	 */
	public void fillGame() {
		game = new	char[][] {
			{'X','X','X','O','O','O','X'},
			{'X','X','X','O','O','O','X'},
			{'X','X','X','O','O','O','X'},
			{'X','X','X','O','O','O','X'},
			{'X','X','X','O','O','O','X'},
			{'X','X','X','O','O','O','X'}
						};
	}
}
