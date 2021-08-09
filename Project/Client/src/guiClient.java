import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class guiClient  {
    

    private JFrame mainFrame;
    private JTextField userInput;
    private JLayeredPane lp = new JLayeredPane();
    private String inputArea;
    private String input;
    private Dimension panelDimension = new Dimension(300,200);
    private Dimension areaDimension = new Dimension(100,50);

    public guiClient() {
        mainFrame = new JFrame();
        JPanel mainPanel = new JPanel();
        JTextField welcomeText = new JTextField("Welcome! Please choose from the following choices!");
        JTextArea mainArea = new JTextArea(1,5);
        JScrollPane scrollPane = new JScrollPane(mainArea);
        userInput = new JTextField("Input goes here.");

        lp.setLayout(new BorderLayout());
        lp.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        lp.setPreferredSize(panelDimension);
        lp.setMaximumSize(panelDimension);
        lp.setMinimumSize(panelDimension);
        mainArea.setPreferredSize(areaDimension);
        mainArea.setMinimumSize(areaDimension);
        mainArea.setMaximumSize(areaDimension);
        

        welcomeText.setFont(new Font("Sans Serif", Font.BOLD, 20));
        welcomeText.setBorder(BorderFactory.createEmptyBorder());
        userInput.setBorder(BorderFactory.createLoweredBevelBorder());

        welcomeText.setHorizontalAlignment(JTextField.CENTER);

        welcomeText.setEditable(false);
        
        

        lp.add(mainPanel);
        mainPanel.add(welcomeText, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(userInput, BorderLayout.SOUTH);
        

        /**
         * This method call allows ENTER to be pressed when inputting in the userInput textfield.
         */
        userInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                browseMenu();
            }
        });


        
        mainFrame.setLayeredPane(lp);
        mainFrame.setMinimumSize(new Dimension(700,350));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Main Window");
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);


        
    }

    /**
     * Return input of the textfield to Client.java to send and communicate with the Server.
     * @return 
     */
    public String getInput() {
        return userInput.getText();
    } 

    /**
     * Switching between the multiple panels (eg. Menu, Welcome Screen, Browse, etc..)
     * @param panel
     */
    public void switchPanels(JPanel panel) {
        lp.removeAll();
        lp.add(panel);
        lp.repaint();
        lp.revalidate();
    }

    /**
     * Populating the a String with the responses from Server.
     * @param aString
     */
    public void fillArea(String aString) {
        inputArea = aString;
    }

    /**
     * Used to populate the JTextArea with the string initialized in fillArea().
     * @return
     */
    public String getArea() {
        return inputArea;
    }

    
    /**
     * Browse the Menu Frame.
     */
    public void browseMenu() {
        
        JFrame browseFrame = new JFrame();
        JPanel browsePanel = new JPanel();
        JTextArea browseArea = new JTextArea(1,5);
        JTextField menuText = new JTextField("Menu");
        // JLabel browseLabel = new JLabel("List of Courses and Sections");
        JScrollPane scrollPane = new JScrollPane(browseArea);
        switchPanels(browsePanel);
        userInput = new JTextField();
        
        
        browsePanel.setLayout(new BorderLayout());
        browsePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        browseArea.setBorder(BorderFactory.createLoweredBevelBorder());
        browseArea.setPreferredSize(areaDimension);
        browseArea.setMinimumSize(areaDimension);
        browseArea.setMaximumSize(areaDimension);

        menuText.setBorder(BorderFactory.createEmptyBorder());
        menuText.setFont(new Font("Sans Serif", Font.BOLD, 20));
        menuText.setHorizontalAlignment(JTextField.CENTER);
        menuText.setEditable(false);



        browseArea.setEditable(false);
        browsePanel.add(menuText, BorderLayout.NORTH);
        browsePanel.add(scrollPane, BorderLayout.CENTER);
        browsePanel.add(userInput, BorderLayout.SOUTH);

        

        browseFrame.setLayeredPane(lp);
        browseFrame.setMinimumSize(new Dimension(700,350));
        browseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browseFrame.setTitle("Main Window");
        browseFrame.pack();
        browseFrame.setLocationRelativeTo(null);
        browseFrame.setVisible(true);
        

        
    }

    

    }
