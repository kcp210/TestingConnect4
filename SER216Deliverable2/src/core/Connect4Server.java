package core;
/**The server program connects two clients and runs a connect 4
 * game. It receives input from the clients, makes moves in the game
 * and then returns the data for the displayed gameboard.
 * 
 * @author William Arnold
 * @version 1.0 November 11, 2018
 * 
 */

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class Connect4Server extends Application {

	/**initalize connect 4 game*/
	private Connect4 game = new Connect4();
	private Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
	
	private boolean compPlayerGame = true; //if its a computer player game
	private boolean gameOver = false;
	
	/**Starts the server log GUI
	 * 
	 * @param primaryStage default stage of the application
	 */
	/*
	 * THE START METHOD IS BASED ON THE GUI AND THUS IS NOT TESTED
	 * INSTEAD, THE runServer() METHOD BELOW UTILIZED FOR THE SAME FUNCTION
	 * WITHOUT THE GUI DISPLAY CHANGES
	 */
	public void start(Stage primaryStage) {
		TextArea log = new TextArea(); //logs the server info
		
		Scene scene = new Scene(new ScrollPane(log), 500, 500);
		primaryStage.setTitle("Connect4 Server Log");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread( () -> { //thread connects two clients
			try {
				//creates socket
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> log.appendText("Server started on socket 8000\n"));
				
				while(true) {
					Platform.runLater(() -> log.appendText("Wait for players to join session\n"));
					
					//accepts player x
					Socket playerX = serverSocket.accept();
					Platform.runLater(() -> log.appendText("Player 1 joined session\n"));
				
					new DataOutputStream(
							playerX.getOutputStream()).writeInt(1);
					
					int players = new DataInputStream(playerX.getInputStream()).readInt();
					//reads number of players from the first client
					if(players == 1) {
						log.appendText("Single player game.");
						compPlayerGame = true;
					}
					else {
						log.appendText("Two player game.");
						compPlayerGame = false;
					}
					
					if(compPlayerGame == false) {
						//accepts player o
						Socket playerO = serverSocket.accept();
						Platform.runLater(() -> log.appendText("Player 2 has joined session\n"));
						
						new DataOutputStream(
								playerO.getOutputStream()).writeInt(2);
						
						Platform.runLater(() -> log.appendText("Start a thread for session\n"));
						
						new Thread(new HandleAGame(playerX, playerO)).start();
					}
					else {
						new Thread(new HandleAGame(playerX)).start();
					}
				}
			}
			catch(IOException ex) {
				ex.printStackTrace(); //prints the server info into the log
			}
		}).start(); //start thread
	}
	
	/**run server copies the start function without the GUI
	 * This method is for testing purposes.
	 * 
	 * @param port allows for multiple servers to be opened for testing
	 */
	public void runServer(int port) {
		new Thread( () -> { //thread connects two clients
			try {
				//Create server socket
				ServerSocket serverSocket = new ServerSocket(port);
				
				while(true) {
					//accepts player x
					Socket playerX = serverSocket.accept();
				
					new DataOutputStream(
							playerX.getOutputStream()).writeInt(1);
					
					//reads number of players from pseudo clients
					int players = new DataInputStream(playerX.getInputStream()).readInt();
					if(players == 1) {
						compPlayerGame = true;
					}
					else {
						compPlayerGame = false;
					}
					
					if(compPlayerGame == false) {
						//accepts player o
						Socket playerO = serverSocket.accept();
						
						new DataOutputStream(
								playerO.getOutputStream()).writeInt(2);
						
						new Thread(new HandleAGame(playerX, playerO)).start();
					}
					else {
						new Thread(new HandleAGame(playerX)).start();
					}
				}
			}
			catch(IOException ex) {
				ex.printStackTrace(); //prints the server info into the log
			}
		}).start();
	}
	
	/**Returns the connect 4 to allow access to the
	 * string of the game board
	 * 
	 * @return game is Connect 4 game
	 */
	public Connect4 getGame() {
		return game;
	}
	
	/**returns the status of the game
	 * this method is for testing purposes
	 * 
	 * @return
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	
	/**internal class handles the connect4 game logic
	 * 
	 * @author William Arnold
	 * 
	 */
	class HandleAGame implements Runnable {
		/**initalize socks for each players*/
		private Socket playerX; 
		private Socket playerO;

		/**Create a data io stream for each player*/
		private DataInputStream fromPlayerX;
		private DataOutputStream toPlayerX;
		private DataInputStream fromPlayerO;
		private DataOutputStream toPlayerO;
		
		/**constructor initializes socket for single player game
		 * 
		 * @param playerX
		 */
		public HandleAGame(Socket playerX) {
			this.playerX = playerX;
		}
		
		/**constructor initializes sockets
		 * 
		 * @param playerX
		 * @param playerO
		 */
		public HandleAGame(Socket playerX, Socket playerO) {
			this.playerX = playerX;
			this.playerO = playerO;
		}
		
		/**run method handles the return of game data in accordance
		 * with the client input
		 * 
		 */
		public void run() {
			try { // <- reading from client
				DataInputStream fromPlayerX = new DataInputStream(
						playerX.getInputStream());
				DataOutputStream toPlayerX = new DataOutputStream(
						playerX.getOutputStream());
				DataInputStream fromPlayerO = null;
				DataOutputStream toPlayerO = null;
				if(compPlayerGame == false) {
					fromPlayerO = new DataInputStream(
							playerO.getInputStream());
					toPlayerO = new DataOutputStream(
							playerO.getOutputStream());
				}
				
				//write anything as a cue
				toPlayerX.writeInt(45);
				
				//while players are connected
				while(true) {
					while(gameOver == false) {
						//read the column from the player
						int col = fromPlayerX.readInt();
						
						//return the row of the 2d char array that the piece drops into
						int row = game.dropXInt(col);
						
						
						//sends move and sends win statement
						if(game.checkForWinX()) {
							toPlayerX.writeInt(11);
							if(compPlayerGame == false) {
								toPlayerO.writeInt(11);
								sendMove(toPlayerO, col, row);
							}
							sendMove(toPlayerX, col, row);
							gameOver = true;
							break;
						}
						else if(game.isBoardFull()) {
							toPlayerX.writeInt(13);
							if(compPlayerGame == false) {
								toPlayerO.writeInt(13);
								sendMove(toPlayerO, col, row);
							}
							sendMove(toPlayerX, col, row);
							gameOver = true;
							break;
						}
						else {
							toPlayerX.writeInt(9);
							if(compPlayerGame == false) {
								toPlayerO.writeInt(9);
								sendMove(toPlayerO, col, row);
							}
							sendMove(toPlayerX, col, row);
						}
						
						/*
						 * If its a two player game, Player O does this
						 */
						if(compPlayerGame == false) {
							col = fromPlayerO.readInt();
							
							row = game.dropOInt(col);
							
							
							//sends move and sends win statement
							if(game.checkForWinO()) {
								toPlayerX.writeInt(12);
								toPlayerO.writeInt(12);
								sendMove(toPlayerX, col, row);
								sendMove(toPlayerO, col, row);
								gameOver = true;
								break;
							}
							else {
								toPlayerO.writeInt(9);
								toPlayerX.writeInt(9);
								sendMove(toPlayerX, col, row);
								sendMove(toPlayerO, col, row);
							}
						}
						
						/*
						 * If its a one player game, Player O is a computer player
						 */
						else {
							col = comp.makeMove(game.getGame());
							
							row = game.dropOInt(col);
							
							if(game.checkForWinO()) {
								toPlayerX.writeInt(12);
								sendMove(toPlayerX, col, row);
								gameOver = true;
								break;
							}
							else if(game.isBoardFull()) {
								toPlayerX.writeInt(13);
								sendMove(toPlayerX, col, row);
								gameOver = true;
								break;
							}
							else {
								toPlayerX.writeInt(9);
								sendMove(toPlayerX, col, row);
							}
						}
					}
				}
			}
			catch(IOException ex) {
				System.err.println(ex);
			}
		
		}
		
		/**sends the space that the user's token was placed into
		 * 
		 * @param out stream that the move will be returned on
		 * @param column of the game space
		 * @param row of the game space
		 * @throws IOException
		 */
		private void sendMove(DataOutputStream out, int column, int row) 
			throws IOException {
			out.writeInt(row);
			out.writeInt(column);
		}
		
		
	}
	
	/**main method launches javafx application
	 * 
	 * @param args main
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

