import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public final class JFrame1 extends JFrame{
    private Connection connection;

    private JPanel mainPanel;
    private JPanel midPanel; 
    private CardLayout cardLayout;
    private JMenuBar menuBar;
    private DefaultTableModel tableModel;


    // Other Panels
    private Enroll enrollPanel;
    private view_sts viewPanel;
    private StudentUpdate updatePanel;


    // Home
    private JMenu homeMenu;
    private JMenuItem newMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem logOutMenuItem;

    //Student
    private JMenu studentMenu;
    private JMenuItem viewStudent;
    private JMenuItem addStudent;
    private JMenuItem updateStudent;
    private JMenuItem deleteStudent;

    //course
    private JMenu courseMenu;
    private JMenuItem viewCourse;
    private JMenuItem updateCourse;
    private JMenuItem searchCourse;
    private JMenuItem deleteCourse;

    //setting
    private JMenu settingMenu;
    private JMenuItem darkMenu;
    private JMenuItem lightMenu;
    private JMenuItem exitMenu;


    public JFrame1() {
        initializeDatabaseConnection();
        initialize();
    }

    private void initializeDatabaseConnection() {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }
 
    public void initialize() {
        setTitle("Admin");
        setSize(1080, 1080);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);


        mainPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        mainPanel = new JPanel(new BorderLayout());
        cardLayout = new CardLayout(10,10);
        midPanel = new JPanel(cardLayout); // Initialize midPanel with CardLayout

       // mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        mainPanel.setBackground(Color.blue);

        // Middle panel (Content Area)
        midPanel.setBackground(Color.black);
        midPanel.setPreferredSize(new Dimension(600, 600));
        mainPanel.add(midPanel, BorderLayout.SOUTH);

        menuBar = new JMenuBar();
        menuBar.setBackground(Color.green);
        menuBar.setLayout(new GridLayout(1, 5));


        
       // JPanel viewSts_panel = createViewStudentPanel();

        enrollPanel = new Enroll(connection);
        viewPanel = new view_sts(connection);

        updatePanel = new StudentUpdate(connection);
        // JPanel addSts_panel = EnrollmentPanel();
        // JPanel updateSts_panel = UpdateStudentPanel();
        // JPanel deletePanel = DeleteStudentPanel();

        midPanel.add(viewPanel.createViewStudentPanel(), "View");
        midPanel.add(enrollPanel.getEnrollPanel(), "Enroll");
        midPanel.add(updatePanel.createUpdateStudentPanel(), "Update");
        // midPanel.add(deletePanel, "Delete");



        

        // Home
        homeMenu =  new JMenu("Home");
        ImageIcon homeIcon = new ImageIcon("D:/Assignments/I3/Testing/JDBC/Admin/src/Icon/home.png");
        homeMenu.setIcon(homeIcon);
        newMenuItem = new JMenuItem("New");
        saveMenuItem = new JMenuItem("Save");
        logOutMenuItem = new JMenuItem("Log out");

        // Student
        studentMenu =  new JMenu("Student");
        ImageIcon studentIcon = new ImageIcon("D:/Assignments/I3/Testing/JDBC/Admin/src/Icon/student.png");
        studentMenu.setIcon(studentIcon);
        viewStudent = new JMenuItem("View");
        viewStudent.addActionListener(e -> cardLayout.show(midPanel, "View"));
        
        addStudent = new JMenuItem("Enroll");
        addStudent.addActionListener(e -> cardLayout.show(midPanel, "Enroll"));
        updateStudent = new JMenuItem("Update");
        updateStudent.addActionListener(e -> cardLayout.show(midPanel, "Update"));
        deleteStudent = new JMenuItem("Delete");
        deleteStudent.addActionListener(e -> cardLayout.show(midPanel, "Delete"));



        // Course
        courseMenu =  new JMenu("Course");
        ImageIcon courseIcon = new ImageIcon("D:/Assignments/I3/Testing/JDBC/Admin/src/Icon/course.png");
        courseMenu.setIcon(courseIcon);
        viewCourse = new JMenuItem("View");
        updateCourse = new JMenuItem("Update");
        searchCourse = new JMenuItem("Search");
        deleteCourse = new JMenuItem("Delete");

        // Setting
        settingMenu =  new JMenu("Setting");
        ImageIcon settingIcon = new ImageIcon("D:/Assignments/I3/Testing/JDBC/Admin/src/Icon/course.png");
        settingMenu.setIcon(settingIcon);
        darkMenu = new JMenuItem("Dark mode");
        lightMenu = new JMenuItem("Light mode");
        exitMenu = new JMenuItem("Exit");


        JMenuItem[] item = {homeMenu, studentMenu, newMenuItem, courseMenu, saveMenuItem, logOutMenuItem, viewStudent, addStudent, updateStudent, deleteStudent,viewCourse, updateCourse, searchCourse, deleteCourse};
        for (JMenuItem item1 : item) {
            item1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        ImageIcon[] icons = {homeIcon, studentIcon, courseIcon, settingIcon};
        for (ImageIcon icon : icons) {
            icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        }


        homeMenu.add(newMenuItem);
        homeMenu.add(saveMenuItem); 
        homeMenu.add(logOutMenuItem);

        studentMenu.add(viewStudent);
        studentMenu.add(addStudent);
        studentMenu.add(updateStudent); 
        studentMenu.add(deleteStudent);

        courseMenu.add(viewCourse);
        courseMenu.add(updateCourse);
        courseMenu.add(searchCourse);
        courseMenu.add(deleteCourse);

        settingMenu.add(darkMenu);
        settingMenu.add(lightMenu);
        settingMenu.add(exitMenu);

        menuBar.add(homeMenu);
        menuBar.add(studentMenu);
        menuBar.add(courseMenu);
        menuBar.add(settingMenu);
        add(menuBar, BorderLayout.SOUTH);







        // Top panel
        JPanel top_panel = new JPanel();
        top_panel.setBackground(Color.white);
        top_panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));


        // Profile panel
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(Color.green);
        //profilePanel.setLayout(new FlowLayout(FlowLayout.LEAD, 10, 10));

        // Profile picture
        ImageIcon profileIcon = new ImageIcon("D:/Assignments/I3/Testing/JDBC/Admin/src/Icon/profile.png");
        profileIcon.setImage(profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        JLabel profileLabel = new JLabel(profileIcon);

        // User name label
        JLabel AdminLabel = new JLabel("Admin");
        AdminLabel.setFont(new Font("Arial", Font.BOLD, 20));
        AdminLabel.setForeground(Color.white);
        JLabel userNameLabel = new JLabel("Sarith Seyla");
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userNameLabel.setForeground(Color.white);

        profilePanel.add(profileLabel);
        profilePanel.add(AdminLabel,BorderLayout.NORTH);
        profilePanel.add(userNameLabel, BorderLayout.SOUTH);

        top_panel.add(profilePanel);



        JLabel label1 = new JLabel("Student Management System");
        label1.setFont(new Font("Arial", Font.BOLD, 35));
        label1.setForeground(Color.blue);

        ImageIcon labelIcon = new ImageIcon("D:/Assignments/I3/Testing/JDBC/Admin/src/Icon/Logo.png");
        labelIcon.setImage(labelIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        label1.setIcon(labelIcon);
        label1.setIconTextGap(10);
        top_panel.add(label1);


        // Change font size for Menu and MeniItem
        Font f = new Font("Times New Romand", Font.PLAIN, 20);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        UIManager.put("CheckBoxMenuItem.font", f);
        UIManager.put("RadioButtonMenuItem.font", f);


        


        

        add(top_panel, BorderLayout.NORTH);
        add(mainPanel,BorderLayout.CENTER);
        setVisible(true);
 

    }

       // Method to show the view_sts content
    // private JPanel ViewStudentPanel() {
    //     //midPanel.removeAll(); // Clear the midPanel
    //     midPanel.add(new DisplayStudent(), BorderLayout.CENTER); // Placeholder for the view_sts content
    //     //midPanel.revalidate(); // Refresh the panel
    //     //midPanel.repaint(); // Repaint the panel
    
    //     return midPanel;
    // }


    // private class LogOut{
    //     JButton Logout = new JButton("Log out");
    //     Logout.setFont(new Font("Arial", Font.BOLD, 13));
    //     Logout.setPreferredSize(new Dimension(160, 30));
    //     Logout.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //             int response = JOptionPane.showConfirmDialog(null, "Do you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
                
    //             if (response == JOptionPane.YES_OPTION) {
    //                 CardLayout c4 = (CardLayout) mainPanel.getLayout();
    //                 c4.show(mainPanel, "loginScreen");
    //             } else {
    //                 System.out.println("Stayed logged in");
    //             }
    //         }
    //     });
    // }


    
    // public void reloadData() {
    //     System.out.println("ReloadData called");
    //     try {
    //         Statement stmt = connection.createStatement();
    //         ResultSet rs = stmt.executeQuery("SELECT * FROM student_db.students");
    //         tableModel.setRowCount(0); // Clear existing rows
    //         while (rs.next()) {
    //             tableModel.addRow(new Object[]{
    //                 rs.getInt("id"),
    //                 rs.getString("first_name"),
    //                 rs.getString("last_name"),
    //                 rs.getString("gender"),
    //                 rs.getDate("dob"),
    //                 rs.getString("email"),
    //                 rs.getString("address"),
    //                 rs.getString("phone_number")
    //             });
    //         }
    //     } catch (SQLException e) {
    //         JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    //     }
    // }
    
    
    

    // private JPanel createViewStudentPanel() {
    //     return new view_sts(connection); // Pass the connection to the view_sts panel
    // }
    
    // private JPanel createViewStudentPanel() {
    //     JPanel panel = new JPanel();
    //     panel.add(new JLabel("View Student Panel"));
    //     return panel;
    // }
 

}

