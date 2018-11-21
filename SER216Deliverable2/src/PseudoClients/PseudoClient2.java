package PseudoClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * Clients 2 and 3 create a two player game where X wins
 */
public class PseudoClient2 {
	
	private boolean isConnected;
	
	private String host = "localhost";
	
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	public void connectServer() {
		try {
			//initialize socket
			Socket socket = new Socket("localhost", 8001);
			
			// io stream
			fromServer = new DataInputStream(socket.getInputStream());
			
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex){
			isConnected = false;
			ex.printStackTrace();
		}
		
		new Thread(() -> {
			try {
				fromServer.readInt();
				
				int player = 1;
				
				isConnected = true;
				
				if(player == 1) {
					toServer.writeInt(2);
				}
				
				if(player == 1) {
					fromServer.readInt();
					
					toServer.writeInt(0); //X's first move
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving X's row
					fromServer.readInt(); //receiving X's column
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving O's row
					fromServer.readInt(); //receiving O's column
					
					toServer.writeInt(1); //X's first move
					
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
					
					toServer.writeInt(3); //X's first move
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving X's row
					fromServer.readInt(); //receiving X's column
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving O's row
					fromServer.readInt(); //receiving O's column
				}
				else if(player == 2) {
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving X's row
					fromServer.readInt(); //receiving X's column
					
					toServer.writeInt(0);
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving O's row
					fromServer.readInt(); //receiving O's column
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving X's row
					fromServer.readInt(); //receiving X's column
					
					toServer.writeInt(0);
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving O's row
					fromServer.readInt(); //receiving O's column
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving X's row
					fromServer.readInt(); //receiving X's column
					
					toServer.writeInt(1);
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving O's row
					fromServer.readInt(); //receiving O's column
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving X's row
					fromServer.readInt(); //receiving X's column
					
					toServer.writeInt(1);
					
					fromServer.readInt(); //receiving status
					fromServer.readInt(); //receiving O's row
					fromServer.readInt(); //receiving O's column
				}
			}
			catch(IOException ex) {
				System.err.println(ex);
			}
		}).start();
	}
	
}
