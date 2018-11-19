/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlControllers;


import entityClasses.PatientStatus;
import entityClasses.InsurancePolicy;
import entityClasses.NextOfKin;
import entityClasses.Patient;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Controller for the CreateNewPatient pane.
 * @author TLohan
 */
public class CreateNewPatientController extends HospitalEmployeeController implements Initializable {  
    
    @FXML
    private GridPane centralGridPane;
    
    @FXML
    private TextField forenameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private ChoiceBox<String> titleComboBox;
    
    @FXML
    private ToggleGroup genderToggleGroup;
    
    @FXML
    private DatePicker dobInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField emailAddressInput;

    @FXML
    private TextField nextOfKinNameInput;

    @FXML
    private TextField nextOfKinPhoneNumberInput;

    @FXML
    private TextField ppsNumberInput;
    
    @FXML
    private ToggleGroup insuranceToggleGroup;
    
     @FXML
    private ToggleButton yesInsuranceToggle;

      @FXML
    private ToggleButton noInsuranceToggle;

    @FXML
    private TextField insurancePolicyNumberInput;

    @FXML
    private TextField insuranceCompanyNameInput;

    @FXML
    private TextField NextOfKinRelationshipInput;

    @FXML
    private TextArea summaryOnAdmittanceTextArea;

    @FXML
    private Button submitBtn;
    
    
    /**
     * Action called  by clicking the submitBtn Button
     * @param event - The click event
     */
    @FXML
    void submitPatient(ActionEvent event) {
       Patient patient = createPatientObject();
       if (formIsValid){
        database.addPatientToTriageNurseWaitingList(patient);
        showWaitingList();   
       }
    }
    
    
    /**
     * Creates the Patient object from the data entered in the form.
     * @return Patient
     */
    private Patient createPatientObject(){
        Patient patient = new Patient();
        resetValidation();
        if (titleComboBox.getValue() != null){
            patient.setTitle(titleComboBox.getValue());
        } else {
            showValidation(titleComboBox, "Error");
        }
        
        try {
            patient.setForename(forenameInput.getText());
        } catch (IllegalArgumentException e) {
            showValidation(forenameInput, "Error");
        }
        
        try {
            patient.setLastname(lastNameInput.getText());
        } catch (IllegalArgumentException e) {
            showValidation(lastNameInput, "Error");
        }
        
        try {
            patient.setDateOfBirth(dobInput.getValue().toString());
       } catch (NullPointerException e){
            showValidation(dobInput, "Error");
        }
        
        try {
            patient.setPhoneNumber(phoneNumberInput.getText());
        } catch (IllegalArgumentException e){
            showValidation(phoneNumberInput, "Error");
        }
        patient.setEmailAddress(emailAddressInput.getText());
        patient.setPPSNumber(ppsNumberInput.getText());
        
        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setFullName(nextOfKinNameInput.getText());
        nextOfKin.setPhoneNumber(nextOfKinPhoneNumberInput.getText());
        nextOfKin.setRelationshipToPatient(NextOfKinRelationshipInput.getText());
        patient.setNextOfKin(nextOfKin);
        
        if (insuranceToggleGroup.getSelectedToggle().equals(yesInsuranceToggle)){
            InsurancePolicy insurancePolicy = new InsurancePolicy();
            insurancePolicy.setPolicyId(insurancePolicyNumberInput.getText());
            insurancePolicy.setInsuranceCompany(insuranceCompanyNameInput.getText());
            patient.setInsurancePolicy(insurancePolicy);
        }
        
        if (summaryOnAdmittanceTextArea.getText() != ""){
            patient.setSummaryOnAdmittance(summaryOnAdmittanceTextArea.getText());
        } else {
            showValidation(summaryOnAdmittanceTextArea, "Error");
        }
        patient.setStatus(PatientStatus.WAITING_FOR_TRIAGE_NURSE);
        
        return patient;
    }
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> titles  = FXCollections.observableArrayList(
                "Mr.",
                "Mrs.",
                "Ms.",
                "Dr.",
                "Prof.",
                "Other"
        );
        
        validatedFields = new Control[6];
        validatedFields[0] = forenameInput;
        validatedFields[1] = titleComboBox;
        validatedFields[2] = lastNameInput;
        validatedFields[3] = dobInput;
        validatedFields[4]= phoneNumberInput;
        validatedFields[5] = summaryOnAdmittanceTextArea;
        
        titleComboBox.setItems(titles);
        
        
        insuranceToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle){
                if (insuranceToggleGroup.getSelectedToggle() != null){
                    if (insuranceToggleGroup.getSelectedToggle().equals(yesInsuranceToggle)){
                        insuranceCompanyNameInput.setDisable(false);
                        insurancePolicyNumberInput.setDisable(false);
                    } else if (insuranceToggleGroup.getSelectedToggle().equals(noInsuranceToggle)){
                        insuranceCompanyNameInput.setDisable(true);
                        insurancePolicyNumberInput.setDisable(true);
                    }
                }
            }
        });
        
        
    }
    
}
