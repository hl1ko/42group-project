public class CashDispenser 
{
   // the default initial number of bills in the cash dispenser

   /* private final static int INITIAL_100_COUNT = 10;
   private final static int INITIAL_500_COUNT = 4;
   private final static int INITIAL_1000_COUNT = 0;
   public int cash100Count; // number of $100 bills remaining
   public int cash500Count;
   public int cash1000Count; */

   private final static int[] __init_BillsCount = {1,1,1};
   private final static int[] BillsDeno = {1000, 500, 100};
   private final static int[] BillsDenoMultiple = {(1000/500), (500/100), 0}; // {2, 5, 0}
   private int[] BillsCount;

   
   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser()
   {
      BillsCount = __init_BillsCount;

      /*cash100Count = INITIAL_100_COUNT; // set count attribute to default
      cash500Count = INITIAL_500_COUNT;
      cash1000Count = INITIAL_1000_COUNT; */
      
   } // end CashDispenser constructor

   private int[] getWithdrawlBillsAmount( int amount ){
      int[] Withdrawl = {0, 0, 0};
      Withdrawl[0] = (amount/ 1000);
      amount = amount % 1000;
      Withdrawl[1] = (amount/ 500);
      amount = amount % 500;
      Withdrawl[2] = (amount/ 100);
      return Withdrawl;
   }

   private void minusBills( int[] Withdrawal){
      for( int i = 0 ; i < 3 ; i ++ ){
         if(BillsCount[i] < Withdrawal[i] && i < 2){
            int delta = Withdrawal[i] - BillsCount[i];
            Withdrawal[i] = Withdrawal[i] - delta;
            Withdrawal[i+1] = delta * BillsDenoMultiple[i];
         }
         BillsCount[i] -= Withdrawal[i];
      }
   }
   public boolean AnyBillsAvaliable (){
      if((BillsCount[0] == 0) && (BillsCount[1] == 0) && (BillsCount[2] == 0)){
         return false;
      }
      return true;
   }
   public boolean isSufficientCashAvailable( int amount ){
      if(((BillsCount[0] * BillsDeno[0] + BillsCount[1] * BillsDeno[1] + BillsCount[2] * BillsDeno[2] )) < (amount)){
         return false;
      }
      return true;
   }

   public String showAvaliableBills(){
      String outputString = "Available banknote denominations: ";
      for(int i = 0; i < 3; i++){
         if(BillsCount[i] > 0){
            outputString = outputString + "$" + Integer.toString(BillsDeno[i]);
            if(i < 2){
               outputString = outputString + ", ";
            }     
         }
         
      }
      return outputString;
   }

   public void dispenseCash( int amount)
   {
      //System.out.println("----TEST-------Returned BillsCount: " + BillsCount[0] + ", " + BillsCount[1] + ", " + BillsCount[2]);

      int[] withdrawCash = getWithdrawlBillsAmount(amount);
      if(isSufficientCashAvailable(amount)){
         minusBills(withdrawCash);
      }
      
      //System.out.println("----TEST-------Returned BillsCount: " + BillsCount[0] + ", " + BillsCount[1] + ", " + BillsCount[2]);
   }




   // simulates dispensing of specified amount of cash
   /* 

   public void OLD____dispenseCash( int amount )
   {
      while (amount >= 100){
         if (amount >= 1000 && cash1000Count > 0){
            cash1000Count -= 1;
            amount -= 1000;
         } else if (amount >= 500 && cash500Count > 0){
            cash500Count -= 1;
            amount -= 500;
         } else if (amount >= 100 && cash100Count > 0){
            cash100Count -= 1;
            amount -= 100;
         } else {
            // Not enough bills to continue
            break;
         }
      }
   } // end method dispenseCash

   // indicates whether cash dispenser can dispense desired amount
   public boolean OLD____isSufficientCashAvailable( int amount )
   {
      int cash100Needed = 0;
      int cash500Needed = 0;
      int cash1000Needed = 0;

      // Count how many of each bill would be needed
      while (amount >= 100) {
         if (amount >= 1000 && cash1000Count > cash1000Needed) {
            cash1000Needed++;
            amount -= 1000;
         } else if (amount >= 500 && cash500Count > cash500Needed) {
            cash500Needed++;
            amount -= 500;
         } else if (amount >= 100 && cash100Count > cash100Needed) {
            cash100Needed++;
            amount -= 100;
         } else {
            return false;
         }
      }

      return cash100Needed <= cash100Count && cash500Needed <= cash500Count && cash1000Needed <= cash1000Count;
   } // end method isSufficientCashAvailable 

    */
} // end class CashDispenser