package com.example.rubankgui;
import java.util.Calendar;

/**
 * Defines a profile for an account holder based on their first name, last name, and date of birth.
 * @author Deshna Doshi, Haejin Song
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    private static final int NOT_EQUAL = -2; // for the compareTo method; -2 output means profiles are not equal
    private static final int REALIGN_CAL = 1;
    /**
     * Constructor to initialize the values of the instance variables.
     * @param fname first name of the account holder.
     * @param lname last name of the account holder.
     * @param dob date of birth of the account holder.
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Determines if the date of birth is a valid date.
     * @return true if the date of borth is valid, false otherwise.
     */
    public boolean isValidDOB(){
       return dob.isValid();
    }

    /**
     * Compares two profiles to each other, based on the first name, last name, and date of birth.
     * @param compareProfile the object to be compared.
     * @return 0 if the two objects are equal, -2 otherwise.
     */
    @Override
    public int compareTo(Profile compareProfile){
        int compareResult = 0;

        int fNameCompareResult = fname.toLowerCase().compareTo(compareProfile.getFname().toLowerCase());
        int lNameCompareResult = lname.toLowerCase().compareTo(compareProfile.getLname().toLowerCase());
        int dobCompareResult = dob.compareTo(compareProfile.getDOB());

        if (fNameCompareResult == 0 && lNameCompareResult == 0 && dobCompareResult == 0){
            return compareResult;
        }

        return NOT_EQUAL;
    }

    /**
     * Determine if the profile holder is within the required age to open/hold an account
     * @return age of the account holder
     */
    public int age(){
        Calendar currentDate = Calendar.getInstance();
        int age = currentDate.get(Calendar.YEAR) - dob.getYear();

        // Check if the birthday has already passed this year
        if (currentDate.get(Calendar.MONTH) + REALIGN_CAL < dob.getMonth() || (currentDate.get(Calendar.MONTH) + REALIGN_CAL == dob.getMonth() &&
                currentDate.get(Calendar.DAY_OF_MONTH) < dob.getDay())) {
            age--;
        }

        return age;
    }

    /**
     * Prints the profile information as a string.
     * @return
     */
    public String toString(){
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Getter for the first name instance variable.
     * @return the first name.
     */
    public String getFname(){
        return fname;
    }

    /**
     * Getter for the last name instance variable.
     * @return the last name.
     */
    public String getLname(){
        return lname;
    }

    /**
     * Getter for the date of birth instance variable.
     * @return the date of birth.
     */
    public Date getDOB(){
        return dob;
    }

}
