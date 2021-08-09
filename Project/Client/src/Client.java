import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Client implements ActionListener {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private BufferedReader stdIn;
    // private String stash;
    // private String request;
    private JFrame mainFrame;
    private JTextField inputTextField;
    private JLayeredPane lp = new JLayeredPane();
    private String inputString;
    private String input;
    private JTextArea mainTextArea;
    private JButton OK = new JButton("OK");
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
    
    
    public Client(String serverName, int port) {
        try{
            aSocket = new Socket(serverName, port);

            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader((aSocket.getInputStream())));
            socketOut = new PrintWriter((aSocket.getOutputStream()), true);
            makeGUI();
            
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
                    //System.out.printf("%s\n%s\n%s\n", DELIMITER, RESPONSE_HEADER, SUB_DELIM);
                    //System.out.print(response);
                    
                    fillArea(response);
                    slct = Integer.parseInt(getInput());
                    System.out.println("\"" + slct + "\" will be sent to server" );
                    socketOut.println(slct);
                    //System.out.println(SUB_DELIM);
                } else {
                    System.out.printf("%s\n%s\n%s\n", SUB_DELIM, AWAITING_HEADER, SUB_DELIM);
                }


            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        try {
            mainFrame.dispose();
			stdIn.close();
			socketIn.close();
			socketOut.close();
		}catch(IOException e) {
			e.getStackTrace();
		}

    }


    public void makeGUI() {
        mainFrame = new JFrame();
        JPanel mainPanel = new JPanel();

        mainTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(mainTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        inputTextField = new JTextField();
        //Dimension panelDimension = new Dimension(1280,720);
        Dimension textAreaDimension = new Dimension(1000,350);
        Dimension minInputDimension = new Dimension(1000,70);
        //Dimension maxInputDimension = new Dimension(1000,150);

        
        


        lp.setLayout(new BorderLayout());
        lp.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        mainPanel.setLayout(new BorderLayout(0,2));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        lp.add(mainPanel);

        mainTextArea.setEditable(false);
                


        mainTextArea.setFont(new Font("MONOSPACED", Font.BOLD, 20));
        inputTextField.setBorder(BorderFactory.createLoweredBevelBorder());

        

        
        mainPanel.add(OK, BorderLayout.LINE_END);
        OK.addActionListener(this);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputTextField, BorderLayout.SOUTH);
        mainTextArea.setMaximumSize(textAreaDimension);
        inputTextField.setPreferredSize(minInputDimension);


        mainFrame.setLayeredPane(lp);
        mainFrame.setPreferredSize(new Dimension(1280,720));
        mainFrame.setMinimumSize(new Dimension(1280,720));
        mainFrame.setMaximumSize(new Dimension(1280, 720));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Main Window");
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }


    /**
     * Return input of the textfield to Client.java to send 
     * and communicate with the Server.
     * @return input
     */
    public String getInput() {
        
        /**
         * This lambda expression call allows ENTER to be pressed when 
         * inputting in the userInput textfield.
         */
        input = "";
        while(input.equals("")) {
                inputTextField.addActionListener(this);
        }
        
        
        return input;
    } 




    /**
     * Populating the a String with the responses from Server.
     * @param aResponse
     */
    public void fillArea(String aResponse) {
        inputString = aResponse;
        if(inputString != null)
            mainTextArea.setText(getArea());
    }

    /**
     * Used to populate the JTextArea with the string initialized
     * in fillArea().
     * @return
     */
    public String getArea() {
        return inputString;
    }


    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        try{
            if(input.equals("")){
                input = inputTextField.getText();
                inputTextField.setText("");
            }
        } catch (NumberFormatException ee) {
            ee.getSuppressed();
        }

        if(e.getSource() == OK){
            input = inputTextField.getText();
            inputTextField.setText("");}

                
    }
    
    /**
     * Creates a Client object called {@code newClient} and a CourseCatalogue object called {@code cat}. We then call 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Client newClient = new Client("192.168.0.119", 9898);
         newClient.communicate();
    }
}
