
import java.awt.*;
import javax.swing.*;

public class Universal_Textfield extends JTextField {

    private Screen screen;
    boolean isEssential = false;
    private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
    String inputString = this.getText();

    public Universal_Textfield() {
        this.setFont(FONTSTYLE);
    }

    public Universal_Textfield(Font STYLE) {
        this.setFont(STYLE);
    }
}
