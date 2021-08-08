import java.net.*;
import java.io.*;



public class Server {

	private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private static DBManager manager;
	private Student user;
	


	//private ExecutorService pool;


	public Server(int port) {
		try{
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setDBManager(DBManager dbManager) {
		manager = dbManager;
	}
	
	public void runServer() {
		user = manager.getStudents().get(1);
        //System.out.println(user);
	}



	/**
	 * Main method that connects the Client to the Server.
	 * @param args
	 */
	public static void main (String [] args) {
		try{
			Server myServer = new Server (9898);
			myServer.aSocket = myServer.serverSocket.accept();
			System.out.println("Connection accepted by server!");
			myServer.socketIn = new BufferedReader (new InputStreamReader (myServer.aSocket.getInputStream()));
			myServer.socketOut = new PrintWriter((myServer.aSocket.getOutputStream()), true);
			CourseCatalogue cat = new CourseCatalogue();
			myServer.runServer();
		
			myServer.socketIn.close();
			myServer.socketOut.close();
		} catch (IOException e) {
			e.getStackTrace();
		}

	}


}