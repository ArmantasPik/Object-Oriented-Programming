package personPackage;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import windowPackage.AlertBox;

import java.util.ArrayList;
import java.util.List;

public class PersonNode extends PersonNodeAbstract{

    private double xAxis;
    private double yAxis;
    private VBox nodeBox;
    private HBox buttonBox;
    private TextField firstNameField;
    private TextField lastNameField;
    private Label idField;
    private TextField birthYearField;
    private TextField birthCityField;
    private Button editData;
    private Button deleteData;
    private Person person;
    private Line fatherConnectionLine;
    private Line motherConnectionLine;
    private Line spouseConnectionLine;
    private List<Person> listOfPeople;
    private List<PersonNode> listOfNodes;
    private List<String> listOfFatherID;
    private List<String> listOfMotherID;
    private List<String> listOfSpouseID;
    private ArrayList<ArrayList<Integer>> hierarchy2D;
    private ComboBox<String> fatherBox;
    private ComboBox<String> motherBox;
    private ComboBox<String> spouseBox;
    private int deletion;

    public PersonNode(double xAxis, double yAxis, Person person, List<Person> listOfPeople, List<String> listOfFatherID, List<String> listOfMotherID, List<String> listOfSpouseID,
                      List<PersonNode> listOfNodes, ArrayList<ArrayList<Integer>> hierarchy2D, ComboBox<String> fatherBox, ComboBox<String> motherBox, ComboBox<String> spouseBox) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.nodeBox = new VBox(10);
        this.buttonBox = new HBox(10);
        this.firstNameField = new TextField("First Name");
        this.lastNameField = new TextField("Last Name");
        this.idField = new Label();
        this.birthYearField = new TextField("Birth Year");
        this.birthCityField = new TextField("Birth City");
        this.editData = new Button("Edit");
        this.deleteData = new Button("Delete");
        this.person = person;
        this.fatherConnectionLine = new Line();
        this.motherConnectionLine = new Line();
        this.spouseConnectionLine = new Line();
        this.deletion = 0;
        this.listOfPeople = listOfPeople;
        this.listOfNodes = listOfNodes;
        this.hierarchy2D = hierarchy2D;
        this.listOfFatherID = listOfFatherID;
        this.listOfMotherID = listOfMotherID;
        this.listOfSpouseID = listOfSpouseID;
        this.fatherBox = fatherBox;
        this.motherBox = motherBox;
        this.spouseBox = spouseBox;
    }

    @Override
    public void displayNode(AnchorPane anchorPane) {
        nodeBox.setPrefSize(250, 200);
        nodeBox.setStyle("-fx-background-color: #0073ff");
        nodeBox.setPadding(new Insets(20, 20, 20, 20));
        nodeBox.setLayoutX(xAxis);
        nodeBox.setLayoutY(yAxis);

        buttonBox.getChildren().addAll(editData, deleteData);

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        idField.setText("Person's ID: " + person.getID());
        idField.setStyle("-fx-background-color: white");
        birthYearField.setText(person.getBirthYear());
        birthCityField.setText(person.getBirthCity());

        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        birthYearField.setPromptText("Birth Year");
        birthCityField.setPromptText("Birth City");

        nodeBox.getChildren().addAll(firstNameField, lastNameField, idField, birthYearField, birthCityField, buttonBox);
        anchorPane.getChildren().addAll(nodeBox, fatherConnectionLine, motherConnectionLine, spouseConnectionLine);

        deleteData.setOnAction(e -> {
            anchorPane.getChildren().removeAll(this.nodeBox, this.fatherConnectionLine, this.motherConnectionLine, this.spouseConnectionLine);
            person.setDeletion(1);

            int hierarchyNumber = 0;

            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < listOfPeople.size(); i++) {
                    Person testPerson = listOfPeople.get(i);

                    if (testPerson.getDeletion() == 1) {
                        hierarchyNumber = Integer.parseInt(listOfPeople.get(i).getRelationship());
                        hierarchy2D.get(hierarchyNumber).remove(0);

                        listOfNodes.remove(i);
                        listOfPeople.remove(i);
                        listOfFatherID.remove(i);
                        listOfMotherID.remove(i);
                        listOfSpouseID.remove(i);
                        fatherBox.getItems().remove(i);
                        motherBox.getItems().remove(i);
                        spouseBox.getItems().remove(i);
                    }
                }
            }

            hierarchy2D.get(hierarchyNumber).trimToSize();
        });

        editData.setOnAction(e -> {
            if(validateData(firstNameField, lastNameField, birthYearField, birthCityField, birthYearField)) {
                person.setFirstName(firstNameField.getText());
                person.setLastName(lastNameField.getText());
                person.setBirthCity(birthCityField.getText());
                person.setBirthYear(birthYearField.getText());

                AlertBox.display("Edited data", "Successfully edited person data!");
            }
        });
    }

    @Override
    public void deleteNode(AnchorPane anchorPane) {
        anchorPane.getChildren().removeAll(this.nodeBox, this.fatherConnectionLine, this.motherConnectionLine, this.spouseConnectionLine);
    }

    public int getDeletion() {
        return deletion;
    }

    public void setDeletion(int deletion) {
        this.deletion = deletion;
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
}
