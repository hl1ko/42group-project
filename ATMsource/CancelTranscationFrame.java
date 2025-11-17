import java.awt.*;
import javax.swing.*;

public class CancelTranscationFrame extends JFrame{
    public CancelTranscationFrame() {
        super("Cancel Transcation");

        setVisible(true); // display logout frame
        setSize(800, 600); // set frame size
        setLocationRelativeTo(null);

        JLabel msg = new JLabel("<html>Transcation canceling...<br><br>Direct to Main Menu.</html>");
        msg.setFont(new Font("Verdana", Font.PLAIN, 25));
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        add(msg, BorderLayout.CENTER);

        try {
            Thread.sleep(3000);  // wait 8 seconds after the window is closed by user112312
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dispose();
    }
}
