import java.math.BigDecimal;
import java.math.RoundingMode;

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
        int targetAccountNumber = 0;
        screen.displayMessage("Please enter Target Account Number: ");
        do { 
            targetAccountNumber = keypad.getInput();
            if(targetAccountNumber == getAccountNumber()){
                screen.displayMessageLine("You can not transfer to yourself.");
                screen.displayMessage("Please enter again: ");
            }
            if(bankDatabase.checkAccountExist(targetAccountNumber) == false){
                screen.displayMessageLine("Target Account does not exist.");
                screen.displayMessage("Please enter again: ");
            }
        } while (targetAccountNumber == getAccountNumber() || bankDatabase.checkAccountExist(targetAccountNumber) == false);
        
        amount = promptForTransferAmount();

        if(bankDatabase.getAvailableBalance(getAccountNumber()) < amount){
         screen.displayMessageLine("You do not have sufficient balance.");
        }else if(amount == 0){
         screen.displayMessageLine("Transfer cancelled.");
        }else{
         bankDatabase.debit(getAccountNumber(), amount);
         bankDatabase.credit(targetAccountNumber, amount);
         screen.displayMessageLine("Transfer success.");
         screen.displayMessageLine("NOTE: The money just transfer will not be available until we verify the transacation.");
        }
    }

   private double promptForTransferAmount()
   {
      Screen screen = getScreen(); // get reference to screen

      // display the prompt
      screen.displayMessage( "\nPlease enter a transfer amount in " + 
         "Dollars (or 0 to cancel) up to maximun of two digits (.00): " );
      float input = keypad.getInputFloat(); // receive input of deposit amount
      go2digits(input);
      // check whether the user canceled or entered a valid amount
      if ( input == CANCELED ) 
         return CANCELED;
      else
      {
         return ( double ) input; // return dollar amount 
      } // end else
   }
   public static double go2digits(double input){
   return BigDecimal.valueOf(input).setScale(2, RoundingMode.FLOOR).doubleValue();
   }
}
