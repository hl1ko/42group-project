
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Wframe extends JFrame {

    private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
    private final Font NUMBERFONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
    private final Border FONTBORDER = BorderFactory.createEmptyBorder(5, 3, 5, 0);
    private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
    private final int PANELHEIGHT = 80;
    JPanel NPanel = new JPanel();
    JPanel SPanel = new JPanel();
    JPanel CPanel = new JPanel();
    JLabel NorthLabel = new JLabel("", SwingConstants.CENTER);
    JLabel SouthLabel = new JLabel("For further assistance, please contact customer support.", SwingConstants.CENTER);

    public Wframe() {
        LabelInit();
        NPanel.add(NorthLabel);
        SPanel.add(SouthLabel);
        defaultComponent();
        addPanels();
    }

    private void defaultComponent() {
        //
        NPanel.setPreferredSize(new Dimension(800, PANELHEIGHT));
        NPanel.setBackground(Color.white);
        //
        SPanel.setPreferredSize(new Dimension(800, PANELHEIGHT));
        SPanel.setBackground(Color.white);
        //
        CPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        CPanel.setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
        CPanel.setBackground(Color.GRAY);
    }

    private void LabelInit() {

        NorthLabel.setFont(FONTSTYLE);
        NorthLabel.setBorder(HEADFOOTBORDER);
        NorthLabel.setPreferredSize(new Dimension(800, PANELHEIGHT));

        SouthLabel.setFont(FONTSTYLE);
        SouthLabel.setBorder(HEADFOOTBORDER);
        SouthLabel.setPreferredSize(new Dimension(800, PANELHEIGHT));
    }

    private void addPanels() {
        add(NPanel, BorderLayout.NORTH);
        add(SPanel, BorderLayout.SOUTH);
    }

    public void setNorthText(String inputString) {
        NorthLabel.setText(inputString);
    }

    public void setSouthText(String inputString) {
        SouthLabel.setText(inputString);
    }

}
