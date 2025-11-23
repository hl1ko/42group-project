
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class URussian_Frame extends JFrame {
    int delay = 3000;
    public URussian_Frame(String Title, String Message, int layer) {
        init(Message, Title);
        SwingUtilities.invokeLater(() -> {
            Timer timer = new Timer(delay  * layer, (ActionEvent e) -> {
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    public URussian_Frame(String Title, String Message, int layer, URussian_Frame Sframe) {
        init(Title, Message);
        SwingUtilities.invokeLater(() -> {
            Timer timer = new Timer(delay * layer, (ActionEvent e) -> {
                dispose();
                Sframe.setVisible(true);
                ((Timer) e.getSource()).stop();
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    private void init(String Title, String Message) {
        setTitle(Title);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel CPanel = new JPanel();
        CPanel.setPreferredSize(new Dimension(800, 600));
        CPanel.add(new JLabel(Message), BorderLayout.CENTER);
        add(CPanel);
        requestFocusInWindow();
    }
}
