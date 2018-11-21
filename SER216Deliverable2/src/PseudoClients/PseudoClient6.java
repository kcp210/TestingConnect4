package PseudoClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * Client 6 tests when the game connection fails
 */
public class PseudoClient6 {
	
	private boolean isConnected;

	private String host = "localhost";
	
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	public void connectServer() {
		
		try {
			//initialize socket
			Socket socket = new Socket("localhost", 8009);
			
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
		}
		catch(IOException ex) {
			System.err.println(ex);
		}
	}
}
