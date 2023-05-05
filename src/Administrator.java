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

public Administrator() {
    startAuctionButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            startTimer();
            timer.start();

        }
    });
    closeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            aframe.dispose();

        }
    });

}

    public void startTimer(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sec--;
                if (sec==-1){
                    timer.stop();
                    tableData();
                }
                else if(sec>=0&&sec<10)timerLabel.setText("00:0"+sec);
                else timerLabel.setText("00:"+sec);
            }
        });
table1.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        DefaultTableModel dm =(DefaultTableModel) table1.getModel();
        int selectedRow = table1.getSelectedRow();
        adminNameData=dm.getValueAt(selectedRow,0).toString();
        adminPriceData=dm.getValueAt(selectedRow,2).toString();
        byte[] img = (byte[]) dm.getValueAt(selectedRow,1);
        ImageIcon imageIcon = new ImageIcon(img);
        Image im = imageIcon.getImage();
        Image newimg = im.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon finalPic = new ImageIcon(newimg);
        adminImageData = finalPic;




    }
});
        }
    public void tableData() {
        try{
            String a= "Select* from auction";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","root","root");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(a);
            table1.setModel(Customer.buildTableModel(rs));
        }catch (Exception ex1){
            JOptionPane.showMessageDialog(null,ex1.getMessage());
        }
    }
}
