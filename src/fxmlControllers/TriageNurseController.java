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
 * Controller for the TraiageNurse Pane
 * @author TLohan
 */
public class TriageNurseController extends HospitalEmployeeController {
    
    private TN_callNextPatientController nextPatientController = null;
    
    @FXML   
    private Button callNextPatientBtn;
    
    
    /**
     * Triggered when the user clicks the callNextPatientBtn Button.
     * Displays the TN_CallNextPatient Pane in the centralGridPane Pane.
     */
    @FXML
    private void callNextPatient(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/TN_CallNextPatient.fxml"));
            nextPatientController = new TN_callNextPatientController();
            nextPatientController.setDatabase(database);
            loader.setController(nextPatientController);
            Parent fxml = loader.load();
            setCentralGridPane(fxml);
        } catch (IOException ex) {
            Logger.getLogger(TriageNurseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Triggered when the user clicks on the showWaitingListBtn Button.
     * Checks if the user is in the process of dealing with another patient and if so
     * adds that patient back into the queue.
     */
    @Override
    protected void showWaitingList(){
        // check if there is an unsubmitted patient form.
        if (nextPatientController != null){
            if (!nextPatientController.submitted && nextPatientController.patient != null){
                // if there is, add that patient back to the waiting list.
                nextPatientController.database.addPatientToTriageNurseWaitingList(nextPatientController.patient);
            }
        }
        super.showWaitingList();
    }
}