package main;
import javax.swing.*;



public class project_i3 extends JFrame {

    public static void main(String[] args) {
        

        JFrame frame = new JFrame("Student management system");
        frame.setSize(1300, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {
        e.printStackTrace();
    }
        frame.add(new MainPanel());
        frame.setVisible(true);
        

    }
} 