package com.example.rubankgui;
import java.util.Calendar;

/**
 Defines a date and determines its validity of a given date given the day, month, and year.
 @author Deshna Doshi, Haejin Song
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    private static final int MONTHS_PER_YEAR = 12;
    private static final int MAX_BOOKING_TIMEFRAME = 6;
    private static final int MIN_MONTH_VAL = 1;
    private static final int CALENDAR_CLASS_ALIGN = 1;
    private static final int DAY_31_MONTHS = 31;
    private static final int DAY_30_MONTHS = 30;
    private static final int DAY_29_MONTHS = 29;
    private static final int DAY_28_MONTHS = 28;
    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    /**
     Constructor to initialize values of instance variables.
     @param year the year of the date.
     @param month the month of the date.
     @param day the day of the date.
     */
    public Date (int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     Determines if the date is a valid calendar date, within six months, and in the future.
     @return true if the date is valid, within six months, and in the future, false otherwise.
     */
    public boolean advancedIsValid(){
        if(!validCalendarDate()){
            // System.out.println("Invalid calendar date.");
        } else if (!withinSixMonths()){
            // System.out.println("Not wihtin six months.");
        } else if (!isFutureDate()){
            // System.out.println("Not a future date.");
        }
        return (validCalendarDate() && withinSixMonths() && isFutureDate());
    }

    /**
     Determines if the date is a legitimate calendar date.
     @return true if the date is a real date, false otherwise.
     */
    public boolean validCalendarDate(){
        if (validMonth() && validYear() && validDay()){
            return true;
        }
        return false;
    }

    /**
     Determines if the month of the date is valid.
     @return true if the month is valid, false otherwise.
     */
    public boolean validMonth(){
        if(month <= MONTHS_PER_YEAR && month >= MIN_MONTH_VAL){
            return true;
        }
        return false;
    }

    /**
     Determines if the day of the date is valid.
     @return true if the day is valid, false otherwise.
     */
    public boolean validDay(){
        boolean isLeapYear = isLeapYear();
        Calendar monthInt = Calendar.getInstance();
        int alignMonth = month;
        alignMonth -= CALENDAR_CLASS_ALIGN;  // need to do this bc in calendar the months start from 0 index

        if (alignMonth == monthInt.JANUARY || alignMonth == monthInt.MARCH || alignMonth == monthInt.MAY || alignMonth == monthInt.JULY || alignMonth == monthInt.AUGUST || alignMonth == monthInt.OCTOBER || alignMonth == monthInt.DECEMBER){
            if (day <= DAY_31_MONTHS && day >= FIRST_DAY_OF_MONTH){
                return true;
            }
        } else if (alignMonth == monthInt.APRIL || alignMonth == monthInt.JUNE || alignMonth == monthInt.SEPTEMBER || alignMonth == monthInt.NOVEMBER){
            if (day <= DAY_30_MONTHS && day >= FIRST_DAY_OF_MONTH){
                return true;
            }
        } else if (alignMonth == monthInt.FEBRUARY){
            if (isLeapYear){
                if (day <= DAY_29_MONTHS && day >= FIRST_DAY_OF_MONTH){
                    return true;
                }
            } else {
                if (day <= DAY_28_MONTHS && day >= FIRST_DAY_OF_MONTH){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     Determines if the year of the date is valid.
     @return true if the year is valid, false otherwise.
     */
    public boolean validYear(){
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);

        // since dates can only be for future, must be within 2023 and 2024 at the bare minimum
        int MIN_YR = currentYear;
        int MAX_YR = currentYear + CALENDAR_CLASS_ALIGN;

        if (year == MIN_YR || year == MAX_YR){
            return true;
        }
        return false;
    }

    /**
     Determines if the date is within six months of today's date.
     @return true if the date is within six months, false otherwise.
     */
    public boolean withinSixMonths(){
        Calendar today = Calendar.getInstance();
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        int currentMonth = today.get(Calendar.MONTH) + CALENDAR_CLASS_ALIGN; // goes from 0 to 11
        // int currentYear = today.get(Calendar.YEAR);

        today.add(Calendar.MONTH, 6);
        // System.out.println(today.getTime() + " six months fro now");
        int sixMonthsDay = today.get(Calendar.DAY_OF_MONTH);
        int sixMonthsMonth = today.get(Calendar.MONTH) + CALENDAR_CLASS_ALIGN;
        int sixMonthsYear = today.get(Calendar.YEAR);

        // System.out.println(sixMonthsDay + " " + sixMonthsMonth + " "  + sixMonthsYear);

        if (year == sixMonthsYear){
            if (month < sixMonthsMonth){
                return true;
            } else if (month == sixMonthsMonth){
                if (day <= sixMonthsDay){
                    return true;
                }
            }
        } else if (year < sixMonthsYear){
            if(isFutureDate()){
                if(month > currentMonth){
                    return true;
                } else if (month == currentMonth){
                    if (day >= currentDay){
                        return true;
                    }
                }
            }
        }

        // currentMonth += CALENDAR_CLASS_ALIGN; // Need to add 1 so that the current month uses 1 - Jan, 2 - Feb ... instead of 0 - Jan, 1 - Feb

        return false;
    }

    /**
     Determines if the month and year of the date are within six months.
     @param currDay the day of today's date.
     @param currMonth the month of today's date.
     @param currYear the year of today's date.
     @return true if the month and year are within six months, false otherwise.
     */
    public boolean withinSixMonths_MonthYearCheck(int currDay, int currMonth, int currYear){
        int sixMonthLimit = currMonth;
        int correspondMonthToYear = currYear;

        int [] possibleMonths = new int[MAX_BOOKING_TIMEFRAME + CALENDAR_CLASS_ALIGN]; // this array will contain all months that are within 6 months from the time frame
        int [] possibleYear = new int [MAX_BOOKING_TIMEFRAME + CALENDAR_CLASS_ALIGN];
        int arrayIndexer = possibleMonths.length - CALENDAR_CLASS_ALIGN;

        while (arrayIndexer >= 0){
            possibleMonths[arrayIndexer] = sixMonthLimit;
            possibleYear[arrayIndexer] = correspondMonthToYear; // populating list of all possible months and their corresponding years into an array to determine post-6 mos date
            sixMonthLimit++;

            if (sixMonthLimit > MONTHS_PER_YEAR) {
                sixMonthLimit = CALENDAR_CLASS_ALIGN;
                correspondMonthToYear++;
            }
            arrayIndexer--;
        }

        currMonth += MAX_BOOKING_TIMEFRAME;
        if (currMonth >= MONTHS_PER_YEAR){
            currMonth -= MONTHS_PER_YEAR; // month number in the next year: month 7/2023 + 6 = 13 - 12 = 1 = Jan of 2024
            currYear++; // year also goes up by 1
        }

        if (year <= currYear){
            for (int i = 0; i < possibleMonths.length; i++){
                if (possibleMonths[i] == month && possibleYear[i] == year){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the day of the date (given the month and year are within six months) is within six months.
     * @return true if the day (and month and year) are within six months, false otherwise.
     */
    public boolean withinSixMonths_CompleteCheck(/*int currDay, int currMonth, int currYear*/){

        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH);
        int todayMonth = today.get(Calendar.MONTH) + CALENDAR_CLASS_ALIGN;
        int todayYear = today.get(Calendar.YEAR);

        if (withinSixMonths_MonthYearCheck(todayDay, todayMonth, todayYear)){
            if (month == todayMonth) {
                if (day >= todayDay) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     Determines if a date is a future date.
     @return true if the date is in the future, false otherwise.
     */
    public boolean isFutureDate(){
        Calendar today = Calendar.getInstance();
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        int currentMonth = today.get(Calendar.MONTH) + CALENDAR_CLASS_ALIGN; // goes from 0 to 11
        int currentYear = today.get(Calendar.YEAR);

        if(year > currentYear){
            return true;
        } else if (year == currentYear){
            if (month > currentMonth){
                return true;
            } else if (month == currentMonth){
                if (day > currentDay){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     Determines if the year is a leap year.
     @return true if year is a leap year, false otherwise.
     */
    public boolean isLeapYear(){

        if (year % QUADRENNIAL == 0){
            if (year % CENTENNIAL == 0){
                if (year % QUATERCENTENNIAL == 0){
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     Determines if the day, month, and year are legitimate calendar days, months, and years.
     @return true if the date is a legitimate date, false otherwise.
     */
    public boolean isValid(){
        return validDay() && validMonth() && basicValidYear();
    }

    /**
     Determines if the year is within 1900 and 2100.
     Arbitrarily set guidelines for determining a valid year.
     @return true if the year is within 1900 and 2100, false otherwise.
     */
    public boolean basicValidYear(){
        if(year >= MIN_YEAR && year <= MAX_YEAR){
            return true;
        }
        return false;
    }

    /**
     Converts the date into a "X/X/XXXX" formatted String.
     @return the date as a String.
     */
    @Override
    public String toString(){
        return month + "/" + day +"/" + year;
    }

    /**
     Determines if two dates are equivalent.
     @param checkDate the date that is being compared against the date calling the method.
     @return -1 if checkDate is larger, 0 if the dates are equal, 1 if checkDate is smaller.
     */
    @Override
    public int compareTo(Date checkDate) {
        // A.compareTo(B) if -1: A < B; 1: A > B, 0: A = B
        int EQUAL = 1;
        if (this.year == checkDate.year){
            if (this.month == checkDate.month){
                if (this.day == checkDate.day){
                    EQUAL = 0; // they are same 2 same
                } else if (this.day < checkDate.day){
                    EQUAL = -1; // o > this
                }
            } else if (this.month < checkDate.month){
                EQUAL = -1; // o date is after this
            }
        } else if (this.year < checkDate.year){
            EQUAL = -1; // o date is after this
        }
        return EQUAL;
    }



    /**
     * Getter for the year instance variable.
     * @return the year in the date.
     */
    public int getYear(){
        return year;
    }

    /**
     * Getter for the month instance variable.
     * @return the month in the date.
     */
    public int getMonth(){
        return month;
    }

    /**
     * Getter for the day instance variable.
     * @return the day in the date.
     */
    public int getDay(){
        return day;
    }
}
