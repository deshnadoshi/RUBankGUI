package com.example.rubankgui;
import java.text.DecimalFormat;

/**
 * Defines a College Checking account for a user based on their information, balance, and college campus.
 * @author Deshna Doshi, Haejin Song
 */

public class CollegeChecking extends Checking {
    private Campus campus;
    private static final int EQUAL_COMPARATOR = 0;
    private static final double INTEREST_RATE = 0.01;
    private static final int FEE = 0; // No monthly fee for CollegeChecking
    private static final int MONTHS = 12;
    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 24;

    /**
     * Constructor to initialize the instance variable.
     * @param campus the Campus associated with the College Checking account.
     */
    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
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
     * Provides the campus code associated with the account.
     * @return the campus code (0, 1, 2, representing NB, Newark, Camden respectively).
     */
    public int getCampusCode() {
        return campus.getCode();
    }

    /**
     * Determines if the account holder is at least 16 and under 24.
     * @return true if the holder is at least 16 and under 24, false otherwise.
     */
    @Override
    public boolean checkAge() {
        if (holder.age() >= MIN_AGE && holder.age() <= MAX_AGE){
            return true;
        }
        return false;
    }

    /**
     * Determines if the balance amount if valid.
     * Same implementation as parent class, Checking.
     * @return true if the balance is more than 0, false otherwise.
     */
    @Override
    public boolean balanceIsValid() {
        return super.balanceIsValid();
    }


    /**
     * Updates the balance with the interest rate and fees applied.
     */
    @Override
    public void updateBalance() {
        double monthlyInterest = (balance * INTEREST_RATE) / MONTHS;
        balance += monthlyInterest; // add the interest to the balance
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
     * Calculates the fee that applies to the account. (For a CollegeChecking, the fee is 0)
     * @return 0, the fee of a CollegeChecking account.
     */
    @Override
    public int calcFee() {
        return 0;
    }

    /**
     * Displays the account information.
     * @return a String of the account information.
     */
    @Override
    public String toString(){
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        String balanceFormat = currencyFormat.format(balance);

        return "College Checking::" + holder.toString() + "::Balance " + balanceFormat + "::" + campus.toString();
    }

    /**
     * Updates the balance with the fees and monthly interest, displays the account information
     * @return a String of the account information, with fees and interest included.
     */
    @Override
    public String netBalanceToString(){
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        String balanceFormat = currencyFormat.format(balance);
        String feeFormat = currencyFormat.format(calcFee());
        String interestFormat = currencyFormat.format(calcInterest());

        return "College Checking::" + holder.toString() + "::Balance " + balanceFormat + "::" + campus.toString()
                + "::fee " + feeFormat + "::monthly interest " + interestFormat;
    }

    @Override
    public boolean equals(Object compareCollegeChecking){
        // if (compareChecking == null) return false;
        //Class compareCollegeCheckingClass = compareCollegeChecking.getClass();
        if (!CollegeChecking.class.equals(compareCollegeChecking.getClass()) && !Checking.class.equals(compareCollegeChecking.getClass())){
            return false;
        }

        Account collchecking = (Account) compareCollegeChecking; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (collchecking.getHolder().getFname().equalsIgnoreCase(this.holder.getFname())){
            fnameMatch = true;
        }
        if (collchecking.getHolder().getLname().equalsIgnoreCase(this.holder.getLname())){
            lnameMatch = true;
        }
        if (collchecking.getHolder().getDOB().compareTo(this.holder.getDOB()) == EQUAL_COMPARATOR){
            dobMatch = true;
        }

        return (fnameMatch && lnameMatch && dobMatch);
    }

    /**
     * Determines if two accounts are equivalent/of the same type
     * Makes sure to check that C and CC are not the same type of account..
     * @param compareCollegeChecking the object being compared.
     * @return true if the accounts are equivalent, false otherwise.
     */
    public boolean equalsAdvanced(Object compareCollegeChecking) {
        if (!CollegeChecking.class.equals(compareCollegeChecking.getClass())){
            return false;
        }

        Account collchecking = (Account) compareCollegeChecking; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (collchecking.getHolder().getFname().equalsIgnoreCase(this.holder.getFname())){
            fnameMatch = true;
        }
        if (collchecking.getHolder().getLname().equalsIgnoreCase(this.holder.getLname())){
            lnameMatch = true;
        }
        if (collchecking.getHolder().getDOB().compareTo(this.holder.getDOB()) == EQUAL_COMPARATOR){
            dobMatch = true;
        }

        return (fnameMatch && lnameMatch && dobMatch);
    }

}
