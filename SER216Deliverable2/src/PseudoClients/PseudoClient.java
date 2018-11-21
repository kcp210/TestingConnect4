package PseudoClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * Client 1 creates a single player game state where the computer player wins
 */
public class PseudoClient {
	
	private boolean isConnected;

	private String host = "localhost";
	
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
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
		try {
			fromServer.readInt();
			
			isConnected = true;
			
			toServer.writeInt(1); //number of players
			
			fromServer.readInt(); //cue
			
			toServer.writeInt(3); //X's first move
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving X's row
			fromServer.readInt(); //receiving X's column
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving O's row
			fromServer.readInt(); //receiving O's column
			
			toServer.writeInt(2); //X's first move
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving X's row
			fromServer.readInt(); //receiving X's column
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving O's row
			fromServer.readInt(); //receiving O's column
			
			toServer.writeInt(2); //X's first move
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving X's row
			fromServer.readInt(); //receiving X's column
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving O's row
			fromServer.readInt(); //receiving O's column
			
			toServer.writeInt(2); //X's first move
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving X's row
			fromServer.readInt(); //receiving X's column
			
			fromServer.readInt(); //receiving status
			fromServer.readInt(); //receiving O's row
			fromServer.readInt(); //receiving O's column
		}
		catch(IOException ex) {
			System.err.println(ex);
		}
	}
}
