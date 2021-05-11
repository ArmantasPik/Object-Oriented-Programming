package mainpackage;

/**
 * This class is used to set and get one months loan data
 * @author Armantas Pikšrys PS3
 */
public class MonthlyData {
    private int month;
    private double monthlyPayment, monthlyContribution, monthlyInterest, loanLeft;

    /**
     * Empty initialization monthly data constructor
     */
    public MonthlyData() {
        this.month = 0;
        this.monthlyPayment = 0;
        this.monthlyContribution = 0;
        this.monthlyInterest = 0;
        this.loanLeft = 0;
    }

    /**
     * Constructor to initialize monthly data
     * @param month month number of the payment
     * @param monthlyPayment months monthly payment
     * @param monthlyContribution months contribution to the loan
     * @param monthlyInterest months paid interest
     * @param loanLeft months left loan to pay
     */
    public MonthlyData(int month, double monthlyPayment, double monthlyContribution, double monthlyInterest, double loanLeft) {
        this.month = month;
        this.monthlyPayment = monthlyPayment;
        this.monthlyContribution = monthlyContribution;
        this.monthlyInterest = monthlyInterest;
        this.loanLeft = loanLeft;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getMonthlyContribution() {
        return monthlyContribution;
    }

    public void setMonthlyContribution(double monthlyContribution) {
        this.monthlyContribution = monthlyContribution;
    }

    public double getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public double getLoanLeft() {
        return loanLeft;
    }

    public void setLoanLeft(double loanLeft) {
        this.loanLeft = loanLeft;
    }
}
