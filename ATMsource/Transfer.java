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

      screen.displayMessageLine("\nStarting transferring section...");
      screen.displayMessageLine("You may enter '0' to cancel to transaction during the section.");

      //provide message to prompt user to input values
      screen.displayMessage("\nEnter the target account number: ");

      // receive target account number from user and store it
      targetAccountNumber = keypad.getInput();

      // return to main menu if user input zero
      if ( targetAccountNumber == CANCELED ){
         screen.displayMessageLine( "\nCanceling transaction..." );
         return;
      }

      // check whether target account inputted by user is same as the logged account
      // if not the transaction will be cancelled
      if ( targetAccountNumber == getAccountNumber() ){
         screen.displayMessageLine("\nYou cannot transfer to the same account.");
         screen.displayMessageLine("Transaction cancelled.");
         return;
      }

      // check whether target account inputted by user is exist
      // if not the transaction will be cancelled
      if ( !bankDatabase.checkAccountExist(targetAccountNumber) ){
         screen.displayMessageLine("Target account does not exist.");
         screen.displayMessageLine("Transaction cancelled.");
         return;
      }

      // receive the value inputted by user and store it
      amount = promptForTransferAmount();

      //cancel and back to main menu if amount inputted by user is zero
      if ( amount == CANCELED ){
         screen.displayMessageLine( "Transaction cancelled." );
         return;
      }

      // cancel and back to main menu if the available balance of the account is lower than the amount inputted
      // i.e. user inputted amount > available balance
      if ( bankDatabase.getAvailableBalance(getAccountNumber()) < amount ){
         screen.displayMessageLine("\nInsufficient funds in your account.");
         screen.displayMessageLine( "\nCanceling transaction..." );
         return;
      }

      // prompt user to input the money transfer recipient account number and its transfer amount
      screen.displayMessageLine("\n----------------------------------------------------------------------");
      screen.displayMessageLine("Recipient account number: " + targetAccountNumber);

      // prompt user the money values that will be tranferred
      screen.displayMessage("Transfer amount         : "); 
      screen.displayDollarAmount(amount);
      screen.displayMessageLine("");
      screen.displayMessageLine("----------------------------------------------------------------------");
      screen.displayMessageLine("Please check the transfer info above.");

      // let user to decide whether the transaction will continue or not
      screen.displayMessageLine("\nEnter 1 to confirm the transfer action, or enter other number to cancel.");
      screen.displayMessage("Your input: ");

      if ( keypad.getInput() != 1 ){
         screen.displayMessageLine( "\nCanceling transaction..." );
         return;  // cancel the transaction if the user input 1
      }

      // add the amount to the target account, and debit the amount from the user logged in.
      bankDatabase.transfer(getAccountNumber(), targetAccountNumber, amount);

      // prompt user that the transaction is completed.
      screen.displayMessageLine("\nTransfer success.");
   }

   private double promptForTransferAmount()
   {
      Screen screen = getScreen(); // get reference to screen

      // prompt user to enter the first entry
      screen.displayMessage( "\nPlease enter a transfer amount in dollars up to maximum of two digits (.00): ");
      double input = keypad.getInputFloat();
      
      if ( input <= 0 ){
         screen.displayMessageLine("\nThe amount must be greater than 0.");
         return CANCELED;
      }

      // receive for the second entry for data vaildation
      screen.displayMessage( "\nPlease re-enter the transfer amount: " );
      double input1 = keypad.getInputFloat();

      if ( input1 != input ) {   //check if the second entry is equal to the first entry, if not the transaction would be cancelled
         screen.displayMessageLine( "\nThe transfer amount did not match.");
         return CANCELED;
      }

      return ( double ) input; // return dollar amount 

   }
}
