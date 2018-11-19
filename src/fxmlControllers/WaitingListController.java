/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import customDataStructures.DoubleLinkedList;
import interfaces.IDatabase;
import entityClasses.HospitalEmployee;
import entityClasses.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for the WaitingList Pane
 * @author TLohan
 */
public class WaitingListController {

    private HospitalEmployee currentEmployee;    
    private IDatabase database;
    
    @FXML
    public Tab DOC_tabBtn;
    
    @FXML
    public TabPane tabPane;
    
    @FXML
    private TableView<Patient> triageNurseTableView;
    
    @FXML
    private TableView<Patient> doctorTableView;
    
    @FXML 
    private TableColumn<Patient, String> TN_forenameColumn;
    
    @FXML 
    private TableColumn<Patient, String> TN_surnameColumn;
    
    @FXML
    private TableColumn<Patient, String> TN_conditionColumn;
    
    @FXML
    private TableColumn<Patient, String> DOC_priorityColumn;
    
    @FXML
    private TableColumn<Patient, String> DOC_forenameColumn;
     
    @FXML
    private TableColumn<Patient, String> DOC_surnameColumn;
      
    @FXML
    private TableColumn<Patient, String> DOC_conditionColumn;
    
     /**
     * Sets the database attribute
     * @param database - The database to be used.
     */
    public void setDatabase(IDatabase database){
        this.database = database;
    }
    
    public void setCurrentEmployee(HospitalEmployee employee){
        this.currentEmployee = employee;
    }
    
    /**
     * Populates the TriageNurseWaitingList table.
     */
    private void populateTriageNurseWaitingListTable(){
        TN_forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
        TN_forenameColumn.setMinWidth(150);
        TN_surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        TN_surnameColumn.setMinWidth(150);
        TN_conditionColumn.setCellValueFactory(new PropertyValueFactory<>("summaryOnAdmittance"));
        triageNurseTableView.setItems(getTriageNurseWaitingList());

    }
    
    /**
     * Populates the DoctorWaitingListTable.
     */
    private void populateDoctorWaitingListTable(){
        DOC_priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        DOC_priorityColumn.setMinWidth(80);
        DOC_forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
        DOC_forenameColumn.setMinWidth(150);
        DOC_surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        DOC_surnameColumn.setMinWidth(150);
        DOC_conditionColumn.setCellValueFactory(new PropertyValueFactory<>("summaryOnAdmittance"));
        doctorTableView.setItems(getDoctorWaitingList());   
    }
    
    /**
     * Triggered when the user clicks on the TN_tabBtn.
     * Displays the TriageNurse's Waiting List
     */
    @FXML
    public void onSelectTNTab(){
        populateTriageNurseWaitingListTable();
    }
    /**
     * Triggered when the user clicks on the DOC_tabBtn.
     * Displays the Doctor's Waiting List
     */    
    @FXML 
    public void onSelectDocTab(){
        populateDoctorWaitingListTable();
    }
    
    /**
     * Retrieves the TriageNurses's WaitingList from the server
     * @return ObservableList of Patients.
     */
    private ObservableList<Patient> getTriageNurseWaitingList(){
        DoubleLinkedList<Patient> wl = database.getTriageNurseWaitingList();
        ObservableList<Patient> patientsFromDB = (FXCollections.observableArrayList(wl.getArrList()));
        return patientsFromDB;
    }
    /**
     * Retrieves the Doctor's WaitingList from the server
     * @return ObservableList of Patients.
     */    
    public ObservableList<Patient> getDoctorWaitingList(){
        ObservableList<Patient> patientsFromDB = (FXCollections.observableArrayList(database.getDoctorWaitingList().getArrList()));
        return patientsFromDB;
    }

}
