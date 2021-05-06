package personPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.List;

public class PersonNodeFilter extends PersonNodeAbstract{

    private double xAxis;
    private double yAxis;
    private VBox nodeBox;
    private Label firstNameField;
    private Label lastNameField;
    private Label idField;
    private Label birthYearField;
    private Label birthCityField;
    private Person person;
    private Line fatherConnectionLine;
    private Line motherConnectionLine;
    private Line spouseConnectionLine;
    private List<Person> listOfPeople;

    public PersonNodeFilter(double xAxis, double yAxis, Person person) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.person = person;
        this.listOfPeople = listOfPeople;
        this.nodeBox = new VBox(10);
        this.firstNameField = new Label();
        this.lastNameField = new Label();
        this.idField = new Label();
        this.birthYearField = new Label();
        this.birthCityField = new Label();
        this.fatherConnectionLine = new Line();
        this.motherConnectionLine = new Line();
        this.spouseConnectionLine = new Line();

    }

    @Override
    public void displayNode(AnchorPane anchorPane) {
        nodeBox.setPrefSize(250, 200);
        nodeBox.setStyle("-fx-background-color: #5c9fc4");
        nodeBox.setPadding(new Insets(20, 20, 20, 20));
        nodeBox.setLayoutX(xAxis);
        nodeBox.setLayoutY(yAxis);

        firstNameField.setText("First Name: " + person.getFirstName());
        lastNameField.setText("Last Name: " + person.getLastName());
        idField.setText(" ID: " + person.getID());
        birthYearField.setText("Birth Year: " + person.getBirthYear());
        birthCityField.setText("Birth City: " + person.getBirthCity());

        firstNameField.setStyle("-fx-background-color: #cfcfcf");
        lastNameField.setStyle("-fx-background-color: #cfcfcf");
        idField.setStyle("-fx-background-color: #cfcfcf");
        birthYearField.setStyle("-fx-background-color: #cfcfcf");
        birthCityField.setStyle("-fx-background-color: #cfcfcf");

        nodeBox.getChildren().addAll(firstNameField, lastNameField, idField, birthYearField, birthCityField);
        anchorPane.getChildren().addAll(nodeBox, fatherConnectionLine, motherConnectionLine, spouseConnectionLine);
    }

    @Override
    public void deleteNode(AnchorPane anchorPane) {
        anchorPane.getChildren().removeAll(this.nodeBox, this.fatherConnectionLine, this.motherConnectionLine, this.spouseConnectionLine);
    }

    public VBox getNodeBox() {
        return nodeBox;
    }

    public void setNodeBox(VBox nodeBox) {
        this.nodeBox = nodeBox;
    }

    public Line getFatherConnectionLine() {
        return fatherConnectionLine;
    }

    public void setFatherConnectionLine(Line fatherConnectionLine) {
        this.fatherConnectionLine = fatherConnectionLine;
    }

    public Line getMotherConnectionLine() {
        return motherConnectionLine;
    }

    public void setMotherConnectionLine(Line motherConnectionLine) {
        this.motherConnectionLine = motherConnectionLine;
    }

    public Line getSpouseConnectionLine() {
        return spouseConnectionLine;
    }

    public void setSpouseConnectionLine(Line spouseConnectionLine) {
        this.spouseConnectionLine = spouseConnectionLine;
    }

    public double getxAxis() {
        return xAxis;
    }

    public void setxAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }
}
