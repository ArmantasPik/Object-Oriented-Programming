<<<<<<< HEAD
package windowpackage;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import mainpackage.UserData;

/**
 * This class is used to display a loan chart
 * @author Armantas Pikšrys PS3
 */
public class LoanChart extends Window {
    /**
     * LoanChart object empty constructor
     */
    public LoanChart(){}

    /**
     *This method creates a loan chart and then displays a separate window with a specified window title, message, UserData object and BorderPane object
     * @param title title of the separate window
     * @param message message that the separate window can display
     * @param userData UserData object
     * @param root BorderPane object that will contain nodes to display
     */
    @Override
    public void display(String title, String message, UserData userData, BorderPane root) {
        //Defining line chart
        NumberAxis xAxis = new NumberAxis(0, userData.getLoanTermTotal() + userData.getLoanTermTotal() / 12, userData.getLoanTermTotal() / 12);
        xAxis.setLabel("Months");

        NumberAxis yAxis = new NumberAxis(0, userData.getMonthlyPayment()[0] + userData.getMonthlyPayment()[0] / 10, userData.getMonthlyPayment()[0] / 10);
        yAxis.setLabel("Euro €");

        LineChart lineChart = new LineChart(xAxis, yAxis);

        //Prepare series
        XYChart.Series seriesPayment = new XYChart.Series();
        XYChart.Series seriesInterest = new XYChart.Series();
        XYChart.Series seriesContribution = new XYChart.Series();
        seriesPayment.setName("Monthly Payment");
        seriesInterest.setName("Monthly Interest");
        seriesContribution.setName("Monthly Contribution");
        for(int i = 0; i < userData.getLoanTermTotal(); i++) {
            seriesPayment.getData().add(new XYChart.Data(i + 1, userData.getMonthlyPayment()[i]));
            seriesInterest.getData().add(new XYChart.Data(i + 1, userData.getMonthlyInterest()[i]));
            seriesContribution.getData().add(new XYChart.Data(i + 1, userData.getMonthlyContribution()[i]));
        }

        //Setting the data to Line Chart
        lineChart.getData().addAll(seriesPayment, seriesInterest, seriesContribution);

        //Setting up Pane
        root.setCenter(lineChart);

        //Call super class to display
        super.display(title, message, userData, root);
    }

}
=======
package windowpackage;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import mainpackage.UserData;

/**
 * This class is used to display a loan chart
 * @author Armantas Pikšrys PS3
 */
public class LoanChart extends Window {
    /**
     * LoanChart object empty constructor
     */
    public LoanChart(){}

    /**
     *This method creates a loan chart and then displays a separate window with a specified window title, message, UserData object and BorderPane object
     * @param title title of the separate window
     * @param message message that the separate window can display
     * @param userData UserData object
     * @param root BorderPane object that will contain nodes to display
     */
    @Override
    public void display(String title, String message, UserData userData, BorderPane root) {
        //Defining line chart
        NumberAxis xAxis = new NumberAxis(0, userData.getLoanTermTotal() + userData.getLoanTermTotal() / 12, userData.getLoanTermTotal() / 12);
        xAxis.setLabel("Months");

        NumberAxis yAxis = new NumberAxis(0, userData.getMonthlyPayment()[0] + userData.getMonthlyPayment()[0] / 10, userData.getMonthlyPayment()[0] / 10);
        yAxis.setLabel("Euro €");

        LineChart lineChart = new LineChart(xAxis, yAxis);

        //Prepare series
        XYChart.Series seriesPayment = new XYChart.Series();
        XYChart.Series seriesInterest = new XYChart.Series();
        XYChart.Series seriesContribution = new XYChart.Series();
        seriesPayment.setName("Monthly Payment");
        seriesInterest.setName("Monthly Interest");
        seriesContribution.setName("Monthly Contribution");
        for(int i = 0; i < userData.getLoanTermTotal(); i++) {
            seriesPayment.getData().add(new XYChart.Data(i + 1, userData.getMonthlyPayment()[i]));
            seriesInterest.getData().add(new XYChart.Data(i + 1, userData.getMonthlyInterest()[i]));
            seriesContribution.getData().add(new XYChart.Data(i + 1, userData.getMonthlyContribution()[i]));
        }

        //Setting the data to Line Chart
        lineChart.getData().addAll(seriesPayment, seriesInterest, seriesContribution);

        //Setting up Pane
        root.setCenter(lineChart);

        //Call super class to display
        super.display(title, message, userData, root);
    }

}
>>>>>>> 00aeec047a54339b3dd9a90e146b2ad2412eb7da
