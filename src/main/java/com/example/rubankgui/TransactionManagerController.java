package com.example.rubankgui;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

import java.lang.String;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;

/**
 * Controls the GUI components.
 * @author Deshna Doshi, Haejin Song
 */
public class TransactionManagerController {

    Account[] allAccounts = new Account[4];
    AccountDatabase actDb = new AccountDatabase(allAccounts, 0);

    // "Open" Tab GUI Components
    @FXML
    private TextField openFname;
    @FXML
    private TextField openLname;
    @FXML
    private DatePicker openDOB;
    @FXML
    private TextField openAmt;
    @FXML
    private RadioButton openC;
    @FXML
    private RadioButton openCC;
    @FXML
    private RadioButton openCCNB;
    @FXML
    private RadioButton openCCNewark;
    @FXML
    private RadioButton openCCCamden;
    @FXML
    private RadioButton openS;
    @FXML
    private CheckBox openSLoyal;
    @FXML
    private RadioButton openMM;
    @FXML
    private Button openSubmit;
    @FXML
    private TextArea openResult;

    @FXML
    private ToggleGroup Account;

    @FXML
    private ToggleGroup CampusName;

    // "Close" Tab GUI Components
    @FXML
    private TextField closeFname;
    @FXML
    private TextField closeLname;
    @FXML
    private DatePicker closeDOB;
    @FXML
    private RadioButton closeC;
    @FXML
    private RadioButton closeCC;
    @FXML
    private RadioButton closeS;
    @FXML
    private RadioButton closeMM;
    @FXML
    private Button closeSubmit;
    @FXML
    private TextArea closeResult;
    @FXML
    private ToggleGroup CloseAccount;

    // "Deposit" Tab GUI Components
    @FXML
    private TextField depFname;
    @FXML
    private TextField depLname;
    @FXML
    private DatePicker depDOB;
    @FXML
    private RadioButton depC;
    @FXML
    private RadioButton depCC;
    @FXML
    private RadioButton depS;
    @FXML
    private RadioButton depMM;
    @FXML
    private TextField depAmt;
    @FXML
    private Button depSubmit;
    @FXML
    private TextArea depositResult;
    @FXML
    private ToggleGroup DepositAccount;

    // "Withdraw" Tab GUI Components
    @FXML
    private TextField withFname;
    @FXML
    private TextField withLname;
    @FXML
    private DatePicker withDOB;
    @FXML
    private RadioButton withC;
    @FXML
    private RadioButton withCC;
    @FXML
    private RadioButton withS;
    @FXML
    private RadioButton withMM;
    @FXML
    private TextField withAmt;
    @FXML
    private Button withSubmit;
    @FXML
    private TextArea withResult;
    @FXML
    private ToggleGroup WithdrawAccount;

    // "Account Database" Tab GUI Components

    @FXML
    private Button pPrint;

    @FXML
    private Button piPrint;

    @FXML
    private Button ubPrint;

    @FXML
    private Button loadAct;

    @FXML
    private TextArea actDBShow;

    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 24;
    private static final int ACC_IND = 0;
    private static final int FNAME_IND = 1;
    private static final int LNAME_IND = 2;
    private static final int BDAY_IND = 3;
    private static final int BALANCE_IND = 4;
    private static final int MM_MIN = 2000;
    private static final int STATUS_IND = 5;


    /**
     * Opens an account.
     * @param event The action of clicking the Open button.
     */
    @FXML
    private void openingAccount(ActionEvent event){
        boolean openActDOB = true; // Checking for input validity.
        boolean valid = checkValidOpenAccount();
        if (valid) {
            openResult.clear();
            LocalDate dob = openDOB.getValue();
            int day = dob.getDayOfMonth();
            int month = dob.getMonthValue();
            int year = dob.getYear();
            RadioButton actTypeButton = (RadioButton) Account.getSelectedToggle();
            String actTypeFXID = actTypeButton.getId();
            Date birthday = new Date(year, month, day);
            if (!checkDate(birthday, actNameOpen(actTypeFXID)).equals("T")){
                openActDOB = false; // If the birthday is not valid
                openResult.appendText(checkDate(birthday, actNameOpen(actTypeFXID)));
            }
            boolean openActAmt = checkValidAmount(actNameOpen(actTypeFXID));
            if (!openActAmt || !openActDOB) resetAllOpen(); // This needs to stay outside of the above if-statements, otherwise there's a NullPointExcep
            if (openActDOB && openActAmt) { // Can open the account
                openResult.clear();
                String actName = actNameOpen(actTypeFXID); // Tells us the type of account to open
                Account add = makeOpenAccount(actName, birthday);
                if (actDb.open(add)) {
                    printInfo("opened", openResult, add, actName);
                } else printInfo("in_database", openResult, add, actName);
            }
        }
    }

    /**
     * Closes an account.
     * @param event The action of clicking the Close button.
     */
    @FXML
    private void closingAccount(ActionEvent event){
        boolean closeActDOB = true; // Checking for input validity.
        boolean valid = checkValidCloseAccount();
        if (valid){
            LocalDate dob = closeDOB.getValue();
            int day = dob.getDayOfMonth();
            int month = dob.getMonthValue();
            int year = dob.getYear();

            RadioButton actTypeButton = (RadioButton) CloseAccount.getSelectedToggle();
            String actTypeFXID = actTypeButton.getId();

            Date birthday = new Date(year, month, day);
            if (!checkDate(birthday, actNameClose(actTypeFXID)).equals("T")){
                // If the birthday is not valid
                closeActDOB = false;
                closeResult.appendText(checkDate(birthday, actNameClose(actTypeFXID)));
            }
            if (!closeActDOB){
                resetAllClose();
            }
            if (closeActDOB){
                closeResult.clear();
                String actName = actNameClose(actTypeFXID); // Tells us the type of account to close
                Account close = makeCloseAccount(actName, birthday);
                if (actDb.close(close)) {
                    printInfo("closed", closeResult, close, actName);
                } else printInfo("not_in_database", closeResult, close, actName);
            }
        }
    }

    /**
     * Deposits to an account.
     * @param event The action of clicking the Deposit button.
     */
    @FXML
    private void depositAccount(ActionEvent event){
        boolean valid = checkValidDepositAccount();
        boolean depActDOB = true; // Checking for input validity.
        boolean depActAmt = true; // Checking for input validity
        if (valid) {
            LocalDate dob = depDOB.getValue();
            int day = dob.getDayOfMonth();
            int month = dob.getMonthValue();
            int year = dob.getYear();
            RadioButton actTypeButton = (RadioButton) DepositAccount.getSelectedToggle();
            String actTypeFXID = actTypeButton.getId();
            Date birthday = new Date(year, month, day);
            if (!checkDate(birthday, actNameDep(actTypeFXID)).equals("T")){
                depActDOB = false; // If the birthday is not valid
                depositResult.appendText(checkDate(birthday, actNameDep(actTypeFXID)));
            }
            String initialDeposit = depAmt.getText();
            if (initialDeposit.matches("-?\\d+(\\.\\d{1,2})?")) {
                double openAmount = Double.parseDouble(initialDeposit);
                if (openAmount <= 0){
                    depActAmt = false;
                    depositResult.appendText("Deposit - amount cannot be 0 or negative.\n");
                }
            } else {
                depActAmt = false;
                depositResult.appendText("Not a valid amount.\n");
            }
            if (!depActDOB || !depActAmt) resetAllDep();
            if (depActDOB && depActAmt){
                depositResult.clear();
                String actName = actNameDep(actTypeFXID); // Tells us the type of account to open
                Account dep = makeDepositAccount(actName, birthday, initialDeposit);
                if (!actDb.depositNotFound(dep)) {
                    actDb.deposit(dep);
                    printInfo("deposit", depositResult, dep, actName);
                } else printInfo("not_in_database", depositResult, dep, actName);
            }
        }
    }

    /**
     * Withdraws from an account.
     * @param event The action of clicking the Withdraw button.
     */
    @FXML
    private void withdrawAccount(ActionEvent event){
        boolean valid = checkValidWithdrawAccount();
        boolean withActDOB = true; // Checking for input validity.
        boolean withActAmt = true; // Checking for input validity
        if (valid){
            LocalDate dob = withDOB.getValue();
            int day = dob.getDayOfMonth();
            int month = dob.getMonthValue();
            int year = dob.getYear();
            RadioButton actTypeButton = (RadioButton) WithdrawAccount.getSelectedToggle();
            String actTypeFXID = actTypeButton.getId();
            Date birthday = new Date(year, month, day);
            if (!checkDate(birthday, actNameWith(actTypeFXID)).equals("T")){
                withActDOB = false; // If the birthday is not valid
                withResult.appendText(checkDate(birthday, actNameWith(actTypeFXID)));
            }
            String initWith = withAmt.getText();
            if (initWith.matches("-?\\d+(\\.\\d{1,2})?")) {
                double wdrawAmount = Double.parseDouble(initWith);
                if (wdrawAmount <= 0){
                    withActAmt = false;
                    withResult.appendText("Withdraw - amount cannot be 0 or negative.\n");
                }
            } else {
                withActAmt = false;
                withResult.appendText("Not a valid amount.\n");
            }
            if (!withActDOB || !withActAmt) resetAllWith();
            if (withActDOB && withActAmt){
                withResult.clear();
                String actName = actNameWith(actTypeFXID); // Tells us the type of account to open
                Account with = makeWithdrawAccount(actName, birthday, initWith);
                if (actDb.contains(with)) {
                    if (actDb.withdraw(with)) {
                        if(actNameWith(actTypeFXID).equals("MM")){
                            actDb.updateWithdraws(with);
                            actDb.updateLoyalty(with);
                        }
                        printInfo("withdraw", withResult, with, actName);
                    } else printInfo("insufficient", withResult, with, actName);
                } else printInfo("not_in_database", withResult, with, actName);
            }
        }
    }

    /**
     * Prints a sorted list of the account database.
     * @param event The action of clicking the respective print button.
     */
    @FXML
    private void printAll(ActionEvent event){
        actDBShow.appendText(actDb.printSorted());
    }

    /**
     * Prints a sorted list of the interests and feeds.
     * @param event The action of clicking the respective print button.
     */
    @FXML
    private void printIF(ActionEvent event){
        actDBShow.appendText(actDb.printFeesAndInterests());
    }

    /**
     * Prints a sorted list of the updated balances.
     * @param event The action of clicking the respective print button.
     */
    @FXML
    private void printUB(ActionEvent event){
        actDBShow.appendText(actDb.printUpdatedBalances());
    }

    /**
     * Reads data from a file and opens accounts.
     * @param event The action of clicking the upload button.
     */
    @FXML
    private void loadActs(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        boolean success = false;
        try {
            Scanner accountsList = new Scanner(selectedFile);
            while (accountsList.hasNextLine()) {
                String data = accountsList.nextLine();
                if (data.isEmpty()) break;
                String[] splitData = data.split(",");
                Account newAccount = makeAccountFromArray(splitData);
                success = true;
                actDb.open(newAccount);
            }
        } catch (FileNotFoundException | NullPointerException e) {
            actDBShow.appendText("File not found\n");
        } finally {
            if (success) actDBShow.appendText("Accounts loaded\n");
        }
    }

    // Helper Methods

    /**
     * Opens an account based on the account name and birthday.
     * @param actName Type of account.
     * @param birthday DOB of holder.
     * @return new Account object.
     */
    private Account makeOpenAccount(String actName, Date birthday) {
        Account acc = null;
        switch (actName) {
            case "C" -> {
                Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                acc = new Checking(holder, Double.parseDouble(openAmt.getText()));
            } case "CC" -> {
                RadioButton campusTypeButton = (RadioButton) CampusName.getSelectedToggle();
                String campusTypeFXID = campusTypeButton.getId();
                Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                acc = new CollegeChecking(holder, Double.parseDouble(openAmt.getText()), ccCampus(campusTypeFXID));
            } case "S" -> {
                Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                acc = new Savings(holder, Double.parseDouble(openAmt.getText()), openSLoyal.isSelected());
            } case "MM" -> {
                Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                acc = new MoneyMarket(holder, Double.parseDouble(openAmt.getText()), true, 0);
            }
        } return acc;
    }

    /**
     * Makes a shell account to close.
     * @param actName Type of account.
     * @param birthday DOB of holder.
     * @return new shell Account object.
     */
    private Account makeCloseAccount(String actName, Date birthday) {
        Account acc = null;
        switch (actName) {
            case "C" -> {
                Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                acc = new Checking(holder, 0);
            }
            case "CC" -> {
                Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                acc = new CollegeChecking(holder, 0, Campus.NEWARK);
            }
            case "S" -> {
                Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                acc = new Savings(holder, 0, true);
            }
            case "MM" -> {
                Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                acc = new MoneyMarket(holder, 0, true, 0);
            }
        } return acc;
    }

    /**
     * Makes a shell account to deposit.
     * @param actName Type of account.
     * @param birthday DOB of holder.
     * @param initialDeposit Amount to deposit.
     * @return new shell Account object.
     */
    private Account makeDepositAccount(String actName, Date birthday, String initialDeposit) {
        Account dep = null;
        switch (actName) {
            case "C" -> {
                Profile holder = new Profile(depFname.getText(), depLname.getText(), birthday);
                dep = new Checking(holder, Double.parseDouble(initialDeposit));
            }
            case "CC" -> {
                Profile holder = new Profile(depFname.getText(), depLname.getText(), birthday);
                dep = new CollegeChecking(holder, Double.parseDouble(initialDeposit), Campus.NEWARK);
            }
            case "S" -> {
                Profile holder = new Profile(depFname.getText(), depLname.getText(), birthday);
                dep = new Savings(holder, Double.parseDouble(initialDeposit), true);
            }
            case "MM" -> {
                Profile holder = new Profile(depFname.getText(), depLname.getText(), birthday);
                dep = new MoneyMarket(holder, Double.parseDouble(initialDeposit), true, 0);
            }
        } return dep;
    }

    /**
     * Makes a shell account to withdraw.
     * @param actName Type of account.
     * @param birthday DOB of holder.
     * @param initWith Amount to withdraw.
     * @return new shell Account object.
     */
    private Account makeWithdrawAccount(String actName, Date birthday, String initWith) {
        Account with = null;
        switch (actName) {
            case "C" -> {
                Profile holder = new Profile(withFname.getText(), withLname.getText(), birthday);
                with = new Checking(holder, Double.parseDouble(initWith));
            }
            case "CC" -> {
                Profile holder = new Profile(withFname.getText(), withLname.getText(), birthday);
                with = new CollegeChecking(holder, Double.parseDouble(initWith), Campus.NEWARK);
            }
            case "S" -> {
                Profile holder = new Profile(withFname.getText(), withLname.getText(), birthday);
                with = new Savings(holder, Double.parseDouble(initWith), true);
            }
            case "MM" -> {
                Profile holder = new Profile(withFname.getText(), withLname.getText(), birthday);
                with = new MoneyMarket(holder, Double.parseDouble(initWith), true, 0);
            }
        } return with;
    }

    /**
     * Print most recent action to the TextArea.
     * @param type Action that occured.
     * @param textarea GUI component to print to.
     * @param acc Account affected.
     * @param actName Type of account.
     */
    private void printInfo(String type, TextArea textarea, Account acc, String actName) {
        switch (type) {
            case "opened" -> textarea.appendText(acc.getHolder().getFname() + " " + acc.getHolder().getLname() +
                    " " + acc.getHolder().getDOB() + "(" + actName + ")" + " opened.\n");
            case "in_database" ->
                    textarea.appendText(acc.getHolder().getFname() + " " + acc.getHolder().getLname() + " " + acc.getHolder().getDOB() +
                            "(" + actName + ") " + "is already in the database.\n");
            case "closed" -> textarea.appendText(acc.getHolder().getFname() + " " + acc.getHolder().getLname() +
                    " " + acc.getHolder().getDOB() + "(" + actName + ")" + " has been closed.\n");
            case "not_in_database" ->
                    textarea.appendText(acc.getHolder().getFname() + " " + acc.getHolder().getLname() + " " + acc.getHolder().getDOB() +
                            "(" + actName + ") " + "is not in the database.\n");
            case "deposit" -> textarea.appendText(acc.getHolder().getFname() + " " + acc.getHolder().getLname() +
                    " " + acc.getHolder().getDOB() + "(" + actName + ")" + " Deposit - balance updated.\n");
            case "insufficient" -> textarea.appendText(acc.getHolder().getFname() + " " + acc.getHolder().getLname() + " " + acc.getHolder().getDOB() +
                    "(" + actName + ") " + " Withdraw - insufficient fund.\n");
            case "withdraw" -> textarea.appendText(acc.getHolder().getFname() + " " + acc.getHolder().getLname() +
                    " " + acc.getHolder().getDOB() + "(" + actName + ")" + " Withdraw - balance updated.");
        }
    }

    /**
     * Determine if entered amount is valid.
     * @param actType Type of account.
     * @return true if the amount is valid, false otherwise.
     */
    private boolean checkValidAmount(String actType) {
        // Checking if the initial amount is valid
        String initialDeposit = openAmt.getText();
        if (initialDeposit.matches("-?\\d+(\\.\\d{1,2})?")) {
            // String contains only digits
            double openAmount = Double.parseDouble(initialDeposit);
            if (openAmount <= 0){
                openResult.appendText("Initial deposit cannot be 0 or negative.\n");
                return false;
            } else if (actType.equals("MM") && openAmount < 2000) {
                openResult.appendText("Minimum of $2000 to open a Money Market account.\n");
                return false;
            }
        } else {
            openResult.appendText("Not a valid amount.\n");
            return false;
        }
        return true;
    }

    /**
     * Check if any fields are empty in Open tab.
     * @return true if all data has been filled out, false otherwise.
     */
    private boolean checkValidOpenAccount() {
        if (openFname.getText().isBlank()){
            openResult.appendText("Missing data for opening an account.\n");
            resetAllOpen();
            return false;
        }
        if (openLname.getText().isBlank()){
            openResult.appendText("Missing data for opening an account.\n");
            resetAllOpen();
            return false;
        }
        if (openDOB.getValue() == null){
            openResult.appendText("Please use Date Picker to choose DOB from calendar. Missing data for opening an account.\n");
            resetAllOpen();
            return false;
        }
        if (openAmt.getText().isBlank()){
            openResult.appendText("Missing data for opening an account.\n");
            resetAllOpen();
            return false;
        }
        // If no account is selected, then you can't make an account.
        if (Account.getSelectedToggle() == null){
            openResult.appendText("Missing data for opening an account.\n");
            resetAllOpen();
            return false;
        } else {
            RadioButton actTypeButton = (RadioButton) Account.getSelectedToggle();
            String actTypeFXID = actTypeButton.getId(); // FXID of the type of account selected
            if (actTypeFXID.equals("openCC")){
                if (CampusName.getSelectedToggle() == null){
                    openResult.appendText("Invalid campus code.\n");
                    resetAllOpen();
                    return false;
                }
            }
        } return true;
    }

    /**
     * Check if any fields are empty in Close tab.
     * @return true if all data has been filled out, false otherwise.
     */
    private boolean checkValidCloseAccount() {
        if (closeFname.getText().isBlank()){
            openResult.appendText("Missing data for closing an account.\n");
            resetAllClose();
            return false;
        }
        if (closeLname.getText().isBlank()){
            closeResult.appendText("Missing data for closing an account.\n");
            resetAllClose();
            return false;
        }

        if (closeDOB.getValue() == null){
            closeResult.appendText("Please use Date Picker to choose DOB from calendar. Missing data for closing an account.\n");
            resetAllClose();
            return false;
        }

        // If no account type is selected, then you can't close an account.
        if (CloseAccount.getSelectedToggle() == null){
            closeResult.appendText("Missing data for closing an account.\n");
            resetAllClose();
            return false;
        } return true;
    }

    /**
     * Check if any fields are empty in Deposit tab.
     * @return true if all data has been filled out, false otherwise.
     */
    private boolean checkValidDepositAccount() {
        if (depFname.getText().isBlank()){
            depositResult.appendText("Missing data for making deposit in account.\n");
            resetAllDep();
            return false;
        }
        if (depLname.getText().isBlank()){
            depositResult.appendText("Missing data for making deposit in account.\n");
            resetAllDep();
            return false;
        }
        if (depDOB.getValue() == null){
            depositResult.appendText("Please use Date Picker to choose DOB from calendar. Missing data for making deposit in account.\n");
            resetAllDep();
            return false;
        }
        if (DepositAccount.getSelectedToggle() == null){
            depositResult.appendText("Missing data for making deposit in account.\n");
            resetAllDep();
            return false;
        } return true;
    }

    /**
     * Check if any fields are empty in Withdraw tab.
     * @return true if all data has been filled out, false otherwise.
     */
    private boolean checkValidWithdrawAccount() {
        if (withFname.getText().isBlank()){
            withResult.appendText("Missing data for withdrawing from account.\n");
            resetAllWith();
            return false;
        }
        if (withLname.getText().isBlank()){
            withResult.appendText("Missing data for withdrawing from account.\n");
            resetAllWith();
            return false;
        }
        if (withDOB.getValue() == null){
            withResult.appendText("Please use Date Picker to choose DOB from calendar. Missing data for withdrawing from account.\n");
            resetAllWith();
            return false;
        }
        if (WithdrawAccount.getSelectedToggle() == null){
            withResult.appendText("Missing data for withdrawing from account.\n");
            resetAllWith();
            return false;
        } return true;
    }

    /**
     * Determine the type of campus based on the fxid of the selected button.
     * @param fxidVal String of the fxid of the selected button.
     * @return Campus object of the type of campus.
     */
    private Campus ccCampus(String fxidVal){
        Campus campus = Campus.NEWARK;

        String campusToStr = "";
        if (fxidVal.equals("openCCCamden")){
            campusToStr = "CAMDEN";
        } else if (fxidVal.equals("openCCNB")){
            campusToStr = "NEW_BRUNSWICK";
        }  else if (fxidVal.equals("openCCNewark")){
            campusToStr = "NEWARK";
        }

        for (Campus check: Campus.values()) {
            if (check.toString().equals(campusToStr)) {
                campus = check;
            }
        }
        return campus;
    }

    /**
     * Determine the type of account based on the fxid of the selected button.
     * @param fxidVal String of the fxid of the selected button.
     * @return String of the type of account.
     */
    private String actNameOpen(String fxidVal){
        switch (fxidVal) {
            case "openC" -> {
                return "C";
            }
            case "openCC" -> {
                return "CC";
            }
            case "openS" -> {
                return "S";
            }
            case "openMM" -> {
                return "MM";
            }
        }
        return "NA";
    }

    /**
     * Determine the type of account based on the fxid of the selected button.
     * @param fxidVal String of the fxid of the selected button.
     * @return String of the type of account.
     */
    private String actNameClose(String fxidVal){
        switch (fxidVal) {
            case "closeC" -> {
                return "C";
            }
            case "closeCC" -> {
                return "CC";
            }
            case "closeS" -> {
                return "S";
            }
            case "closeMM" -> {
                return "MM";
            }
        }
        return "NA";
    }

    /**
     * Determine the type of account based on the fxid of the selected button.
     * @param fxidVal String of the fxid of the selected button.
     * @return String of the type of account.
     */
    private String actNameDep(String fxidVal){
        switch (fxidVal) {
            case "depC" -> {
                return "C";
            }
            case "depCC" -> {
                return "CC";
            }
            case "depS" -> {
                return "S";
            }
            case "depMM" -> {
                return "MM";
            }
        }
        return "NA";
    }

    /**
     * Determine the type of account based on the fxid of the selected button.
     * @param fxidVal String of the fxid of the selected button.
     * @return String of the type of account.
     */
    private String actNameWith(String fxidVal){
        switch (fxidVal) {
            case "withC" -> {
                return "C";
            }
            case "withCC" -> {
                return "CC";
            }
            case "withS" -> {
                return "S";
            }
            case "withMM" -> {
                return "MM";
            }
        }
        return "NA";
    }

    /**
     * Resets all the fields in the Open tab.
     */
    private void resetAllOpen(){
        openFname.clear();
        openLname.clear();
        openDOB.setValue(null);
        openAmt.clear();
        openSLoyal.setSelected(false);
        Account.getToggles().forEach(toggle -> toggle.setSelected(false));
        CampusName.getToggles().forEach(toggle -> toggle.setSelected(false));
    }

    /**
     * Resets all the fields in the Close tab.
     */
    private void resetAllClose(){
        closeFname.clear();
        closeLname.clear();
        closeDOB.setValue(null);
        CloseAccount.getToggles().forEach(toggle -> toggle.setSelected(false));
    }

    /**
     * Resets all the fields in the Deposit tab.
     */
    private void resetAllDep(){
        depFname.clear();
        depLname.clear();
        depDOB.setValue(null);
        depAmt.clear();
        DepositAccount.getToggles().forEach(toggle -> toggle.setSelected(false));
    }

    /**
     * Resets all the fields in the Withdraw tab.
     */
    private void resetAllWith(){
        withFname.clear();
        withLname.clear();
        withDOB.setValue(null);
        withAmt.clear();
        WithdrawAccount.getToggles().forEach(toggle -> toggle.setSelected(false));
    }

    /**
     * Checks if College Checking account is selected and allows Campus selection.
     * @param event The action of choosing a College Checking account.
     */
    @FXML
    private void initializeOpenCC(ActionEvent event) {
        openCCNewark.setDisable(true);
        openCCNB.setDisable(true);
        openCCCamden.setDisable(true);
        openSLoyal.setDisable(true);

        openCC.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = !newValue;
            openCCNewark.setDisable(disable);
            openCCNB.setDisable(disable);
            openCCCamden.setDisable(disable);
        });

        Account.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                openCCNB.setDisable(true);
                openCCCamden.setDisable(true);
                openCCNewark.setDisable(true);
                openSLoyal.setDisable(true);
            }
        });

        boolean disable = !openCC.isSelected();
        openCCNewark.setDisable(disable);
        openCCNB.setDisable(disable);
        openCCCamden.setDisable(disable);

        openS.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean disables = !newValue;
            openSLoyal.setDisable(disables);
        });

        boolean disables = !openS.isSelected();
        openSLoyal.setDisable(disables);
    }


    /**
     * Checks if Savings account is selected and allows Loyal selection.
     * @param event The action of choosing a Savings account.
     */
    @FXML
    private void initializeOpenS(ActionEvent event){
        openSLoyal.setDisable(true);
        if (openS.isSelected()){
            openSLoyal.setDisable(false);
        } else {
            openS.setDisable(true);
        }
        openS.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = !newValue;
            openSLoyal.setDisable(disable);
        });

        Account.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                openSLoyal.setDisable(true);
            }
        });

        boolean disable = !openS.isSelected();
        openSLoyal.setDisable(disable);

    }

    /**
     * Helper method to open accounts from file.
     * @param account Array to hold all the accounts.
     * @return new Account object that was created.
     */
    private Account makeAccountFromArray(String[] account) {
        String[] parsedBday = account[BDAY_IND].split("/");
        Date birthday = new Date(Integer.parseInt(parsedBday[2]), Integer.parseInt(parsedBday[0]), Integer.parseInt(parsedBday[1]));
        switch (account[ACC_IND]) {
            case "C" -> {
                if (checkDate(birthday, "C").equals("T")) {
                    Profile newProfile = new Profile(account[FNAME_IND], account[LNAME_IND], birthday);
                    return new Checking(newProfile, Double.parseDouble(account[BALANCE_IND]));
                }
            } case "CC" -> {
                if (checkDate(birthday, "CC").equals("T")) {
                    Profile newProfile = new Profile(account[FNAME_IND], account[LNAME_IND], birthday);
                    if (checkCampus(Integer.parseInt(account[5]))) { // if it's a valid campus
                        return new CollegeChecking(newProfile, Double.parseDouble(account[BALANCE_IND]), findCampus(Integer.parseInt(account[STATUS_IND])));
                    }
                }
            } case "S" -> {
                if (checkDate(birthday, "S").equals("T")) {
                    Profile newProfile = new Profile(account[FNAME_IND], account[LNAME_IND], birthday);
                    return new Savings(newProfile, Double.parseDouble(account[BALANCE_IND]), !account[STATUS_IND].equals("0"));
                }
            } case "MM" -> {
                if (checkDate(birthday, "MM").equals("T")){
                    Profile newProfile = new Profile(account[FNAME_IND], account[LNAME_IND], birthday);
                    return new MoneyMarket(newProfile, Double.parseDouble(account[BALANCE_IND]), true, 0);
                }
            }
        } return null;
    }

    /**
     * Determine if campus chosen is valid.
     * @param code Campus code.
     * @return true if campus chosen is valid, false otherwise.
     */
    private boolean checkCampus(int code){
        if (code != 0 && code != 1 && code != 2){
            //System.out.println("Invalid campus code.");
            return false;
        }
        Campus campus = Campus.NEWARK;
        for (Campus check: Campus.values()) {
            if (check.getCode() == code) {
                campus = check;
                return true;
            }
        }
        return false;
    }

    /**
     * Determine which campus is chosen.
     * @param campusCode Campus code.
     * @return Campus Enum that is chosen.
     */
    private Campus findCampus(int campusCode){
        Campus campus = Campus.NEWARK;
        for (Campus check: Campus.values()) {
            if (check.getCode() == campusCode) {
                campus = check;
            }
        }
        return campus;
    }

    /**
     * Checks if the birthday associated with a given account is appropriate.
     * @param bday Date object containing the account holder's birthday.
     * @param accountType String containing the type of account.
     * @return Message based on the validity/invalidity of the date.
     */
    private String checkDate(Date bday, String accountType){
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int todaysDay = currentDate.get(Calendar.DAY_OF_MONTH);
        Profile temp = new Profile("Temp", "Temp", bday);

        if (bday.getYear() == currentYear){
            return ("DOB invalid: " + bday + " cannot be today or a future day.\n");
        } else if (!bday.isValid()){
            return ("DOB invalid: " + bday + " not a valid calendar date!\n");
        } else if (accountType.equals("CC")){
            if (temp.age() < MIN_AGE ){
                return ("DOB invalid: " + bday + " under 16.\n");
            } else if (temp.age() > MAX_AGE){
                return ("DOB invalid: " + bday + " over 24.\n");
            } else if (temp.age() == MAX_AGE){
                if(bday.getDay() < todaysDay){
                    return ("DOB invalid: " + bday + " over 24.\n");
                }
            }
        } else if (!accountType.equals("CC")){
            if (temp.age() < MIN_AGE ){
                return ("DOB invalid: " + bday + " under 16.\n");
            }
        }
        return "T"; // Return statement when all the input is correct
    }
}