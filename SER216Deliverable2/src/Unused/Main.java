package Unused;
import java.util.Scanner;

import core.Connect4;
import core.Connect4ComputerPlayer;

/**The Main class contains the main method that is used to test the program.
 * 
 * @author William Arnold
 * @version 1.0 Septermber 29, 2018
 * 
 */

public class Main {
	
	/**Main method guides the users through turns and calls methods from both classes
	 *@param args[]
	 */
	public static void main(String args[]) {
		//initializations
		Scanner scan = new Scanner(System.in);
		
		Connect4 game = new Connect4();
		Connect4TextConsole console = new Connect4TextConsole();
		Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
		
		boolean win = false;
		
		//start of game
		console.printBoard();
		
		System.out.println("Begin Game.");
		System.out.println("PlayerX - your turn. Choose a column number from 1-7.");
		
		boolean good = false;
		
		int col;
		while(good == false) {
			col = scan.nextInt();
			if(game.isValid(col)) {
				game.dropX(col - 1);
				good = true;
			}
		}
		
		console.convertBoard(game.getGame());
		console.printBoard();
		
		//loops through turns until a player wins
		while(win == false) {
			System.out.println("PlayerO - your turn. Choose a column number from 1-7.");
			
			System.out.println(comp.makeMove(game.getGame()) + 1);
			
			game.dropO(comp.makeMove(game.getGame()) );
			
			console.convertBoard(game.getGame());
			console.printBoard();
			
			if(game.checkForWinO() == true) {
				System.out.println("Player O Won the Game");
				win = true;
				break;
			}
			
			System.out.println("PlayerX - your turn. Choose a column number from 1-7.");
			
			boolean good1 = false;
			
			int col1;
			while(good1 == false) {
				col1 = scan.nextInt();
				if(game.isValid(col1)) {
					game.dropX(col1 - 1);
					good1 = true;
				}
			}
			
			console.convertBoard(game.getGame());
			console.printBoard();
			
			if(game.checkForWinX() == true) {
				System.out.println("Player X Won the Game");
				win = true;
				break;
			}
			System.out.println();
		}
	}
}
