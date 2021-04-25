package windowpackage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainpackage.UserData;

/**
 * This abstract class is used to display a separate window
 * @author Armantas Pikšrys PS3
 */
public abstract class Window {

    /**
     * This method displays a separate window with a specified window title, message, UserData object and BorderPane object
     * @param title title of the separate window
     * @param message message that the separate window can display
     * @param userData UserData object
     * @param root BorderPane object that contains nodes to display
     */
    protected void display(String title, String message, UserData userData, BorderPane root) {
        //Initializing window
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);
        window.setMinHeight(500);

        Label label = new Label(message);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        //Close Table Button
        Button closeButton = new Button("Go Back");
        closeButton.setOnAction(e -> window.close());

        //Defining HBox
        HBox bottomLayout = new HBox(10);
        bottomLayout.getChildren().addAll(label, closeButton);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomLayout.setPadding(new Insets(20, 20, 20, 20));
        bottomLayout.setStyle("-fx-background-color: #d2cbc5");

        root.setBottom(bottomLayout);

        //Display
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
        window.showAndWait();
    }
}
