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
        int slct = -1;

        while(slct != 0) {
            String response = "";
            try {
                System.out.println("In line 36: " + socketIn.ready());
                // System.out.println(socketIn.readLine());
                while(socketIn.ready()) {
                    response += socketIn.readLine();
                    response += "\n";
                    System.out.println("we looping");
                }
                System.out.println("we NOT looping NOMO");

                if (!response.isEmpty()){
                    System.out.println("Response is: " + response);
                    slct = Integer.parseInt(stdIn.readLine());
                    System.out.println("slct equals: " + slct);
                    socketOut.println(slct);    
                } else {
                    System.out.println("Awaiting");
                }


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
