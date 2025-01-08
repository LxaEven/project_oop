import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import com.formdev.flatlaf.*;
import java.awt.*;
import java.awt.event.*;

public class ViewScore extends JPanel {
    public ViewScore(JPanel mainPanel) {

        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "Web#11*03";

        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        

        JLabel tableLabel = new JLabel("Student Score", SwingConstants.CENTER);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 15));

        DefaultTableModel tableModel = new DefaultTableModel() {
            
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        JTable table = new JTable(tableModel);

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {

            
            tableModel.addColumn("student_id");
            tableModel.addColumn("student_firstname");
            tableModel.addColumn("student_lastname");
            tableModel.addColumn("phone_number");
            tableModel.addColumn("student_birth");
            tableModel.addColumn("student_score");

            
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String studentfirstName = rs.getString("student_firstname");
                String studentlastName = rs.getString("student_lastname");
                String studentPhoneNumber = rs.getString("phone_number");
                String studentBirth = rs.getString("student_birth");
                float studentScore = rs.getFloat("student_score");
                tableModel.addRow(new Object[]{id, studentfirstName, studentlastName, studentPhoneNumber, studentBirth, studentScore});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "Error: " + e.getMessage());
        }
        
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(20);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        

        
        JScrollPane scrollPanel = new JScrollPane(table);
        add(scrollPanel, BorderLayout.CENTER);


        JButton ViewProfile = new JButton("View Profile");
        ViewProfile.setFont(new Font("Arial", Font.BOLD, 13));
        ViewProfile.setPreferredSize(new Dimension(160, 30));
        ViewProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c4 = (CardLayout) mainPanel.getLayout();
                c4.show(mainPanel, "ViewProfile");
            }
        });


        JButton ViewScore = new JButton("View Score");
        ViewScore.setFont(new Font("Arial", Font.BOLD, 13));
        ViewScore.setPreferredSize(new Dimension(160, 30));
        ViewScore.setBackground(Color.GRAY);
        ViewScore.setForeground(Color.WHITE);
        ViewScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c4 = (CardLayout) mainPanel.getLayout();
                c4.show(mainPanel, "ViewScore");
            }
        });


        JButton ViewCourse = new JButton("View Course");
        ViewCourse.setFont(new Font("Arial", Font.BOLD, 13));
        ViewCourse.setPreferredSize(new Dimension(160, 30));
        ViewCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c4 = (CardLayout) mainPanel.getLayout();
                c4.show(mainPanel, "ViewCourse");
            }
        });


        JButton ChangePassword = new JButton("Change Password");
        ChangePassword.setFont(new Font("Arial", Font.BOLD, 13));
        ChangePassword.setPreferredSize(new Dimension(160, 30));
        ChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c4 = (CardLayout) mainPanel.getLayout();
                c4.show(mainPanel, "ChangePassword");
            }
        });


        JButton Logout = new JButton("Logout");
        Logout.setFont(new Font("Arial", Font.BOLD, 13));
        Logout.setPreferredSize(new Dimension(160, 30));
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                    null, 
                    "Do you want to log out?", 
                    "Log Out", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (response == JOptionPane.YES_OPTION) {
                    CardLayout c4 = (CardLayout) mainPanel.getLayout();
                    c4.show(mainPanel, "loginScreen");
                } else {
                    System.out.println("Stayed logged in");
                }
            }
        });


        JButton CloseProgram = new JButton("Exit");
        CloseProgram.setFont(new Font("Arial", Font.BOLD, 13));
        CloseProgram.setPreferredSize(new Dimension(160, 30));
        CloseProgram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                    null, 
                    "Do you want to Exit the program?", 
                    "Exit the program", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (response == JOptionPane.NO_OPTION) {
                    CardLayout c4 = (CardLayout) mainPanel.getLayout();
                    c4.show(mainPanel, "ViewScore");
                } else {
                    System.out.println("Program ended");
                    System.exit(0);
                }
            }
        });

         JButton darkMode = new JButton("Dark Mode");
        darkMode.setFont(new Font("Arial", Font.BOLD, 13));
        darkMode.setPreferredSize(new Dimension(160, 30));
        darkMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel( new FlatDarkLaf() );
                    SwingUtilities.updateComponentTreeUI(mainPanel);
                } catch( Exception ex ) {
                    System.err.println( "Failed to initialize LaF" );
                }
            }
        });

        JButton lightMode = new JButton("Light Mode");
        lightMode.setFont(new Font("Arial", Font.BOLD, 13));
        lightMode.setPreferredSize(new Dimension(160, 30));
        lightMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel( new FlatLightLaf() );
                    SwingUtilities.updateComponentTreeUI(mainPanel);
                } catch( Exception ex ) {
                    System.err.println( "Failed to initialize LaF" );
                }
            }
        });



        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(titleLabel, gbc);
        gbc.gridy++;
        buttonPanel.add(ViewProfile, gbc);
        gbc.gridy++;
        buttonPanel.add(ViewScore, gbc);
        gbc.gridy++;
        buttonPanel.add(ViewCourse, gbc);
        gbc.gridy++;
        buttonPanel.add(ChangePassword, gbc);
        gbc.gridy++;
        buttonPanel.add(Logout, gbc);
        gbc.gridy++;
        buttonPanel.add(CloseProgram, gbc);
        buttonPanel.setBackground(Color.GRAY);
        add(buttonPanel, BorderLayout.WEST);

        JPanel ModePanel = new JPanel(new GridBagLayout());
        ModePanel.setPreferredSize(new Dimension(180, 50));
        gbc.gridy = 0;
        gbc.gridx = 0;
        ModePanel.add(darkMode, gbc);
        gbc.gridx++;
        ModePanel.add(lightMode, gbc);
        ModePanel.setBackground(Color.GRAY);
        add(ModePanel, BorderLayout.NORTH);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.GRAY);
        eastPanel.setPreferredSize(new Dimension(50, 300));
        add(eastPanel, BorderLayout.EAST);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.GRAY);
        southPanel.setPreferredSize(new Dimension(200, 50));
        add(southPanel, BorderLayout.SOUTH);
    
    }
}
