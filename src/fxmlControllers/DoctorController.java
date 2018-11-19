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
 * Controller for the Doctor Pane
 * @author TLohan
 */
public class DoctorController extends HospitalEmployeeController {
    
    private Doctor_callNextPatientController callNextPatientController = null;
    
    @FXML   
    private Button callNextPatientBtn;
    
    /**
     * Triggered when the user clicks the callNextPatientBtn Button.
     * Creates the Doctor_CallNextPatient Pane and displays it in the root's CentralGridPane
     */
    @FXML
    private void callNextPatient(){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/Doctor_CallNextPatient.fxml"));
            callNextPatientController = new Doctor_callNextPatientController();
            callNextPatientController.setDatabase(database);
            loader.setController(callNextPatientController);
            Parent fxml = loader.load();
            setCentralGridPane(fxml);
        } catch (IOException ex) {
            Logger.getLogger(TriageNurseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Triggered when the User clicks on the showWaitingListBtn.
     * Creates the WaitingList Pane and displays it in the root's centralGridPane
     * If the User has begun editing a patient but not submitted the changes, that patient is returned to the queue.
     */
    @FXML
    @Override
    protected void showWaitingList(){
        // check if there is an unsubmitted patient form
        if (callNextPatientController != null){
            if (!callNextPatientController.submitted && callNextPatientController.patient != null){
                // if there is add that patient back to the waiting list
                callNextPatientController.database.addPatientToDoctorWaitingList(callNextPatientController.patient);
            }
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/WaitingList.fxml"));
        try {
            WaitingListController controller = new WaitingListController();
            controller.setDatabase(database);
            loader.setController(controller);
            Parent FXML = loader.load();
            controller.setCurrentEmployee(currentEmployee);
            // doctor should see the doctor waitlist by default
            controller.tabPane.getSelectionModel().select(controller.DOC_tabBtn);
            setCentralGridPane(FXML);
        } catch (IOException ex) {
            Logger.getLogger(HospitalEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
