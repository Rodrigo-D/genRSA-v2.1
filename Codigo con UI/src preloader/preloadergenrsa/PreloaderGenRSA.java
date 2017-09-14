/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preloadergenrsa;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author rdiazarr 
 */
public class PreloaderGenRSA extends Preloader {
    
    ProgressBar progressBar;
    Stage stage;
    
    private Scene createPreloaderScene() {
              
        ImageView genRSAIcon = new ImageView(new Image(PreloaderGenRSA.class.getResourceAsStream("/allImages/genRSA.png")));
     
        genRSAIcon.setFitHeight(150.0);
        genRSAIcon.setFitWidth(200.0);
        genRSAIcon.setLayoutX(158.0);
        genRSAIcon.setLayoutY(39.0);
        genRSAIcon.setPreserveRatio(true);
        
        
        ImageView description = new ImageView(new Image(PreloaderGenRSA.class.getResourceAsStream("/allImages/description.PNG")));
        
        description.setFitHeight(35.0);
        description.setFitWidth(378.0);
        description.setLayoutX(55.0);
        description.setLayoutY(225.0);
        description.setPreserveRatio(true);
        
        
        progressBar = new ProgressBar(0.0);
        
        progressBar.setLayoutY(277.0);
        progressBar.setPrefHeight(18.0);
        progressBar.setPrefWidth(500.0);
        
        
        AnchorPane root = new AnchorPane();
        
        root.setPrefHeight(300.0);
        root.setPrefWidth(500.0);
        
        root.getChildren().addAll(genRSAIcon, description, progressBar);
        root.setStyle("-fx-background-color: #ffffff;");
                
        return new Scene(root);   
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());    
        stage.setTitle("Cargando genRSA");   
        stage.getIcons().add(new Image(PreloaderGenRSA.class.getResourceAsStream("/allImages/genRSA.png")));
        stage.show();
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
    
    @Override
    public void handleApplicationNotification(PreloaderNotification preN) {
        if (preN instanceof ProgressNotification) {
             ProgressNotification pn= (ProgressNotification) preN;
             progressBar.setProgress(pn.getProgress());
        }
    }    
    
}
