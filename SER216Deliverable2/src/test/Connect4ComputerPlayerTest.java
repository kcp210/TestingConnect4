package test;

import core.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Connect4ComputerPlayerTest {
	
	private static Connect4ComputerPlayer comp;
	private static Connect4 game;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		comp = new Connect4ComputerPlayer();
		
		game = new Connect4();
		
		//Creating a predefined game state
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
		game.dropO(0);
		game.dropX(0);
		game.dropO(0);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		game = null;
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMakeMove() {
		
		//Successful game state
		assertTrue(comp.makeMove(game.getGame()) == 1);
		
		game.fillGame();
		
		//Unsuccessful game state
		assertTrue(comp.makeMove(game.getGame()) == -1);
		
	}

}
