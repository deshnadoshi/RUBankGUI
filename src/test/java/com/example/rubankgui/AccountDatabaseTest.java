package com.example.rubankgui;

import static org.junit.Assert.*;

/**
 * JUnit Test Class for AccountDatabase.java, close() method
 * @author Deshna Doshi, Haejin Song
 */
public class AccountDatabaseTest {

    /**
     * Confirms that an account that exists can be closed.
     */
    @org.junit.Test
    public void test_CloseExistingAccount() {

        Profile deshna = new Profile("Deshna", "Doshi", new Date(2003, 11, 5));
        Profile haejin = new Profile("Haejin", "Song", new Date(2003, 10, 10));

        Account checking = new Checking(deshna, 2500);
        Account savings = new Savings(haejin, 3500, true);

        Account [] accounts = {checking, savings};

        AccountDatabase database = new AccountDatabase(accounts, 2);
        database.open(checking);
        database.open(savings);

        assertTrue(database.close(checking));

    }

    /**
     * Confirms that an account that doesn't exist cannot be closed.
     */
    @org.junit.Test
    public void test_CloseNonExistentAccount(){

        Profile deshna = new Profile("Deshna", "Doshi", new Date(2003, 11, 5));
        Profile haejin = new Profile("Haejin", "Song", new Date(2003, 10, 10));

        Account checking = new Checking(deshna, 2500);
        Account savings = new Savings(haejin, 3500, true);

        Account [] accounts = {checking, savings};

        AccountDatabase database = new AccountDatabase(accounts, 2);
        database.open(savings);

        assertFalse(database.close(checking));

    }
}