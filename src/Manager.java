import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Manager  {
    private JTextField nameData;
    private JTextField priceData;
    private JButton addItemButton;
    private JButton closeButton;
    private JButton addImageButton;
    private JTextField path;
    private JPanel ManagerPanel;
    private JLabel imageLabel;
    JFrame mframe = new JFrame();

    {
        mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mframe.setContentPane(ManagerPanel);
        mframe.pack();
        mframe.setLocationRelativeTo(null);
        mframe.setVisible(true);

    }



    public Manager() {
    addItemButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameData.getText().equals("")||path.getText().equals("")||priceData.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Please fill all the fields to add item!");
            }else{
                String sql = "insert into auction"+"(ITEM_NAME,IMAGE,PRICE)"+"values(?,?,?)";
                try{
                    File f = new File(path.getText());
                    InputStream inputstream = new FileInputStream(f);

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","root","root");
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1,nameData.getText());
                    statement.setBlob(2, inputstream);
                    statement.setString(3, priceData.getText());
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Details added successfully !");
                    nameData.setText("");
                    priceData.setText("");
                    imageLabel.setIcon(null);
                    path.setText("");

                    } catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
            }
        });

    addImageButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedImage = fileChooser.getSelectedFile();
                    path.setText(selectedImage.getAbsolutePath());
                    imageLabel.setIcon(resize(path.getText()));
                }
            }
        });
    closeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mframe.dispose();
        }
    });
}
    public ImageIcon resize(String path){
        ImageIcon myImg = new ImageIcon(path);
        Image image = myImg.getImage();
        Image newImage = image.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon finalImage = new ImageIcon(newImage);
        return finalImage;
    }

}
