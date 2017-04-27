/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sign;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 *
 * @author rdiazarr
 */
public class SignController {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="privKey1"
    private TextField privKey1; // Value injected by FXMLLoader

    @FXML // fx:id="originalData"
    private TextArea originalData; // Value injected by FXMLLoader

    @FXML // fx:id="textOriginal"
    private CheckBox textOriginal; // Value injected by FXMLLoader

    @FXML // fx:id="cipherData1"
    private TextArea cipherData1; // Value injected by FXMLLoader

    @FXML // fx:id="pubKey1"
    private TextField pubKey1; // Value injected by FXMLLoader

    @FXML // fx:id="cipherData2"
    private TextArea cipherData2; // Value injected by FXMLLoader

    @FXML // fx:id="decipherData"
    private TextArea decipherData; // Value injected by FXMLLoader

    @FXML // fx:id="textCiphered"
    private CheckBox textCiphered; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert privKey1 != null : "fx:id=\"privKey1\" was not injected: check your FXML file 'Sign.fxml'.";
        assert originalData != null : "fx:id=\"originalData\" was not injected: check your FXML file 'Sign.fxml'.";
        assert textOriginal != null : "fx:id=\"textOriginal\" was not injected: check your FXML file 'Sign.fxml'.";
        assert cipherData1 != null : "fx:id=\"cipherData1\" was not injected: check your FXML file 'Sign.fxml'.";
        assert pubKey1 != null : "fx:id=\"pubKey1\" was not injected: check your FXML file 'Sign.fxml'.";
        assert cipherData2 != null : "fx:id=\"cipherData2\" was not injected: check your FXML file 'Sign.fxml'.";
        assert decipherData != null : "fx:id=\"decipherData\" was not injected: check your FXML file 'Sign.fxml'.";
        assert textCiphered != null : "fx:id=\"textCiphered\" was not injected: check your FXML file 'Sign.fxml'.";

    }
    
    
    
    
    
    @FXML
    void cleanSign(ActionEvent event) {

    }

    @FXML
    void cleanValidateSign(ActionEvent event) {

    }

    @FXML
    void sign(ActionEvent event) {

    }

    @FXML
    void signInfo(ActionEvent event) {

    }

    @FXML
    void validateSign(ActionEvent event) {

    }

    @FXML
    void validateSignInfo(ActionEvent event) {

    }
}