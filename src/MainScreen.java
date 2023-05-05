import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen {
    private JPanel AuctionPanel;
    private JButton User;
    private JButton Admin;
    private JButton ItemManagement;
    JFrame jframe = new JFrame();{
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setContentPane(AuctionPanel);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        User.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Customer();
            }
        });
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
