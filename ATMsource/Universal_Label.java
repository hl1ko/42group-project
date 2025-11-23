import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Universal_Label extends JLabel {
    private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
    private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
    
    public Universal_Label(String message) {
        this.setText(message);
        this.setFont(FONTSTYLE);
    }
    public Universal_Label(String message, Font fstyle) {
        this.setText(message);
        this.setFont(fstyle);
    }
    public Universal_Label() {
        this.setFont(FONTSTYLE);
    }

    public void usingTitleAndFooterStyle() {
        setPreferredSize(new Dimension(800, 80));
        setBorder(HEADFOOTBORDER);
    }

    public void setSize(int size) {
        this.setFont(new Font("Verdana", Font.PLAIN, size));
    }
}