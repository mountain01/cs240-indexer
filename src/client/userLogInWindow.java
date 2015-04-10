package client;

import server.Models.User;
import shared.Communicator.ClientCommunicator;
import shared.Params.ValidateUser_Params;
import shared.Results.ValidateUser_Result;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Matt on 4/8/2015.
 */
public class userLogInWindow extends JFrame {

    private JButton exitButton;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public userLogInWindow(){
        super("Login");
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(this.getSize());
        // set position of window
        setLocation(850,625);
        makeBox();
    }

    private void makeBox() {
        exitButton = new JButton("Exit");
        loginButton = new JButton("Login");

        this.setSize(375,150);

        JLabel username = new JLabel("Username: ");
        JLabel password = new JLabel("Password: ");
        usernameField = new JTextField(25);
        passwordField = new JPasswordField(25);

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(username, BorderLayout.WEST);
        usernamePanel.add(usernameField,BorderLayout.EAST);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(password,BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.EAST);

        JPanel inputPanel = new JPanel();
        inputPanel.add(usernamePanel,BorderLayout.NORTH);
        inputPanel.add(passwordPanel,BorderLayout.SOUTH);

        this.add(inputPanel,BorderLayout.CENTER);

        JPanel submitPanel = new JPanel();
        loginButton.addActionListener(buttonClicked);
        exitButton.addActionListener(buttonClicked);
        submitPanel.add(loginButton,BorderLayout.WEST);
        submitPanel.add(exitButton,BorderLayout.EAST);

        this.add(submitPanel,BorderLayout.SOUTH);
    }


    private ActionListener buttonClicked = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == loginButton){
                ValidateUser_Params vp = new ValidateUser_Params(usernameField.getText(),new String(passwordField.getPassword()));
                ValidateUser_Result vr = ClientCommunicator.getInstance().validateUser(vp);
                if(vr.isValidUser()){
                    displayInfo(vr.getUser());
                }else{
                    wrongInfo();
                }
            } else if (e.getSource() == exitButton){
                System.exit(0);
            }
        }
    };

    private void wrongInfo() {
        JOptionPane.showMessageDialog(this,"Invalid Username/Password","Login Failed",JOptionPane.ERROR_MESSAGE);
    }

    private void displayInfo(User user) {
        JOptionPane.showMessageDialog(this,user.getWelcomeMessage(),"Welcome to Indexer",JOptionPane.PLAIN_MESSAGE);
    }
}
