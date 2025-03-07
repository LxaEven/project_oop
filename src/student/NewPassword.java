package student;

import java.awt.*;
import java.awt.event.*;
import  java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import login.*;

public class NewPassword extends JPanel {
    private JPanel Panel;
    private student studentFrame;
    GridBagConstraints gbc = new GridBagConstraints();
    public NewPassword(JPanel Panel, student studentFrame) {
        this.studentFrame = studentFrame;
        setLayout(new BorderLayout());
        newPassword();
    }

    void newPassword(){
        JPasswordField oldPasswordField = new JPasswordField();
        oldPasswordField.setBorder(BorderFactory.createTitledBorder(
             BorderFactory.createLineBorder(Color.GRAY), "Old Password",
             TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 20)));
        oldPasswordField.setPreferredSize(new Dimension(500, 60));
        oldPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBorder(BorderFactory.createTitledBorder(
             BorderFactory.createLineBorder(Color.GRAY), "New Password",
             TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 20)));
        newPasswordField.setPreferredSize(new Dimension(500, 60));
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder(
             BorderFactory.createLineBorder(Color.GRAY), "Confirm Password",
             TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 20)));
        confirmPasswordField.setPreferredSize(new Dimension(500, 60));
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));

        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(new Color(173, 216, 230));
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                oldPasswordField.setEchoChar((char) 0);
                newPasswordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                oldPasswordField.setEchoChar('•');
                newPasswordField.setEchoChar('•');
                confirmPasswordField.setEchoChar('•');
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(200, 40));
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setBackground(new Color(144, 238, 144));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                if(oldPassword.isEmpty()){
                    JOptionPane.showMessageDialog(Panel, "Please fill all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }if(newPassword.isEmpty()){
                    JOptionPane.showMessageDialog(Panel, "Please fill all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }if(confirmPassword.isEmpty()){
                    JOptionPane.showMessageDialog(Panel, "Please fill all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(Panel, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(newPassword.length() < 8){
                    JOptionPane.showMessageDialog(Panel, "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(newPassword.equals(oldPassword)){
                    JOptionPane.showMessageDialog(Panel, "New password cannot be same as old password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn = main.DBConnect.getConnection();
                     PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM student WHERE (student_email = ? OR phone_number = ?) AND student_password = ?");
                     PreparedStatement updateStmt = conn.prepareStatement("UPDATE student SET student_password = ? WHERE (student_email = ? OR phone_number = ?)")) {

                    String identifier = MainPanel.loginUserIdentifier;
                    checkStmt.setString(1, identifier);
                    checkStmt.setString(2, identifier);
                    checkStmt.setString(3, oldPassword);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        updateStmt.setString(1, newPassword);
                        updateStmt.setString(2, identifier);
                        updateStmt.setString(3, identifier);
                        updateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(Panel, "Password changed successfully.\nPlease login again.", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                        MainPanel mainPanel = new MainPanel();
                        mainPanel.showScreen("Login");
                        mainPanel.setVisible(true);
                        studentFrame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(Panel, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Panel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel formPanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(oldPasswordField, gbc);
        gbc.gridy++;
        formPanel.add(newPasswordField, gbc);
        gbc.gridy++;
        formPanel.add(confirmPasswordField, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(showPasswordCheckBox, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);
        formPanel.setBackground(new Color(173, 216, 230));
        add(formPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
