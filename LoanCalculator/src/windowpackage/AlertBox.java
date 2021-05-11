<<<<<<< HEAD
package windowpackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used to display a separate alert window
 * @author Armantas Pikšrys PS3
 */
public class AlertBox {

    /**
     * Method to display alert box window
     * @param title title of the warning message
     * @param message message of the alert box to the user
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);
        window.setMinHeight(200);

        Label label = new Label(message);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #dba1a1");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
=======
package windowpackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used to display a separate alert window
 * @author Armantas Pikšrys PS3
 */
public class AlertBox {

    /**
     * Method to display alert box window
     * @param title title of the warning message
     * @param message message of the alert box to the user
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);
        window.setMinHeight(200);

        Label label = new Label(message);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));

        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #dba1a1");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
>>>>>>> 00aeec047a54339b3dd9a90e146b2ad2412eb7da
