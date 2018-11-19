/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;

import entityClasses.Patient;
import entityClasses.PatientStatus;
import entityClasses.VitalSigns;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controller for the TN_callNextPatient Pane
 * @author TLohan
 */
public class TN_callNextPatientController extends TriageNurseController implements Initializable {
    
    public Patient patient;
    
    @FXML
    private GridPane noRemainingPatientsGridPane;
    
    
    @FXML
    private GridPane basePane;
    
    
    @FXML
    private GridPane centralGridPane;
    
    @FXML
    private Label priorityValueLabel;
                  
    @FXML
    private Button submitBtn;

    @FXML
    private TextArea nursesReportInput;

    @FXML
    private TextField temperatureInput;

    @FXML
    private TextField heightInput;

    @FXML
    private TextField weightInput;

    @FXML
    private TextField pulseInput;

    @FXML
    private Slider prioritySlider;

    @FXML
    private TextField systolicBPInput;

    @FXML
    private TextField diastolicBPInput;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label dobLabel;
   
    public boolean submitted = false;
    
    
    /**
     * Triggered when the user clicks the submitBtn button.
     * Retrieves the data entered into the form and updates that Patient's information.
     * @param event 
     */
    @FXML
    void submit(ActionEvent event) {
        // Get vital signs:
        VitalSigns vitals = new VitalSigns();
        double height, weight, temperature, pulse, systolic, diastolic;
        height = Double.parseDouble(heightInput.getText());
        weight = Double.parseDouble(weightInput.getText());
        temperature = Double.parseDouble(temperatureInput.getText());
        pulse = Double.parseDouble(pulseInput.getText());
        systolic = Double.parseDouble(systolicBPInput.getText());
        diastolic = Double.parseDouble(diastolicBPInput.getText());
        
        vitals.setHeightInCm(height);
        vitals.setPulseInBPM(pulse);
        vitals.setWeightInKg(weight);
        vitals.setTemperatureInCelcius(temperature);
        vitals.setDiastolicBP(diastolic);
        vitals.setSystolicBP(systolic);
        
       
        patient.setVitalSigns(vitals);
        patient.setNursesReport(nursesReportInput.getText());
        patient.setPriority(Math.round((int) prioritySlider.getValue()) + 1);
        
        patient.setStatus(PatientStatus.WAITING_FOR_DOCTOR);
        database.addPatientToDoctorWaitingList(patient);
        
        submitted = true;
        super.showWaitingList();
    }
   
    /**
     * Redirects the user to the WaitingList
     * If the user has not submitted an update to the Patient it called, that patient is added back to the queue.
     */
    @Override
    protected void showWaitingList(){
        if (!submitted){
            database.addPatientToTriageNurseWaitingList(patient);
        }
        super.showWaitingList();
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        patient = database.getNextPatientForTriageNurse();
        
        if (patient.getForename() == null){
            //no patient left in list.
            basePane.setVisible(false);
            noRemainingPatientsGridPane.setVisible(true);
        } else {
            nameLabel.setText(patient.getFullName());
            dobLabel.setText(patient.getDateOfBirth());

            prioritySlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                String value = String.format("%s", Math.round((double) new_val));
                priorityValueLabel.setText(value);
            });          
        }
    }
}
