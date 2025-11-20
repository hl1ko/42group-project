// Screen.java
// Represents the screen of the ATM

import javax.swing.*;

public class Screen {
    // displays a message without a carriage return

    public void displayMessage(String message) {
        System.out.print(message);
    } // end method displayMessage

    // display a message with a carriage return
    public void displayMessageLine(String message) {
        System.out.println(message);
    } // end method displayMessageLine

    // display a dollar amount
    public void displayDollarAmount(double amount) {
        System.out.printf("$%,.2f", amount);
    } // end method displayDollarAmount

    public String dollarAmountToString(double amount) {
        return String.format("HK$%,.2f", amount);
    }

    public void waitUntilNotDisplaying(JFrame f) {
        SwingUtilities.invokeLater(() -> {
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(800, 600);
            f.setLocationRelativeTo(null);
            // Add components here if needed
            f.setVisible(true);
        });
    }

    public void showMessage1(String inputString, String title) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setUndecorated(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(800, 600);
            f.setLocationRelativeTo(null);
            // Add components here if needed

            JLabel msgLabel = new JLabel(inputString);
            //msgLabel.setFont(FONTSTYLE);
            JOptionPane.showMessageDialog(null, msgLabel, title, JOptionPane.INFORMATION_MESSAGE);
            f.setVisible(true);
            f.setVisible(false);
        });
    }

    /*public void showMultipleLineMessage(String inputString) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setUndecorated(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(800, 600);
            f.setLocationRelativeTo(null);
            // Add components here if needed

            JTextArea msg = new JTextArea(inputString);
            msg.setLineWrap(true);
            msg.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(msg);
            JOptionPane.showMessageDialog(null, scrollPane);
            f.setVisible(true);
            f.setVisible(false);
        });

    } */
} // end class Screen

/**
 * ************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 * ***********************************************************************
 */
