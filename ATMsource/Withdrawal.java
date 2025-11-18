// Withdrawal.java
// Represents a withdrawal ATM transaction
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 0;

   protected int userInput;

   // Withdrawal constructor
   public Withdrawal( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );
      
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   public void execute()
   {
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase(); 
      Screen screen = getScreen();

      WithdrawalFrame withdrawalFrame = new WithdrawalFrame();
      withdrawalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      withdrawalFrame.setSize(800, 600); // set frame size
      withdrawalFrame.setLocationRelativeTo(null);
      withdrawalFrame.setVisible(true); // display frame

      // Wait until the frame is disposed
      while (withdrawalFrame.isDisplayable()) {
         try {
            Thread.sleep(100);  
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }

      if(!cashDispenser.AnyBillsAvaliable()){
         screen.displayMessageLine("This ATM is temporary disabled. Please use other ATM.");
         new MessageFrame("ATM disable","<html>This ATM is temporary disabled.<br></br>Please use other ATM.</html>",5000);
         return;
      }
      
      // loop until cash is dispensed or the user cancels
      do
      {
         // obtain a chosen withdrawal amount from the user 
         amount = displayMenuOfAmounts();
         if(!cashDispenser.canBillsHandleTheJob( amount )){
            screen.displayMessageLine("Not enough bills in the ATM to handle your withdrawal request." +
            "\n Please use other ATM.");
            new MessageFrame("Not enough bills", "<html>Not enough bills in the ATM to handle <br></br>your withdrawal request.<br></br> <br></br>Please use other ATM.</html", 5000);
            return;
         }
         // check whether user chose a withdrawal amount or canceled
         if ( amount != CANCELED )
         {
            // get available balance of account involved
            availableBalance = 
               bankDatabase.getAvailableBalance( getAccountNumber() );
      
            // check whether the user has enough money in the account 
            if ( amount <= availableBalance )
            {   
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                  // update the account involved to reflect withdrawal
                  bankDatabase.debit( getAccountNumber(), amount );
                  
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  cashDispensed = true; // cash was dispensed

                  // instruct user to take cash
                  screen.displayMessageLine( 
                     "\nPlease take your cash now." );
                     logout = true;
                     new MessageFrame("Take Card","<html>Withdrawal success.<br></br>Please take your card now.</html>",3000);
                     new MessageFrame("Take Cash","Please take your cash now.",3000);
                     new MessageFrame("Logout","<html>Goodbye! Thank you.</html>",5000);
               } // end if
               else
               { // cash dispenser does not have enough cash
                  screen.displayMessageLine( 
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount." );
                     new MessageFrame("Insufficient cash","<html>Insufficient cash available in the ATM.<br></br>Please choose a smaller amount.</html>", 3000);
               }
            } // end if
            else // not enough money available in user's account
            {
               screen.displayMessageLine( 
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
                  new MessageFrame("Insufficient funds", "<html>Insufficient funds in your account.<br></br>Please choose a smaller amount.</html>",5000);
            } // end else
         } // end if
         else // user chose cancel menu option 
         {
            screen.displayMessageLine( "\nCanceling transaction..." );
            new CancelTranscationFrame();
            return; // return to main menu because user canceled
         } // end else
      } while ( !cashDispensed );

   } // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts()
   {
      int userChoice = -1; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers, the first element (0) is reserved
      int amounts[] = { 0, 100, 200, 500, 800, 1000};

      // loop while no valid choice has been made
      while ( userChoice == -1 )
      {
         // display the menu
         screen.displayMessageLine( "\nAvailable banknote denominations: " + cashDispenser.showAvaliableBills());
         screen.displayMessageLine( "Withdrawal Menu:" );
         screen.displayMessageLine( "1 - $100" );
         screen.displayMessageLine( "2 - $200" );
         screen.displayMessageLine( "3 - $500" );
         screen.displayMessageLine( "4 - $800" );
         screen.displayMessageLine( "5 - $1000" );
         screen.displayMessageLine( "0 - Cancel transaction" );
         screen.displayMessageLine( "----------------------------------------------------------------------" );
         screen.displayMessage( "Enter a custom amount or choose an option from menu: " );

         ////

         int input = userInput; // get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {  
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2 or 3), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
               userChoice = amounts[ input ]; // save user's choice
               break;      
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice               break;
            default: // the user did not enter a value from 1-5
               if ( input % 100 == 0 ){
                  userChoice = input;
               } else {
                  screen.displayMessageLine("\nInvalid value or choice.");
                  screen.displayMessageLine("If you are willing to enter a custom value, make sure it is divisible by 100 and try again.");
               }
               
         } // end switch
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts

   private class WithdrawalFrame extends JFrame {
      private JButton confirmButton;
      private JButton cancelButton;
      private JButton resetButton;

      private JButton withdrawJOption1;
      private JButton withdrawJOption2;
      private JButton withdrawJOption3;
      private JButton withdrawJOption4;
      private JButton withdrawJOption5;
      private JTextField customAmount;
      private JLabel showWithdrawAmount;

      private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
      private final Font NUMBERFONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
      private final Border FONTBORDER = BorderFactory.createEmptyBorder(5, 3, 5, 0);
      private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
      private final int PANELHEIGHT = 80;

      private WithdrawalFrame() {
         super("Withdrawal");

         JPanel northPanel = new JPanel();
         northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
         northPanel.setPreferredSize(new Dimension(800, 50));

         JLabel title = new JLabel("Withdrawal", SwingConstants.CENTER);
         title.setFont(FONTSTYLE);
         title.setBorder(HEADFOOTBORDER);
         title.setAlignmentX(Component.CENTER_ALIGNMENT);
         title.setPreferredSize(new Dimension(800, 25));

         JLabel availableNotesMsg = new JLabel(cashDispenser.showAvaliableBills(), SwingConstants.CENTER);
         availableNotesMsg.setFont(FONTSTYLE);
         availableNotesMsg.setBorder(HEADFOOTBORDER);
         availableNotesMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
         availableNotesMsg.setPreferredSize(new Dimension(800, 25));

         /*JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         centerPanel
               .setBorder(BorderFactory.createEmptyBorder(60, 230, 0, 230));
         centerPanel.setBackground(Color.GRAY);

         //panel layout
         JPanel withdrawalPanel = new JPanel();
         withdrawalPanel.setLayout(new GridLayout(9, 1));
         withdrawalPanel
               .setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
         withdrawalPanel.setPreferredSize(new Dimension(320, 270));
         withdrawalPanel.setBackground(Color.WHITE);*/

       // Withdrawal panel
JPanel withdrawalPanel = new JPanel();
withdrawalPanel.setLayout(new FlowLayout( FlowLayout.LEFT));
//withdrawalPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
withdrawalPanel.setPreferredSize(new Dimension(720,350));
withdrawalPanel.setBackground(Color.WHITE);

//JPanel centerPanel = new JPanel(new BoxLayout(withdrawalPanel,BoxLayout.Y_AXIS));
//centerPanel.setBorder(BorderFactory.createEmptyBorder(60, 230, 0, 230));
//centerPanel.setBackground(Color.GRAY);

JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         centerPanel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
         centerPanel.setBackground(Color.GRAY);



JPanel optionPanel = new JPanel();

optionPanel.setLayout(new GridLayout(3, 2,10,10)); // 3 rows, 2 columns, gaps
optionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
optionPanel.setPreferredSize(new Dimension(710, 150));
Dimension buttonSize = new Dimension(100, 35);


         JLabel withdrawalMenu = new JLabel("Withdrawal Menu:");
         withdrawalMenu.setFont(FONTSTYLE);
         withdrawalMenu.setBorder(FONTBORDER);
         withdrawalMenu.setBackground(Color.WHITE);

         withdrawJOption1 = new JButton("1 - $100");
         withdrawJOption1.setFont(FONTSTYLE);
         withdrawJOption1.setPreferredSize(buttonSize);
         withdrawJOption2 = new JButton("2 - $200");
         withdrawJOption2.setFont(FONTSTYLE);
         withdrawJOption2.setPreferredSize(buttonSize);
         withdrawJOption3 = new JButton("3 - $500");
         withdrawJOption3.setFont(FONTSTYLE);
         withdrawJOption3.setPreferredSize(buttonSize);
         withdrawJOption4 = new JButton("4 - $800");
         withdrawJOption4.setFont(FONTSTYLE);
         withdrawJOption4.setPreferredSize(buttonSize);
         withdrawJOption5 = new JButton("5 - $1000");
         withdrawJOption5.setFont(FONTSTYLE);
         withdrawJOption5.setPreferredSize(buttonSize);

         optionPanel.add(withdrawJOption1);
         optionPanel.add(withdrawJOption2);
         optionPanel.add(withdrawJOption3);
         optionPanel.add(withdrawJOption4);
         optionPanel.add(withdrawJOption5);

         JPanel customPanel = new JPanel();
         customPanel.setLayout(new GridLayout(1, 2));
         customPanel.setBackground(Color.WHITE);
         customPanel.setPreferredSize(new Dimension(710,40));

         JLabel withdrawJOption0 = new JLabel("or enter a custom amount (hkd):");
         withdrawJOption0.setFont(NUMBERFONTSTYLE);
         withdrawJOption0.setBorder(FONTBORDER);

         //
         customAmount = new JTextField();
         customAmount.setFont(NUMBERFONTSTYLE);
         customAmount.requestFocusInWindow();

         customPanel.add(withdrawJOption0);
         customPanel.add(customAmount);
         
         JLabel withdrawAmount = new JLabel("withdraw amount (hkd):");
         withdrawAmount.setFont(NUMBERFONTSTYLE);
         //withdrawAmount.setBorder(FONTBORDER);
         //withdrawAmount.setPreferredSize(buttonSize);

         showWithdrawAmount = new JLabel("0");
         showWithdrawAmount.setFont(NUMBERFONTSTYLE);
         //showWithdrawAmount.setBorder(FONTBORDER);
         showWithdrawAmount.setPreferredSize( new Dimension(120, 35));

         resetButton = new JButton("Reset Amount");
         resetButton.setFont(NUMBERFONTSTYLE);
         resetButton.setPreferredSize(new Dimension(185,35));

         JPanel showAmountPanel = new JPanel();
         showAmountPanel.setLayout(new GridLayout(1,2));
         showAmountPanel.setBackground(Color.WHITE);
         //showAmountPanel.setPreferredSize(new Dimension(515,35));

         JPanel groupPanel = new JPanel();
         groupPanel.setLayout(new GridLayout(1,2));

         showAmountPanel.add(withdrawAmount);
         //showAmountPanel.add(new JLabel());
         showAmountPanel.add(showWithdrawAmount);
         //showAmountPanel.add(resetButton);

         
        /*JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0; // single row

        // Label (½ width)
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        panel.add(withdrawAmount, gbc);

        // TextField (¼ width)
        gbc.gridx = 1;
        gbc.weightx = 0.25;
        panel.add(showWithdrawAmount, gbc);

        // Button (¼ width)
        gbc.gridx = 2;
        gbc.weightx = 0.25;
        panel.add(resetButton, gbc);

         
JPanel panel1 = new JPanel(new GridBagLayout());
        //GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; // single row
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0; // no vertical expansion

        // Column 0: spacer
        gbc.gridx = 0;
        gbc.weightx = 0.0; // no extra space
        panel1.add(Box.createHorizontalStrut(10), gbc);

        // Column 1: Label
        gbc.gridx = 1;
        gbc.weightx = 0.4; // adjust as needed
        panel1.add(withdrawAmount, gbc);

        // Column 2: Label
        gbc.gridx = 2;
        gbc.weightx = 0.3;
        panel.add(showWithdrawAmount, gbc);

        // Column 3: Button
        gbc.gridx = 3;
        gbc.weightx = 0.3;
        panel.add(resetButton, gbc);*/



         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(1,3));
         buttonPanel.setPreferredSize(new Dimension(700,35));

         confirmButton = new JButton("Confirm Withdrawal");
         confirmButton.setFont(FONTSTYLE);
         confirmButton.setPreferredSize(new Dimension(250,35));

         cancelButton = new JButton("Back to Main Menu");
         cancelButton.setFont(FONTSTYLE);
         cancelButton.setPreferredSize(new Dimension(250,35));

         buttonPanel.add(cancelButton);
         buttonPanel.add(new JLabel());
         
         buttonPanel.add(confirmButton);

         JLabel footer = new JLabel("For further assistance, please contact customer support.");
         footer.setFont(FONTSTYLE);
         footer.setBorder(HEADFOOTBORDER);
         footer.setPreferredSize(new Dimension(800, PANELHEIGHT));

         
optionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
customPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
showAmountPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

         JLabel space = new JLabel();
         space.setPreferredSize(new Dimension(200, 35));


         northPanel.add(title);
         northPanel.add(availableNotesMsg);

         withdrawalPanel.add(withdrawalMenu);
         withdrawalPanel.add(optionPanel);
         withdrawalPanel.add(customPanel);
         withdrawalPanel.add(showAmountPanel);
         withdrawalPanel.add(resetButton);
         withdrawalPanel.add(cancelButton);
         withdrawalPanel.add(space);
         withdrawalPanel.add(confirmButton);


         centerPanel.add(withdrawalPanel);
         
         add(northPanel, BorderLayout.NORTH);
         add(centerPanel, BorderLayout.CENTER);
         add(footer, BorderLayout.SOUTH);   

         //event handel...
         WithdrawalHandler handler = new WithdrawalHandler();
         cancelButton.addActionListener(handler);
         confirmButton.addActionListener(handler);
         resetButton.addActionListener(handler);
         withdrawJOption1.addActionListener(handler);
         withdrawJOption2.addActionListener(handler);
         withdrawJOption3.addActionListener(handler);
         withdrawJOption4.addActionListener(handler);
         withdrawJOption5.addActionListener(handler);
         customAmount.addActionListener(handler);
      }

    private class WithdrawalHandler implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         if (event.getSource() == withdrawJOption1) {
            showWithdrawAmount.setText("100");
            customAmount.setText("");
         }

         if (event.getSource() == withdrawJOption2) {
            showWithdrawAmount.setText("200");
            customAmount.setText("");
         }

         if (event.getSource() == withdrawJOption3) {
            showWithdrawAmount.setText("500");
            customAmount.setText("");
         }

         if (event.getSource() == withdrawJOption4) {
            showWithdrawAmount.setText("800");
            customAmount.setText("");
         }

         if (event.getSource() == withdrawJOption5) {
            showWithdrawAmount.setText("1000");
            customAmount.setText("");
         }

         if (event.getSource() == customAmount) {
            if(Integer.parseInt(customAmount.getText()) % 100 != 0){
               JOptionPane.showMessageDialog(null, "Make sure the custom amount is divisible by 100 and try again.","Invalid value", JOptionPane.PLAIN_MESSAGE);
            }else{
               if(customAmount.getText().isEmpty() ){
                  showWithdrawAmount.setText("0");   
               }else{
                  showWithdrawAmount.setText(customAmount.getText());
               }
            }  
            customAmount.setText("");   
         }

         if (event.getSource() == resetButton) {
            showWithdrawAmount.setText("0");
            customAmount.setText("");
         }

         if (event.getSource() == confirmButton) {
            userInput = Integer.parseInt(showWithdrawAmount.getText());
            dispose(); // close the GUI window
         }

         if (event.getSource() == cancelButton) {
            userInput = 0;
            dispose();  // close the GUI window
         }
      }
   }
}
} // end class Withdrawal



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