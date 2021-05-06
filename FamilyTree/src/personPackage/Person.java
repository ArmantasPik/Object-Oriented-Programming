package personPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty ID;
    private StringProperty birthYear;
    private StringProperty birthCity;
    private String relationship;
    private String father;
    private String mother;
    private String spouse;
    private int deletion;


    public Person(String firstName, String lastName, String ID, String birthYear, String birthCity, String relationship, String father, String mother, String spouse) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.ID = new SimpleStringProperty(ID);
        this.birthYear = new SimpleStringProperty(birthYear);
        this.birthCity = new SimpleStringProperty(birthCity);
        this.relationship = relationship;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        this.deletion = 0;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getBirthYear() {
        return birthYear.get();
    }

    public StringProperty birthYearProperty() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear.set(birthYear);
    }

    public String getBirthCity() {
        return birthCity.get();
    }

    public StringProperty birthCityProperty() {
        return birthCity;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getDeletion() {
        return deletion;
    }

    public void setDeletion(int deletion) {
        this.deletion = deletion;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity.set(birthCity);
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
