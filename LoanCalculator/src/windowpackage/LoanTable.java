<<<<<<< HEAD
package windowpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import mainpackage.MonthlyData;
import mainpackage.UserData;

import java.text.DecimalFormat;

/**
 * This class is used to display a loan table
 * @author Armantas Pikšrys PS3
 */
public class LoanTable extends Window {

    private TableView table;
    private ObservableList<MonthlyData> data;

    /**
     * LoanTable object constructor initialized table view object
     */
    public LoanTable() {
        this.table = new TableView();
    }

    /**
     *This method creates a loan table and then displays a separate window with a specified window title, message, UserData object and BorderPane object
     * @param title title of the separate window
     * @param message message that the separate window can display
     * @param userData UserData object
     * @param root BorderPane object that will contain nodes to display
     */
    @Override
    public void display(String title, String message, UserData userData, BorderPane root) {
        table = new TableView();

        //Creating table
        table.setEditable(true);
        TableColumn monthCol = new TableColumn("Month");
        monthCol.setMinWidth(150);
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn paymentCol = new TableColumn("Monthly Payment");
        paymentCol.setMinWidth(150);
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("monthlyPayment"));

        TableColumn contributionCol = new TableColumn("Monthly Contribution");
        contributionCol.setMinWidth(150);
        contributionCol.setCellValueFactory(new PropertyValueFactory<>("monthlyContribution"));

        TableColumn interestCol = new TableColumn("Monthly Interest");
        interestCol.setMinWidth(150);
        interestCol.setCellValueFactory(new PropertyValueFactory<>("monthlyInterest"));

        TableColumn loanLeftCol = new TableColumn("Loan Left");
        loanLeftCol.setMinWidth(150);
        loanLeftCol.setCellValueFactory(new PropertyValueFactory<>("loanLeft"));

        table.setItems(getMonthlyData(userData));

        table.getColumns().addAll(monthCol, paymentCol, contributionCol, interestCol, loanLeftCol);

        //Setting up Pane
        root.setCenter(table);

        //Calling super class to display
        super.display(title, message, userData, root);
    }

    /**
     * This private method is used by the display method to create an observable list of user data
     * @param userData UserData object that contains loan information
     * @return returns an observable list of user data information
     */
    private ObservableList<MonthlyData> getMonthlyData(UserData userData) {
        ObservableList<MonthlyData> monthlyData = FXCollections.observableArrayList();
        for(int i = 0; i < userData.getLoanTermTotal(); i++)
            monthlyData.add(new MonthlyData(userData.getMonthNumber()[i], Math.round(userData.getMonthlyPayment()[i] * 100) / 100.0, Math.round(userData.getMonthlyContribution()[i] * 100) / 100.0,
                                            Math.round(userData.getMonthlyInterest()[i] * 100) / 100.0, Math.round(userData.getLoanLeft()[i] * 100) / 100));

        return monthlyData;
    }
}
=======
package windowpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import mainpackage.MonthlyData;
import mainpackage.UserData;

import java.text.DecimalFormat;

/**
 * This class is used to display a loan table
 * @author Armantas Pikšrys PS3
 */
public class LoanTable extends Window {

    private TableView table;
    private ObservableList<MonthlyData> data;

    /**
     * LoanTable object constructor initialized table view object
     */
    public LoanTable() {
        this.table = new TableView();
    }

    /**
     *This method creates a loan table and then displays a separate window with a specified window title, message, UserData object and BorderPane object
     * @param title title of the separate window
     * @param message message that the separate window can display
     * @param userData UserData object
     * @param root BorderPane object that will contain nodes to display
     */
    @Override
    public void display(String title, String message, UserData userData, BorderPane root) {
        table = new TableView();

        //Creating table
        table.setEditable(true);
        TableColumn monthCol = new TableColumn("Month");
        monthCol.setMinWidth(150);
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn paymentCol = new TableColumn("Monthly Payment");
        paymentCol.setMinWidth(150);
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("monthlyPayment"));

        TableColumn contributionCol = new TableColumn("Monthly Contribution");
        contributionCol.setMinWidth(150);
        contributionCol.setCellValueFactory(new PropertyValueFactory<>("monthlyContribution"));

        TableColumn interestCol = new TableColumn("Monthly Interest");
        interestCol.setMinWidth(150);
        interestCol.setCellValueFactory(new PropertyValueFactory<>("monthlyInterest"));

        TableColumn loanLeftCol = new TableColumn("Loan Left");
        loanLeftCol.setMinWidth(150);
        loanLeftCol.setCellValueFactory(new PropertyValueFactory<>("loanLeft"));

        table.setItems(getMonthlyData(userData));

        table.getColumns().addAll(monthCol, paymentCol, contributionCol, interestCol, loanLeftCol);

        //Setting up Pane
        root.setCenter(table);

        //Calling super class to display
        super.display(title, message, userData, root);
    }

    /**
     * This private method is used by the display method to create an observable list of user data
     * @param userData UserData object that contains loan information
     * @return returns an observable list of user data information
     */
    private ObservableList<MonthlyData> getMonthlyData(UserData userData) {
        ObservableList<MonthlyData> monthlyData = FXCollections.observableArrayList();
        for(int i = 0; i < userData.getLoanTermTotal(); i++)
            monthlyData.add(new MonthlyData(userData.getMonthNumber()[i], Math.round(userData.getMonthlyPayment()[i] * 100) / 100.0, Math.round(userData.getMonthlyContribution()[i] * 100) / 100.0,
                                            Math.round(userData.getMonthlyInterest()[i] * 100) / 100.0, Math.round(userData.getLoanLeft()[i] * 100) / 100));

        return monthlyData;
    }
}
>>>>>>> 00aeec047a54339b3dd9a90e146b2ad2412eb7da
