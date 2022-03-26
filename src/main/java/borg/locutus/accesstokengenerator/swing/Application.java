package borg.locutus.accesstokengenerator.swing;

import borg.locutus.accesstokengenerator.authentication.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {
    JTextField emailField;
    JPasswordField passwordField;

    public JLabel statusLabel;

    public Application() {
        JFrame mainFrame = new JFrame();

        JLabel emailLabel = new JLabel("E-Mail (Microsoft):");
        emailLabel.setBounds(20, 20, 150, 30);
        JLabel passwordLabel = new JLabel("Password (Microsoft):");
        passwordLabel.setBounds(20, 70, 150, 30);

        emailField = new JTextField("");
        emailField.setBounds(170,20, 200,30);
        passwordField = new JPasswordField("");
        passwordField.setBounds(170,70, 200,30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(20, 120, 100, 30);
        submitButton.addActionListener(new SubmitDataActionListener());

        JButton copyAccessTokenButton = new JButton("Copy Access Token");
        copyAccessTokenButton.setBounds(20, 170, 100, 30);
        copyAccessTokenButton.addActionListener(new CopyAccessTokenActionListener());

        JButton copyUUIDButton = new JButton("Copy UUID");
        copyUUIDButton.setBounds(140, 170, 100, 30);
        copyUUIDButton.addActionListener(new CopyUUIDActionListener());

        JButton copyUsernameButton = new JButton("Copy Minecraft Name");
        copyUsernameButton.setBounds(260, 170, 100, 30);
        copyUsernameButton.addActionListener(new CopyUsernameActionListener());

        statusLabel = new JLabel("");
        statusLabel.setBounds(20, 220, 560, 30);

        mainFrame.add(emailLabel);
        mainFrame.add(emailField);
        mainFrame.add(passwordLabel);
        mainFrame.add(passwordField);
        mainFrame.add(submitButton);
        mainFrame.add(copyAccessTokenButton);
        mainFrame.add(copyUsernameButton);
        mainFrame.add(copyUUIDButton);
        mainFrame.add(statusLabel);

        mainFrame.setSize(600,400);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    class SubmitDataActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            Authentication.authenticate(email, password);
        }
    }

    class CopyAccessTokenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringSelection selection = new StringSelection(Authentication.getAccessToken());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }

    class CopyUUIDActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringSelection selection = new StringSelection(Authentication.getUuid().toString());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }

    class CopyUsernameActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringSelection selection = new StringSelection(Authentication.getUsername());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}
