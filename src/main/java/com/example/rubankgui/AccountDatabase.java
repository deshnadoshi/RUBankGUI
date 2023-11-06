package com.example.rubankgui;

/**
 * Defines a linear data structure using an array to hold a list of accounts with different types.
 * @author Deshna Doshi, Haejin Song
 */
public class AccountDatabase {
    private static final int GROWTH_CAPACITY = 4;
    private Account [] accounts; // list of various types of accounts
    private int numAcct = 0; // number of accounts in the array

    private static final int NOT_FOUND = -1;

    /**
     * Constructor for the AccountDatabase class.
     * @param accounts
     * @param numAcct
     */
    public AccountDatabase(Account[] accounts, int numAcct) {
        this.accounts = new Account[0];
        this.numAcct = 0;
    }

    /**
     * @param account Account that is being searched for
     * @return index of account if found, else NOT_FOUND
     */
    private int find(Account account) {
        if (account == null){
            return NOT_FOUND;
        }
        for (int i = 0; i < numAcct; i++) {
            if (account.equals(accounts[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    } // this will consider CC and C as the same account, S amd MM are considered different
    // for use in contains () to check if an account alr exists

    /**
     * Increases the size of the accounts array by 4.
     */
    private void grow() {
        Account [] growAccountsArray = new Account[accounts.length + GROWTH_CAPACITY];
        for (int i = 0; i < accounts.length; i++) {
            growAccountsArray[i] = accounts[i];
        }
        accounts = growAccountsArray;
    } //increase the capacity by 4

    /**
     * Check if an account already exists in the list.
     * @param account Account to be compared
     * @return true if account is already in the database, false otherwise
     */
    public boolean contains(Account account) {
        return find(account) != NOT_FOUND;
    } //overload if necessary

    /**
     * Add an account to the array, if it doesn't already exist
     * @param account Account to be opened
     * @return true if the account is added, false if it is not added.
     */
    public boolean open(Account account) {
        if (!contains(account)) {
            if (this.numAcct >= this.accounts.length) {
                this.grow();
            }
            this.accounts[this.numAcct] = account;
            this.numAcct += 1;
            return true;
        }
        return false;
    } //add a new account

    /**
     * @param account Account that is being removed from the database
     * @return true if successfully closed, false otherwise
     */
    public boolean close(Account account) {
        int removedAccountIndex = findUniqueIndex(account);
        if (removedAccountIndex == NOT_FOUND) return false;

        for (int i = removedAccountIndex; i < this.numAcct - 1; i++) {
            this.accounts[i] = this.accounts[i + 1];
        }

        this.accounts[this.numAcct - 1] = null;
        this.numAcct -= 1;
        return true;
        //remove the given account
    }

    /**
     * @param account Account that is being withdrawn from
     * @return true if successfully withdrawn from, false otherwise
     */
    public boolean withdraw(Account account) {
        int withdrawFromAccount = findUniqueIndex(account);
        // account.balance is the amount to withdraw
        // need check if account.balance is > the real account's current balance
        if (account.getBalance() <= accounts[withdrawFromAccount].getBalance()){
            accounts[withdrawFromAccount].balance -= account.getBalance();
            // reduce the withdrawn amount from the real account's current balance
            return true;
        }
        // false if insufficient fund
        return false;
    }

    /**
     * @param account Account that money is being deposited in
     */
    public void deposit(Account account) {
        int depositToAccount = advancedFind(account);
        if (depositToAccount == NOT_FOUND){
            return;
        }
        // account.getBalance() contains the amount to deposit
        // Will need to create a "shell" account to hold just the deposit amount, to populate it into the actual account
        accounts[depositToAccount].balance += account.getBalance();
    }

    /**
     * @param account Account that is being searched for
     * @return index of account if found, else NOT_FOUND
     * Note: This is different from find because it makes sure that the difference
     * between a CC and C is accounted for when looking for accounts. This is necessary
     * for methods like deposit().
     */
    private int advancedFind(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (account.equalsAdvanced(accounts[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Prints out accounts sorted by account types, then last name,
     * then first name, then DOB.
     */
    public String printSorted() {
        String retString = "";
        if (numAcct == 0) return ("Account Database is empty!");
        else {
            Account[] sortedArray = arrayForSorting(accounts);
            quickSort(sortedArray, 0, sortedArray.length - 1);
            retString += ("\n*Accounts sorted by account type and profile.\n");
            for (int i = 0; i < numAcct; i++) {
                retString += ("\n" + sortedArray[i] + "\n");
            }
            retString += ("\n*end of list.\n");
        }
        return retString;
    }

    /**
     * Prints out accounts sorted by account type, and displays calculated
     * fees and monthly interest based on current account balances.
     */
    public String printFeesAndInterests() {
        String retString = "";
        if (numAcct == 0) return ("Account Database is empty!");
        else {
            Account[] sortedArray = arrayForSorting(accounts);
            quickSort(sortedArray, 0, sortedArray.length - 1);
            retString += ("\n*list of accounts with fee and monthly interest\n");
            for (int i = 0; i < numAcct; i++) {
                retString += ("\n" + sortedArray[i].netBalanceToString() + "\n");
            }
            retString += ("\n*end of list.\n");
        }
        return retString;
    }

    /**
     * Prints out accounts sorted by account type, and updates account balances
     * by adding fees and interest. Also resets withdrawals for MM.
     * Acts like one month has passed.
     */
    public String printUpdatedBalances() {
        String retString = "";

        if (numAcct == 0) return("\nAccount Database is empty!\n");
        else {
            Account[] sortedArray = arrayForSorting(accounts);
            quickSort(sortedArray, 0, sortedArray.length - 1);

            retString += "\n*list of accounts with fees and interests applied.\n";
            for (int i = 0; i < numAcct; i++) {
                sortedArray[i].updateBalance();
                if (sortedArray[i].getClass().equals(MoneyMarket.class)) {
                    ((MoneyMarket) sortedArray[i]).resetWithdrawal();
                }
                retString += ("\n" + sortedArray[i] + "\n");
            }
            retString += ("\n*end of list.\n");
        }

        return retString;
    }

    /**
     * @param accountsArray Account that nulls are being removed from
     * @return an array with no nulls
     */
    public Account[] arrayForSorting(Account[] accountsArray) {
        Account[] temp = new Account[numAcct];
        for (int i = 0; i < numAcct; i++) {
            temp[i] = accountsArray[i];
        }
        return temp;
    }

    /**
     Implementation of Quicksort for print methods.
     @param unsortedArray the array that is being sorted
     @param low the first index of the part of the array being sorted
     @param high the last index of the part of the array being sorted
     */
    public void quickSort(Account[] unsortedArray, int low, int high) {
        if (low >= high || low < 0) {
            return;
        }
        int pivot = partition(unsortedArray, low, high);
        quickSort(unsortedArray, low, pivot - 1);
        quickSort(unsortedArray, pivot + 1, high);
    }

    /**
     * @param unsortedArray Account that is being sorted
     * @param low Value of lowest index
     * @param high Value of highest index
     * @return current pivot
     */
    private int partition(Account[] unsortedArray, int low, int high) {
        Account pivot = unsortedArray[high];
        int temp_pivot = low - 1;
        for (int i = low; i < high; i++) {
            if (unsortedArray[i].getClass().toString().compareTo(pivot.getClass().toString()) < 0) {
                temp_pivot += 1;
                swap(i, temp_pivot, unsortedArray);
            } else if (unsortedArray[i].getClass().toString().compareTo(pivot.getClass().toString()) == 0 &&
                    unsortedArray[i].getHolder().getLname().compareTo(pivot.getHolder().getLname()) < 0) {
                temp_pivot += 1;
                swap(i, temp_pivot, unsortedArray);
            } else if (unsortedArray[i].getClass().toString().compareTo(pivot.getClass().toString()) == 0 &&
                    unsortedArray[i].getHolder().getLname().compareTo(pivot.getHolder().getLname()) == 0 &&
                    unsortedArray[i].getHolder().getFname().compareTo(pivot.getHolder().getFname()) < 0) {
                temp_pivot += 1;
                swap(i, temp_pivot, unsortedArray);
            } else if (unsortedArray[i].getClass().toString().compareTo(pivot.getClass().toString()) == 0 &&
                    unsortedArray[i].getHolder().getLname().compareTo(pivot.getHolder().getLname()) == 0 &&
                    unsortedArray[i].getHolder().getFname().compareTo(pivot.getHolder().getFname()) == 0 &&
                    unsortedArray[i].getHolder().getDOB().compareTo(pivot.getHolder().getDOB()) < 0) {
                temp_pivot += 1;
                swap(i, temp_pivot, unsortedArray);
            }
        }
        temp_pivot += 1;
        swap(temp_pivot, high, unsortedArray);
        return temp_pivot;
    }

    /**
     * Swap i and j with each other in the array
     */
    private void swap(int i, int j, Account[] A) {
        Account temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    /**
     * @param account Account that is being searched for
     * @return true if found, otherwise false
     */
    public boolean depositNotFound(Account account) {
        int depositToAccount = advancedFind(account);
        if (depositToAccount == NOT_FOUND) {
            return true;
        }
        //deposit(account);
        return false;
    }

    /**
     * @param account Account that is being searched for
     * @return index of account if found, else NOT_FOUND
     */
    public int findUniqueIndex(Account account){
        for (int i = 0; i < numAcct; i++) {
            if(accounts[i].getClass().toString().equals(account.getClass().toString())){
                if (account.equals(accounts[i])){
                    return i;
                }
            }
        }
        return NOT_FOUND;

    }

    /**
     * @param mm Account that will have withdraws updated
     */
    public void updateWithdraws(Account mm){
        int mmInd = find(mm); // found index of mm in db

        if (mmInd != -1){ // if it is found
            Account updateAcc = accounts[mmInd]; // get the account object
            MoneyMarket updateMM = (MoneyMarket) updateAcc; // turn into mm object so u can add withdrawal amt
            updateMM.addWithdrawal();
        }
    }

    public void updateLoyalty(Account mm){
        int mmInd = find(mm); // found index of mm in db

        if (mmInd != -1){ // if it is found
            Account updateAcc = accounts[mmInd]; // get the account object
            MoneyMarket updateMM = (MoneyMarket) updateAcc; // turn into mm object so u can add withdrawal amt
            updateMM.checkLoyalty();
        }
    }

    /**
     * Getter for the number of accounts in the database.
     * @return the number of accounts in the database.
     */
    public int getNumAcct(){
        return numAcct;
    }

}
