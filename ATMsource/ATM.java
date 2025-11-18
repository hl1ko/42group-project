import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ATM {
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private BankDatabase bankDatabase; // account information database

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int TRANSFER = 3;
   private static final int EXIT = 4;

   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
   } // end no-argument ATM constructor

   private class AuthenticatorFrame extends JFrame {
      private JLabel pinPrompt;
      private JLabel accountPrompt;
      private JTextField accountField;
      private JPasswordField pinField;
      private JButton resetButton;

      private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
      private final Font NUMBERFONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
      private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
      private final int NORTHSOUTHPANELHEIGHT = 80;

      private AuthenticatorFrame() {
         super("Account Authentication");

         // header panel layout
         JPanel northPanel = new JPanel();
         northPanel.setLayout(new GridLayout(2, 1));
         northPanel.setPreferredSize(new Dimension(800, NORTHSOUTHPANELHEIGHT));
         northPanel.setBorder(HEADFOOTBORDER);

         // center panel layout
         JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         centerPanel
               .setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
         centerPanel.setBackground(Color.GRAY);

         // input panel layout
         JPanel inputPanel = new JPanel();
         inputPanel.setLayout(new GridLayout(6, 1));
         inputPanel
               .setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
         inputPanel.setPreferredSize(new Dimension(300, 200));
         inputPanel.setBackground(Color.WHITE);

         /* 
         JPanel southPanel = new JPanel();
         southPanel.setLayout(new GridLayout(2, 1));
         southPanel.setPreferredSize(new Dimension(800, NORTHSOUTHPANELHEIGHT));
         southPanel.setBorder(HEADFOOTBORDER);
         */

         // labels for header
         JLabel welcomeMsg = new JLabel("Welcome!");
         welcomeMsg.setFont(new Font("Verdana", Font.PLAIN, 25));
         JLabel promptAccInfoMsg1 = new JLabel("To authenticate, input account info and press Enter.");
         promptAccInfoMsg1.setFont(FONTSTYLE);
         
         // labels and fields for input panel
         accountPrompt = new JLabel("Account number");
         accountPrompt.setFont(FONTSTYLE);
         accountField = new JTextField();
         accountField.setFont(NUMBERFONTSTYLE);
         pinPrompt = new JLabel("PIN");
         pinPrompt.setFont(FONTSTYLE);
         pinField = new JPasswordField();
         pinField.setFont(NUMBERFONTSTYLE);
         pinField.setEditable(false);
         resetButton = new JButton("Reset");
         resetButton.setFont(FONTSTYLE);
         //accountField.setText("12345"); //For debug
         //pinField.setText("54321");   //For debug

         // labels for footer
         JLabel availableNotesMsg = new JLabel(cashDispenser.showAvaliableBills(), SwingConstants.CENTER);
         availableNotesMsg.setFont(FONTSTYLE);
         availableNotesMsg.setPreferredSize(new Dimension(800, NORTHSOUTHPANELHEIGHT));
         availableNotesMsg.setBorder(HEADFOOTBORDER);
         
         // adding components to panels
         northPanel.add(welcomeMsg);
         northPanel.add(promptAccInfoMsg1);
         inputPanel.add(accountPrompt);
         inputPanel.add(accountField);
         inputPanel.add(pinPrompt);
         inputPanel.add(pinField);
         inputPanel.add(new JLabel());
         inputPanel.add(resetButton);

         centerPanel.add(inputPanel);
         

         // adding panels to main frame
         add(northPanel, BorderLayout.NORTH);
         add(centerPanel, BorderLayout.CENTER);
         add(availableNotesMsg, BorderLayout.SOUTH);

         // add fields to action handler
         AuthenticateHandler handler = new AuthenticateHandler();
         accountField.addActionListener(handler);
         pinField.addActionListener(handler);
         resetButton.addActionListener(handler);
      }

      private class AuthenticateHandler implements ActionListener {
         public void actionPerformed(ActionEvent event) {
            int accountNumber = -1;
            int pin = -1;

            if (event.getSource() == accountField) {

               if (accountField.getText().length() > 0) {

                  if (accountField.getText().length() <= 9) {
                     accountNumber = Integer.parseInt(accountField.getText());
                     accountField.setEditable(false);
                     pinField.setEditable(true);
                     pinField.requestFocusInWindow();
                  } else {
                     cancel("The given account number exceeds the maximum length of 9 digits", JOptionPane.WARNING_MESSAGE);
                     accountField.requestFocusInWindow();
                  }

               } else {
                  JOptionPane.showMessageDialog(AuthenticatorFrame.this, "Nothing has been inputted for account number.");
                  accountField.requestFocusInWindow();
               }

            } else if (event.getSource() == pinField) {
               accountNumber = Integer.parseInt(accountField.getText());

               if (pinField.getPassword().length > 0) {
                  pin = Integer.parseInt(new String(pinField.getPassword()));

                  // set userAuthenticated to boolean value returned by database
                  userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

                  if (userAuthenticated) {
                     currentAccountNumber = accountNumber; // save user's account #
                     dispose(); // close the GUI window
                  } else {
                     cancel("The account information is not correct.", JOptionPane.WARNING_MESSAGE);
                  }

               } else {
                  cancel("Nothing has been inputted for PIN.", JOptionPane.WARNING_MESSAGE);
               }

            } else if (event.getSource() == resetButton) {
               cancel("", JOptionPane.INFORMATION_MESSAGE);
            }

         }

         private void cancel(String message, int messageType) {
            accountField.setText("");
            pinField.setText("");
            accountField.setEditable(true);
            pinField.setEditable(false);

            JOptionPane.showMessageDialog(AuthenticatorFrame.this, message + " The authenticate section is canceled.",
                  "Authentication canceled", messageType);

            accountField.requestFocusInWindow();
         }

      }
   }

   // start ATM
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         authenticateUser();
         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      } // end while
   } // end method run

   // attempts to authenticate user against database
   private void authenticateUser() {
      do {
         // calling the UI
         AuthenticatorFrame authenticatorFrame = new AuthenticatorFrame();
         authenticatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         authenticatorFrame.setSize(800, 600); // set frame size
         authenticatorFrame.setLocationRelativeTo(null);
         authenticatorFrame.setVisible(true); // display frame

         // Wait until the frame is disposed
         while (authenticatorFrame.isDisplayable()) {
            try {
               Thread.sleep(100);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      } while (!userAuthenticated);

   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:
            case WITHDRAWAL:
            case TRANSFER:

               // initialize as new object of chosen type
               currentTransaction = createTransaction(mainMenuSelection);

               currentTransaction.execute(); // execute transaction

               if (currentTransaction.logout){  // if user attempt to logout from GUI
                  userExited = true;
               }

               break;
            case EXIT: // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");

               new MessageFrame("Logout","<html>Goodbye! Thank you.<br><br>Please take your card now.</html>",5000);
               
               userExited = true; // this ATM session should end
               break;
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine(
                     "\nYou did not enter a valid selection. Try again.");
               break;
         } // end switch

         

      } // end while
   } // end method performTransactions

   // display the main menu and return an input selection
   private int displayMainMenu() {

      screen.displayMessageLine("\nMain menu:");
      screen.displayMessageLine("1 - View my balance");
      screen.displayMessageLine("2 - Withdraw cash");
      screen.displayMessageLine("3 - Transfer");
      screen.displayMessageLine("4 - Exit\n");
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   } // end method displayMainMenu

   // return object of specified Transaction subclass
   private Transaction createTransaction(int type) {
      Transaction temp = null; // temporary Transaction variable

      // determine which type of Transaction to create
      switch (type) {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            temp = new BalanceInquiry(
                  currentAccountNumber, screen, bankDatabase);
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
         case TRANSFER:
            temp = new Transfer(currentAccountNumber, screen, bankDatabase, keypad);
            break;
      } // end switch

      return temp; // return the newly created object
   } // end method createTransaction

} // end class ATM



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/