import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCLI {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private BufferedReader stdIn;
    private guiClient user;
    private static final String DELIMITER = "********************"
                                          + "********************"
                                          + "********************"
                                          + "********************";
    private static final String SUB_DELIM = "--------------------"
                                          + "--------------------"
                                          + "--------------------"
                                          + "--------------------";
    private static final String PROCESSING_HEADER = "                                   "
                                                  + "PROCESSING"
                                                  + "                                   ";
    private static final String RESPONSE_HEADER = "                                    "
                                                + "RESPONSE"
                                                + "                                    ";
    private static final String AWAITING_HEADER = "                                    "
                                                + "AWAITING"
                                                + "                                    ";
    
    public ClientCLI(String serverName, int port) {
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
                System.out.println("Checking readiness: " + socketIn.ready());
                if (socketIn.ready()) {
                    System.out.printf("%s\n%s\n%s\n", DELIMITER, PROCESSING_HEADER, SUB_DELIM);
                }
                while(socketIn.ready()) {
                    response += socketIn.readLine();
                    response += "\n";
                    System.out.println("we looping");
                }

                if (!response.isEmpty()){
                    System.out.printf("%s\n%s\n%s\n", DELIMITER, RESPONSE_HEADER, SUB_DELIM);
                    System.out.print(response);
                    slct = Integer.parseInt(stdIn.readLine());
                    System.out.println("\"" + slct + "\" will be sent to server" );
                    socketOut.println(slct);
                    System.out.println(SUB_DELIM);
                } else {
                    System.out.printf("%s\n%s\n%s\n", SUB_DELIM, AWAITING_HEADER, SUB_DELIM);
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
        ClientCLI newClient = new ClientCLI("localhost", 9898);
        newClient.communicate();
    }
}