/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paradox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rdiazarr
 */
public class ParadoxController {

   
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Module"
    private TextField Module; // Value injected by FXMLLoader

    @FXML // fx:id="Exponent"
    private TextField Exponent; // Value injected by FXMLLoader

    @FXML // fx:id="Message"
    private TextField Message; // Value injected by FXMLLoader

    @FXML // fx:id="CiphersStats"
    private TextField CiphersStats; // Value injected by FXMLLoader

    @FXML // fx:id="AvgCiphersStats"
    private TextField AvgCiphersStats; // Value injected by FXMLLoader

    @FXML // fx:id="Results"
    private TextArea Results; // Value injected by FXMLLoader

    @FXML // fx:id="PrivateKey"
    private TextField PrivateKey; // Value injected by FXMLLoader

    @FXML // fx:id="Time"
    private TextField Time; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Module != null : "fx:id=\"Module\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Exponent != null : "fx:id=\"Exponent\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Message != null : "fx:id=\"Message\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert CiphersStats != null : "fx:id=\"CiphersStats\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert AvgCiphersStats != null : "fx:id=\"AvgCiphersStats\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Results != null : "fx:id=\"Results\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert PrivateKey != null : "fx:id=\"PrivateKey\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Time != null : "fx:id=\"Time\" was not injected: check your FXML file 'Paradox.fxml'.";

    }

    @FXML
    public void clean(ActionEvent event) {

    }

    @FXML
    public void info(ActionEvent event) {

    }

    @FXML
    public void start(ActionEvent event) {

    }    
    
    
    public TextField getModule() {
        return this.Module;
    }

    public TextField getExponent() {
        return this.Exponent;
    }

    public TextField getMessage() {
        return this.Message;
    }

    public TextField getCiphersStats() {
        return this.CiphersStats;
    }

    public TextField getAvgCiphersStats() {
        return this.AvgCiphersStats;
    }

    public TextArea getResults() {
        return this.Results;
    }

    public TextField getPrivateKey() {
        return this.PrivateKey;
    }

    public TextField getTime() {
        return this.Time;
    }
    
    
    
     
}
