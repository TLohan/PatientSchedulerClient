/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import interfaces.IDatabase;
import entityClasses.HospitalEmployee;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Controller for the FXMLLogIn Pane
 * @author TLohan
 */
public class FXMLLogInController {
    
    // The number of invalid login attempts a user can make before
    // being locked out
    private final int MAX_LOGIN_ATTEMPTS = 3;
    
    public IDatabase database;
    private int loginAttemptsCounter = 0;
    private int remainingLoginAttempts;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label validationLabel; 
                  
    @FXML
    private TilePane errorPane;
    
    @FXML
    private TextField loginHospital_id;
    
    @FXML
    private TextField loginPassword;
    
   @FXML 
   private  Button loginBtn;
   
    private String user_id;
    private String password;
    
    /**
     * Displays the ErrorPane.
     */
    public void displayErrorPane(){
        errorPane.setVisible(true);
        loginBtn.setDisable(true);
        loginHospital_id.setDisable(true);
        loginPassword.setDisable(true);
        loginBtn.setCursor(Cursor.NONE);        
    }
    
    /**
     * Triggered if the user clicks the loginBtn Button
     * Validates the loginHospital_id and loginPassword TextFields
     * If fields are invalid an error message is displayed in the validationLabel Label
     * If the credentials are valid the User is logged in their Department's Pane
     * @param event 
     */
    @FXML
    private void onLogInClick(ActionEvent event){
        
        // Get hospital_id and password:
        user_id = loginHospital_id.getText();
        password = loginPassword.getText();
        
        // SKIP LOGIN:
//        HospitalEmployee tmp = database.getHospitalEmployee("d00140105");
//        processValidLogin(tmp);

        if (loginAttemptsCounter <= (MAX_LOGIN_ATTEMPTS-1)){
            if (testCredentials(user_id, password)){
                HospitalEmployee employee = getHospitalEmployee(user_id);
                processValidLogin(employee);
            }
        } else {
            shutOut();
        }
    }
    
    /**
     * Gets a HospitalEmployee from the server
     * @param user_id The user_id of the employee
     * @return HospitalEmployee. null if the employee does not exist
     */
    private HospitalEmployee getHospitalEmployee(String user_id){
        HospitalEmployee employee = null;
        employee = database.getHospitalEmployeeByUsername(user_id);
        return employee;
    }
    
    /**
     * Tests the user's input. 
     * Shows a validation error if the either/or field is blank or the 
     * user_id/password combination is invalid
     * @param user_id - The user_id of the employee
     * @param password - The password of the employee
     * @return Boolean true if the login credentials are valid
     */
    private boolean testCredentials(String user_id, String password){
        if ("".equals(user_id) && "".equals(password)) {
            validationLabel.setText("Please enter a HospitalID and Password.");
        } else if ("".equals(user_id)) {
            validationLabel.setText("Please enter a HospitalID");
        } else if ("".equals(password)){
            validationLabel.setText("Please enter a valid password.");
        } else {
            loginAttemptsCounter++;
            HospitalEmployee employee = getHospitalEmployee(user_id);
            if (employee.getPassword() != null && employee.getPassword().equals(password)){
                return true;
            }
            processInvalidLogin();
        }
        validationLabel.setVisible(true);
        return false;
    }
    
    /**
     * If the user's credentials are valid this method will display that user's Department's Pane
     * @param employee - The employee which has been logged in
     */
    public void processValidLogin(HospitalEmployee employee){
        validationLabel.setVisible(false);
        HospitalEmployeeController controller;
        Stage parentStage = (Stage) loginBtn.getScene().getWindow();
        try{
            Parent FXML;
            FXMLLoader loader;
            switch (employee.getDepartment()){
                case RECEPTION:
                    loader = new FXMLLoader(getClass().getResource("/resources/fxml/Receptionist.fxml"));
                    controller = new ReceptionistController();
                    controller.setDatabase(database);
                    loader.setController(controller);
                    FXML = loader.load();
                    controller.setCurrentEmployee(employee);
                    break;
                case TRIAGE_NURSE:
                    loader = new FXMLLoader(getClass().getResource("/resources/fxml/TriageNurse.fxml"));
                    controller = new TriageNurseController();
                    controller.setDatabase(database);
                    loader.setController(controller);
                    FXML = loader.load();
                    controller.setCurrentEmployee(employee);
                    break;
                case DOCTOR:
                    loader = new FXMLLoader(getClass().getResource("/resources/fxml/Doctor.fxml"));
                    controller = new DoctorController();
                    controller.setDatabase(database);
                    loader.setController(controller);
                    FXML = loader.load();
                    controller.setCurrentEmployee(employee);
                    break;
                default:
                    FXML = null;
            }
            Scene receptionistScene = new Scene(FXML);
            parentStage.setScene(receptionistScene);
            parentStage.show();
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
        
    }
    
    /**
     * Displays the error message and number of remaining attempts
     */
    private void processInvalidLogin(){
        remainingLoginAttempts = MAX_LOGIN_ATTEMPTS - loginAttemptsCounter;
        validationLabel.setText("Try again! Remaining attempts: " + remainingLoginAttempts);
        validationLabel.setVisible(true);
    }
    
    /**
     * Displays an error message telling the user they have been locked out
     */
    private void shutOut(){
        validationLabel.setText("You have exceeded the number of allowed login attempts.\nPlease contact the system administrator.");
        validationLabel.setVisible(true);
        loginBtn.setDisable(true);
        loginHospital_id.setDisable(true);
        loginPassword.setDisable(true);
        loginBtn.setCursor(Cursor.NONE);
    }
    

    
} 
