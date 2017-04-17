/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genrsa;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rdiazarr
 */
public class GenRSA extends Application {
    
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("escena.fxml"));
            
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("escena.fxml"));
            //Parent root = (Parent)loader.load();
            //SceneController myController = loader.getController();
            //myController.setStage(primaryStage);
            
            Scene scene = new Scene(root);
            
            primaryStage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            primaryStage.setMinHeight(MINIMUM_WINDOW_HEIGHT);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Aplicacion pantalla principal");            
            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(GenRSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(GenRSA.class, (java.lang.String[])null);
    }
    
}
