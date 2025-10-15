public class Transfer extends Transaction{
    private double amount; // amount to transfer
    private Keypad keypad; // reference to keypad
    private final static int CANCELED = 0; // constant for cancel option

    public Transfer( int userAccountNumber, Screen atmScreen, //constructor for initializing objects
      BankDatabase atmBankDatabase, Keypad atmKeypad)
   {
      super( userAccountNumber, atmScreen, atmBankDatabase );  //call superclass's constructor for initializing three of the variables
      keypad = atmKeypad;  //additional constructor implemention to initialize extra object variable
   } // end Transfer constructor

   public void execute() 
   {
      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();

      int targetAccountNumber; //integer variable which its content will be inputted by users

      //provide message to prompt user to input values
      screen.displayMessage("Please enter Target Account Number (0 to cancel): ");

      //looping statement inside the block if the account number entered by the user is same
      do { 
         targetAccountNumber = keypad.getInput();
         if ( targetAccountNumber == 0 ){
            screen.displayMessageLine("Transfer cancelled");
            return;
         }
         if ( targetAccountNumber == getAccountNumber() ){
            screen.displayMessageLine("You can not transfer to the same account.");
            screen.displayMessage("Please enter again: ");
         }
      } while (targetAccountNumber == getAccountNumber() && targetAccountNumber != 0);

      //this part is not looping to prevent 
      if ( bankDatabase.checkAccountExist(targetAccountNumber) == false ){
         screen.displayMessageLine("Target account does not exist.");
         screen.displayMessageLine("Transfer cancelled.");
         return;
      }

      //assign value inputted by user to 
      amount = promptForTransferAmount();

      //cancel and back to main menu if amount inputted by user is zero
      if ( amount == CANCELED ){
         screen.displayMessageLine("Transfer cancelled.");
         return;
      }

      //cancel and back to main menu if the available balance of the targeted account is higher than the amount inputted
      //i.e. Target available balance < user inputted amount
      if ( bankDatabase.getAvailableBalance(getAccountNumber()) < amount ){
         screen.displayMessageLine("\nInsufficient funds in your account.");
         screen.displayMessageLine("Transfer cancelled.");
         return;
      }

      //prompt user to input the money transfer recipient account number and its transfer amount
      screen.displayMessageLine("=====");
      screen.displayMessageLine("Recipient account number: " + targetAccountNumber);

      //prompt user the money values that will be tranferred
      screen.displayMessage("Transfer amount: "); 
      screen.displayDollarAmount(amount);
      screen.displayMessageLine("");
      screen.displayMessageLine("=====");
      screen.displayMessageLine("Please check the transfer info above.");

      //let user to decide whether the transaction will continue or not
      screen.displayMessageLine("Enter 1 to confirm this transfer action. To cancel the transaction, enter number other then 1.");
      screen.displayMessage("Your input: ");
      if ( keypad.getInput() != 1 ){
         screen.displayMessageLine("Transfer cancelled.");
         return;  //cancel the transaction if the user input 1
      }

      //add the amount to the target account, and debit the amount from the user logged in.
      bankDatabase.debit(getAccountNumber(), amount);
      bankDatabase.credit(targetAccountNumber, amount);

      //prompt user that the transaction is completed.
      screen.displayMessageLine("Transfer success.");
      screen.displayMessageLine("NOTE: The money just transfer will not be available until we verify the transacation.");
   }

   private double promptForTransferAmount()
   {
      Screen screen = getScreen(); // get reference to screen

      // prompt user to enter the first entry
      screen.displayMessage( "\nPlease enter a transfer amount in " + 
         "Dollars (or 0 to cancel) up to maximun of two digits (.00): " );
      double input = keypad.getInputFloat(); 

      // receive for the second entry for data vaildation
      screen.displayMessage( "\nPlease enter the transfer amount again: " );
      double input1 = keypad.getInputFloat();
      if ( input1 != input ) {   //check if the second entry is equal to the first entry, if not the transaction would be cancelled
         screen.displayMessageLine( "\nThe transfer amount did not match. Cancelling transfer action." );
         return CANCELED;
      }

      // check whether the user canceled or entered a valid amount
      if ( input == CANCELED ) 
         return CANCELED;
      else
      {
         return ( double ) input; // return dollar amount 
      } // end else
   }
}
