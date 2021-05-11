package mainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FamilyTree extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
        primaryStage.setTitle("Family Tree Editor");

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(primaryStage);
        });

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void closeProgram(Stage primaryStage) {
        primaryStage.close();
        System.out.println("closing...");
    }

}
