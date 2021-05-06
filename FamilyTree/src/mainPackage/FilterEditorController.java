package mainPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;
import personPackage.Person;
import personPackage.PersonNode;
import personPackage.PersonNodeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilterEditorController {

    private List<Person> personList;
    private ObservableList<Person> personListObservable = FXCollections.observableArrayList();
    private FilteredList<Person> filterData;
    private ArrayList<ArrayList<Integer>> hierarchy2D;
    private List<PersonNode> listOfNodes;
    private List<PersonNodeFilter> currentListOfNodes;

    @FXML
    private TableView<Person> peopleTable;

    @FXML
    private TableColumn<Person, String> tableFirstName;

    @FXML
    private TableColumn<Person, String> tableLastName;

    @FXML
    private TableColumn<Person, String> tableID;

    @FXML
    private TableColumn<Person, String> tableBirthYear;

    @FXML
    private TableColumn<Person, String> tableBirthCity;

    @FXML
    private TextField filterField;

    @FXML
    private AnchorPane anchorPane;

    public FilterEditorController() {
        this.personList = new ArrayList<>();
        this.currentListOfNodes = new ArrayList<>();
        this.hierarchy2D = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            hierarchy2D.add(new ArrayList<>());
    }

    public void initializeWindow(List<Person> personList, List<PersonNode> listOfNodes) {

        this.personList = personList;
        this.listOfNodes = listOfNodes;

        //Associate data
        tableFirstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        tableLastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        tableID.setCellValueFactory(new PropertyValueFactory<Person, String>("ID"));
        tableBirthYear.setCellValueFactory(new PropertyValueFactory<Person, String>("birthYear"));
        tableBirthCity.setCellValueFactory(new PropertyValueFactory<Person, String>("birthCity"));

        //Add data
        peopleTable.setItems(FXCollections.observableList(personList));

        //Set up filter
        FilteredList<Person> filterPeople = new FilteredList<>(FXCollections.observableList(personList), p -> true);

        filterField.textProperty().addListener(((observableValue, s, t1) -> {
            filterPeople.setPredicate(person -> {
                if (s == null || t1.isEmpty()) {
                    return true;
                }

                String toLowerText = s.toLowerCase(Locale.ROOT);

                if (person.getFirstName().toLowerCase(Locale.ROOT).contains(toLowerText)) {
                    return true;
                } else if (person.getLastName().toLowerCase(Locale.ROOT).contains(toLowerText)) {
                    return true;
                }

                return false;
            });
        }));
        this.filterData = filterPeople;

        SortedList<Person> sortedList = new SortedList<>(filterPeople);

        sortedList.comparatorProperty().bind(peopleTable.comparatorProperty());
        peopleTable.setItems(sortedList);

    }

    @FXML
    void showTree(ActionEvent event) {
        //Delete previous tree
        for (int i = 0; i < currentListOfNodes.size(); i++) {
            currentListOfNodes.get(i).deleteNode(anchorPane);
        }

        for (int i = 0; i < 10; i++)
            hierarchy2D.get(i).clear();

        currentListOfNodes.clear();

        //Create new tree
        for (int i = 0; i < filterData.size(); i++) {
            //Get hierarchy 2D number
            int hierarchyNumber = Integer.parseInt(filterData.get(i).getRelationship());
            hierarchy2D.get(hierarchyNumber).add(hierarchy2D.get(hierarchyNumber).size());

            //Create personNode object
            PersonNodeFilter personNode = new PersonNodeFilter(50 + 400 * hierarchy2D.get(hierarchyNumber).get(hierarchy2D.get(hierarchyNumber).size() - 1), 100 + 400 * hierarchyNumber, filterData.get(i));

            currentListOfNodes.add(personNode);

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

            for (int j = 0; j < filterData.size(); j++) {
                if (filterData.get(j).getID().equals(filterData.get(i).getFather()) && hierarchyNumber != 0) {
                    fatherConnectionLine.setEndX(currentListOfNodes.get(j).getxAxis() + listOfNodes.get(j).getNodeBox().getWidth() / 2);
                    fatherConnectionLine.setEndY(currentListOfNodes.get(j).getyAxis() + 200);
                    personNode.setFatherConnectionLine(fatherConnectionLine);
                }

                if (filterData.get(j).getID().equals(filterData.get(i).getMother()) && hierarchyNumber != 0) {
                    motherConnectionLine.setEndX(currentListOfNodes.get(j).getxAxis() + listOfNodes.get(j).getNodeBox().getWidth() / 2);
                    motherConnectionLine.setEndY(currentListOfNodes.get(j).getyAxis() + 200);
                    personNode.setMotherConnectionLine(motherConnectionLine);
                }

                if (filterData.get(j).getID().equals(filterData.get(i).getSpouse())) {
                    spouseConnectionLine.setEndX(currentListOfNodes.get(j).getxAxis() + listOfNodes.get(j).getNodeBox().getWidth() / 2);
                    spouseConnectionLine.setEndY(currentListOfNodes.get(j).getyAxis() + 200);
                    personNode.setSpouseConnectionLine(spouseConnectionLine);
                }
            }

            //Display person node object
            personNode.displayNode(anchorPane);
        }
    }

    @FXML
    void saveToPDF(ActionEvent event) throws Exception {
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            Scale scale = new Scale(0.2, 0.2);

            AnchorPane tempAnchorPane = new AnchorPane();

            for(int i = 0; i < currentListOfNodes.size(); i++) {
                tempAnchorPane.getChildren().addAll(currentListOfNodes.get(i).getNodeBox(), currentListOfNodes.get(i).getFatherConnectionLine(),
                        currentListOfNodes.get(i).getMotherConnectionLine(), currentListOfNodes.get(i).getSpouseConnectionLine());
            }

            //Associate data
            TableView<Person> personTableTemp = new TableView<>();
            TableColumn<Person, String> firstNameTemp = new TableColumn<>("First Name");
            TableColumn<Person, String> lastNameTemp = new TableColumn<>("Last Name");
            TableColumn<Person, String> idTemp = new TableColumn<>("ID");
            TableColumn<Person, String> birthYearTemp = new TableColumn<>("Birth Year");
            TableColumn<Person, String> birthCityTemp = new TableColumn<>("Birth City");

            firstNameTemp.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
            lastNameTemp.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
            idTemp.setCellValueFactory(new PropertyValueFactory<Person, String>("ID"));
            birthYearTemp.setCellValueFactory(new PropertyValueFactory<Person, String>("birthYear"));
            birthCityTemp.setCellValueFactory(new PropertyValueFactory<Person, String>("birthCity"));

            personTableTemp.getColumns().addAll(firstNameTemp, lastNameTemp, idTemp, birthYearTemp, birthCityTemp);
            personTableTemp.setPrefHeight(1000);

            //Add data
            personTableTemp.setItems(FXCollections.observableList(filterData));
            personTableTemp.setLayoutX(1000);
            personTableTemp.setLayoutY(2500);

            tempAnchorPane.getChildren().add(personTableTemp);

            tempAnchorPane.getTransforms().add(scale);

            job.showPrintDialog(null);
            job.printPage(tempAnchorPane);
            job.endJob();
        }
    }

}
