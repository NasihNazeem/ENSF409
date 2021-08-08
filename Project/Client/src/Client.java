import java.net.*;
import java.io.*;

public class Client {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private BufferedReader stdIn;
    private guiClient user;

    
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
    //192.168.0.119


    public void communicate() {
        String line = "";
        String response = "";

        while(!line.contentEquals("QUIT")) {
            try {
                //System.out.println("Please log in by entering your student number: ");
                line = stdIn.readLine();
                socketOut.println(line);
                response = socketIn.readLine();
                System.out.println("Response is: " + response);


            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		}catch(IOException e) {
			e.getStackTrace();
		}

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
