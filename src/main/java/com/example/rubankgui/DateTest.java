package com.example.rubankgui;
import static org.junit.Assert.*;

/**
 * JUnit Test Class for Date.java, isValid() method
 * @author Deshna Doshi, Haejin Song
 */

public class DateTest {
    /**
     Confirms that a non-leap year does not have more than 28 days.
     */
    @org.junit.Test
    public void test_NonLeapYearFeb() {
        Date testCase1 = new Date(2023, 2, 29);
        assertFalse(testCase1.isValid());
    }

    /**
     Confirms that a month is within a range of 1-12.
     */
    @org.junit.Test
    public void test_MonthRange() {
        Date testCase2 = new Date(2012,13,2);
        assertFalse(testCase2.isValid());
    }

    /**
     Confirms that Jan, Mar, May, Jul, Aug, Oct, Dec do not have more than 31 days.
     */
    @org.junit.Test
    public void test_31DayRange() {
        Date testCase3 = new Date(2009,7,33);
        assertFalse(testCase3.isValid());
    }

    /**
     Confirms that Apr, Jun, Sep, Nov do not have more than 30 days.
     */
    @org.junit.Test
    public void test_30DayRange() {
        Date testCase4 = new Date(2025,6,31);
        assertFalse(testCase4.isValid());
    }

    /**
     Confirms that the day is not less than 1 for any month.
     */
    @org.junit.Test
    public void test_MinMonthVal() {
        Date testCase5 = new Date(2026,9,0);
        assertFalse(testCase5.isValid());
    }

    /**
     Confirms that a leap-year can have 29 days.
     */
    @org.junit.Test
    public void test_LeapYearFeb() {
        Date testCase6 = new Date(2024,2,29);
        assertTrue(testCase6.isValid());
    }

    /**
     Confirms that any arbitrary and legitimate date is valid.
     */
    @org.junit.Test
    public void test_RealDate() {
        Date testCase7 = new Date(2023,9,26);
        assertTrue(testCase7.isValid());
    }

    /**
     Confirms that a year before 1900 and a year after 2100 are not valid.
     */
    @org.junit.Test
    public void test_OutofBoundsYears() {
        Date testCase8a = new Date(1888,5,1);
        Date testCase8b = new Date(2103,6,24);

        assertFalse(testCase8a.isValid());
        assertFalse(testCase8b.isValid());

    }
}