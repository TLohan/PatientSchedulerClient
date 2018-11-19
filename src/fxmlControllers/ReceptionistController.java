/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 * Controller for the Receptionist Pane
 * @author TLohan
 */
public class ReceptionistController extends HospitalEmployeeController{
   
    @FXML   
    private Button newPatientBtn;
    
    /**
     * Triggered when the user clicks the newPatientButton.
     * Displays the CreateNewPatient Pane in the CentralGridPane.
     */
    @FXML
    private void createNewPatient(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/CreateNewPatient.fxml"));
        try {
            CreateNewPatientController controller = new CreateNewPatientController();
            controller.setDatabase(database);
            loader.setController(controller);
            Parent FXML = loader.load();
            setCentralGridPane(FXML);
        } catch (IOException ex) {
            Logger.getLogger(ReceptionistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
