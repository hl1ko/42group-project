public class CashDispenser 
{
   // the default initial number of bills in the cash dispenser
   private final static int INITIAL_100_COUNT = 10;
   private final static int INITIAL_500_COUNT = 4;
   private final static int INITIAL_1000_COUNT = 0;
   public int cash100Count; // number of $100 bills remaining
   public int cash500Count;
   public int cash1000Count;

   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser()
   {
      cash100Count = INITIAL_100_COUNT; // set count attribute to default
      cash500Count = INITIAL_500_COUNT;
      cash1000Count = INITIAL_1000_COUNT;
   } // end CashDispenser constructor

   // simulates dispensing of specified amount of cash
   public void dispenseCash( int amount )
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
   public boolean isSufficientCashAvailable( int amount )
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
} // end class CashDispenser