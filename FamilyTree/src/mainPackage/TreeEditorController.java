package mainPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personPackage.Person;
import personPackage.PersonNode;
import personPackage.PersonNodeAbstract;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreeEditorController {

    private List<Person> listOfPeople;
    private List<String> listOfFatherID;
    private List<String> listOfMotherID;
    private List<String> listOfSpouseID;
    private List<PersonNode> listOfNodes;
    private ObservableList<String> relationshipList = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private ArrayList<ArrayList<Integer>> hierarchy2D;
    private File dataFile;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField birthYearField;

    @FXML
    private TextField birthCityField;

    @FXML
    private Button insertButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> relationshipBox;

    @FXML
    private ComboBox<String> fatherBox;

    @FXML
    private ComboBox<String> motherBox;

    @FXML
    private ComboBox<String> spouseBox;

    @FXML
    private Button filterButton;

    @FXML
    private VBox mainStage;

    public TreeEditorController() {
        this.listOfPeople = new ArrayList<>();
        this.listOfNodes = new ArrayList<>();
        this.listOfFatherID = new ArrayList<>();
        this.listOfMotherID = new ArrayList<>();
        this.listOfSpouseID = new ArrayList<>();
        this.hierarchy2D = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            hierarchy2D.add(new ArrayList<>());
    }

    @FXML
    private void insertPerson(ActionEvent event) {

        if (PersonNode.validateData(firstNameField, lastNameField, idField, birthCityField, birthYearField)) {
            //Pass data to person object
            Person person = new Person(firstNameField.getText(), lastNameField.getText(), idField.getText(), birthYearField.getText(), birthCityField.getText(), relationshipBox.getValue(),
                    fatherBox.getValue(), motherBox.getValue(), spouseBox.getValue());

            //Get hierarchy 2D number
            int hierarchyNumber = Integer.parseInt(relationshipBox.getValue());
            hierarchy2D.get(hierarchyNumber).add(hierarchy2D.get(hierarchyNumber).size());

            //Create personNode object
            PersonNode personNode = new PersonNode(50 + 400 * hierarchy2D.get(hierarchyNumber).get(hierarchy2D.get(hierarchyNumber).size() - 1), 100 + 400 * hierarchyNumber, person,
                    listOfPeople, listOfFatherID, listOfMotherID, listOfSpouseID, listOfNodes, hierarchy2D, fatherBox, motherBox, spouseBox);

            //Add new person to array lists
            listOfPeople.add(person);
            listOfNodes.add(personNode);
            listOfFatherID.add(idField.getText());
            listOfMotherID.add(idField.getText());
            listOfSpouseID.add(idField.getText());

            //Create connection Line based on relative
            Line fatherConnectionLine = new Line();
            Line motherConnectionLine = new Line();
            Line spouseConnectionLine = new Line();
            fatherConnectionLine.setStartX(personNode.getxAxis() + 125);
            fatherConnectionLine.setStartY(personNode.getyAxis());
            motherConnectionLine.setStartX(personNode.getxAxis() + 125);
            motherConnectionLine.setStartY(personNode.getyAxis());
            spouseConnectionLine.setStartX(personNode.getxAxis() + 125);
            spouseConnectionLine.setStartY(personNode.getyAxis());
            fatherConnectionLine.setStroke(Color.BLUE);
            motherConnectionLine.setStroke(Color.HOTPINK);
            spouseConnectionLine.setStroke(Color.RED);

            for (int i = 0; i < listOfPeople.size(); i++) {
                if (listOfPeople.get(i).getID().equals(person.getFather()) && hierarchyNumber != 0) {
                    fatherConnectionLine.setEndX(listOfNodes.get(i).getxAxis() + listOfNodes.get(i).getNodeBox().getWidth() / 2);
                    fatherConnectionLine.setEndY(listOfNodes.get(i).getyAxis() + listOfNodes.get(i).getNodeBox().getHeight());
                    personNode.setFatherConnectionLine(fatherConnectionLine);
                }

                if (listOfPeople.get(i).getID().equals(person.getMother()) && hierarchyNumber != 0) {
                    motherConnectionLine.setEndX(listOfNodes.get(i).getxAxis() + listOfNodes.get(i).getNodeBox().getWidth() / 2);
                    motherConnectionLine.setEndY(listOfNodes.get(i).getyAxis() + listOfNodes.get(i).getNodeBox().getHeight());
                    personNode.setMotherConnectionLine(motherConnectionLine);
                }

                if (listOfPeople.get(i).getID().equals(person.getSpouse()) && hierarchyNumber != 0) {
                    spouseConnectionLine.setEndX(listOfNodes.get(i).getxAxis() + listOfNodes.get(i).getNodeBox().getWidth() / 2);
                    spouseConnectionLine.setEndY(listOfNodes.get(i).getyAxis() + listOfNodes.get(i).getNodeBox().getHeight());
                    personNode.setSpouseConnectionLine(spouseConnectionLine);
                }
            }

            //Display person node object
            personNode.displayNode(anchorPane);

            //Update ID choice box
            updateChoiceBox();
        }
    }

    @FXML
    private void initialize() {
        relationshipBox.getItems().addAll(relationshipList);
        relationshipBox.setValue("0");
        relationshipBox.setVisibleRowCount(10);
        fatherBox.setVisibleRowCount(10);
        motherBox.setVisibleRowCount(10);
        spouseBox.setVisibleRowCount(10);
    }

    @FXML
    private void saveFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");

        fileChooser.setInitialDirectory(new File("C:\\Users\\arman\\Desktop\\vu\\Java\\FamilyTree\\data"));
        fileChooser.getExtensionFilters().add(extensionFilter);

        //Show dialog
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                PrintWriter writer = new PrintWriter(file);
                Person person;
                String fatherConnection, motherConnection, spouseConnection;

                for (int i = 0; i < listOfPeople.size(); i++) {
                    person = listOfPeople.get(i);

                    if (person.getFather() == null) {
                        fatherConnection = "-1";
                    } else {
                        fatherConnection = person.getFather();
                    }

                    if (person.getMother() == null) {
                        motherConnection = "-1";
                    } else {
                        motherConnection = person.getMother();
                    }

                    if (person.getSpouse() == null) {
                        spouseConnection = "-1";
                    } else {
                        spouseConnection = person.getSpouse();
                    }

                    writer.println(person.getFirstName() + "," + person.getLastName() + "," + person.getID() + "," +
                            person.getBirthYear() + "," + person.getBirthCity() + "," + person.getRelationship() + "," + fatherConnection + "," + motherConnection + "," + spouseConnection);
                }

                writer.close();
            } catch (IOException exc) {
                Logger.getLogger(TreeEditorController.class.getName()).log(Level.SEVERE, null, exc);
            }
        }
    }

    public void updateChoiceBox() {
        ObservableList<String> idListF = FXCollections.observableList(listOfFatherID);
        ObservableList<String> idListM = FXCollections.observableList(listOfMotherID);
        ObservableList<String> idListS = FXCollections.observableList(listOfSpouseID);
        fatherBox.getItems().clear();
        motherBox.getItems().clear();
        spouseBox.getItems().clear();
        fatherBox.getItems().addAll(idListF);
        motherBox.getItems().addAll(idListM);
        spouseBox.getItems().addAll(idListS);
    }

    public void initializeTree() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\arman\\Desktop\\vu\\Java\\FamilyTree\\data"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        dataFile = fc.showOpenDialog(null);

        if (dataFile != null) {
            //Read from file
            String line = "";
            String splitBy = ",";
            String fileName = dataFile.getAbsolutePath();

            try {
                BufferedReader br = new BufferedReader(new FileReader(fileName));

                while ((line = br.readLine()) != null) {
                    String[] readPerson = line.split(splitBy); // use comma as separator
                    Person person = new Person(readPerson[0], readPerson[1], readPerson[2], readPerson[3], readPerson[4], readPerson[5], readPerson[6], readPerson[7], readPerson[8]);

                    //Get hierarchy 2D number
                    int hierarchyNumber = Integer.parseInt(readPerson[5]);
                    hierarchy2D.get(hierarchyNumber).add(hierarchy2D.get(hierarchyNumber).size());

                    //Create personNode object
                    PersonNode personNode = new PersonNode(50 + 400 * hierarchy2D.get(hierarchyNumber).get(hierarchy2D.get(hierarchyNumber).size() - 1), 100 + 400 * hierarchyNumber, person,
                            listOfPeople, listOfFatherID, listOfMotherID, listOfSpouseID, listOfNodes, hierarchy2D, fatherBox, motherBox, spouseBox);

                    //Add new person to array lists
                    listOfPeople.add(person);
                    listOfNodes.add(personNode);
                    listOfFatherID.add(readPerson[2]);
                    listOfMotherID.add(readPerson[2]);
                    listOfSpouseID.add(readPerson[2]);

                    //Create connection Line based on relative
                    Line fatherConnectionLine = new Line();
                    Line motherConnectionLine = new Line();
                    Line spouseConnectionLine = new Line();
                    fatherConnectionLine.setStartX(personNode.getxAxis() + 125);
                    fatherConnectionLine.setStartY(personNode.getyAxis());
                    motherConnectionLine.setStartX(personNode.getxAxis() + 125);
                    motherConnectionLine.setStartY(personNode.getyAxis());
                    spouseConnectionLine.setStartX(personNode.getxAxis() + 125);
                    spouseConnectionLine.setStartY(personNode.getyAxis());
                    fatherConnectionLine.setStroke(Color.BLUE);
                    motherConnectionLine.setStroke(Color.HOTPINK);
                    spouseConnectionLine.setStroke(Color.RED);

                    for (int i = 0; i < listOfPeople.size(); i++) {
                        if (listOfPeople.get(i).getID().equals(person.getFather()) && hierarchyNumber != 0) {
                            fatherConnectionLine.setEndX(listOfNodes.get(i).getxAxis() + 125);
                            fatherConnectionLine.setEndY(listOfNodes.get(i).getyAxis() + 232);
                            personNode.setFatherConnectionLine(fatherConnectionLine);
                        }

                        if (listOfPeople.get(i).getID().equals(person.getMother()) && hierarchyNumber != 0) {
                            motherConnectionLine.setEndX(listOfNodes.get(i).getxAxis() + 125);
                            motherConnectionLine.setEndY(listOfNodes.get(i).getyAxis() + 232);
                            personNode.setMotherConnectionLine(motherConnectionLine);
                        }

                        if (listOfPeople.get(i).getID().equals(person.getSpouse())) {
                            spouseConnectionLine.setEndX(listOfNodes.get(i).getxAxis() + 125);
                            spouseConnectionLine.setEndY(listOfNodes.get(i).getyAxis() + 232);
                            personNode.setSpouseConnectionLine(spouseConnectionLine);
                        }
                    }

                    //Display person node object
                    personNode.displayNode(anchorPane);

                    //Update ID choice box
                    updateChoiceBox();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("file is not valid");
        }

    }

    @FXML
    void filterTree(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FilterEditor.fxml"));
        Parent rooter = loader.load();

        FilterEditorController editorController = loader.getController();

        editorController.initializeWindow(listOfPeople, listOfNodes);

        Stage stage = new Stage();
        stage.setScene(new Scene(rooter));
        stage.setTitle("Family Tree Filter Editor");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
