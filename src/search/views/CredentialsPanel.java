package search.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matt on 3/25/2015.
 */
public class CredentialsPanel extends JPanel {
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton executeButton;
    public JTextField hostField;
    public JTextField portField;

    public CredentialsPanel(){
        super();
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        add(Box.createRigidArea(new Dimension(10,0)));
        add(new JLabel("HOST: "));
        add(Box.createRigidArea(new Dimension(5,0)));

        hostField = new JTextField(10);
        hostField.setMinimumSize(hostField.getPreferredSize());
        hostField.setMaximumSize(hostField.getPreferredSize());
        add(hostField);
        add(Box.createRigidArea(new Dimension(15,0)));

        add(Box.createRigidArea(new Dimension(10,0)));
        add(new JLabel("PORT: "));
        add(Box.createRigidArea(new Dimension(5,0)));

        portField = new JTextField(10);
        portField.setMinimumSize(portField.getPreferredSize());
        portField.setMaximumSize(portField.getPreferredSize());
        add(portField);
        add(Box.createRigidArea(new Dimension(15,0)));

        add(Box.createRigidArea(new Dimension(10,0)));
        add(new JLabel("USERNAME: "));
        add(Box.createRigidArea(new Dimension(5,0)));

        usernameField = new JTextField(10);
        usernameField.setMinimumSize(usernameField.getPreferredSize());
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        add(usernameField);
        add(Box.createRigidArea(new Dimension(15,0)));

        add(Box.createRigidArea(new Dimension(10,0)));
        add(new JLabel("PASSWORD: "));
        add(Box.createRigidArea(new Dimension(5,0)));

        passwordField = new JPasswordField(10);
        passwordField.setMinimumSize(passwordField.getPreferredSize());
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        add(passwordField);
        add(Box.createRigidArea(new Dimension(15,0)));

        executeButton = new JButton("Log In");
        add(executeButton);
        add(Box.createRigidArea(new Dimension(10,0)));

        this.setMaximumSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public String getPasswordField() {
        return new String(passwordField.getPassword());
    }

    public String getHostField() {
        return hostField.getText();
    }

    public void setHostField(String hostField) {
        this.hostField.setText(hostField);
    }

    public String getPortField() {
        return portField.getText();
    }

    public void setPortField(String portField) {
        this.portField.setText(portField);
    }
}
