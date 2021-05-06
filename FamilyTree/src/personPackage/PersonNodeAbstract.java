package personPackage;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import windowPackage.AlertBox;

public abstract class PersonNodeAbstract {

    public abstract void displayNode(AnchorPane anchorPane);

    public abstract void deleteNode(AnchorPane anchorPane);

    public static boolean validateData(TextField firstNameField, TextField lastNameField, TextField idField, TextField birthCityField, TextField birthYearField) {
        if (isInt(idField, idField.getText()) && isInt(birthYearField, birthYearField.getText())) {

            if (firstNameField.getText().trim().isEmpty()) {
                AlertBox.display("Warning", "First name field can't be empty");
                return false;
            } else if (lastNameField.getText().trim().isEmpty()) {
                AlertBox.display("Warning", "Last name field can't be empty");
                return false;
            } else if (birthCityField.getText().trim().isEmpty()) {
                AlertBox.display("Warning", "Birth city field can't be empty");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean isInt(TextField input, String message) {
        try {
            int intValue = Integer.parseInt(input.getText());
            return true;
        } catch (NumberFormatException e) {
            AlertBox.display("Warning", message + " is not of type integer");
            return false;
        }
    }
}
