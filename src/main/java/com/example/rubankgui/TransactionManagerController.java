package com.example.rubankgui;
import java.util.Calendar;

import java.lang.String;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private TextArea depResult;

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
    // "Account Database" Tab GUI Components

    @FXML
    private Button pPrint;

    @FXML
    private Button piPrint;

    @FXML
    private Button ubPrint;

    @FXML
    private TextArea actDBShow;

    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 24;


    // Open Account Methods
    @FXML
    private void openingAccount(ActionEvent event){
        boolean fnameValid = true;
        boolean lnameValid = true;
        boolean dobValid = true;
        boolean amtValid = true;
        boolean actValid = true;
        boolean campusValid = true;

        boolean openActDOB = true; // Checking for input validity.
        boolean openActAmt = true; // Checking for input validity.

        if (openFname.getText().isBlank()){
            fnameValid = false; // First name field is empty.
            openResult.setText("Missing data for opening an account.");
            resetAllOpen();
        }
        if (openLname.getText().isBlank()){
            lnameValid = false; // Last name field is empty.
            openResult.setText("Missing data for opening an account.");
            resetAllOpen();
        }

        if (openDOB.getValue() == null){
            dobValid = false;
            openResult.setText("Missing data for opening an account.");
            resetAllOpen();
        }

        if (openAmt.getText().isBlank()){
            amtValid = false;
            openResult.setText("Missing data for opening an account.");
            resetAllOpen();
        }

        // If no account is selected, then you can't make an account.
        if (Account.getSelectedToggle() == null){
            actValid = false;
            openResult.setText("Missing data for opening an account.");
            resetAllOpen();
        } else {
            RadioButton actTypeButton = (RadioButton) Account.getSelectedToggle();
            String actTypeFXID = actTypeButton.getId(); // FXID of the type of account selected

            if (actTypeFXID.equals("openCC")){
                if (CampusName.getSelectedToggle() == null){
                    campusValid = false;
                    openResult.setText("Invalid campus code.");
                    resetAllOpen();
                }
            }
        }

        if (fnameValid && lnameValid && dobValid && amtValid && actValid && campusValid){
            openResult.clear();
            LocalDate dob = openDOB.getValue();
            int day = dob.getDayOfMonth();
            int month = dob.getMonthValue();
            int year = dob.getYear();

            RadioButton actTypeButton = (RadioButton) Account.getSelectedToggle();
            String actTypeFXID = actTypeButton.getId();

            Date birthday = new Date(year, month, day);
            if (!checkDate(birthday, actNameOpen(actTypeFXID)).equals("T")){
                // If the birthday is not valid
                openActDOB = false;
                openResult.setText(checkDate(birthday, actNameOpen(actTypeFXID)));
            }

            // Checking if the initial amount is valid
            String initialDeposit = openAmt.getText();
            if (initialDeposit.matches("-?\\d*")) {
                // String contains only digits
                int openAmount = Integer.parseInt(initialDeposit);
                if (openAmount <= 0){
                    openActAmt = false;
                    openResult.setText("Initial deposit cannot be 0 or negative.");
                }
            } else {
                openActAmt = false;
                openResult.setText("Not a valid amount.");
            }

            if (!openActAmt || !openActDOB){
                resetAllOpen(); // This needs to stay outside of the above if-statements, otherwise there's a NullPointExcep
            }

            if (openActDOB && openActAmt){
                // Can open the account
                openResult.clear();
                String actName = actNameOpen(actTypeFXID); // Tells us the type of account to open
                if (actName.equals("C")){
                    Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                    Account add = new Checking(holder, Integer.parseInt(openAmt.getText()));
                    if (actDb.open(add)){
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " opened.");
                    } else {
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is already in the database.");
                    }

                } else if (actName.equals("CC")){
                    RadioButton campusTypeButton = (RadioButton) CampusName.getSelectedToggle();
                    String campusTypeFXID = actTypeButton.getId();
                    Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                    Account add = new CollegeChecking(holder, Integer.parseInt(openAmt.getText()), ccCampus(campusTypeFXID));
                    if (actDb.open(add)){
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " opened.");
                    } else {
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is already in the database.");
                    }
                } else if (actName.equals("S")){
                    Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                    Account add = new Savings(holder, Integer.parseInt(openAmt.getText()), openSLoyal.isSelected());
                    if (actDb.open(add)){
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " opened.");
                    } else {
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is already in the database.");
                    }
                } else if (actName.equals("MM")){
                    Profile holder = new Profile(openFname.getText(), openLname.getText(), birthday);
                    Account add = new MoneyMarket(holder, Integer.parseInt(openAmt.getText()), true, 0);
                    if (actDb.open(add)){
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " opened.");
                    } else {
                        openResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is already in the database.");
                    }
                }
            }
        }

    }


    // Close Account Methods
    @FXML
    private void closingAccount(ActionEvent event){
        boolean fnameValid = true;
        boolean lnameValid = true;
        boolean dobValid = true;
        boolean actValid = true;

        boolean closeActDOB = true; // Checking for input validity.

        if (closeFname.getText().isBlank()){
            fnameValid = false; // First name field is empty.
            openResult.setText("Missing data for closing an account.");
            resetAllClose();
        }
        if (closeLname.getText().isBlank()){
            lnameValid = false; // Last name field is empty.
            closeResult.setText("Missing data for closing an account.");
            resetAllClose();
        }

        if (closeDOB.getValue() == null){
            dobValid = false;
            closeResult.setText("Missing data for closing an account.");
            resetAllClose();
        }

        // If no account type is selected, then you can't close an account.
        if (CloseAccount.getSelectedToggle() == null){
            actValid = false;
            closeResult.setText("Missing data for closing an account.");
            resetAllClose();
        }

        if (fnameValid && lnameValid && dobValid && actValid){
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
                closeResult.setText(checkDate(birthday, actNameClose(actTypeFXID)));
            }

            if (!closeActDOB){
                resetAllClose();
            }

            if (closeActDOB){
                closeResult.clear();
                String actName = actNameClose(actTypeFXID); // Tells us the type of account to open
                if (actName.equals("C")){
                    Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                    Account add = new Checking(holder, 0);
                    if (actDb.close(add)){
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " has been closed.");
                    } else {
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is not in the database.");
                    }

                } else if (actName.equals("CC")){
                    Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                    Account add = new CollegeChecking(holder, 0, Campus.NEWARK);
                    if (actDb.close(add)){
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " has been closed.");
                    } else {
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is not in the database.");
                    }
                } else if (actName.equals("S")){
                    Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                    Account add = new Savings(holder, 0, true);
                    if (actDb.close(add)){
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " has been closed.");
                    } else {
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is not in the database.");
                    }
                } else if (actName.equals("MM")){
                    Profile holder = new Profile(closeFname.getText(), closeLname.getText(), birthday);
                    Account add = new MoneyMarket(holder, 0, true, 0);
                    if (actDb.close(add)){
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() +
                                " " + add.getHolder().getDOB() + "(" + actName + ")" + " has been closed.");
                    } else {
                        closeResult.setText(add.getHolder().getFname() + " " + add.getHolder().getLname() + " " + add.getHolder().getDOB() +
                                "(" + actName + ") " + "is not in the database.");
                    }
                }
            }

        }
    }

    // Deposit Account Methods

    // Withdraw Account Methods

    // Account Database Methods









    // General Methods
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
     * Checks if College Checking account is selected and allows Campus selection.
     * @param event The action of choosing a College Checking account.
     */
    @FXML
    private void initializeOpenCC(ActionEvent event){
        openCC.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = !newValue;
            openCCNewark.setDisable(disable);
            openCCNB.setDisable(disable);
            openCCCamden.setDisable(disable);
        });

    }

    /**
     * Checks if Savings account is selected and allows Loyal selection.
     * @param event The action of choosing a Savings account.
     */
    @FXML
    private void initializeOpenS(ActionEvent event){
        openS.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = !newValue;
            openSLoyal.setDisable(disable);
        });
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
            return ("DOB invalid: " + bday + " cannot be today or a future day.");
        } else if (!bday.isValid()){
            return ("DOB invalid: " + bday + " not a valid calendar date!");
        } else if (accountType.equals("CC")){
            if (temp.age() < MIN_AGE ){
                return ("DOB invalid: " + bday + " under 16.");
            } else if (temp.age() > MAX_AGE){
                return ("DOB invalid: " + bday + " over 24.");
            } else if (temp.age() == MAX_AGE){
                if(bday.getDay() < todaysDay){
                    return ("DOB invalid: " + bday + " over 24.");
                }
            }
        } else if (!accountType.equals("CC")){
            if (temp.age() < MIN_AGE ){
                return ("DOB invalid: " + bday + " under 16.");
            }
        }
        return "T"; // Return statement when all the input is correct
    }











}