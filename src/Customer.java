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
    JFrame cframe = new JFrame();
    public Customer(){
        cframe.setContentPane(customerPanel);
        cframe.pack();
        cframe.setVisible(true);
        sec=Administrator.sec;
        startTimer();
        timer.start();
        name= Administrator.adminNameData;
        priceS=Administrator.adminPriceData;
        imageS=Administrator.adminImageData;
        itemName.setText(name);
        price.setText(priceS);
        image.setIcon(imageS);
        ADDBIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bidName.getText().equals("")|| bidPrice.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Please Fill All Fields to add Record.");
                }else{
                    try {
                        String sql = "insert into bid"+"(Bidder_Name,Bid)"+"values (?,?)";
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","root","root");
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1,bidName.getText());
                        statement.setInt(2, Integer.parseInt(bidPrice.getText()));
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null,"ITEM ADDED SUCCESSFULLY");
                        bidName.setText("");
                        bidPrice.setText("");
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                    tableData();
                }
            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cframe.dispose();
            }
        });
    }
    public void startTimer(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sec==60) timerLabel.setText("AUCTION NOT STARTED!");
                else sec--;
                if(sec==-1){
                    timer.stop();
                    String sql = "select * from bid where bid =(select max(bid) from bid)";
                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","root","root");
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery(sql);
                        while (rs.next()){
                            bidder = rs.getString(1);
                            bid = rs.getInt(2);
                        }
                        String sql1 = "UPDATE auction " +
                                "SET BIDDER_NAME = '"+bidder+"'"+
                                ",SOLD_AT= "+bid+
                                " WHERE ITEM_NAME= '"+name+"'";
                        String sql2= "delete from bid";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                        preparedStatement.executeUpdate();
                        PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
                        preparedStatement1.executeUpdate();
                        JOptionPane.showMessageDialog(null,"ITEM:"+name+" SOLD TO "+bidder+" AT "+bid);
                    }catch (Exception e2){
                        JOptionPane.showMessageDialog(null,"some error");
                    }
                    tableData();
                }
                else if(sec>=0&&sec<10) timerLabel.setText("00:0"+sec);
                else if(sec>10&&sec<60) timerLabel.setText("00:"+ sec);
            }
        });
    }
    public void tableData(){

        try{
            String a= "Select* from bid";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","root","root");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(a);
            bidDetails.setModel(Objects.requireNonNull(buildTableModel(rs)));
        }catch (Exception ex1){
            JOptionPane.showMessageDialog(null,ex1.getMessage());
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
        throws SQLException{ ResultSetMetaData metaData=rs.getMetaData();
        //names of columns
        Vector<String> columnNames= new Vector<String>();
        int columncount = metaData.getColumnCount();
        for (int column =1; column <= columncount; column++){
            columnNames.add(metaData.getColumnName(column));

        }
        //data of the table
        Vector<Vector<Object>>data = new Vector<Vector<Object>>();
        while (rs.next()){
            Vector<Object>vector = new Vector<Object>();
            for (int columnIndex=1; columnIndex<=columncount;columnIndex++){
                vector.add(rs.getObject(columnIndex));{
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return  new DefaultTableModel(data,columnNames);
        }

        return null;
    }
}
