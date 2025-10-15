public class ChequeAccount extends Account{
    
    private double limitPerCheque = 50000.0;

    // ChequeAccount constructor
    public ChequeAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance); //call superclass's constructor for initializing all the variables
    }

    public void setChequeLimit(double amount){ 
        if (amount >= 0.0)
            limitPerCheque = amount;
        else
            throw new IllegalArgumentException("The data given is invalid");
    }

    public double getChequeLimit(){
        return limitPerCheque;
    }

}