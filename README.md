# PatientSchedulerClient

This is a client-server application designed to help the processing of patients as they present to a hospital emergency room. Each patient will interact with staff from three departments as they pass through the ER system:  the admissions staff, a triage nurse and a doctor, with more information being recorded at each stage. Admissions staff record basic information on the patient’s arrival at the ER and the patient is added to the waiting list to see the next available triage nurse. The nurse inspects the patient, assigns him/her a priority  and adds them to the waiting list to see the next available doctor. When a doctor calls a patient he/she will get the next patient with the highest priority. Each member of staff can log in and will be shown the appropriate UI for their department so there is only one version of the client application.


## Running the application
Download  and extract the project. Navigate to the 'dist' folder and execute the EmerergencyRoomAdminApp jar file.
__Please note the [server](https://github.com/TLohan/PatientSchedulerServer/blob/master/dist/PatientSchedulerServer.jar) application must be running first for the application to be functional.__

### Dummy Login Credentials

|Department|Username|Password|
|----|------|-------|
|Admissions|test_receptionist|alpha|
|Triage Nurse|test_nurse|beta|
|Doctor|test_doctor|gamma|
