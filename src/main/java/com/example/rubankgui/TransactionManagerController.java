package com.example.rubankgui;

import java.lang.String;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TransactionManagerController {
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

    // Open Account Methods
    @FXML
    private boolean validProfile(ActionEvent event){
        boolean isValidProfile = false;

        String fname = openFname.getText();
        String lname = openLname.getText();

        LocalDate dob = openDOB.getValue();
        int day = dob.getDayOfMonth();
        int month = dob.getMonthValue();
        int year = dob.getYear();

        return isValidProfile;
    }

    @FXML
    private boolean canOpen(ActionEvent event){
        boolean canOpenAct = false;
        //  Can use this method to determine if we can open an account or not
        // Does it already exist/etc. --> look thru transactionmgr.java in proj 2 for requirements
        String fname = openFname.getText();
        String lname = openLname.getText();

        LocalDate dob = openDOB.getValue();
        int day = dob.getDayOfMonth();
        int month = dob.getMonthValue();
        int year = dob.getYear();

        return canOpenAct;
    }

    @FXML
    private void openingAccount(ActionEvent event){
        String fname = openFname.getText();
        String lname = openLname.getText();

        LocalDate dob = openDOB.getValue();
        int day = dob.getDayOfMonth();
        int month = dob.getMonthValue();
        int year = dob.getYear();

        String initialDeposit = openAmt.getText();
        int depositAmt = Integer.parseInt(initialDeposit);


        if (openC.isSelected()){
            // Do checking stuff
        } else if (openCC.isSelected()){

            if (openCCNB.isSelected()){
                // New Brunswick account
            } else if (openCCCamden.isSelected()){
                // Camden account
            } else if (openCCNewark.isSelected()){
                // Newark account
            }

            // Do collegechecking stuff
        } else if (openS.isSelected()){
            if (openSLoyal.isSelected()){
                // Account holder is loyal
            }
            // Do savings stuff
        } else if (openMM.isSelected()){
            // Do moneymarket stuff
        }

        // Add code to open the account here
        // Will also need to add code to check if all the inputs are valid
        boolean acctOpened = false;
        boolean isValidProfile = false;

        if (openSubmit.isPressed()) {
            if (acctOpened) {
                openResult.appendText("Account successfully opened.");
            } else if (!acctOpened) {
                openResult.appendText("Failed to open account.");
                if (!isValidProfile) {
                    openResult.appendText("Invalid profile ");
                }
            }
        }

    }

    // Close Account Methods

    // Deposit Account Methods

    // Withdraw Account Methods

    // Account Database Methods










}