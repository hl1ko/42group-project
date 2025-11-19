import java.awt.*;
import javax.swing.*;

<<<<<<< HEAD
public class MessageFrame extends JFrame {
    public MessageFrame(String title, String message, int displaySeconds) {
=======
public class MessageFrame extends JFrame{
    public MessageFrame(String title, String message, int time) {
>>>>>>> 55e2d2a1ef0c03106e0f1ebb1ec3c1a5e6018c4c
        super(title);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel msg = new JLabel(message);
        msg.setFont(new Font("Verdana", Font.PLAIN, 25));
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        add(msg, BorderLayout.CENTER);

<<<<<<< HEAD
        Timer timer = new Timer(displaySeconds * 1000, e -> dispose());
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
=======
        try {
            Thread.sleep(time);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispose();
>>>>>>> 55e2d2a1ef0c03106e0f1ebb1ec3c1a5e6018c4c
    }
}