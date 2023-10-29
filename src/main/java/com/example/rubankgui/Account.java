package com.example.rubankgui;

/**
 * Defines a bank account for a user based on their information and balance.
 * @author Deshna Doshi, Haejin Song
 */
public abstract class Account implements Comparable <Account>{
    protected Profile holder;
    protected double balance;

    /**
     * Constructor to initialize the instance variables.
     * @param holder Profile information of the account holder.
     * @param balance Balance in the account.
     */
    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Default constructor to ensure proper usage for child classes.
     */
    public Account() {

    }

    /**
     * Abstract method to be implemented in subclasses; provides the monthly fee associated with the account.
     */
    public abstract double monthlyFee();

    /**
     * Abstract method to be implemented in subclasses; provides the monthly interest rate associated with the account.
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method to be implemented in subclasses; determines if the holder is within the age requirements of a certain account.
     * @return true if the holder is within the age range, false otherwise.
     */
    public abstract boolean checkAge();

    /**
     * Abstract method to be implemented in subclasses; determines if the entered balance amount if valid based on the account requirements.
     * @return true if the balance is valid, false otherwise.
     */
    public abstract boolean balanceIsValid();

    /**
     * Abstract method to be implemented in subclasses; updates the accounts balance with the required fees and monthly interest.
     */
    public abstract void updateBalance();

    /**
     * Abstract method to be implemented in subclasses; calculates the interest earned on the account.
     * @return the monthly interest earned on the account.
     */
    public abstract double calcInterest();

    /**
     * Abstract method to be implemented in subclasses; calculates the fees applied to the account.
     * @return the fee applied to the account.
     */
    public abstract int calcFee();

    /**
     * Abstract method to be implemented in subclasses; updates the balance with the fees and monthly interest, displays the account information.
     * @return a String of the account information, with fees and interest included.
     */
    public abstract String netBalanceToString();

    /**
     * Abstract method to be implemented in subclasses; displays the account information.
     * @return a String of the account information.
     */
    @Override
    public abstract String toString();

    /**
     * Abstract method to be implemented in subclasses; determines if two accounts have the same holder and are of the same type.
     * @param account the Account object that is being checked.
     * @return true if two accounts are equivalent, false otherwise.
     */
    @Override
    public abstract boolean equals(Object account);

    /**
     * Abstract method to be implemented in subclasses; determines if two accounts have the same holder and are the same type.
     * It differs from equals() because it checks to make sure there is a difference between C and CC.
     * @param account the Account object that is being checked.
     * @return true if two accounts are equivalent, false otherwise.
     */
    public abstract boolean equalsAdvanced(Object account);

    /**
     * Getter for the balance instance variable.
     * @return the balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Getter for the profile of the account holder.
     * @return the Profile object.
     */
    public Profile getHolder() {
        return holder;
    }

}
