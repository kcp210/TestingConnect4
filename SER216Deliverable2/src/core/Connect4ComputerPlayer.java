package core;
/**The Connect4ComputerPlayer class contains the
 * method that returns an integer as a player
 * would select a column in the console input.
 * 
 * @author William Arnold
 * @version 1.0 Septermber 29, 2018
 * 
 */

public class Connect4ComputerPlayer {
	/**
	 * Make move selects the farthest left column for the AI to select
	 * 
	 * @param game gives the method the gameboard data
	 * @return returns the column to drop the piece into. If it returns -1 then the board is full.
	 */
	public int makeMove(char[][] game) {
		int count = 0;
		
		while(count < 7) {//checks that the board is full
			if(game[0][count] == 'X' || game[0][count] == 'O') { //column is full
				count++;
			}
			else {
				return count;
			}
		}
		
		return -1; //No move could be made
		
	}
}
