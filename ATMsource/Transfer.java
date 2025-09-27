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
        do { 
            System.out.print("Please enter Target Account Number: ");
            targetAccountNumber = keypad.getInput();
            if(targetAccountNumber == getAccountNumber()){
                System.out.println("You cant transfer to yourself.");
            }
            if(bankDatabase.checkAccountExist(getAccountNumber()) == false){
                System.out.println("Target Account does not exist.");
            }
            //
        } while (targetAccountNumber == getAccountNumber() || bankDatabase.checkAccountExist(targetAccountNumber) == false);
        amount = promptForTransferAmount();
        
        if(bankDatabase.getAvailableBalance(getAccountNumber()) < amount){
         System.out.print("You do not have sufficient balance.");
        }else{
         bankDatabase.debit(getAccountNumber(), amount);
         bankDatabase.credit(targetAccountNumber, amount);
        }
    }

   private double promptForTransferAmount()
   {
      Screen screen = getScreen(); // get reference to screen

      // display the prompt
      screen.displayMessage( "\nPlease enter a transfer amount in " + 
         "CENTS (or 0 to cancel): " );
      int input = keypad.getInput(); // receive input of deposit amount
      
      // check whether the user canceled or entered a valid amount
      if ( input == CANCELED ) 
         return CANCELED;
      else
      {
         return ( double ) input / 100; // return dollar amount 
      } // end else
   }
}
