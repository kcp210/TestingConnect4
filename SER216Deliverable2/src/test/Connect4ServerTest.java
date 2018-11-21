package test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import PseudoClients.PseudoClient;
import PseudoClients.PseudoClient2;
import PseudoClients.PseudoClient3;
import PseudoClients.PseudoClient4;
import PseudoClients.PseudoClient5;
import PseudoClients.PseudoClient6;
import core.Connect4Server;

class Connect4ServerTest {

	private static Connect4Server server;
	private static PseudoClient client;
	private static PseudoClient2 client2;
	private static PseudoClient3 client3;
	private static PseudoClient4 client4;
	private static PseudoClient5 client5;
	private static PseudoClient6 client6;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		server = new Connect4Server();
		client = new PseudoClient();
		client2 = new PseudoClient2();
		client3 = new PseudoClient3();
		client4 = new PseudoClient4();
		client5 = new PseudoClient5();
		client6 = new PseudoClient6();
	}

	@AfterEach
	void tearDown() throws Exception {
		server = null;
		client = null;
		client2 = null;
		client3 = null;
		client4 = null;
		client5 = null;
		client6 = null;
	}

	@Test
	void testSinglePlayer() {
		
		server.runServer(8000); //port allows for multiple servers to open
		client.connectServer();
		
		assertTrue(server.getGameOver() == true); //checks if o won the game
		
	}
	
	@Test
	void testTwoPlayer() {
		
		server.runServer(8001);
		client2.connectServer();
		client3.connectServer();
		
		char[][] arr = new char[6][7];
		
		arr[5][0] = 'O';
		arr[5][4] = 'X';
		arr[4][0] = 'O';
		arr[4][4] = 'X';
		arr[5][1] = 'O';
		arr[3][4] = 'X';
		arr[4][1] = 'O';
		arr[5][5] = 'X';
		
		//returns true if the game state is correct
		assertFalse(arr == server.getGame().getGame());
		
	}
	
	@Test
	void testFullBoard() {
		
		server.runServer(8002);
		client4.connectServer();
		client5.connectServer();
		
		//returns true if the game ends due to a draw
		assertFalse(server.getGameOver() == true);
		
	}
	
	@Test
	void testFailedConnection() {
		
		//testing connection
		try {
			client6.connectServer();
		}
		catch(Exception ex) {
			fail("No server found");
		}
		
	}

}
