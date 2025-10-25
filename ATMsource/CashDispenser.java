public class CashDispenser 
{
   // the default initial number of bills in the cash dispenser
   private final static int[] __init_BillsCount = {100,100,100}; // initial bills amount of each bill denomination. used in constructor
   private final static int[] BillsDeno = {1000, 500, 100}; // bills denomonation. HKD$1000, HKD$500, HKD$100
   private final static int[] BillsDenoMultiple = {(1000/500), (500/100), 0}; // {2, 5, 0} // convert ratio from larger bills denomination to smaller one.
   private final int[] BillsCount; // amount of each bill denomination in atm machine

   
   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser()
   {
      BillsCount = __init_BillsCount; //initialize bills count 
   } // end CashDispenser constructor
   private int getCashSum(){
      return (BillsCount[0] * BillsDeno[0] + BillsCount[1] * BillsDeno[1] + BillsCount[2] * BillsDeno[2]); 
   } // ^^^^get total money in atm by using amount of specific bills multiplied with bills denomination
   private int[] getWithdrawlBillsAmount( int amount ){
      int[] WithdrawlBills = {0, 0, 0};
      WithdrawlBills[0] = (amount/ BillsDeno[0]); // get amount of $1000 bills that is withdrawing
      amount = amount % BillsDeno[0]; // get remaining amount of withdrawing money
      WithdrawlBills[1] = (amount/ BillsDeno[1]); // get amount of $500 bills that is withdrawing
      amount = amount % BillsDeno[1]; // get remaining amount of withdrawing money
      WithdrawlBills[2] = (amount/ BillsDeno[2]); // get amount of $100 bills that is withdrawing
      return WithdrawlBills;
   }
   private void calculateBills( int[] WithdrawlBills){
      for( int i = 0 ; i < WithdrawlBills.length ; i ++ ){
         if(BillsCount[i] < WithdrawlBills[i] && i < (WithdrawlBills.length-1)){ // if currently element(bill denominations) can not handle whole withdrawl
            int DeltaBills = WithdrawlBills[i] - BillsCount[i]; // get difference between withdraw bills and bills existing in atm machine
            WithdrawlBills[i] = WithdrawlBills[i] - DeltaBills; // subtract current element in withdraw bills to make it congruent
            WithdrawlBills[i+1] = DeltaBills * BillsDenoMultiple[i]; // use difference to miltiplie with the ratio and add it to next element
         }
         BillsCount[i] -= WithdrawlBills[i]; // subtract bills amount
      }
   }

   public boolean AnyBillsAvaliable (){
      return !((BillsCount[0] == 0) && (BillsCount[1] == 0) && (BillsCount[2] == 0)); // if no bills exist in ATM then return false.
   }
   public boolean isSufficientCashAvailable( int amount ){
      return (getCashSum()) >= (amount); // check if money in atm can handle the withdraw
   }
   public boolean canBillsHandleTheJob( int amount ){ // check if current existing bills in machine can handle job. e.g atm has 2*$1000 bills in atm only. can it handle withdraw action of $1800?
      if((amount % BillsDeno[1]) <= (BillsCount[2] * BillsDeno[2])){ // check if amount of $100 bills is enough to handle the rest of withdraw when atm does not have $500
            if(((amount % BillsDeno[0]) - (amount % BillsDeno[1])) <= (BillsCount[1] * BillsDeno[1])){ // same logic. this time is $500, $1000
               return true;
            }
         }
         return false;
   }
   public String showAvaliableBills(){ // provided show dollar method is not capable here as it shows decimal places
      String outputString = ""; // get empty String
      for(int i = 0; i < BillsCount.length; i++){ // set up loop to check each billsCount element.
         if(BillsCount[i] > 0){ // if it bigger than 0, that bill can be withdrawn.
            outputString = outputString + "$" + Integer.toString(BillsDeno[i]) + "  "; // overlap it to the String with value of bills denomination and $ sign
         }
      }
      return outputString; // return it to its caller
   }
   public void dispenseCash( int amount)
   {
      int[] withdrawCash = getWithdrawlBillsAmount(amount); // get amount of withdrawing bills first
      if(isSufficientCashAvailable(amount)){ // check if money is enough. actually if bills are capable to handle current withdraw action are also checked at withdraw class
         calculateBills(withdrawCash); // subtract billsCount
      }
   }
} // end class CashDispenser