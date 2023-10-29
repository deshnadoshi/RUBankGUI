package com.example.rubankgui;
import java.text.DecimalFormat;

/**
 * Defines a Checking account for a user based on their information and balance.
 * @author Deshna Doshi, Haejin Song
 */

public class Checking extends Account {
    private static final double INTEREST_RATE = 0.01;
    private static final int FEE = 12;
    private static final int MIN_AGE = 16;
    private static final int MONTHS = 12;
    private static final int EQUAL_COMPARATOR = 0;
    private static final int INVALID_BALANCE  = 0;
    private static final int MIN_BALANCE = 1000;

    /**
     * Constructor to initialize the instance variables.
     * @param holder the Profile object that includes an account holder's information.
     * @param balance the balance in the account.
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Provides the monthly fee associated with the account.
     * @return the monthly fee.
     */
    public double monthlyFee() {
        return FEE;
    }

    /**
     * Provides the monthly interest rate associated with the account.
     * @return the monthly interest rate.
     */
    public double monthlyInterest() {
        return INTEREST_RATE;
    }

    /**
     * Determines if the account holder is at least 16.
     * @return true if the holder is at least 16, false otherwise.
     */
    @Override
    public boolean checkAge() {
        if (holder.age() >= MIN_AGE){
            return true;
        }
        return false;
    }

    /**
     * Determines if the balance amount if valid.
     * @return true if the balance is more than 0, false otherwise.
     */
    @Override
    public boolean balanceIsValid() {
        return !(balance <= INVALID_BALANCE);
    }

    /**
     * Updates the balance with the interest rate and fees applied.
     */
    @Override
    public void updateBalance() {
        double monthlyInterest = (balance * INTEREST_RATE) / MONTHS;
        balance += monthlyInterest; // add the interest to the balance
        if (checkApplyFee()){
            balance -= FEE;
        }
    }

    /**
     * Calculates the monthly interest that applies to the account.
     * @return the monthly interest.
     */
    @Override
    public double calcInterest() {
        return balance * (INTEREST_RATE / MONTHS);
    }

    /**
     * Calculates the fee that applies to the account.
     * @return the fee that applies to the account.
     */
    @Override
    public int calcFee() {
        if (checkApplyFee()){
            return FEE;
        }
        return 0;
    }

    /**
     * Determines if the fee applies, based on the balance.
     * @return true if the balance is at least 1000, false otherwise.
     */
    public boolean checkApplyFee(){
        return !(balance >= MIN_BALANCE); // if the balance is more than 500 don't apply the fee
    }

    /**
     * Determines if two accounts are equivalent/of the same type.
     * @param compareChecking the object being compared.
     * @return true if the accounts are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object compareChecking){
        // if (compareChecking == null) return false;
        Class compareCheckingClass = compareChecking.getClass();
        if (!CollegeChecking.class.equals(compareChecking.getClass()) && !Checking.class.equals(compareChecking.getClass())) {
            return false;
        }

        Account checking = (Account) compareChecking; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (checking.getHolder().getFname().equalsIgnoreCase(this.holder.getFname())){
            fnameMatch = true;
        }
        if (checking.getHolder().getLname().equalsIgnoreCase(this.holder.getLname())){
            lnameMatch = true;
        }
        if (checking.getHolder().getDOB().compareTo(this.holder.getDOB()) == EQUAL_COMPARATOR){
            dobMatch = true;
        }

        return (fnameMatch && lnameMatch && dobMatch);
    }

    /**
     * Determines if two accounts are equivalent/of the same type
     * Makes sure to check that C and CC are not the same type of account..
     * @param compareChecking the object being compared.
     * @return true if the accounts are equivalent, false otherwise.
     */
    public boolean equalsAdvanced(Object compareChecking){
        if (!Checking.class.equals(compareChecking.getClass())) {
            return false;
        }

        Account checking = (Account) compareChecking; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (checking.getHolder().getFname().equalsIgnoreCase(this.holder.getFname())){
            fnameMatch = true;
        }
        if (checking.getHolder().getLname().equalsIgnoreCase(this.holder.getLname())){
            lnameMatch = true;
        }
        if (checking.getHolder().getDOB().compareTo(this.holder.getDOB()) == EQUAL_COMPARATOR){
            dobMatch = true;
        }

        return (fnameMatch && lnameMatch && dobMatch);
    }

    /**
     * Determines if two accounts have the same holder information.
     * @param checking the account to be compared.
     * @return -2 if the account is null, -1 if checking is less than holder,
     * 0 if checking is equal to holder, and 1 if checking is greater than holder.
     */
    @Override
    public int compareTo(Account checking) {
        if (checking == null)
            return -2;
        return checking.getHolder().compareTo(this.holder);
    }

    /**
     * Displays the account information.
     * @return a String of the account information.
     */
    @Override
    public String toString(){
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        String balanceFormat = currencyFormat.format(balance);

        return "Checking::" + holder.toString() + "::Balance " + balanceFormat;
    }

    /**
     * Updates the balance with the fees and monthly interest, displays the account information
     * @return @return a String of the account information, with fees and interest included.
     */
    @Override
    public String netBalanceToString(){
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        String balanceFormat = currencyFormat.format(balance);
        String feeFormat = currencyFormat.format(calcFee());
        String interestFormat = currencyFormat.format(calcInterest());

        return "Checking::" + holder.toString() + "::Balance " + balanceFormat + "::fee " + feeFormat +
                "::monthly interest " + interestFormat;
    }


}
