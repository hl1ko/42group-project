public class SavingAccount extends Account {
    
    private double interestRate = 0.25;

    public SavingAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
    }

    public void setInterestRate(double rate){
        interestRate = rate;
    }

    public double getInterestRate(){
        return interestRate;
    }

}

