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

   private boolean userExited;
   private Transaction currentTransaction = null;
   private int transactionType;

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
      private JTextField accountField;
      private JPasswordField pinField;
      private JButton resetButton;

      private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
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
         centerPanel.setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
         centerPanel.setBackground(Color.GRAY);

         // input panel layout
         JPanel inputPanel = new JPanel();
         inputPanel.setLayout(new GridLayout(6, 1));
         inputPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
         inputPanel.setPreferredSize(new Dimension(300, 200));
         inputPanel.setBackground(Color.WHITE);

         // labels for header
         CustomLabel welcomeMsg = new CustomLabel("Welcome!");
         welcomeMsg.setSize(25);

         CustomLabel promptAccInfoMsg1 = new CustomLabel("To authenticate, input account info and press Enter.");
         
         // labels and fields for input panel
         CustomLabel accountPrompt = new CustomLabel("Account number");
         accountField = new JTextField();
         accountField.setFont(FONTSTYLE);
         CustomLabel pinPrompt = new CustomLabel("PIN");
         pinField = new JPasswordField();
         pinField.setFont(FONTSTYLE);
         pinField.setEditable(false);
         resetButton = new JButton("Reset");
         resetButton.setFont(FONTSTYLE);

         /**************************************/
         //For debug
         accountField.setText("12345"); 
         pinField.setText("54321");   
         /**************************************/

         // labels for footer
         CustomLabel availableNotesMsg = new CustomLabel(cashDispenser.showAvaliableBills());
         availableNotesMsg.usingTitleAndFooterStyle();
         
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
                     accountField.setEditable(false);
                     pinField.setEditable(true);
                     pinField.requestFocusInWindow();
                  } else { 
                     cancel("The account number exceeds the maximum length of 9 digits.", JOptionPane.WARNING_MESSAGE);
                  }

               } else {
                  cancel("The account number field is empty.", JOptionPane.INFORMATION_MESSAGE);
               }

            }
            
            if (event.getSource() == pinField) {
               
               if (pinField.getPassword().length == 5) {
                  accountNumber = Integer.parseInt(accountField.getText());
                  pin = Integer.parseInt(new String(pinField.getPassword()));

                  // set userAuthenticated to boolean value returned by database
                  userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

                  if (userAuthenticated) {
                     currentAccountNumber = accountNumber; // save user's account #
                     dispose(); // close the GUI window
                  } else {
                     cancel("Incorrect account information.", JOptionPane.WARNING_MESSAGE);
                  }

               } else {
                  cancel("The PIN should be consist of 5 digits.", JOptionPane.WARNING_MESSAGE);
               }

            }
            
            if (event.getSource() == resetButton) {
               cancel("Done.", JOptionPane.INFORMATION_MESSAGE);
            }

         }

         private void cancel(String message, int messageType) {
            accountField.setText("");
            pinField.setText("");
            accountField.setEditable(true);
            pinField.setEditable(false);

            CustomLabel msgLabel = new CustomLabel("<html>" + message + "<br><br>Authenticate section canceled.</html>");
            JOptionPane.showMessageDialog(AuthenticatorFrame.this, msgLabel,"Authentication canceled", messageType);

            accountField.requestFocusInWindow();
         }

      }
   }

   // display the main menu and return an input selection
   private class MainMenuFrame extends JFrame {
      private JButton balanceButton;
      private JButton withdrawalButton;
      private JButton transferButton;
      private JButton exitButton;
      private JTextField inputField;

      private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);

      public MainMenuFrame () {
         super ("Main Menu");
         setLayout(new BorderLayout(10, 10));

         // The top of the panel
         CustomLabel title = new CustomLabel("Main Menu");
         title.usingTitleAndFooterStyle();
         title.setSize(25);
         add(title, BorderLayout.NORTH);

         // Center panel
         JPanel centerPanel = new JPanel();
         centerPanel.setLayout(null);
         add(centerPanel, BorderLayout.CENTER);

         //Menu button
         balanceButton = new JButton("1 - View my balance");
         withdrawalButton = new JButton("2 - Withdraw cash");
         transferButton = new JButton("3 - Transfer");
         exitButton = new JButton("4 - Exit");

         //The Front of the button
         balanceButton.setFont(FONTSTYLE);
         withdrawalButton.setFont(FONTSTYLE);
         transferButton.setFont(FONTSTYLE);
         exitButton.setFont(FONTSTYLE);

         //Button position and size
         balanceButton.setBounds(50,90,300,100);
         withdrawalButton.setBounds(50,200,300,100);
         transferButton.setBounds(430,90,300,100);
         exitButton.setBounds(430,200,300,100);

         //Button background color
         balanceButton.setBackground( Color.WHITE );
         withdrawalButton.setBackground( Color.WHITE);
         transferButton.setBackground( Color.WHITE);
         exitButton.setBackground( Color.WHITE );

         //Center panel button
         centerPanel.add(balanceButton);
         centerPanel.add(withdrawalButton);
         centerPanel.add(transferButton);
         centerPanel.add(exitButton);

         //Buttom Panel 
         JPanel buttomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
         CustomLabel footer = new CustomLabel ("Enter a choice:");
         footer.setSize(25);
         buttomPanel.add(footer);

         //Input field
         inputField = new JTextField(2);
         inputField.setFont(FONTSTYLE);
         buttomPanel.add(inputField);
         add(buttomPanel, BorderLayout.SOUTH);

         inputField.requestFocus();

         MenuButtonHandler handler = new MenuButtonHandler();
         balanceButton.addActionListener(handler);
         withdrawalButton.addActionListener(handler);
         transferButton.addActionListener(handler);
         exitButton.addActionListener(handler);
         inputField.addActionListener(handler);
      }

      public void addNotify() {
         super.addNotify();
         inputField.requestFocusInWindow();
      }

      // return object of specified Transaction subclass
      private class MenuButtonHandler implements ActionListener {
         public void actionPerformed(ActionEvent event) {
            
            // determine which type of Transaction to create
            if (event.getSource() == balanceButton) {
               transactionType = BALANCE_INQUIRY;
               executeTransaction();
               dispose();
            } else if (event.getSource() == withdrawalButton) {
               transactionType = WITHDRAWAL;
               executeTransaction();
               dispose();
            } else if (event.getSource() == transferButton) {
               transactionType = TRANSFER;
               executeTransaction();
               dispose();
            } else if (event.getSource() == exitButton) {
               exit();
               dispose();
            } else if (event.getSource() == inputField) {
               transactionType = Integer.parseInt(inputField.getText());

               if (transactionType < BALANCE_INQUIRY || transactionType > EXIT) {
                  JLabel msgLabel = new JLabel("Your input is not within the range of choices");
                  msgLabel.setFont(FONTSTYLE);
                  JOptionPane.showMessageDialog(MainMenuFrame.this, msgLabel,
                  "", 1);

                  inputField.requestFocusInWindow();
               }

               executeTransaction();
               dispose();
            }

         } // end method createTransaction

         private void executeTransaction() {
            if (transactionType != EXIT) {
               currentTransaction = createTransaction(transactionType);
               currentTransaction.execute();
            } else {
               exit();
            }
         }

         private void exit() {
            transactionType = EXIT;
            userExited = true;
            currentTransaction = null;
         }
      }

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
      
   // start ATM
   }

   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         authenticateUser();
         performTransactions(); // user is now authenticated
         displayExitFrame(transactionType);
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      } // end while
   } // end method run

   private void authenticateUser() {
      AuthenticatorFrame authenticatorFrame = new AuthenticatorFrame();
      authenticatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      authenticatorFrame.setSize(800, 600); // set frame size
      authenticatorFrame.setLocationRelativeTo(null);
      authenticatorFrame.setVisible(true); // display frame
      
      // Wait until the frame is disposed
      while (authenticatorFrame.isDisplayable()) {}
   }

   // display the main menu and perform transactions
   private void performTransactions() {
      userExited = false; // user has not chosen to exit

      do {
         // calling the UI
         MainMenuFrame mainMenuFrame = new MainMenuFrame();
         mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         mainMenuFrame.setSize(800, 600); // set frame size
         mainMenuFrame.setLocationRelativeTo(null);
         mainMenuFrame.setVisible(true); // display frame

         while (mainMenuFrame.isDisplayable()) {}

         while (currentTransaction != null && currentTransaction.getToMainMenuFlag() == false && currentTransaction.getLogoutFlag() == false) {
            try {
               Thread.sleep(100);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }

         if (currentTransaction != null && currentTransaction.getLogoutFlag() == true) {  // if user attempt to logout from GUI
            userExited = true;
         }

      } while (!userExited);

   }

   private void waitUntilNotDisplaying(JFrame f) {
      while (f.isDisplayable()) {
      }
   }

   private void displayExitFrame(int transactionType) {

      switch (transactionType) {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            waitUntilNotDisplaying(new MessageFrame("Take Card","<html>Please take your card now.</html>", 3));
            waitUntilNotDisplaying(new LogoutFrame(3));
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            Withdrawal withdrawal = (Withdrawal) currentTransaction;

            switch (withdrawal.getStateNum()) {
               case 0:
                  waitUntilNotDisplaying(new MessageFrame("Not enough bills", "<html>Not enough bills in the ATM to handle your withdrawal request.<br></br> Please use other ATM.</html", 3));
                  break;
               case 1:
                  waitUntilNotDisplaying(new MessageFrame("Not enough bills", "<html>Not enough bills in the ATM to handle your withdrawal request.<br></br> Please use other ATM.</html", 3));
                  break;
               case 2:
                  waitUntilNotDisplaying(new MessageFrame("Take Card","<html>Withdrawal success.<br></br>Please take your card now.</html>", 3));
                  waitUntilNotDisplaying(new MessageFrame("Take Cash","Please take your cash now.", 3));
                  waitUntilNotDisplaying(new LogoutFrame(3));
                  break;
               case 3:
                  waitUntilNotDisplaying(new MessageFrame("Insufficient cash","<html>Insufficient cash available in the ATM.<br></br>Please choose a smaller amount.</html>", 3));
                  break;
               case 4:
                  waitUntilNotDisplaying(new MessageFrame("Insufficient funds", "<html>Insufficient funds in your account.<br></br>Please choose a smaller amount.</html>", 3));
                  break;
               case 5:
                  waitUntilNotDisplaying(new LogoutFrame(3));
                  break;
            }

            break;
         case TRANSFER:


            break;
         case EXIT:
            waitUntilNotDisplaying(new MessageFrame("Take Card","<html>Please take your card now.</html>", 3));
            waitUntilNotDisplaying(new LogoutFrame(3));
            break;
      }
   }
   
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