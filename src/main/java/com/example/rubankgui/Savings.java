package com.example.rubankgui;
import java.text.DecimalFormat;

/**
 * Defines a Savings account for a user based on their information, balance, and loyalty status.
 * @author Deshna Doshi, Haejin Song
 */
public class Savings extends Account {
    protected boolean isLoyal;

    private static final double INTEREST_RATE = 0.04;
    private static final int FEE = 25;
    private static final int MIN_AGE = 16;
    private static final double  LOYAL_INTEREST_RATE = 0.0425; // Interest rate for loyal customers
    private static final int MONTHS = 12;
    private static final int EQUAL_COMPARATOR = 0;
    private static final int NOT_EQUAL = -2;
    private static final int MIN_BALANCE = 500;
    private static final int INVALID_BALANCE = 0;

    /**
     * Constructor to initialize the instance variable.
     * @param isLoyal Customer loyalty metric.
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Default constructor to ensure proper usage for child classes.
     */
    public Savings() {

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
        if(holder.age() >= MIN_AGE) {
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
        if (balance <= INVALID_BALANCE) {
            return false;
        }

        return true;
    }

    /**
     * Updates the balance with the interest rate and fees applied.
     */
    @Override
    public void updateBalance() {
        double monthlyInterest = 0;
        if (isLoyal) {
            monthlyInterest = (LOYAL_INTEREST_RATE / MONTHS) * balance;
            balance += monthlyInterest; // add the interest to the balance
        } else {
            monthlyInterest = (INTEREST_RATE / MONTHS) * balance;
            balance += monthlyInterest; // add the interest to the balance
        }
        if (checkApplyFee()){
            balance -= FEE;
        }
    }

    /**
     * Determines if the fee applies, based on the balance.
     * @return true if the balance is at least 500, false otherwise.
     */
    public boolean checkApplyFee() {
        if (balance >= MIN_BALANCE){
            return false; // if the balance is more than 500 don't apply the fee
        }

        return true;
    }

    /**
     * Calculates the monthly interest that applies to the account.
     * @return the monthly interest.
     */
    public double calcInterest() {
        if (isLoyal) {
            return (LOYAL_INTEREST_RATE / MONTHS) * balance;
        } else {
            return (INTEREST_RATE / MONTHS) * balance;
        }
    }

    /**
     * Checks if the account is loyal
     * @return true if the account is loyal, false otherwise
     */
    public boolean checkLoyalty(){
        if (balance >= MIN_BALANCE){
            isLoyal = true;
            return true;
        } else {
            isLoyal = false;
            return false;
        }
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
     * Determines if two accounts are equivalent/of the same type.
     * @param compareSavings the object being compared.
     * @return true if the accounts are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object compareSavings) {
        // if (compareSavings == null) return false;
        if (!Savings.class.equals(compareSavings.getClass())){
            return false;
        }
        Account savings = (Savings) compareSavings; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (savings.getHolder().getFname().equalsIgnoreCase(holder.getFname())){
            fnameMatch = true;
        }
        if (savings.getHolder().getLname().equalsIgnoreCase(holder.getLname())){
            lnameMatch = true;
        }
        if (savings.getHolder().getDOB().compareTo(holder.getDOB()) == EQUAL_COMPARATOR){
            dobMatch = true;
        }

        return (fnameMatch && lnameMatch && dobMatch);
    }

    /**
     * Determines if two accounts are equivalent/of the same type
     * Same as equals()
     * @param compareSavings the object being compared.
     * @return true if the accounts are equivalent, false otherwise.
     */
    public boolean equalsAdvanced(Object compareSavings) {
        // if (compareSavings == null) return false;
        if (!Savings.class.equals(compareSavings.getClass())){
            return false;
        }
        Account savings = (Savings) compareSavings; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (savings.getHolder().getFname().equalsIgnoreCase(holder.getFname())){
            fnameMatch = true;
        }
        if (savings.getHolder().getLname().equalsIgnoreCase(holder.getLname())){
            lnameMatch = true;
        }
        if (savings.getHolder().getDOB().compareTo(holder.getDOB()) == EQUAL_COMPARATOR){
            dobMatch = true;
        }

        return (fnameMatch && lnameMatch && dobMatch);
    }

    /**
     * Determines if two accounts have the same holder information.
     * @param savings the account to be compared.
     * @return true if the holder information of any two accounts is the same, false otherwise.
     */
    @Override
    public int compareTo(Account savings) {
        if (savings == null)
            return NOT_EQUAL;
        int profileCompare = savings.getHolder().compareTo(this.holder);
        boolean typeCompare = savings.getClass().equals(this.getClass());
        // makes sure that a MM doesn't get confused for a savings, if they have the same profile info

        if (profileCompare == EQUAL_COMPARATOR && typeCompare) {
            return EQUAL_COMPARATOR;
        }

        return NOT_EQUAL; // if either profile is not the same or the type is not the same
    }

    /**
     * Displays the account information.
     * @return a String of the account information.
     */
    @Override
    public String toString() {
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        String balanceFormat = currencyFormat.format(balance);

        if (isLoyal) {
            return "Savings::" + holder.toString() + "::Balance " + balanceFormat + "::is loyal";
        }

        return "Savings::" + holder.toString() + "::Balance " + balanceFormat;
    }


    /**
     * Updates the balance with the fees and monthly interest, displays the account information
     * @return a String of the account information, with fees and interest included.
     */
    @Override
    public String netBalanceToString() {
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        String balanceFormat = currencyFormat.format(balance);
        String feeFormat = currencyFormat.format(calcFee());
        String interestFormat = currencyFormat.format(calcInterest());

        if (isLoyal) { // isLoyal status is entered by the user
            return "Savings::" + holder.toString() + "::Balance " + balanceFormat + "::is loyal" + "::fee "
                    + feeFormat + "::monthly interest " + interestFormat;
        }
        return "Savings::" + holder.toString() + "::Balance " + balanceFormat + "::fee "
                + feeFormat + "::monthly interest " + interestFormat;
    }

}
