// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;





// public class guiClient implements ActionListener {
    

//     private JFrame mainFrame;
//     private JTextField inputTextField;
//     private JLayeredPane lp = new JLayeredPane();
//     private String inputString;
//     private String input;
//     private JTextArea mainTextArea;

//     public guiClient() {
//         mainFrame = new JFrame();
//         JPanel mainPanel = new JPanel();
        
//         JTextField welcomeText = new JTextField("Welcome! Please choose from the" 
//                                                     + " following choices");
//         mainTextArea = new JTextArea();
//         JScrollPane scrollPane = new JScrollPane(mainTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
//         inputTextField = new JTextField();
//         Dimension panelDimension = new Dimension(1280,720);
//         Dimension textAreaDimension = new Dimension(1000,350);
//         Dimension minInputDimension = new Dimension(1000,70);
//         Dimension maxInputDimension = new Dimension(1000,150);

        
        


//         lp.setLayout(new BorderLayout());
//         lp.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

//         mainPanel.setLayout(new BorderLayout(0,2));
//         mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

//         lp.add(mainPanel);

//         mainTextArea.setEditable(false);
                

//         welcomeText.setFont(new Font("MONOSPACED", Font.BOLD, 20));
//         mainTextArea.setFont(new Font("MONOSPACED", Font.BOLD, 20));
//         welcomeText.setBorder(BorderFactory.createEmptyBorder());
//         inputTextField.setBorder(BorderFactory.createLoweredBevelBorder());
//         welcomeText.setHorizontalAlignment(JTextField.CENTER);
//         welcomeText.setEditable(false);
        

        
//         mainPanel.add(welcomeText, BorderLayout.NORTH);
//         mainPanel.add(scrollPane, BorderLayout.CENTER);
//         mainPanel.add(inputTextField, BorderLayout.SOUTH);
//         mainTextArea.setMaximumSize(textAreaDimension);
//         inputTextField.setPreferredSize(minInputDimension);


//         mainFrame.setLayeredPane(lp);
//         mainFrame.setPreferredSize(new Dimension(1280,720));
//         mainFrame.setMinimumSize(new Dimension(1280,720));
//         mainFrame.setMaximumSize(new Dimension(1280, 720));
//         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         mainFrame.setTitle("Main Window");
//         mainFrame.pack();
//         mainFrame.setLocationRelativeTo(null);
//         mainFrame.setVisible(true);


        
//     }

//     public void exit() {
//         mainFrame.dispose();
//     }

    

//     /**
//      * Return input of the textfield to Client.java to send 
//      * and communicate with the Server.
//      * @return input
//      */
//     public String getInput() {
        
//         /**
//          * This lambda expression call allows ENTER to be pressed when 
//          * inputting in the userInput textfield.
//          */
//         input = "";
//         while(input.equals("")) {
//             inputTextField.addActionListener(this);
//         }
        
//         return input;
//     } 

//     public void resetTextField() {
//         inputTextField.setText(null);
//     }



//     /**
//      * Populating the a String with the responses from Server.
//      * @param aResponse
//      */
//     public void fillArea(String aResponse) {
//         inputString = aResponse;
//         if(inputString != null)
//             mainTextArea.setText(getArea());
//     }

//     /**
//      * Used to populate the JTextArea with the string initialized
//      * in fillArea().
//      * @return
//      */
//     public String getArea() {
//         return inputString;
//     }


//     public void actionPerformed(ActionEvent e) {
//         // TODO Auto-generated method stub
//         input = inputTextField.getText();
                
//     }
    

//     }
