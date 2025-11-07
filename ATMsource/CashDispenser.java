public class CashDispenser 
{
   // the default initial number of bills in the cash dispenser
   private final static int[] __init_BillsCount = {1,1,11};
   private final static int[] BillsDeno = {1000, 500, 100};
   private final static int[] BillsDenoMultiple = {(1000/500), (500/100), 0}; // {2, 5, 0}
   private final int[] BillsCount;

   
   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser()
   {
      BillsCount = __init_BillsCount;
   } // end CashDispenser constructor
   private int getCashSum(){
      return (BillsCount[0] * BillsDeno[0] + BillsCount[1] * BillsDeno[1] + BillsCount[2] * BillsDeno[2]);
   }
   private int[] getWithdrawlBillsAmount( int amount ){
      int[] WithdrawlBills = {0, 0, 0};
      WithdrawlBills[0] = (amount/ BillsDeno[0]);
      amount = amount % BillsDeno[0];
      WithdrawlBills[1] = (amount/ BillsDeno[1]);
      amount = amount % BillsDeno[1];
      WithdrawlBills[2] = (amount/ BillsDeno[2]);
      return WithdrawlBills;
   }
   private void calculateBills( int[] WithdrawlBills){
      for( int i = 0 ; i < WithdrawlBills.length ; i ++ ){
         if(BillsCount[i] < WithdrawlBills[i] && i < (WithdrawlBills.length-1)){
            int DeltaBills = WithdrawlBills[i] - BillsCount[i];
            WithdrawlBills[i] = WithdrawlBills[i] - DeltaBills;
            WithdrawlBills[i+1] = WithdrawlBills[i+1] + DeltaBills * BillsDenoMultiple[i];
         }
         BillsCount[i] -= WithdrawlBills[i];
      }
   }
   private int[] trialrunBills( int[] WithdrawlBills){
      var mimicBills = new int[3];
      mimicBills = BillsCount.clone();
      System.out.println("Mimic: " + mimicBills[0] + ", " + mimicBills[1] + ", " + mimicBills[2]);
      for( int i = 0 ; i < WithdrawlBills.length ; i ++ ){
         if(mimicBills[i] < WithdrawlBills[i] && i < (WithdrawlBills.length-1)){
            int DeltaBills = WithdrawlBills[i] - mimicBills[i];
            WithdrawlBills[i] = WithdrawlBills[i] - DeltaBills;
            WithdrawlBills[i+1] = WithdrawlBills[i+1] + DeltaBills * BillsDenoMultiple[i];
         }
         mimicBills[i] -= WithdrawlBills[i];
      }
      System.out.println("Mimic: " + mimicBills[0] + ", " + mimicBills[1] + ", " + mimicBills[2]);
      return mimicBills;
   }

   public boolean AnyBillsAvaliable (){
      return !((BillsCount[0] == 0) && (BillsCount[1] == 0) && (BillsCount[2] == 0));
   }
   public boolean isSufficientCashAvailable( int amount ){
      return (getCashSum()) >= (amount);
   }
   public boolean canBillsHandleTheJob( int amount ){
      var mimicBills = new int[3];
      int[] withdrawCash = getWithdrawlBillsAmount(amount);
      mimicBills = trialrunBills( withdrawCash);
      for( int i = 0 ; i < mimicBills.length ; i ++ ){
         if(mimicBills[i] < 0){
            return false;
         }
      }
      return true;
   }
   public String showAvaliableBills(){
      String outputString = "";
      for(int i = 0; i < BillsCount.length; i++){
         if(BillsCount[i] > 0){
            outputString = outputString + "$" + Integer.toString(BillsDeno[i]) + "  "; 
         }
      }
      return outputString;
   }
   public void dispenseCash( int amount)
   {
      System.out.println("Bill: " + BillsCount[0] + ", " + BillsCount[1] + ", " + BillsCount[2]);
      int[] withdrawCash = getWithdrawlBillsAmount(amount);
      if(isSufficientCashAvailable(amount)){
         calculateBills(withdrawCash);
      }
      System.out.println("Bill: " + BillsCount[0] + ", " + BillsCount[1] + ", " + BillsCount[2]);
   }
} // end class CashDispenser