import java.awt.*;
import javax.swing.*;

public class LogoutFrame extends JFrame {
    public LogoutFrame(String message) {
        super("Goodbye");

        setVisible(true); // display logout frame
        setSize(800, 600); // set frame size
        setLocationRelativeTo(null);

        JLabel msg = new JLabel("<html>Goodbye! Thank you.<br><br>" + message + "</html>");
        msg.setFont(new Font("Verdana", Font.PLAIN, 25));
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        add(msg, BorderLayout.CENTER);

        try {
            Thread.sleep(8000);  // wait 8 seconds after the window is closed by user112312
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dispose();
    }
}