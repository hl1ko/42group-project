
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Wbutton extends JButton {

    private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
    Dimension buttonSize = new Dimension(100, 35);
    Color ShowingColor = Color.white;
    Color HoverColor = Color.gray;

    //this.setBackground(new Color(65, 125, 128));
    public Wbutton(String label, Color ShowColor, Color HoverColor) {
        ShowingColor = ShowColor;
        HoverColor = HoverColor;

        this.setText(label);
        this.setFont(FONTSTYLE);
        this.setPreferredSize(buttonSize);

        this.setBackground(ShowingColor);
        addListeners();

    }

    public Wbutton(String label) {
        this.setText(label);
        this.setFont(FONTSTYLE);
        this.setPreferredSize(buttonSize);
        this.setBackground(ShowingColor);

        focusListener focus = new focusListener();
        this.addFocusListener(focus);

        mouseH mouseEvent = new mouseH();
        this.addMouseListener(mouseEvent);
        addListeners();
    }

    public class focusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            setColor(ShowingColor);
        }

        @Override
        public void focusLost(FocusEvent e) {
            setColor(ShowingColor);
        }
    }

    public class mouseH implements MouseListener {

        @Override
        public void mouseEntered(MouseEvent e) {
            setColor(ShowingColor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setColor(ShowingColor);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

    private void setColor(Color inputColor) {
        this.setBackground(inputColor);
    }

    private void addListeners() {
        focusListener fe = new focusListener();
        this.addFocusListener(fe);
        mouseH me = new mouseH();
        this.addMouseListener(me);
    }

    public void setShowingColors(Color inputColor){
        ShowingColor = inputColor;
    }
    public void setHoverColors(Color inputColor){
        HoverColor = inputColor;
    }
}
