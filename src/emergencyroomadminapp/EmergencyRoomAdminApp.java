/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emergencyroomadminapp;

import entityClasses.HospitalEmployee;
import fxmlControllers.FXMLLogInController;
import interfaces.IDatabase;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the entry point for the application.
 * @author TLohan
 */
public class EmergencyRoomAdminApp extends Application {
    
    HospitalEmployee currentEmployee = new HospitalEmployee();
    IDatabase database;
    
    /**
     *  Default constructor for the application
     */
    public EmergencyRoomAdminApp(){
        
    }
    
    /**
     * Connects to the database and loads the login page.
     * @param window - the window which serves as the root for the GUI.
     * @throws Exception 
     */
    @Override
    public void start(Stage window) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/LogIn.fxml"));
        FXMLLogInController controller = new FXMLLogInController();
        Parent parent = null;
        try {
            // try to connect to the database
            database = new Database();
            controller.database = database;
            loader.setController(controller);
            parent = loader.load();
        } catch (IOException e) {
            // No server connection. Display an error.
            loader.setController(controller);
            parent = loader.load();
            controller.displayErrorPane();
        } finally {
            Scene loginScene = new Scene(parent);
            window.setTitle("ER Patient Admin");
            window.setScene(loginScene);
            window.setMinWidth(900);
            window.setMinHeight(700);
            window.show();
        }
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
