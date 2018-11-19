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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * Controller for the Doctor_callNextPatient Pane
 * @author TLohan
 */
public class Doctor_callNextPatientController extends HospitalEmployeeController implements Initializable {
    public Patient patient;
    
    @FXML
    private GridPane noRemainingPatientsGridPane;
    
    
    @FXML
    private GridPane basePane;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label dobLabel;

    @FXML
    private  Label priorityLabel;
   
    @FXML
    private Label admittanceReport;

    @FXML
    private Label bloodPressureLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label pulseLabel;

    @FXML
    private Label nursesReportLabel;

    @FXML
    private TextArea treatmentPlanReport;

    @FXML
    private Button submitBtn;
    
    public boolean submitted = false;
    
    /**
     * Triggered when the user clicks on the submitBtn Button
     * Collects the data from the form, persists it to the server and redirects to the WaitingList Pane
     * @param event 
     */
    
    @FXML
    void submit(ActionEvent event) {
        formIsValid = true;
        String doctorsReport = treatmentPlanReport.getText();
        if ("".equals(doctorsReport)){
            formIsValid = false;
            showValidation(treatmentPlanReport, "Error");
        }
        patient.setDoctorsReport(doctorsReport);
        patient.setStatus(PatientStatus.DISCHARGED);
        if (formIsValid){
            database.savePatient(patient);
            submitted = true;
            showWaitingList();            
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        validatedFields = new Control[1];
        validatedFields[0] = treatmentPlanReport;
        patient = database.getNextPatientForDoctor();
        
        if (patient.getForename() == null){
            //no patient left in list.
            basePane.setVisible(false);
            noRemainingPatientsGridPane.setVisible(true);
        } else {
            nameLabel.setText("Name:\t" + patient.getFullName());
            dobLabel.setText("DOB:\t" + patient.getDateOfBirth());
            priorityLabel.setText("Priority:\t" + patient.getPriority());

            admittanceReport.setText(patient.getSummaryOnAdmittance());

            VitalSigns vitals = patient.getVitalSigns();

            bloodPressureLabel.setText(vitals.getDiastolicBP() + " over " +vitals.getSystolicBP() );
            heightLabel.setText(vitals.getHeightInCm() + " cm");
            weightLabel.setText(vitals.getWeightInKg() + " kg");
            pulseLabel.setText(vitals.getPulseInBPM() + " BPM");
            temperatureLabel.setText(vitals.getTemperatureInCelcius() + " °C");

            nursesReportLabel.setText(patient.getNursesReport());
        }
        
        
    }
    
}
