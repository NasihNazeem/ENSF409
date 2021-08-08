

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class guiClient implements ActionListener {
    
    private Dimension buttonDimension = new Dimension(3,10);
    private JButton OK;
    private JFrame mainFrame, browseFrame;
    private JTextField userInput;


    public guiClient() {
        mainFrame = new JFrame();
        JPanel mainPanel = new JPanel();
        JTextField welcomeText = new JTextField("Welcome to your course registration!");
        JTextField chooseText = new JTextField("Please choose from the following choices");
        
        //browse = new JButton("Browse Courses");

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 10));
        welcomeText.setBorder(BorderFactory.createEmptyBorder());
        chooseText.setBorder(BorderFactory.createEmptyBorder());
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        chooseText.setHorizontalAlignment(JTextField.CENTER);
        chooseText.setEditable(false);
        welcomeText.setEditable(false);

        //browse.setSize(buttonDimension);
        //browse.setFocusable(false);


        //browse.addActionListener(this);

        mainPanel.add(welcomeText, BorderLayout.NORTH);
        mainPanel.add(chooseText);

        //mainPanel.add(browse, BorderLayout.SOUTH);
        mainPanel.setPreferredSize(new Dimension(200,100));
        mainFrame.setContentPane(mainPanel);
        mainFrame.setSize(350,200);
        mainFrame.setMinimumSize(new Dimension(350,200));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Main Window");
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    public String getInput() {
        return userInput.getText();
    } 

    

    public void browseCourseOfferings() {
        
        browseFrame = new JFrame();
        JPanel browsePanel = new JPanel();
        JTextArea browseArea = new JTextArea(2, 10);
        JLabel browseLabel = new JLabel("List of Courses and Sections");
        JScrollPane scrollPane = new JScrollPane(browseArea);
        OK = new JButton("OK");
        browsePanel.setLayout(new BorderLayout());
        browsePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 10));
        browseArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        //mainFrame.remove(mainPanel);

        OK.addActionListener(this);
        browseLabel.setLabelFor(scrollPane);
        //browseArea.setText(db.printAllSectionsRA());
        browseArea.setEditable(false);
        browseFrame.setPreferredSize(new Dimension(750,500));
        browseFrame.setContentPane(browsePanel);
        browsePanel.add(scrollPane, BorderLayout.CENTER);
        browsePanel.add(OK, BorderLayout.SOUTH);
        browseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browseFrame.setTitle("Browse Window");
        browseFrame.pack();
        browseFrame.setLocationRelativeTo(null);
        browseFrame.setVisible(true);

        

        
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        //if(e.getSource() == browse){
        //    mainFrame.setVisible(false);
            //browseFrame.setVisible(true);
        //    browseCourseOfferings();
            //JOptionPane.showMessageDialog(mainFrame, "The backend has not been developed yet, please return later to view the courses.");
        //}
        if(e.getSource() == OK) {
            mainFrame.setVisible(true);
            browseFrame.setVisible(false);
        }
        if(e.getActionCommand() == "ENTER")
        {
            
        }
        
    }
}