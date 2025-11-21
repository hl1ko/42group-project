// BalanceInquiry.java
// Represents a balance inquiry ATM transaction

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BalanceInquiry extends Transaction {
   private double availableBalance;
   private double totalBalance;

   // BalanceInquiry constructor
   public BalanceInquiry(int userAccountNumber, Screen atmScreen,
         BankDatabase atmBankDatabase) {
      super(userAccountNumber, atmScreen, atmBankDatabase);
   } // end BalanceInquiry constructor

   // performs the transaction
   public void execute() {
      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();

      // get the available balance for the account involved
      availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

      // get the total balance for the account involved
      totalBalance = bankDatabase.getTotalBalance(getAccountNumber());

      // calling the UI
      BalanceInquiryFrame balanceInquiryFrame = new BalanceInquiryFrame();
      balanceInquiryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      balanceInquiryFrame.setSize(800, 600); // set frame size
      balanceInquiryFrame.setLocationRelativeTo(null);
      balanceInquiryFrame.setVisible(true); // display frame

   } // end method execute

   private class BalanceInquiryFrame extends JFrame {
      private JButton cancelButton;
      private JButton logoutButton;

      private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);

      private BalanceInquiryFrame() {
         super("Balance Inquiry");
         Screen screen = new Screen();

         // header panel layout
         CustomLabel header = new CustomLabel("Balance Inquiry");
         header.usingTitleAndFooterStyle();

         // center panel layout
         JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         centerPanel.setBorder(BorderFactory.createEmptyBorder(60, 230, 0, 230));
         centerPanel.setBackground(Color.GRAY);

         // info panel layout
         JPanel infoPanel = new JPanel();
         infoPanel.setLayout(new GridLayout(9, 1));
         infoPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
         infoPanel.setPreferredSize(new Dimension(320, 270));
         infoPanel.setBackground(Color.WHITE);

         // labels for available balance
         CustomLabel availableBalanceMsg = new CustomLabel("Available balance:");
         CustomLabel showAvailableBalanceMsg = new CustomLabel(screen.dollarAmountToString(availableBalance));

         // labels for total balance
         CustomLabel totalBalanceMsg = new CustomLabel("Total balance:");
         CustomLabel showTotalBalanceMsg = new CustomLabel(screen.dollarAmountToString(totalBalance));

         // button for back to main menu
         cancelButton = new JButton("Back to Main Menu");
         cancelButton.setFont(FONTSTYLE);
         cancelButton.setBackground( Color.WHITE );

         // button for cancelling transaction
         logoutButton = new JButton("Logout");
         logoutButton.setFont(FONTSTYLE);
         logoutButton.setBackground( Color.WHITE );

         // labal for footer
         CustomLabel footer = new CustomLabel("For further assistance, please contact customer support.");
         footer.usingTitleAndFooterStyle();

         // adding components to panels
         infoPanel.add(availableBalanceMsg);
         infoPanel.add(showAvailableBalanceMsg);
         infoPanel.add(new JLabel());
         infoPanel.add(totalBalanceMsg);
         infoPanel.add(showTotalBalanceMsg);
         infoPanel.add(new JLabel());
         infoPanel.add(cancelButton);
         infoPanel.add(new JLabel());
         infoPanel.add(logoutButton);
         centerPanel.add(infoPanel);

         // adding panels to main frame
         add(header, BorderLayout.NORTH);
         add(centerPanel, BorderLayout.CENTER);
         add(footer, BorderLayout.SOUTH);

         // add fields to action handler
         BalanceInquiryHeadler handler = new BalanceInquiryHeadler();
         cancelButton.addActionListener(handler);
         logoutButton.addActionListener(handler);
      }

      private class BalanceInquiryHeadler implements ActionListener {
         public void actionPerformed(ActionEvent event) {
            if (event.getSource() == cancelButton) {
               toMainMenu = true;
               dispose();  // close the GUI window
            }

            if (event.getSource() == logoutButton) {
               logout = true; // user is attemptted to log out
               dispose(); // close the GUI window
            }
         }
      }
   }
} // end class BalanceInquiry

/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and *
 * Pearson Education, Inc. All Rights Reserved. *
 * *
 * DISCLAIMER: The authors and publisher of this book have used their *
 * best efforts in preparing the book. These efforts include the *
 * development, research, and testing of the theories and programs *
 * to determine their effectiveness. The authors and publisher make *
 * no warranty of any kind, expressed or implied, with regard to these *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or *
 * consequential damages in connection with, or arising out of, the *
 * furnishing, performance, or use of these programs. *
 *************************************************************************/