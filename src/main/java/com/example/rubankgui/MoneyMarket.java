package com.example.rubankgui;
import java.text.DecimalFormat;

/**
 * Defines a bank account for a user based on their information, balance, loyalty status, and withdrawal amounts.
 * @author Deshna Doshi, Haejin Song
 */
public class MoneyMarket extends Savings {
    private int withdrawal; //number of withdrawals
    private static final double INTEREST_RATE = 0.045;
    private static final int FEE = 25;
    private static final int MIN_AGE = 16;
    private static final double  LOYAL_INTEREST_RATE = 0.0475; // Interest rate for loyal customers
    private static final int EXCEED_WITHDRAWAL_FEE = 10; // If the withdrawal > 3, then $10 fee is deducted.
    private static final int EQUAL_COMPARATOR = 0;
    private static final int NOT_EQUAL = -2;
    private static final int MIN_BALANCE = 2000;
    private static final int MAX_WITHDRAWAL = 3;
    private static final int INVALID_BALANCE  = 0;
    private static final int MONTHS = 12;

    /**
     * Constructor to initialize the instance variable.
     * @param withdrawal Number of withdrawals.
     */
    public MoneyMarket(Profile holder, double balance, boolean isLoyal, int withdrawal) {
        super(holder, balance, true);
        isLoyal = checkLoyalty();
        this.withdrawal = withdrawal;
    }

    /**
     * Provides the monthly fee associated with the account.
     * @return the monthly fee.
     */
    public double monthlyFee(){
        return FEE;
    }

    /**
     * Provides the monthly interest rate associated with the account.
     * @return the monthly interest rate.
     */
    public double monthlyInterest(){
        return INTEREST_RATE;
    }

    /**
     * Determine if the user is old enough to have an account.
     * @return true if the profile holder is above 16, false otherwise.
     */
    @Override
    public boolean checkAge() {
        return holder.age() >= MIN_AGE;
    }

    /**
     * Determines if the balance amount if valid.
     * @param openingAccount boolean to designate if this is the first time opening an account or not.
     * @return true if the balance is more than 0, false otherwise.
     */
    public boolean balanceIsValid(boolean openingAccount) {
        if (openingAccount) { // Checking if this is the first time an account is being opened
            return balance >= MIN_BALANCE;
        } else return !(balance <= INVALID_BALANCE);
    }

    /**
     * Adds one to the amount of withdrawals.
     */
    public void addWithdrawal() {
        this.withdrawal += 1;
    }

    /**
     * Resets the amount of withdrawals to zero.
     */
    public void resetWithdrawal() {
        this.withdrawal = 0;
    }

    /**
     * Determine if the number of withdrawals will lead to a fee being deducted.
     * If yes, deduct the fee. Otherwise, do nothing.
     */
    private void checkWithdrawal() {
        if (withdrawal > MAX_WITHDRAWAL){
            balance -= EXCEED_WITHDRAWAL_FEE;
        }
    }

    /**
     * Calculates the monthly interest that applies to the account.
     * @return the monthly fee that applies to the account.
     */
    @Override
    public double calcInterest() {
        if (checkLoyalty()) {
            return (LOYAL_INTEREST_RATE / MONTHS) * balance;
        } else {
            return (INTEREST_RATE / MONTHS) * balance;
        }
    }

    /**
     * Calculates the fee that applies to the account.
     * @return the fee that applies to the account.
     */
    @Override
    public int calcFee() {
        if (checkApplyFee()){
            if(withdrawal > MAX_WITHDRAWAL && balance < MIN_BALANCE){
                return FEE + EXCEED_WITHDRAWAL_FEE;
            } else if (withdrawal > MAX_WITHDRAWAL){
                return EXCEED_WITHDRAWAL_FEE;
            } else {
                return FEE;
            }
        }
        return 0;
    }

    /**
     * Determines if the fee applies, based on the balance.
     * @return true if the balance is at least 2000 and the withdrawals are less than equal to 3, false otherwise.
     */
    @Override
    public boolean checkApplyFee(){
        return !(balance >= MIN_BALANCE) || withdrawal > MAX_WITHDRAWAL; // if the balance is more than 2000 and withdrawal <= 3 don't apply the fee
    }

    /**
     * Determines if a customer is loyal based on their account balance.
     * @return true if the account holder is loyal, false otherwise.
     */
    @Override
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
     * Determines if two accounts are equivalent/of the same type.
     * @param compareMoneyMarket the object being compared.
     * @return true if the accounts are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object compareMoneyMarket){
        // if (compareMoneyMarket == null) return false;
        if (!MoneyMarket.class.equals(compareMoneyMarket.getClass())) {
            return false;
        }

        Account moneymarket = (Savings) compareMoneyMarket; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (moneymarket.getHolder().getFname().equalsIgnoreCase(this.holder.getFname())){
            fnameMatch = true;
        }
        if (moneymarket.getHolder().getLname().equalsIgnoreCase(holder.getLname())){
            lnameMatch = true;
        }
        if (moneymarket.getHolder().getDOB().compareTo(this.holder.getDOB()) == EQUAL_COMPARATOR){
            dobMatch = true;
        }

        return (fnameMatch && lnameMatch && dobMatch);
    }

    /**
     * Determines if two accounts are equivalent/of the same type
     * Same as equals()
     * @param compareMoneyMarket the object being compared.
     * @return true if the accounts are equivalent, false otherwise.
     */
    public boolean equalsAdvanced(Object compareMoneyMarket) {
        if (!MoneyMarket.class.equals(compareMoneyMarket.getClass())) {
            return false;
        }

        Account moneymarket = (Savings) compareMoneyMarket; // type cast to use in equals

        boolean fnameMatch = false;
        boolean lnameMatch = false;
        boolean dobMatch = false;

        if (moneymarket.getHolder().getFname().equalsIgnoreCase(this.holder.getFname())){
            fnameMatch = true;
        }
        if (moneymarket.getHolder().getLname().equalsIgnoreCase(holder.getLname())){
            lnameMatch = true;
        }
        if (moneymarket.getHolder().getDOB().compareTo(this.holder.getDOB()) == EQUAL_COMPARATOR){
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
        int profileCompare = savings.getHolder().compareTo(this.holder);
        boolean typeCompare = savings.getClass().equals(this.getClass());
        // makes sure that a MM doesn't get confused for a savings, if they have the same profile info
        if (profileCompare == EQUAL_COMPARATOR && typeCompare){
            return EQUAL_COMPARATOR;
        }

        return NOT_EQUAL; // if either profile is not the same or the type is not the same
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
            int fee = calcFee();
            balance -= fee;
        }
    }

    /**
     * Displays the account information.
     * @return a String of the account information.
     */
    @Override
    public String toString(){
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        String balanceFormat = currencyFormat.format(balance);

        if (isLoyal){
            return "Money Market::Savings::" + holder.toString() + "::Balance " + balanceFormat + "::is loyal::withdrawal: " + withdrawal;
        }

        return "Money Market::Savings::" + holder.toString() + "::Balance " + balanceFormat + "::withdrawal: " + withdrawal;
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
        checkLoyalty();

        if (isLoyal){
            return "Money Market::Savings::" + holder.toString() + "::Balance " + balanceFormat + "::is loyal::withdrawal: " + withdrawal
                    + "::fee " + feeFormat + "::monthly interest " + interestFormat;
        }
        return "Money Market::Savings::" + holder.toString() + "::Balance " + balanceFormat + "::withdrawal: " + withdrawal
                + "::fee " + feeFormat + "::monthly interest " + interestFormat;
    }


    public int getWithdrawal(){
        return withdrawal;
    }

}
