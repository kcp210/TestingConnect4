package test;

import static org.junit.jupiter.api.Assertions.*;
import core.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Connect4Test {

	private static Connect4 game;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		game = new Connect4();
	}

	@AfterEach
	public void tearDown() throws Exception {
		game = null;
	}
	
	@Test
	public void testGetElement() {
		
		//create predefined game state
		game.dropX(3);
		game.dropO(3);
		game.dropX(5);
		game.dropO(0);
		
		assertTrue(game.getElement(5, 0) == 'O');
		
	}
	
	@Test
	public void testDrops() {
		
		//create predefined game state
		game.dropX(5);
		
		//create array equal to expected game state
		char[][] arr = new char[6][7];
		arr[5][5] = 'X';
		
		//compare
		assertTrue(game.getGame() == arr);
		
	}
	
	@Test
	public void testDropInts() {
		
		assertTrue(game.dropXInt(2) == 5);
		assertTrue(game.dropOInt(2) == 4);
		assertTrue(game.dropXInt(2) == 3);
		assertTrue(game.dropOInt(2) == 2);
		assertTrue(game.dropXInt(2) == 1);
		assertTrue(game.dropOInt(2) == 0);
		assertTrue(game.dropXInt(2) == -1);
		
	}
	
	@Test
	public void testIsValid() {
		
		assertFalse(game.isValid(-1) == true);
		assertTrue(game.isValid(6) == true);
		assertFalse(game.isValid(9) == true);
		
		//fills row
		game.dropX(2);
		game.dropO(2);
		game.dropX(2);
		game.dropO(2);
		game.dropX(2);
		game.dropO(2);
		
		//make sure full row is not valid
		assertFalse(game.isValid(6) == true);
		
	}
	
	@Test
	public void testcheckForWinX() {
		
		//predefined game state
		game.dropX(5);
		game.dropO(0);
		game.dropX(3);
		game.dropO(3);
		game.dropX(5);
		game.dropO(2);
		game.dropX(4);
		
		assertTrue(game.checkForWinX() == false);
		
		//predefined game state
		game.dropO(2);
		game.dropX(5);
		game.dropO(1);
		game.dropX(5);
		
		assertTrue(game.checkForWinX() == true);
		
	}
	
	@Test
	public void testcheckForWinO() {
		
		//predefined game state
		game.dropX(5);
		game.dropO(0);
		game.dropX(3);
		game.dropO(3);
		game.dropX(5);
		game.dropO(2);
		game.dropX(4);
		game.dropO(2);
		game.dropX(0);
		game.dropO(1);
		game.dropX(0);
		
		assertTrue(game.checkForWinO() == false);
		
		//predefined game state
		game.dropO(1);
		game.dropX(0);
		game.dropO(4);
		
		assertTrue(game.checkForWinO() == true);
		
	}
	
	@Test
	public void testIsBoardFull() {
		
		assertTrue(game.isBoardFull() == false);
		
		game.fillGame();
		
		assertTrue(game.isBoardFull() == true);
		
	}

}
