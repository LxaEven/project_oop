package login;

import Adminsrc.*;
import main.DBConnect;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class AdminLogin extends JPanel {
    private static String identifier;
    private static String password;


    public static JTextField emailField;
        public JPasswordField passwordField;
        private MainPanel mainPanel;
    
        public AdminLogin(MainPanel mainPanel) {
            this.mainPanel = mainPanel;
            setLayout(new BorderLayout(20, 20));
            setBackground(new Color(173, 216, 230)); // Light Blue background
    
            ImageIcon imageIcon = new ImageIcon("Icon/Logo.png");
            Image resizedImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            JLabel logoLabel = new JLabel(resizedIcon);
    
    
            // Create the input panel for email and password
             // Add padding
    
            emailField = new JTextField();
            emailField.setPreferredSize(new Dimension(500, 60));
            emailField.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.GRAY), "Email", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 20)));
            emailField.setFont(new Font("Arial", Font.PLAIN, 20));
    
            passwordField = new JPasswordField();
            passwordField.setPreferredSize(new Dimension(500, 60));
            passwordField.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.GRAY), "Password", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 20)));
            passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
    
            // Create a checkbox to show/hide password
            JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
            showPasswordCheckBox.setBackground(new Color(173, 216, 230));
            showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
            showPasswordCheckBox.addActionListener(e -> {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('•');
                }
            });
    
    
    
            JButton loginButton = new JButton("Log In");
            loginButton.setPreferredSize(new Dimension(200, 50));
            loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
            loginButton.setBackground(new Color(144, 238, 144)); // Light Green
            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(200, 50));
            backButton.setFont(new Font("Arial", Font.PLAIN, 18));
            backButton.setBackground(new Color(255, 102, 102));
    
            GridBagConstraints gbc = new GridBagConstraints();
            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(new Color(173, 216, 230));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            gbc.insets= new Insets(10,10,10,10);
                JPanel buttonPanel = new JPanel(new GridBagLayout());
                buttonPanel.setBackground(new Color(173, 216, 230));
                gbc.gridx = 0;
                gbc.gridy = 0;
                buttonPanel.add(loginButton, gbc);
                gbc.gridx++;
                buttonPanel.add(backButton, gbc);
            gbc.gridx = 0;
            gbc.gridy = 0;
            inputPanel.add(emailField, gbc);
            gbc.gridy++;
            inputPanel.add(passwordField, gbc);
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.WEST;
            inputPanel.add(showPasswordCheckBox, gbc);
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.CENTER;
            inputPanel.add(buttonPanel, gbc);
    
    
            JPanel LogoPanel = new JPanel(new GridBagLayout());
            LogoPanel.setBackground(Color.CYAN);
            LogoPanel.setPreferredSize(new Dimension(200, 0));
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            gbc.gridy = 0;
            LogoPanel.add(logoLabel, gbc);
    
    
            add(inputPanel, BorderLayout.CENTER);
            add(LogoPanel, BorderLayout.WEST);
            loginButton.addActionListener(new LoginButtonListener());
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.showScreen("loginScreen");
                }
            });
        }
    
        private class LoginButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifier = emailField.getText().trim();
                password = new String(passwordField.getPassword()).trim();
                MainPanel.loginUserIdentifier = identifier;
                MainPanel.loginUserPassword = password;
    
                if (identifier.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminLogin.this, "Please enter both Email and password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                if (authenticateUser(identifier, password)) {
                    JOptionPane.showMessageDialog(AdminLogin.this, "Login successfully");
                    JFrame1 frame01;
                    try {
                        frame01 = new JFrame1();
                        frame01.setVisible(true);
                        mainPanel.setVisible(false);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AdminLogin.this, "An error occurred while opening the frame.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(AdminLogin.this, "Invalid Email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
    
            }
        }
    
        private boolean authenticateUser(String identifier, String password) {
            String query = "SELECT * FROM admin_account WHERE admin_email = ? AND admin_password = ?";
            
            try (Connection conn = DBConnect.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, identifier);
                stmt.setString(2, password);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    
        // Method to show the profile name
        public static String showProfileName(JFrame frame){
            String adminName = "";
            String query = "SELECT admin_name FROM admin_account WHERE admin_email = ?";
    
            try (Connection conn = DBConnect.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, emailField.getText());
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                adminName = rs.getString("admin_name");
              
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return adminName;
        
    }

    // Method to show the admin information
    public static String showAdminInfo(JFrame frame){
        String adminEmail =  emailField.getText();
        return adminEmail;
    }

    // Method to show the admin Image
    public static String showAdminImage(JFrame frame){
        ImageIcon profileIcon = new ImageIcon("Icon/profile.png");
        profileIcon.setImage(profileIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        String adminImage = "";
        String query = "SELECT profile_image FROM admin_account WHERE admin_email = ?";

        try (Connection conn = DBConnect.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, emailField.getText());
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                adminImage = rs.getString("profile_image");
            }
            if (adminImage == null || adminImage.isEmpty()) {
                adminImage = profileIcon.toString();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return adminImage;
        
    }


}
