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

        Timer timer = new Timer(3000, e -> dispose());
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
        
    }
}

/*











*/