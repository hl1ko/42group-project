public class SavingAccount extends Account {
    
    private double interestRate = 0.25;

    public SavingAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
    }

    public void setInterestRate(double rate){
        if (rate > 0.0 && rate <= 1.0)
            interestRate = rate;
        else
            throw new IllegalArgumentException("The data given is invalid");
    }

    public double getInterestRate(){
        return interestRate;
    }

}

