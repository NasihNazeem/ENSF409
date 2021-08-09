import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;



public class Client implements ActionListener {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private BufferedReader stdIn;
    private guiClient user;
    private int slct;
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
    private JFrame mainFrame;
    private JTextField userInput;
    private JLayeredPane lp = new JLayeredPane();
    private String inputArea;
    private String input = "";
    public JTextArea mainArea;

    public Client(String serverName, int port) {
        try{
            aSocket = new Socket(serverName, port);

            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader((aSocket.getInputStream())));
            socketOut = new PrintWriter((aSocket.getOutputStream()), true);
            user = new guiClient();
            
            
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
                    mainArea.setText(response);
                    socketOut.println(userInput.get)
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

    public void actionPerformed(ActionEvent e) {
        socketOut.println(userInput.getText());
        userInput.setText("");
    }

    public void makeSLCT(int aSLCT) {
        this.slct = aSLCT;
    }

    public void guiClient() {
        mainFrame = new JFrame();
        JPanel mainPanel = new JPanel();
        JTextField welcomeText = new JTextField("Welcome! Please choose from the" 
                                                    + " following choices");
        mainArea = new JTextArea(1,2);
        JScrollPane scrollPane = new JScrollPane(mainArea, 
                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        userInput = new JTextField();
        Dimension panelDimension = new Dimension(1280,720);
        Dimension areaDimension = new Dimension(1000,500);
        Dimension inputDimension = new Dimension(1000,50);
        Dimension welcomeDimension = new Dimension(1000,50);


        lp.setLayout(new BorderLayout());
        lp.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        lp.add(mainPanel);
        lp.setPreferredSize(panelDimension);
        lp.setMaximumSize(panelDimension);
        lp.setMinimumSize(panelDimension);
        mainArea.setPreferredSize(areaDimension);
        mainArea.setMinimumSize(areaDimension);
        mainArea.setMaximumSize(areaDimension);
        mainArea.setEditable(false);
        
        userInput.setPreferredSize(inputDimension);
        userInput.setMinimumSize(inputDimension);
        userInput.setMaximumSize(inputDimension);
        userInput.setSize(inputDimension);

        welcomeText.setPreferredSize(welcomeDimension);

        welcomeText.setFont(new Font("MONOSPACED", Font.BOLD, 20));
        mainArea.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        welcomeText.setBorder(BorderFactory.createEmptyBorder());
        userInput.setBorder(BorderFactory.createLoweredBevelBorder());
        welcomeText.setLocation(640, 20);
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setEditable(false);
        

        
        mainPanel.add(welcomeText, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(userInput, BorderLayout.SOUTH);


        


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
     * Creates a Client object called {@code newClient} and a CourseCatalogue object called {@code cat}. We then call 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Client newClient = new Client("localhost", 9898);
        newClient.communicate();
    }
}
