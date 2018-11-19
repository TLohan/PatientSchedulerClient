/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import entityClasses.HospitalEmployee;
import interfaces.IDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controller for all controller classes which inherit from HospitalEmployeeController
 * @author TLohan
 */
public class HospitalEmployeeController implements Initializable {
    
    protected HospitalEmployee currentEmployee;
    protected IDatabase database;
    
    
    // FXML Nodes
    @FXML
    private Label employeeNameLabel;
    
    @FXML
    private Button logOutBtn;
    
    @FXML
    protected Button showWaitingListBtn;
    
    @FXML
    private GridPane centralGridPane;
    
    protected Control[] validatedFields;

    protected boolean formIsValid = true;
    
    /**
     * Sets the database attribute
     * @param database - The database to be used
     */
    public void setDatabase(IDatabase database){
        this.database = database;
    }
    
    /**
     * Sets the currently logged in HospitalEmployee
     * @param employee - The employee which is logged into this session
     */
    public void setCurrentEmployee(HospitalEmployee employee){
        this.currentEmployee = employee;
        setEmployeeNameLabel();
    }
    
    /**
     * Fixes a node into the CentralGridPane Pane of the GUI
     * @param node - The node to display 
     */
    protected void setCentralGridPane(Parent node){
        centralGridPane.getChildren().clear();
        centralGridPane.add(node, 0, 0);
    }
    
    /**
     * Displays the employee's name in the employeeNameLabel label
     */
    @FXML
    private void setEmployeeNameLabel(){
        employeeNameLabel.setText("Logged in as: " + currentEmployee.getFullName());
    }
    
    /**
     * Displays the WaitingList Pane in the CentralGridPane
     */
    @FXML
    protected void showWaitingList(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/WaitingList.fxml"));
        try {
            WaitingListController controller = new WaitingListController();
            controller.setDatabase(database);
            loader.setController(controller);
            Parent FXML = loader.load();
            controller.setCurrentEmployee(currentEmployee);
            setCentralGridPane(FXML);
        } catch (IOException ex) {
            Logger.getLogger(HospitalEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Triggered when the user clicks on the logOutBtn Button.
     * Displays the LogIn window.
     */
    @FXML
    public void logOut(){
        Stage parentStage = (Stage) logOutBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/LogIn.fxml"));
            FXMLLogInController controller = new FXMLLogInController();
            controller.database = database;
            loader.setController(controller);
            Parent logInFXML = loader.load();
            Scene loginScene = new Scene(logInFXML);
            parentStage.setScene(loginScene);
            parentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(HospitalEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    protected void showValidation(Control field, String message){
        formIsValid = false;
        field.setStyle("-fx-background-color:  #585e68;  -fx-text-inner-color: #d2edef; -fx-border-color: red");
    }
    
    protected void resetValidation(){
        formIsValid = true;
        for (Control validatedField : validatedFields) {
            validatedField.setStyle("-fx-background-color:  #585e68;  -fx-text-inner-color: #d2edef; fx-border-color: #42f450; ");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.showWaitingList();
    }
}
