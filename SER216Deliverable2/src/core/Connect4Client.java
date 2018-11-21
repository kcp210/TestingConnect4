package core;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**The client creates a thread that seperates between two players
 * and connects to a server running the connect 4 game application.
 * When the game data is returned the client updates the gui.
 * 
 * @author William Arnold
 * @version 2.0 November 20, 2018
 * 
 */

/*
 * CLIENT CLASS ONLY UPDATES THE GUI WHEN A COORDINATE IS RETURNED OR A WIN STATE OCCURS
 * FOR THIS REASON THE CLASS IS NOT TESTED IN JUNIT
 */
@SuppressWarnings("restriction")
public class Connect4Client extends Application {
	/**initializations of necessary booleans*/
	private boolean endGame = false; //if someone won or the board is full
	private boolean isCompOpponent = false; //if its a one player game
	private boolean waiting = true; //if we're waiting for a player to play
	private boolean isConnected;
	
	private int turnNum; //allows x to go first without taking in a previous move
	
	/**initialization of localhost or ip*/
	private String host = "localhost";
	
	/**initialize io stream*/
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	//Initialize panes
	BorderPane masterPane = new BorderPane(); //contains two child panes listed below
	GridPane gridPane = new GridPane(); //Connect4 board pane
	BorderPane borderPane = new BorderPane(); //UI information and instruction
	FlowPane flowPane = new FlowPane();
	
	//initialize UI elements
	Text label;
	Text instructions;
	TextField xInput;
	TextField oInput;
	Button Move;
	Button sPlayer;
	Button tPlayer;
	
	
	/**Starts the client graphic UI
	 * 
	 * @param primaryStage default stage of the application
	 */
	public void start(Stage primaryStage) {
				label = new Text("Connecting to server");
				instructions = new Text("Enter an integer from 1-7.");
				xInput = new TextField();
				oInput = new TextField();
				Move = new Button("Move");
				sPlayer = new Button("1");
				tPlayer = new Button("2");
		
				turnNum = 1; //prevents X from reading in a move on the first move of the game
		
				//Set gaps between board elements
				gridPane.setVgap(10);
				gridPane.setHgap(10);
				
				//Initialize the game board
				for(int i = 0; i < 6; i++) {
					for(int j = 0; j < 7; j++) { //traverse board
						Text t = new Text(" "); //Set the element equal to the player token in that space
						t.setText(" "); //Empty spaces are filled with a space
						t.setStyle("-fx-font: 80 arial;"); //Large font to represent player moves
						gridPane.add(t, j, i); //Add the text to the grid
					}
					gridPane.getColumnConstraints().add(new ColumnConstraints(100));
				}
				

				//format grid pane
				gridPane.getColumnConstraints().add(new ColumnConstraints(100));
				gridPane.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
				
				//Fill flow pane
				flowPane.getChildren().add(Move);
				flowPane.getChildren().add(sPlayer);
				flowPane.getChildren().add(tPlayer);
				
				//Fill border pane
				borderPane.setTop(label);
				borderPane.setCenter(instructions);
				borderPane.setLeft(xInput);
				borderPane.setRight(oInput);
				borderPane.setBottom(flowPane);
				
				//Set label style
				BorderPane.setAlignment(label, Pos.CENTER);
				label.setStyle("-fx-font: 20 arial;");
				
				//Add sections of the master pane
				masterPane.setTop(gridPane);
				masterPane.setCenter(borderPane);
				
				//Build the stage and scene
				Scene scene = new Scene(masterPane, 760, 800);
				primaryStage.setTitle("Connect4");
				primaryStage.setScene(scene);
				primaryStage.show();
				
				//set visibility to false to elements of later use
				Move.setVisible(false);
				xInput.setVisible(false);
				oInput.setVisible(false);
				instructions.setVisible(false);
				
				connectServer();
	}
	
	/**Establishes a thread and connects both players
	 * to the server. Then updates their GUIs accordingly.
	 * 
	 */
	public void connectServer() {
		
		try {
			//initialize socket
			Socket socket = new Socket("localhost", 8000);
			
			// io stream
			fromServer = new DataInputStream(socket.getInputStream());
			
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex){
			isConnected = false;
			ex.printStackTrace();
		}
		
		//thread allows two clients to connect to the server
		new Thread(() -> {
			try {
				//player active in the thread
				int player = fromServer.readInt();
				
				isConnected = true;
				
				/*
				 * Player 1 is Player X
				 */
				if(player == 1) {
					label.setText("Would you like to play with one or two players?");
					
					waitForNumPlayers();
					
					sendNumPlayers();
					
					waiting = false;
					
					//Cue for Player X's UI to change when Player O joins
					fromServer.readInt();
					
					//Set up for X's first turn
					Platform.runLater(() -> {
						label.setText("Player X's Turn");
					});
					
					sPlayer.setVisible(false);
					tPlayer.setVisible(false);
					xInput.setVisible(true);
					oInput.setVisible(false);
					Move.setVisible(true);
				}
				/*
				 * Player 2 is Player O
				 */
				else if(player == 2) {
					label.setText("Waiting for Player X");
					sPlayer.setVisible(false);
					tPlayer.setVisible(false);
				}
				
				//While no one has won or draw
				while(endGame == false) {
					/*
					 * Player 1 is Player X
					 */
					if(player == 1) {
						boolean good = false;
						int col = 0;
						
						if(turnNum > 1) {
							recieveInfoFromServerO();
						}
						
						while(good == false) {
							waiting = true;
							
							//Update GUI for Player X's Turn
							Move.setVisible(true);
							label.setText("Player X's Turn"); 
							xInput.setVisible(true);
							
							//Wait for the button press
							waitForAction();
							
							//return the column to the server
							sendMove();
							
							waiting = false;
							
							try {
								col = Integer.parseInt(xInput.getText());
							} catch(NumberFormatException ex) {
								System.out.println("Exception handled: " + ex);
								col = 0;
							}
							
							xInput.clear();
							
							if(col >= 1 && col <= 7) {
								good = true;
								instructions.setVisible(false);
							}
							else {
								instructions.setVisible(true);
							}
						}
						
						try {
							toServer.writeInt(col - 1);
						}
						catch(IOException ex) {
							System.err.println(ex);
						}
						
						//Set u for Player O's turn
						xInput.setVisible(false);
						label.setText("Waiting for Player O");
						Move.setVisible(false);
						
						//Player O's turn ends when the game board updates
						recieveInfoFromServerX();
					}
					/*
					 * Player 2 is Player O
					 */
					else if(player == 2) {
						boolean good = false;
						int col = 0;
						
						//Set up for Player X's turn
						label.setText("Waiting for Player X");
						Move.setVisible(false);
						oInput.setVisible(false);
						
						//Player X's turn ends when the game board updates
						recieveInfoFromServerX();
						
						while(good == false) {
							waiting = true;
							
							//Set up for Player O's turn
							label.setText("Player O's Turn");
							Move.setVisible(true);
							oInput.setVisible(true);
							
							//Wait for the button press
							waitForAction();
							
							//Return the column to the server
							sendMove();
							
							waiting = false;
							
							try {
								col = Integer.parseInt(oInput.getText());
							} catch(NumberFormatException ex) {
								System.out.println("Exception handled: " + ex);
								col = 0;
							}
							
							oInput.clear();
							
							if(col >= 1 && col <= 7) {
								good = true;
								instructions.setVisible(false);
							}
							else {
								instructions.setVisible(true);
							}
						}
						
						
						try {
							toServer.writeInt(col - 1);
						}
						catch(IOException ex) {
							System.err.println(ex);
						}
						
						//Set up for Player S's turn
						oInput.setVisible(false);
						label.setText("Waiting for Player X");
						Move.setVisible(false);

						//Player X's turn ends when the game board updates
						recieveInfoFromServerO();
					}
					//increase turn by one
					turnNum++;
				}
			}
			//print the stack trace
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}).start(); //start thread
	}
	
	/** Returns true if the client is currently connected to the server.
	 * 
	 * @return isConnected is the boolean connection of the player
	 */
	public boolean getIsConnected() {
		return isConnected;
	}
	
	/**Waits for a player to make a move
	 * 
	 * @throws InterruptedException
	 */
	private void waitForAction() throws InterruptedException {
		while(waiting) {
			//When button pressed
			Move.setOnAction(e -> {
				waiting = false;
			});
			Thread.sleep(50);
			
		}
		
		waiting = true;
	}
	
	/**Waits for a player to make a move
	 * 
	 * @throws InterruptedException
	 */
	private void waitForNumPlayers() throws InterruptedException {
		 
		
		waiting = true;
	}
	
	/** Send move sends the game column to the server.
	 * 
	 * @throws IOException
	 */
	private void sendMove() throws IOException {
		Move.setOnAction(e -> {
			waiting = false;
			
			//Return column of move to the server
			int col = Integer.parseInt(xInput.getText());
			xInput.clear();
			try {
				toServer.writeInt(col - 1);
			}
			catch(IOException ex) {
				System.err.println(ex);
			}
		});
	}
	
	/**Send Num Players sends the number of players selected by the user
	 * to the server.
	 * 
	 * @throws IOException
	 */
	private void sendNumPlayers() throws IOException{
		if(isCompOpponent == true) {
			toServer.writeInt(1);
		}
		else {
			toServer.writeInt(2);
		}
	}
	
	/**reads player X's move and checks status of wins states
	 * 
	 * @throws IOException
	 */
	private void recieveInfoFromServerX() throws IOException {
		int status = fromServer.readInt();
	 
		if(status == 11) {
			endGame = true; //someone won
			recieveMoveX();
			winStateX(); //x won
		}
		else if(status == 13) {
			endGame = true;
			recieveMoveX();
			drawState();
		}
		else {
			recieveMoveX(); //update the gameboard
		}
	}
	
	/**reads player O's move and checks status of win states
	 * 
	 * @throws IOException
	 */
	private void recieveInfoFromServerO() throws IOException {
		int status = fromServer.readInt();
	 
		if(status == 12) {
			endGame = true;//someone won
			recieveMoveO();
			winStateO(); //o won
		}
		else if(status == 13) {
			endGame = true;
			recieveMoveO();
			drawState();
		}
		else {
			recieveMoveO(); //update gameboard
		}
	}
	
	/**reads a space from a server and fills in the space that X
	 * selected to move into
	 * 
	 * @throws IOException
	 */
	private void recieveMoveX() throws IOException {
		int column = fromServer.readInt();
		int row = fromServer.readInt();
		
		Platform.runLater(() -> {
			Text t = new Text("X"); //Set the element equal to the player token in that space
			t.setFill(Color.RED);
			t.setStyle("-fx-font: 80 arial;"); //Large font to represent player moves
			gridPane.add(t, row, column); //Add the text to the grid
		});
	}
	
	/**reads a space from a server and fills in the space that O
	 * selected to move into
	 * 
	 * @throws IOException
	 */
	private void recieveMoveO() throws IOException {
		int column = fromServer.readInt();
		int row = fromServer.readInt();
		
		Platform.runLater(() -> {
			Text t = new Text("O"); //Set the element equal to the player token in that space
			t.setFill(Color.BLUEVIOLET);
			t.setStyle("-fx-font: 80 arial;"); //Large font to represent player moves
			gridPane.add(t, row, column); //Add the text to the grid
		});
	}
	
	/**Sets the GUI if player x wins the game
	 * 
	 */
	public void winStateX() {
		//Node initializations
		label.setText("X Won!");
		Button replay = new Button("Play Again?");
		
		//Add and remove nodes
		borderPane.setRight(null);
		borderPane.setBottom(null);
	}
	
	/**Sets the GUI if player o wins the game
	 * 
	 */
	public void winStateO() {
		//Node initializations
		label.setText("O Won!");
		
		//Add and remove nodes
		borderPane.setLeft(null);
		borderPane.setBottom(null);
	}
	
	/**Sets the GUI if the game baord fills without a win
	 * 
	 */
	public void drawState() {
		label.setText("Draw.");
		
		//Add and remove nodes
		borderPane.setLeft(null);
		borderPane.setBottom(null);
	}
	
	/**Launches the javafx application
	 * 
	 * @param args main
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
