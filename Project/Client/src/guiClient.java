import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;




public class guiClient  {
    

    private JFrame mainFrame;
    private JTextField userInput;
    private JLayeredPane lp = new JLayeredPane();
    private String inputArea;
    private String input = "";
    private JTextArea mainArea;

    public guiClient() {
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
     * Return input of the textfield to Client.java to send 
     * and communicate with the Server.
     * @return input
     */
    public String getInput() {
        input = "";
        while(input.equals("")){
            
            /**
             * This lambda expression call allows ENTER to be pressed when 
             * inputting in the userInput textfield.
             */
        
            userInput.addActionListener(new AbstractAction() {

                public void actionPerformed(ActionEvent e) {
                    
                    input = userInput.getText();
                    userInput.setText("");
                }
                    
            });
        }
        return input;
    } 



    /**
     * Populating the a String with the responses from Server.
     * @param aString
     */
    public void fillArea(String aString) {
        inputArea = aString;
        if(inputArea != null)
            mainArea.setText(getArea());
    }

    /**
     * Used to populate the JTextArea with the string initialized
     * in fillArea().
     * @return
     */
    public String getArea() {
        return inputArea;
    }
    

    }
