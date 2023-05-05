import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Administrator extends Manager {
    private JButton startAuctionButton;
    private JButton closeButton;
    private JPanel AdministratorPanel;
    JLabel timerLabel;
    private JTable table1;
    public static String adminNameData="",adminPriceData="";
    public static ImageIcon adminImageData;
    JFrame aframe = new JFrame();
    Timer timer;
    public static int sec=60;
    {
        aframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aframe.setContentPane(AdministratorPanel);
        aframe.pack();
        aframe.setLocationRelativeTo(null);
        aframe.setVisible(true);
        tableData();
    }




