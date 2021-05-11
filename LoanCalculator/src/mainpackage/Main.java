<<<<<<< HEAD
package mainpackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import windowpackage.LoanChart;
import windowpackage.LoanTable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main JavaFX class for the loan calculator
 * @author Armantas Pikšrys PS3
 */
public class Main extends Application {

    private UserData userData = new UserData();
    private UserData otherUserData = new UserData();
    private LoanTable loanTable = new LoanTable();
    private LoanChart loanChart = new LoanChart();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Primary stage handling and title
        primaryStage.setTitle("Loan Calculator");
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(primaryStage);
        });

        //Border Pane (Main Pane)
        BorderPane mainPane = new BorderPane();

        //Initialize basic UI
        initUI(mainPane);

        //Center grid pane
        GridPane centerGrid = new GridPane();
        centerGrid.setPadding(new Insets(70, 10, 10, 10));
        centerGrid.setVgap(65);
        centerGrid.setHgap(5);
        centerGrid.setStyle("-fx-background-color: BEIGE");

        //Defining text fields and labels for loan amount, loan term and interest rate
        TextField loanAmountField = new TextField();
        loanAmountField.setPromptText("Enter the loan amount");
        GridPane.setConstraints(loanAmountField, 0, 0);
        Label loanAmountLabel = new Label("€");
        GridPane.setConstraints(loanAmountLabel, 1, 0);

        TextField interestRateField = new TextField();
        interestRateField.setPromptText("Enter interest rate");
        GridPane.setConstraints(interestRateField, 0, 2);
        Label interestRateLabel = new Label("%");
        GridPane.setConstraints(interestRateLabel, 1, 2);

        TextField loanTermYearsField = new TextField();
        loanTermYearsField.setPromptText("Enter Years");
        GridPane.setConstraints(loanTermYearsField, 0, 1);
        TextField loanTermMonthsField = new TextField();
        loanTermMonthsField.setPromptText("Enter Months");
        GridPane.setConstraints(loanTermMonthsField, 1, 1);

        centerGrid.getChildren().addAll(loanAmountField, interestRateField, loanTermYearsField, loanTermMonthsField, loanAmountLabel, interestRateLabel);

        //Defining Loan type choice box
        ChoiceBox<String> loanTypeBox = new ChoiceBox<>();
        loanTypeBox.getItems().addAll("Annuity", "Linear");
        loanTypeBox.setValue("Annuity");
        GridPane.setConstraints(loanTypeBox, 0, 3);
        centerGrid.getChildren().add(loanTypeBox);

        //Defining action buttons
        Button showGraph = new Button("Show Graph");
        showGraph.setMinSize(100, 50);
        Button saveFile = new Button("Save to File");
        saveFile.setMinSize(100, 50);
        Button showTable = new Button("Show Table");
        showTable.setMinSize(100, 50);

        //Bottom HBox
        HBox bottomLayout = new HBox(20);
        bottomLayout.getChildren().addAll(showTable, showGraph, saveFile);
        bottomLayout.setStyle("-fx-background-color: #8c8c7b;");
        bottomLayout.setPadding(new Insets(50,60,50,50));
        bottomLayout.setAlignment(Pos.CENTER_RIGHT);

        //Handling Graph Button press
        showGraph.setOnAction(e -> {
            if(userData.validateInput(loanAmountField, loanTermYearsField, loanTermMonthsField, interestRateField)) {
                userData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, loanTypeBox.getValue(), interestRateField);
                userData.calculateLoan();
                if(userData.getLoanType().equals("Annuity"))
                    loanChart.display("Annuity Chart", "Annuity Loan Chart", userData, new BorderPane());
                else
                    loanChart.display("Linear Chart", "Linear Loan Chart", userData, new BorderPane());
            }
        });

        //Handling save file button press
        final String[] loanStatement = new String[1];

        saveFile.setOnAction(e -> {
            if(userData.validateInput(loanAmountField, loanTermYearsField, loanTermMonthsField, interestRateField)) {
                userData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, loanTypeBox.getValue(), interestRateField);
                userData.calculateLoan();
                if(loanTypeBox.getValue() == "Annuity")
                    otherUserData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, "Linear", interestRateField);
                else
                    otherUserData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, "Annuity", interestRateField);

                otherUserData.calculateLoan();

                String loanStatementStart = new String();

                for(int i = 0; i < userData.getLoanTermTotal(); i++)
                    loanStatementStart = loanStatementStart.concat(userData.getMonthNumber()[i] + "   " + Math.round(userData.getMonthlyPayment()[i] * 100) / 100.0 + "   " +
                                                                    Math.round(userData.getMonthlyContribution()[i] * 100) / 100.0 + "   " + Math.round(userData.getMonthlyInterest()[i] * 100) / 100.0 + "   " +
                                                                    Math.round(userData.getLoanLeft()[i] * 100) / 100.0 + "\n");

                loanStatement[0] = "Loan amount: " + userData.getLoanAmount() + "   Total loan term: " + userData.getLoanTermTotal() + "   Interest rate: " + userData.getInterestRate() * 12 * 100 + "  \n" + userData.getLoanType()
                                    + " total paid amount: " + Math.round(userData.getTotalPaid() * 100) / 100.0 + "| Total Interest paid: " + Math.round(userData.getTotalInterestPaid() * 100) / 100.0 + "\n"
                                    + otherUserData.getLoanType() + " total paid amount: " + Math.round(otherUserData.getTotalPaid() * 100) / 100.0 + "| Total Interest paid: " + Math.round(otherUserData.getTotalInterestPaid() * 100) / 100.0 + "\n"
                                    + "Difference in total paid:" + ((userData.getTotalPaid() > otherUserData.getTotalPaid()) ? Math.round((userData.getTotalPaid() - otherUserData.getTotalPaid()) * 100) / 100.0 : Math.round((otherUserData.getTotalPaid() - userData.getTotalPaid()) * 100) / 100.0 )
                                    + "\n\n|Month| " + "|Monthly Payment| " + "|Monthly Contribution| " + "|Monthly Interest| " + "|Left to pay| \n\n" + loanStatementStart;

                FileChooser fileChooser = new FileChooser();

                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extensionFilter);

                //Show dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    saveTextToFile(loanStatement[0], file);
                }
            }
        });

        //Handling Show Table button press
        showTable.setOnAction(e ->{
            if(userData.validateInput(loanAmountField, loanTermYearsField, loanTermMonthsField, interestRateField)) {
                userData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, loanTypeBox.getValue(), interestRateField);
                userData.calculateLoan();
                if(userData.getLoanType().equals("Annuity")) {
                    new LoanTable();
                    loanTable.display("Annuity Table", "Annuity Loan Table", userData, new BorderPane());
                }
                else {
                    new LoanTable();
                    loanTable.display("Linear Table", "Linear Loan Table", userData, new BorderPane());
                }
            }
        });

        //Set up main Border Pane
        mainPane.setCenter(centerGrid);
        mainPane.setBottom(bottomLayout);

        Scene primaryScene = new Scene(mainPane, 600, 800);

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    /**
     * Method to write text to a specified file
     * @param content String object
     * @param file file where to write
     */
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to initialize basic UI
     * @param mainPane main border pane object
     */
    private void initUI(BorderPane mainPane) {
        //Top message
        Label topMessage = new Label("Personal Loan Calculator");
        topMessage.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 30));

        //Top HBox
        HBox topLayout = new HBox(10);
        topLayout.getChildren().add(topMessage);
        topLayout.setStyle("-fx-background-color: #d2d2a6");
        topLayout.setAlignment(Pos.CENTER);
        topLayout.setMinHeight(100);

        //Defining text for instructions
        Text loanAmountText = new Text("Loan amount");
        loanAmountText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Text loanTermText = new Text("Loan term");
        loanTermText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Text interestRateText = new Text("Interest rate");
        interestRateText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Text loanTypeText = new Text("Loan type");
        loanTypeText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        //Left text VBox
        VBox textOptions = new VBox(60);
        textOptions.getChildren().addAll(loanAmountText, loanTermText, interestRateText, loanTypeText);
        textOptions.setStyle("-fx-background-color: BEIGE;");
        textOptions.setPadding(new Insets(60,30,30,50));
        textOptions.setAlignment(Pos.BASELINE_RIGHT);

        mainPane.setStyle("-fx-background-color: BEIGE");
        mainPane.setTop(topLayout);
        mainPane.setLeft(textOptions);

    }

    /**
     * Method to properly close program
     * @param primaryStage primary stage object
     */
    private void closeProgram(Stage primaryStage) {
        primaryStage.close();
    }
}
=======
package mainpackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import windowpackage.LoanChart;
import windowpackage.LoanTable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main JavaFX class for the loan calculator
 * @author Armantas Pikšrys PS3
 */
public class Main extends Application {

    private UserData userData = new UserData();
    private UserData otherUserData = new UserData();
    private LoanTable loanTable = new LoanTable();
    private LoanChart loanChart = new LoanChart();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Primary stage handling and title
        primaryStage.setTitle("Loan Calculator");
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(primaryStage);
        });

        //Border Pane (Main Pane)
        BorderPane mainPane = new BorderPane();

        //Initialize basic UI
        initUI(mainPane);

        //Center grid pane
        GridPane centerGrid = new GridPane();
        centerGrid.setPadding(new Insets(70, 10, 10, 10));
        centerGrid.setVgap(65);
        centerGrid.setHgap(5);
        centerGrid.setStyle("-fx-background-color: BEIGE");

        //Defining text fields and labels for loan amount, loan term and interest rate
        TextField loanAmountField = new TextField();
        loanAmountField.setPromptText("Enter the loan amount");
        GridPane.setConstraints(loanAmountField, 0, 0);
        Label loanAmountLabel = new Label("€");
        GridPane.setConstraints(loanAmountLabel, 1, 0);

        TextField interestRateField = new TextField();
        interestRateField.setPromptText("Enter interest rate");
        GridPane.setConstraints(interestRateField, 0, 2);
        Label interestRateLabel = new Label("%");
        GridPane.setConstraints(interestRateLabel, 1, 2);

        TextField loanTermYearsField = new TextField();
        loanTermYearsField.setPromptText("Enter Years");
        GridPane.setConstraints(loanTermYearsField, 0, 1);
        TextField loanTermMonthsField = new TextField();
        loanTermMonthsField.setPromptText("Enter Months");
        GridPane.setConstraints(loanTermMonthsField, 1, 1);

        centerGrid.getChildren().addAll(loanAmountField, interestRateField, loanTermYearsField, loanTermMonthsField, loanAmountLabel, interestRateLabel);

        //Defining Loan type choice box
        ChoiceBox<String> loanTypeBox = new ChoiceBox<>();
        loanTypeBox.getItems().addAll("Annuity", "Linear");
        loanTypeBox.setValue("Annuity");
        GridPane.setConstraints(loanTypeBox, 0, 3);
        centerGrid.getChildren().add(loanTypeBox);

        //Defining action buttons
        Button showGraph = new Button("Show Graph");
        showGraph.setMinSize(100, 50);
        Button saveFile = new Button("Save to File");
        saveFile.setMinSize(100, 50);
        Button showTable = new Button("Show Table");
        showTable.setMinSize(100, 50);

        //Bottom HBox
        HBox bottomLayout = new HBox(20);
        bottomLayout.getChildren().addAll(showTable, showGraph, saveFile);
        bottomLayout.setStyle("-fx-background-color: #8c8c7b;");
        bottomLayout.setPadding(new Insets(50,60,50,50));
        bottomLayout.setAlignment(Pos.CENTER_RIGHT);

        //Handling Graph Button press
        showGraph.setOnAction(e -> {
            if(userData.validateInput(loanAmountField, loanTermYearsField, loanTermMonthsField, interestRateField)) {
                userData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, loanTypeBox.getValue(), interestRateField);
                userData.calculateLoan();
                if(userData.getLoanType().equals("Annuity"))
                    loanChart.display("Annuity Chart", "Annuity Loan Chart", userData, new BorderPane());
                else
                    loanChart.display("Linear Chart", "Linear Loan Chart", userData, new BorderPane());
            }
        });

        //Handling save file button press
        final String[] loanStatement = new String[1];

        saveFile.setOnAction(e -> {
            if(userData.validateInput(loanAmountField, loanTermYearsField, loanTermMonthsField, interestRateField)) {
                userData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, loanTypeBox.getValue(), interestRateField);
                userData.calculateLoan();
                if(loanTypeBox.getValue() == "Annuity")
                    otherUserData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, "Linear", interestRateField);
                else
                    otherUserData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, "Annuity", interestRateField);

                otherUserData.calculateLoan();

                String loanStatementStart = new String();

                for(int i = 0; i < userData.getLoanTermTotal(); i++)
                    loanStatementStart = loanStatementStart.concat(userData.getMonthNumber()[i] + "   " + Math.round(userData.getMonthlyPayment()[i] * 100) / 100.0 + "   " +
                                                                    Math.round(userData.getMonthlyContribution()[i] * 100) / 100.0 + "   " + Math.round(userData.getMonthlyInterest()[i] * 100) / 100.0 + "   " +
                                                                    Math.round(userData.getLoanLeft()[i] * 100) / 100.0 + "\n");

                loanStatement[0] = "Loan amount: " + userData.getLoanAmount() + "   Total loan term: " + userData.getLoanTermTotal() + "   Interest rate: " + userData.getInterestRate() * 12 * 100 + "  \n" + userData.getLoanType()
                                    + " total paid amount: " + Math.round(userData.getTotalPaid() * 100) / 100.0 + "| Total Interest paid: " + Math.round(userData.getTotalInterestPaid() * 100) / 100.0 + "\n"
                                    + otherUserData.getLoanType() + " total paid amount: " + Math.round(otherUserData.getTotalPaid() * 100) / 100.0 + "| Total Interest paid: " + Math.round(otherUserData.getTotalInterestPaid() * 100) / 100.0 + "\n"
                                    + "Difference in total paid:" + ((userData.getTotalPaid() > otherUserData.getTotalPaid()) ? Math.round((userData.getTotalPaid() - otherUserData.getTotalPaid()) * 100) / 100.0 : Math.round((otherUserData.getTotalPaid() - userData.getTotalPaid()) * 100) / 100.0 )
                                    + "\n\n|Month| " + "|Monthly Payment| " + "|Monthly Contribution| " + "|Monthly Interest| " + "|Left to pay| \n\n" + loanStatementStart;

                FileChooser fileChooser = new FileChooser();

                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extensionFilter);

                //Show dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    saveTextToFile(loanStatement[0], file);
                }
            }
        });

        //Handling Show Table button press
        showTable.setOnAction(e ->{
            if(userData.validateInput(loanAmountField, loanTermYearsField, loanTermMonthsField, interestRateField)) {
                userData.setValues(loanAmountField, loanTermYearsField, loanTermMonthsField, loanTypeBox.getValue(), interestRateField);
                userData.calculateLoan();
                if(userData.getLoanType().equals("Annuity")) {
                    new LoanTable();
                    loanTable.display("Annuity Table", "Annuity Loan Table", userData, new BorderPane());
                }
                else {
                    new LoanTable();
                    loanTable.display("Linear Table", "Linear Loan Table", userData, new BorderPane());
                }
            }
        });

        //Set up main Border Pane
        mainPane.setCenter(centerGrid);
        mainPane.setBottom(bottomLayout);

        Scene primaryScene = new Scene(mainPane, 600, 800);

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    /**
     * Method to write text to a specified file
     * @param content String object
     * @param file file where to write
     */
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to initialize basic UI
     * @param mainPane main border pane object
     */
    private void initUI(BorderPane mainPane) {
        //Top message
        Label topMessage = new Label("Personal Loan Calculator");
        topMessage.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 30));

        //Top HBox
        HBox topLayout = new HBox(10);
        topLayout.getChildren().add(topMessage);
        topLayout.setStyle("-fx-background-color: #d2d2a6");
        topLayout.setAlignment(Pos.CENTER);
        topLayout.setMinHeight(100);

        //Defining text for instructions
        Text loanAmountText = new Text("Loan amount");
        loanAmountText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Text loanTermText = new Text("Loan term");
        loanTermText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Text interestRateText = new Text("Interest rate");
        interestRateText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Text loanTypeText = new Text("Loan type");
        loanTypeText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        //Left text VBox
        VBox textOptions = new VBox(60);
        textOptions.getChildren().addAll(loanAmountText, loanTermText, interestRateText, loanTypeText);
        textOptions.setStyle("-fx-background-color: BEIGE;");
        textOptions.setPadding(new Insets(60,30,30,50));
        textOptions.setAlignment(Pos.BASELINE_RIGHT);

        mainPane.setStyle("-fx-background-color: BEIGE");
        mainPane.setTop(topLayout);
        mainPane.setLeft(textOptions);

    }

    /**
     * Method to properly close program
     * @param primaryStage primary stage object
     */
    private void closeProgram(Stage primaryStage) {
        primaryStage.close();
    }
}
>>>>>>> 00aeec047a54339b3dd9a90e146b2ad2412eb7da
