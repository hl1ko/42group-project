import java.awt.*;
import javax.swing.*;

public class MessageFrame extends JFrame{
    public MessageFrame(String title, String message, int time) {
        super(title);

        setVisible(true); // display logout frame
        setSize(800, 600); // set frame size
        setLocationRelativeTo(null);

        JLabel msg = new JLabel(message);
        msg.setFont(new Font("Verdana", Font.PLAIN, 25));
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        add(msg, BorderLayout.CENTER);

        try {
            Thread.sleep(time);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispose();
    }
    
}
