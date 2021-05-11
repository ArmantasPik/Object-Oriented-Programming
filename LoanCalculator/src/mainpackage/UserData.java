<<<<<<< HEAD
package mainpackage;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import windowpackage.AlertBox;

/**
 * This class is used to store and use loan data of the user
 * @author Armantas Pikšrys PS3
 */
public class UserData {
    private double loanAmount;
    private double interestRate;
    private int loanTermYears, loanTermMonths, loanTermTotal;
    private String loanType;
    private int[] monthNumber;
    private double[] monthlyPayment, monthlyContribution, monthlyInterest, loanLeft;
    private double totalPaid;
    private double totalInterestPaid;

    /**
     * Empty user data constructor
     */
    public UserData() {
        this.loanAmount = 0;
        this.interestRate = 0;
        this.loanTermYears = 0;
        this.loanTermMonths = 0;
        this.loanTermTotal = 0;
        this.loanType = "Annuity";
        this.totalPaid = 0;
        this.totalInterestPaid = 0;
    }

    /**
     * User data constructor to initialize loan data
     * @param loanAmount total loan amount
     * @param loanTermYears loan term, specified years
     * @param loanTermMonths loan term, specified months
     * @param interestRate loans yearly interest rate
     * @param loanType loan type, either annuity or linear
     */
    public UserData(double loanAmount, int loanTermYears, int loanTermMonths, double interestRate, String loanType) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTermYears = loanTermYears;
        this.loanTermMonths = loanTermMonths;
        this.loanType = loanType;
        this.totalPaid = 0;
        this.totalInterestPaid = 0;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setLoanTermYears(int loanTermYears) { this.loanTermYears = loanTermYears; }

    public int getLoanTermYears() {
        return loanTermYears;
    }

    public void setLoanTermMonths(int loanTermMonths) {
        this.loanTermMonths = loanTermMonths;
    }

    public int getLoanTermMonths() {
        return loanTermMonths;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanType() {
        return loanType;
    }

    public int getLoanTermTotal() {
        return loanTermTotal;
    }

    public double[] getMonthlyContribution() {
        return monthlyContribution;
    }

    public double[] getLoanLeft() {
        return loanLeft;
    }

    public double[] getMonthlyInterest() {
        return monthlyInterest;
    }

    public double[] getMonthlyPayment() {
        return monthlyPayment;
    }

    public int[] getMonthNumber() {
        return monthNumber;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public double getTotalInterestPaid() {
        return totalInterestPaid;
    }

    /**
     * Boolean method to validate entered user data input
     * @param loanAmountField Loan amount text field object
     * @param loanTermYearsField loan term of years text field object
     * @param loanTermMonthsField loan term of months text field object
     * @param interestRateField interest rate text field object
     * @return boolean value
     */
    public boolean validateInput(TextField loanAmountField, TextField loanTermYearsField, TextField loanTermMonthsField, TextField interestRateField) {
        double loanAmount, interestRate;
        int loanTermYears, loanTermMonths;

        if(isDouble(loanAmountField, loanAmountField.getText()) && isDouble(interestRateField, interestRateField.getText()) && isInt(loanTermYearsField, loanTermYearsField.getText()) && isInt(loanTermMonthsField, loanTermMonthsField.getText())) {
            loanAmount = Double.parseDouble(loanAmountField.getText());
            interestRate = Double.parseDouble(interestRateField.getText());
            loanTermYears = Integer.parseInt(loanTermYearsField.getText());
            loanTermMonths = Integer.parseInt(loanTermMonthsField.getText());

            if(loanAmount <= 0) {
                AlertBox.display("Warning", "Loan amount cannot be negative or equal to zero");
                return false;
            }
            else if(interestRate < 0 || interestRate > 20) {
                AlertBox.display("Warning", "Interest rate cannot be negative or more than 20%");
                return false;
            }
            else if(loanTermYears <= 0 || loanTermYears > 40) {
                AlertBox.display("Warning", "Loan term in years cannot be negative or longer than 40 years");
                return false;
            }
            else if(loanTermMonths < 0 || loanTermMonths > 12) {
                AlertBox.display("Warning", "Loan term months cannot be negative or exceed 12");
                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    /**
     * Method sets the values of user data object from input fields
     * @param loanAmountField Loan amount text field object
     * @param loanTermYearsField loan term of years text field object
     * @param loanTermMonthsField loan term of months text field object
     * @param interestRateField interest rate text field object
     * @param loanType loan choice box object
     */
    public void setValues(TextField loanAmountField, TextField loanTermYearsField, TextField loanTermMonthsField, String loanType, TextField interestRateField) {
        setLoanAmount(Double.parseDouble(loanAmountField.getText()));
        setLoanTermYears(Integer.parseInt(loanTermYearsField.getText()));
        setLoanTermMonths(Integer.parseInt(loanTermMonthsField.getText()));
        setLoanType(loanType);
        setInterestRate(Double.parseDouble(interestRateField.getText()) / (100 * 12));
        loanTermTotal = loanTermYears * 12 + loanTermMonths;
        monthlyPayment = new double[loanTermTotal];
        monthlyContribution = new double[loanTermTotal];
        monthlyInterest = new double[loanTermTotal];
        loanLeft = new double[loanTermTotal];
        monthNumber = new int[loanTermTotal];
    }

    /**
     * Method that calculates loan payments
     */
    public void calculateLoan() {
        loanLeft[0] = loanAmount;

        if(loanType == "Annuity") {
            totalPaid = 0;
            totalInterestPaid = 0;
            for(int i = 0; i < loanTermTotal; i++) {
                monthNumber[i] = i + 1;
                monthlyPayment[i] =  (interestRate * loanLeft[0]) / (1 - Math.pow((1 + interestRate), (-1 * loanTermTotal)));
                monthlyInterest[i] = interestRate * loanLeft[i];
                totalPaid = totalPaid + monthlyPayment[i];
                totalInterestPaid = totalInterestPaid + monthlyInterest[i];
                monthlyContribution[i] = monthlyPayment[i] - monthlyInterest[i];
                if(i + 1 != loanTermTotal)
                    loanLeft[i + 1] = loanLeft[i] - monthlyContribution[i];
            }
        }
        else if(loanType == "Linear") {
            totalPaid = 0;
            totalInterestPaid = 0;
            for(int i = 0; i < loanTermTotal; i++) {
                monthNumber[i] = i + 1;
                monthlyContribution[i] = loanAmount / loanTermTotal;
                monthlyInterest[i] = loanLeft[i] * interestRate;
                monthlyPayment[i] = monthlyContribution[i] + monthlyInterest[i];
                totalPaid = totalPaid + monthlyPayment[i];
                totalInterestPaid = totalInterestPaid + monthlyInterest[i];
                if(i + 1 != loanTermTotal)
                    loanLeft[i + 1] = loanLeft[i] - monthlyContribution[i];
            }
        }
    }

    /**
     * Method that validates if input is of type Integer, if not it displays an alert box
     * @param input text field object
     * @param message message to display in an alert box if input is not of type Integer
     * @return boolean value
     */
    private static boolean isInt(TextField input, String message) {
        try {
            int intValue = Integer.parseInt(input.getText());
            return true;
        }catch (NumberFormatException e) {
            AlertBox.display("Warning", message + " is not of type integer");
            return false;
        }
    }

    /**
     * Method that validates if input is of type Double, if not it displays an alert box
     * @param input text field object
     * @param message message to display in an alert box if input is not of type Double
     * @return boolean value
     */
    private static boolean isDouble(TextField input, String message) {
        try {
            double doubleValue = Double.parseDouble(input.getText());
            return true;
        }catch (NumberFormatException e) {
            AlertBox.display("Warning", "\"" + message + "\"" + " is not a correct number");
            return false;
        }
    }
}
=======
package mainpackage;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import windowpackage.AlertBox;

/**
 * This class is used to store and use loan data of the user
 * @author Armantas Pikšrys PS3
 */
public class UserData {
    private double loanAmount;
    private double interestRate;
    private int loanTermYears, loanTermMonths, loanTermTotal;
    private String loanType;
    private int[] monthNumber;
    private double[] monthlyPayment, monthlyContribution, monthlyInterest, loanLeft;
    private double totalPaid;
    private double totalInterestPaid;

    /**
     * Empty user data constructor
     */
    public UserData() {
        this.loanAmount = 0;
        this.interestRate = 0;
        this.loanTermYears = 0;
        this.loanTermMonths = 0;
        this.loanTermTotal = 0;
        this.loanType = "Annuity";
        this.totalPaid = 0;
        this.totalInterestPaid = 0;
    }

    /**
     * User data constructor to initialize loan data
     * @param loanAmount total loan amount
     * @param loanTermYears loan term, specified years
     * @param loanTermMonths loan term, specified months
     * @param interestRate loans yearly interest rate
     * @param loanType loan type, either annuity or linear
     */
    public UserData(double loanAmount, int loanTermYears, int loanTermMonths, double interestRate, String loanType) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTermYears = loanTermYears;
        this.loanTermMonths = loanTermMonths;
        this.loanType = loanType;
        this.totalPaid = 0;
        this.totalInterestPaid = 0;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setLoanTermYears(int loanTermYears) { this.loanTermYears = loanTermYears; }

    public int getLoanTermYears() {
        return loanTermYears;
    }

    public void setLoanTermMonths(int loanTermMonths) {
        this.loanTermMonths = loanTermMonths;
    }

    public int getLoanTermMonths() {
        return loanTermMonths;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanType() {
        return loanType;
    }

    public int getLoanTermTotal() {
        return loanTermTotal;
    }

    public double[] getMonthlyContribution() {
        return monthlyContribution;
    }

    public double[] getLoanLeft() {
        return loanLeft;
    }

    public double[] getMonthlyInterest() {
        return monthlyInterest;
    }

    public double[] getMonthlyPayment() {
        return monthlyPayment;
    }

    public int[] getMonthNumber() {
        return monthNumber;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public double getTotalInterestPaid() {
        return totalInterestPaid;
    }

    /**
     * Boolean method to validate entered user data input
     * @param loanAmountField Loan amount text field object
     * @param loanTermYearsField loan term of years text field object
     * @param loanTermMonthsField loan term of months text field object
     * @param interestRateField interest rate text field object
     * @return boolean value
     */
    public boolean validateInput(TextField loanAmountField, TextField loanTermYearsField, TextField loanTermMonthsField, TextField interestRateField) {
        double loanAmount, interestRate;
        int loanTermYears, loanTermMonths;

        if(isDouble(loanAmountField, loanAmountField.getText()) && isDouble(interestRateField, interestRateField.getText()) && isInt(loanTermYearsField, loanTermYearsField.getText()) && isInt(loanTermMonthsField, loanTermMonthsField.getText())) {
            loanAmount = Double.parseDouble(loanAmountField.getText());
            interestRate = Double.parseDouble(interestRateField.getText());
            loanTermYears = Integer.parseInt(loanTermYearsField.getText());
            loanTermMonths = Integer.parseInt(loanTermMonthsField.getText());

            if(loanAmount <= 0) {
                AlertBox.display("Warning", "Loan amount cannot be negative or equal to zero");
                return false;
            }
            else if(interestRate < 0 || interestRate > 20) {
                AlertBox.display("Warning", "Interest rate cannot be negative or more than 20%");
                return false;
            }
            else if(loanTermYears <= 0 || loanTermYears > 40) {
                AlertBox.display("Warning", "Loan term in years cannot be negative or longer than 40 years");
                return false;
            }
            else if(loanTermMonths < 0 || loanTermMonths > 12) {
                AlertBox.display("Warning", "Loan term months cannot be negative or exceed 12");
                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    /**
     * Method sets the values of user data object from input fields
     * @param loanAmountField Loan amount text field object
     * @param loanTermYearsField loan term of years text field object
     * @param loanTermMonthsField loan term of months text field object
     * @param interestRateField interest rate text field object
     * @param loanType loan choice box object
     */
    public void setValues(TextField loanAmountField, TextField loanTermYearsField, TextField loanTermMonthsField, String loanType, TextField interestRateField) {
        setLoanAmount(Double.parseDouble(loanAmountField.getText()));
        setLoanTermYears(Integer.parseInt(loanTermYearsField.getText()));
        setLoanTermMonths(Integer.parseInt(loanTermMonthsField.getText()));
        setLoanType(loanType);
        setInterestRate(Double.parseDouble(interestRateField.getText()) / (100 * 12));
        loanTermTotal = loanTermYears * 12 + loanTermMonths;
        monthlyPayment = new double[loanTermTotal];
        monthlyContribution = new double[loanTermTotal];
        monthlyInterest = new double[loanTermTotal];
        loanLeft = new double[loanTermTotal];
        monthNumber = new int[loanTermTotal];
    }

    /**
     * Method that calculates loan payments
     */
    public void calculateLoan() {
        loanLeft[0] = loanAmount;

        if(loanType == "Annuity") {
            totalPaid = 0;
            totalInterestPaid = 0;
            for(int i = 0; i < loanTermTotal; i++) {
                monthNumber[i] = i + 1;
                monthlyPayment[i] =  (interestRate * loanLeft[0]) / (1 - Math.pow((1 + interestRate), (-1 * loanTermTotal)));
                monthlyInterest[i] = interestRate * loanLeft[i];
                totalPaid = totalPaid + monthlyPayment[i];
                totalInterestPaid = totalInterestPaid + monthlyInterest[i];
                monthlyContribution[i] = monthlyPayment[i] - monthlyInterest[i];
                if(i + 1 != loanTermTotal)
                    loanLeft[i + 1] = loanLeft[i] - monthlyContribution[i];
            }
        }
        else if(loanType == "Linear") {
            totalPaid = 0;
            totalInterestPaid = 0;
            for(int i = 0; i < loanTermTotal; i++) {
                monthNumber[i] = i + 1;
                monthlyContribution[i] = loanAmount / loanTermTotal;
                monthlyInterest[i] = loanLeft[i] * interestRate;
                monthlyPayment[i] = monthlyContribution[i] + monthlyInterest[i];
                totalPaid = totalPaid + monthlyPayment[i];
                totalInterestPaid = totalInterestPaid + monthlyInterest[i];
                if(i + 1 != loanTermTotal)
                    loanLeft[i + 1] = loanLeft[i] - monthlyContribution[i];
            }
        }
    }

    /**
     * Method that validates if input is of type Integer, if not it displays an alert box
     * @param input text field object
     * @param message message to display in an alert box if input is not of type Integer
     * @return boolean value
     */
    private static boolean isInt(TextField input, String message) {
        try {
            int intValue = Integer.parseInt(input.getText());
            return true;
        }catch (NumberFormatException e) {
            AlertBox.display("Warning", message + " is not of type integer");
            return false;
        }
    }

    /**
     * Method that validates if input is of type Double, if not it displays an alert box
     * @param input text field object
     * @param message message to display in an alert box if input is not of type Double
     * @return boolean value
     */
    private static boolean isDouble(TextField input, String message) {
        try {
            double doubleValue = Double.parseDouble(input.getText());
            return true;
        }catch (NumberFormatException e) {
            AlertBox.display("Warning", "\"" + message + "\"" + " is not a correct number");
            return false;
        }
    }
}
>>>>>>> 00aeec047a54339b3dd9a90e146b2ad2412eb7da
