import java.awt.*;
import javax.swing.*;

public class MessageFrame extends JFrame {
    public MessageFrame(String title, String message, int displaySeconds) {
        super(title);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel msg = new JLabel(message);
        msg.setFont(new Font("Verdana", Font.PLAIN, 25));
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        add(msg, BorderLayout.CENTER);

        Timer timer = new Timer(displaySeconds * 1000, e -> dispose());
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
    }
}