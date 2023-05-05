import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class Customer extends Administrator {
    private JTextField bidPrice;
    private JTextField bidName;
    private JButton ADDBIDButton;
    private JButton close;
    private JTable bidDetails;
    private JPanel customerPanel;
    private JLabel itemName;
    private JLabel price;
    private JLabel image;
    private JLabel timerLabel;
    private String priceS="", name="",bidder="";
    private  ImageIcon imageS;
    private int bid;
    Timer timer;
    private static int sec;
