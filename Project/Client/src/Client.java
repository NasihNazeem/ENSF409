import java.net.*;

import java.io.*;

public class Client {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private BufferedReader stdIn;
    private static DBManager manager;
    private static Student user;
    
    public Client(String serverName, int port) {
        try{
            aSocket = new Socket(serverName, port);

            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader((aSocket.getInputStream())));
            socketOut = new PrintWriter((aSocket.getOutputStream()), true);
            
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void setDBManager(DBManager dbManager){
        manager = dbManager;
    }


    public void communicate() {

        user = manager.getStudents().get(1);
        //System.out.println(user);
        new guiClient();

    }

    /**
     * Creates a Client object called {@code newClient} and a CourseCatalogue object called {@code cat}. We then call 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Client newClient = new Client("localhost", 9898);
        
        newClient.communicate();
        

    }
}
