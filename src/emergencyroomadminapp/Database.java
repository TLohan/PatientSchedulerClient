/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emergencyroomadminapp;



import customDataStructures.DoubleLinkedList;
import entityClasses.HospitalEmployee;
import entityClasses.Patient;
import entityClasses.ValidServerRequest;
import interfaces.IDatabase;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class communicates with the server.
 * @author TLohan
 */
public class Database implements IDatabase {
    
    
    private String host_ip;
    private int portNumber;
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    /**
     * Default constructor
     * @throws IOException if unable to connect to the server
     */
    public Database() throws IOException{
        // default values:
        host_ip = "127.0.0.1";
        portNumber = 4444;
        connect();
    }
    
    /**
     * Constructor
     * @param host_ip the server's IP address
     * @param portNo the server's port number
     * @throws IOException if unable to connect to the server
     */
    public Database(String host_ip, int portNo) throws IOException {
        this.host_ip = host_ip;
        this.portNumber = portNo;
        connect();
    }
    
    /**
     * Attempts to make a connection to the server
     * @throws IOException if unable to connect to the server
     */
    private void connect() throws IOException {
        Socket socket = new Socket(host_ip, portNumber);
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }
    
    /**
     * Sends a Patient object to the server to be persisted
     * @param patient - The Patient object to be saved
     */
    @Override
    public void savePatient(Patient patient){
        try {
            oos.writeObject(ValidServerRequest.SAVE_PATIENT);
            oos.writeObject(patient);
        } catch (IOException ex){            
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retrieves a HospitalEmployee object from the server 
     * @param hospitalemployee_id - The ID of the employee to retrieve
     * @return HospitalEmployee. null if employee does not exist
     */
    @Override
    public HospitalEmployee getHospitalEmployee(int hospitalemployee_id){
        HospitalEmployee employee = null;
        try {
            oos.writeObject(ValidServerRequest.GET_HOSPITAL_EMPLOYEE);
            oos.writeObject(hospitalemployee_id);            
            employee = (HospitalEmployee) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employee;
    }
    
    /**
     * Retrieves a HospitalEmployee from the server
     * @param hospital_id - The ID of the employee to retrieve
     * @return HospitalEmployee. 
     * null if the employee does not exist
     */
    @Override
    public HospitalEmployee getHospitalEmployeeByUsername(String hospital_id){
        HospitalEmployee employee = null;
        try {
            oos.writeObject(ValidServerRequest.GET_HOSPITAL_EMPLOYEE_BY_USERNAME);
            oos.writeObject(hospital_id);
            employee = (HospitalEmployee) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employee;
    }
    
    /**
     * Gets the next Patient in the queue to see a TriageNurse. That patient is removed from the queue.
     * @return Patient. null if no Patient remains in the queue
     */
    @Override
    public Patient getNextPatientForTriageNurse(){
        Patient patient = null;
        try {
            oos.writeObject(ValidServerRequest.GET_NEXT_PATIENT_FOR_TRIAGE_NURSE);
            patient = (Patient) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }
    
    /**
     * Sends a HospitalEmployee to the server to be persisted
     * @param hospitalEmployee - The HospitalEmployee object to be persisted
     */
    @Override
    public void saveHospitalEmployee(HospitalEmployee hospitalEmployee){
        try {
            oos.writeObject(ValidServerRequest.SAVE_HOSPITAL_EMPLOYEE);
            oos.writeObject(hospitalEmployee);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the next Patient in the queue to see the Doctor. That patient is removed from the queue.
     * @return Patient. null if no Patients in the queue
     */
    @Override
    public Patient getNextPatientForDoctor(){
        Patient patient = null;
        try {
            oos.writeObject(ValidServerRequest.GET_NEXT_PATIENT_FOR_DOCTOR);
            patient = (Patient) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }
    
    /**
     * Adds a Patient to the TraigeNurse Waiting List. Objects in this list are sorted in a FIFO order.
     * @param patient - The Patient to be added to the WaitingList
     */
    @Override
    public void addPatientToTriageNurseWaitingList(Patient patient){
        try {
            oos.writeObject(ValidServerRequest.ADD_PATIENT_TO_TRIAGE_NURSE_WAITING_LIST);
            oos.writeObject(patient);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    /**
     * Adds a Patient to the Doctor Waiting List. Objects in this list are sorted by their Priority
     * @param patient - The Patient to be added to the WaitingList
     */
    @Override
    public void addPatientToDoctorWaitingList(Patient patient){
        try {
            oos.writeObject(ValidServerRequest.ADD_PATIENT_TO_DOCTOR_WAITING_LIST);
            oos.writeObject(patient);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    
    /**
     * Gets the TriageNurse's WaitingList
     * @return DoubleLinkedList. null if the list is empty.
     */
    @Override
    public DoubleLinkedList<Patient> getTriageNurseWaitingList(){
        DoubleLinkedList<Patient> waitingList = null;
        try {
            oos.writeObject(ValidServerRequest.GET_TRIAGE_NURSE_WAITING_LIST);
            waitingList = (DoubleLinkedList<Patient>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return waitingList;
    }
    
    /**
     * Gets the Doctor's WaitingList
     * @return DoubleLinkedList. null if the list is empty.
     */
    @Override
    public DoubleLinkedList<Patient> getDoctorWaitingList(){
        DoubleLinkedList<Patient> waitingList = null;
        try {
            oos.writeObject(ValidServerRequest.GET_DOCTOR_WAITING_LIST);
            waitingList = (DoubleLinkedList<Patient>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return waitingList;  
    }
   
}   


