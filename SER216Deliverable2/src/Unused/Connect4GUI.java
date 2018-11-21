package Unused;
/**Controls the graphical user interface of the system.
 * 
 * @author William Arnold
 * @version 1.0 October 29, 2018
 * 
 */

import java.util.Scanner;

import core.Connect4;
import core.Connect4ComputerPlayer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.geometry.Pos;

@SuppressWarnings("restriction")
public class Connect4GUI extends Application {
	
	/**Initializations of the game components */
	private Connect4 game = new Connect4();
	private Connect4TextConsole console = new Connect4TextConsole();
	private Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
	
	/**Start method launches the Connect4 graphic UI
	 * 
	 * @param The default stage(window) of the application
	 */
	public void start(Stage primaryStage) {
		//Initialize panes
		BorderPane masterPane = new BorderPane(); //contains two child panes listed below
		GridPane gridPane = new GridPane(); //Connect4 board pane
		BorderPane borderPane = new BorderPane(); //UI information and instruction
		
		//Set gaps between board elements
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		//Initialize the game board
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) { //traverse board
				Text t = new Text(Character.toString(game.getElement(i, j))); //Set the grid cell equal to the player token in 
																			  //the connect4 object (ex: 'X')
				if(game.getElement(i, j) == 'X') {
					t.setFill(Color.RED); //Player X is represented in red
				}
				else if(game.getElement(i, j) == 'O') {
					t.setFill(Color.BLUEVIOLET); //Player O is represented in blue
				}
				else {
					t.setText(" "); //Empty spaces are filled with a space
				}
				t.setStyle("-fx-font: 80 arial;"); //Large font to represent player moves
				gridPane.add(t, j, i); //Add the text to the grid
			}
			gridPane.getColumnConstraints().add(new ColumnConstraints(100)); //Set each column to the width of 100
		}
		
		//initialize UI elements
		Text label = new Text("Connecting to Server");
		Text instructions = new Text("Enter an integer from 1-7.");
		TextField xInput = new TextField();
		TextField oInput = new TextField();
		Button xMove = new Button("Move");
		Button oMove = new Button("Move");
		
		//X goes first, O's info is invisible
		oInput.setVisible(false);
		oMove.setVisible(false);
		
		//Fill border pane
		borderPane.setTop(label);
		borderPane.setCenter(instructions);
		borderPane.setLeft(xInput);
		borderPane.setRight(oInput);
		borderPane.setBottom(xMove);
		
		//Set label style
		BorderPane.setAlignment(label, Pos.CENTER);
		label.setStyle("-fx-font: 20 arial;");
		
		//Add sections of the master pane
		masterPane.setTop(gridPane);
		masterPane.setCenter(borderPane);
		
		//format grid pane
		gridPane.getColumnConstraints().add(new ColumnConstraints(100));
		gridPane.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
		
		//Build the stage and scene
		Scene scene = new Scene(masterPane, 760, 800);
		primaryStage.setTitle("Connect4");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//Initialize the game board
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) { //traverse board
				Text t = new Text(Character.toString(game.getElement(i, j))); //Set the element equal to the player token in that space
				if(game.getElement(i, j) == 'X') {
					t.setFill(Color.RED); //Player X is represented in red
				}
				else if(game.getElement(i, j) == 'O') {
					t.setFill(Color.BLUEVIOLET); //Player O is represented in blue
				}
				else {
					t.setText(" "); //Empty spaces are filled with a space
				}
				t.setStyle("-fx-font: 80 arial;"); //Large font to represent player moves
				gridPane.add(t, j, i); //Add the text to the grid
			}
		}
		
		//X TURN
		xMove.setOnAction(e -> { //when move button pressed
			boolean goodMove = true; //If this flips to false, the GUI stays on X's move
			
			try {
				if(game.isValid(Integer.parseInt(xInput.getText()) - 1)) {
					game.dropX(Integer.parseInt(xInput.getText()) - 1);
					System.out.println("X is dropped"); //Console confirms line is reached
				}
				else {
					instructions.setText("Invalid number. Enter an integer from 1-7.");
					goodMove = false;
				}
			}
			catch(NumberFormatException ex) {
				System.out.println("Invalid use of characters in text field.");
				instructions.setText("Invalid characters. Enter an integer from 1-7.");
				goodMove = false;
			}
			catch(ArrayIndexOutOfBoundsException ex1) {
				System.out.println("Invalid integer input.");
				instructions.setText("Invalid number. Enter an integer from 1-7.");
				goodMove = false;
			}
			if(game.checkForWinX()) { //checks if x won
				System.out.println("X Won");
				winStateX(borderPane);
			}
			//Sets up for O's turn
			if(goodMove == true) {
				xInput.clear(); 
				xInput.setVisible(false);
				xMove.setVisible(false);
				oInput.setVisible(true);
				borderPane.setBottom(oMove);
				oMove.setVisible(true);
				instructions.setText("Enter an integer from 1-7.");
				BorderPane.setAlignment(oMove, Pos.BOTTOM_RIGHT);
				label.setText("Player O's Turn");
			}
			
			//updates gameboard
			update(gridPane);
		});
		
		//O Move
		oMove.setOnAction(e -> {
			boolean goodMove = true;
			
			try {
				if(game.isValid(Integer.parseInt(oInput.getText()) - 1)) {
					game.dropO(Integer.parseInt(oInput.getText()) - 1);
					System.out.println("O is dropped"); //Console confirms line is reached
				}
				else {
					instructions.setText("Invalid number. Enter an integer from 1-7.");
					goodMove = false;
				}
			}
			catch(NumberFormatException ex) {
				System.out.println("Invalid use of characters in text field.");
				instructions.setText("Invalid characters. Enter an integer from 1-7.");
				goodMove = false;
			}
			catch(ArrayIndexOutOfBoundsException ex1) {
				System.out.println("Invalid integer input.");
				instructions.setText("Invalid number. Enter an integer from 1-7.");
				goodMove = false;
			}
			if(game.checkForWinO()) { //checks if O won
				System.out.println("O Won");
				winStateO(borderPane);
			}
			//Sets up for X's turn
			if(goodMove == true) {
				oInput.clear();
				oInput.setVisible(false);
				oMove.setVisible(false);
				xInput.setVisible(true);
				borderPane.setBottom(xMove);
				xMove.setVisible(true);
				instructions.setText("Enter an integer from 1-7.");
				BorderPane.setAlignment(xMove, Pos.BOTTOM_LEFT);
				label.setText("Player X's Turn");
			}
			
			//Update gameboard
			update(gridPane);
		});
	}
	
	/**Updates the gameboard in a single line to make the button action simpler
	 * 
	 * @param gridPane is necessary to change the element characters
	 */
	public void update(GridPane gridPane) {
		//Initialize the game board
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) { //traverse board
				Text t = new Text(Character.toString(game.getElement(i, j))); //Set the element equal to the player token in that space
				if(game.getElement(i, j) == 'X') {
					t.setFill(Color.RED); //Player X is represented in red
				}
				else if(game.getElement(i, j) == 'O') {
					t.setFill(Color.BLUEVIOLET); //Player O is represented in blue
				}
				else {
					t.setText(" "); //Empty spaces are filled with a space
				}
				t.setStyle("-fx-font: 80 arial;"); //Large font to represent player moves
				gridPane.add(t, j, i); //Add the text to the grid
			}
		}
	}
	
	/**If X won the game tell the players and ask to play again
	 * 
	 * @param borderPane is necessary to edit the GUI info
	 */
	public void winStateX(BorderPane borderPane) {
		//Node initializations
		Text text = new Text("X Won!");
		Button replay = new Button("Play Again?");
		
		//Format text
		borderPane.setCenter(text);
		text.setStyle("-fx-font: 40 arial;");
		
		//Add and remove nodes
		borderPane.setTop(replay);
		borderPane.setRight(null);
		borderPane.setBottom(null);
		BorderPane.setAlignment(replay, Pos.CENTER);
		
		//Button listener to restart the game
		Stage primaryStage = new Stage();
		game = new Connect4();
		replay.setOnAction(e -> start(primaryStage));
	}
	
	/**If O won the game tell the players and ask to play again
	 * 
	 * @param borderPane is necessary to edit the GUI info
	 */
	public void winStateO(BorderPane borderPane) {
		//Node initializations
		Text text = new Text("O Won!");
		Button replay = new Button("Play Again?");
		
		//Format text
		borderPane.setCenter(text);
		text.setStyle("-fx-font: 40 arial;");
		
		//Add and remove nodes
		borderPane.setTop(replay);
		borderPane.setLeft(null);
		borderPane.setBottom(null);
		BorderPane.setAlignment(replay, Pos.CENTER);
		
		//Button listener to restart the game
		Stage primaryStage = new Stage();
		game = new Connect4();
		replay.setOnAction(e -> start(primaryStage));
	}
	
	/**Main method launches the Application or runs the console UI.
	 * 
	 * @param default parameters
	 */
//	public static void main(String[] args) {
//		Scanner scan = new Scanner(System.in);
//		boolean good = false;
//		
//		//idiots might input 3 or some silly thing
//		while(good == false) {
//			System.out.println("Enter 1 for console UI. Enter 2 for graphic UI.");
//			
//			int input = scan.nextInt();
//			
//			if(input == 1) {
//				consoleUI();
//				good = true;
//			}
//			else if(input == 2) {
//				good = true;
//				launch(args);
//			}
//			else {
//				good = false;
//			}
//		}
//		
//	}
	
	public static void consoleUI() {
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
