
public class Transfer extends Transaction{
    private double amount; // amount to transfer
    private Keypad keypad; // reference to keypad
    private final static int CANCELED = 0; // constant for cancel option

    public Transfer( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad)
   {
      super( userAccountNumber, atmScreen, atmBankDatabase );
      keypad = atmKeypad;
   } // end BalanceInquiry constructor

    public void execute() {

        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // get the available balance for the account involved
        double availableBalance = 
        bankDatabase.getAvailableBalance( getAccountNumber() );

        // get the total balance for the account involved
        double totalBalance = 
        bankDatabase.getTotalBalance( getAccountNumber() );
        int targetAccountNumber = -1;
        screen.displayMessage("Please enter Target Account Number (0 to cancel): ");
        do { 
            targetAccountNumber = keypad.getInput();
            if(targetAccountNumber == 0){
                screen.displayMessageLine("Transfer cancelled");
                return;
            }
            if(targetAccountNumber == getAccountNumber()){
                screen.displayMessageLine("You can not transfer to yourself.");
                screen.displayMessage("Please enter again: ");
            }
            if(bankDatabase.checkAccountExist(targetAccountNumber) == false){
                screen.displayMessageLine("Target Account does not exist.");
                screen.displayMessage("Please enter again: ");
            }
        } while ((targetAccountNumber == getAccountNumber() || bankDatabase.checkAccountExist(targetAccountNumber) == false) && targetAccountNumber != 0);
        
         amount = promptForTransferAmount();
         if(amount == CANCELED){
         screen.displayMessageLine("Transfer cancelled.");
         return;
         }
         if(bankDatabase.getAvailableBalance(getAccountNumber()) < amount){
         screen.displayMessageLine("You do not have sufficient balance.");
         return;
         }

         screen.displayMessageLine("=====");
         screen.displayMessageLine("Recipient ID: " + targetAccountNumber);
         screen.displayMessageLine("Transfer amount: " + amount);
         screen.displayMessageLine("=====");
         screen.displayMessageLine("Please double check the transfer info above.");
         screen.displayMessage("Enter 1 to confirm this transfer action: ");
         if(keypad.getInput() != 1){
            screen.displayMessageLine("Transfer action canceled.");
            return;
         }

         bankDatabase.debit(getAccountNumber(), amount);
         bankDatabase.credit(targetAccountNumber, amount);
         screen.displayMessageLine("Transfer success.");
         screen.displayMessageLine("NOTE: The money just transfer will not be available until we verify the transacation.");
    }

   private double promptForTransferAmount()
   {
      Screen screen = getScreen(); // get reference to screen

      // display the prompt
      screen.displayMessage( "\nPlease enter a transfer amount in " + 
         "Dollars (or 0 to cancel) up to maximun of two digits (.00): " );
      float input = keypad.getInputFloat(); // receive input of deposit amount
      screen.displayMessage( "\nPlease enter the transfer amount again: " );
      float input1 = keypad.getInputFloat(); // receive input of deposit amount
      if(input1 != input){
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
