public class ChequeAccount extends Account{
    
    private double limitPerCheque = 50000.0;

    public ChequeAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
    }

    public void setChequeLimit(double amount){
        limitPerCheque = amount;
    }

    public double getChequeLimit(){
        return limitPerCheque;
    }

}