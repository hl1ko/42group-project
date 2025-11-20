import java.awt.*;
import javax.swing.*;

public class CustomLabel extends JLabel {
    public CustomLabel(String message) {
        this.setText(message);
        this.setFont(new Font("Verdana", Font.PLAIN, 20));
    }
}