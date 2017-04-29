/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeCipher;

import Imprimir.DeCipherPrint;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author rdiazarr
 */
public class DeCipherController {
  
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="pubKey"
    private TextField pubKey; // Value injected by FXMLLoader
    
    @FXML // fx:id="Modulus"
    private TextField Modulus; // Value injected by FXMLLoader

    @FXML // fx:id="originalData"
    private TextArea originalData; // Value injected by FXMLLoader

    @FXML // fx:id="isTextOriginal"
    private CheckBox isTextOriginal; // Value injected by FXMLLoader

    @FXML // fx:id="cipherData1"
    private TextArea cipherData1; // Value injected by FXMLLoader

    @FXML // fx:id="privKeyCombo"
    private ComboBox privKeyCombo; // Value injected by FXMLLoader
        
    @FXML // fx:id="Modulus1"
    private TextField Modulus1; // Value injected by FXMLLoader

    @FXML // fx:id="cipherData2"
    private TextArea cipherData2; // Value injected by FXMLLoader

    @FXML // fx:id="decipherData"
    private TextArea decipherData; // Value injected by FXMLLoader

    @FXML // fx:id="isTextCiphered"
    private CheckBox isTextCiphered; // Value injected by FXMLLoader
    
    
    
    private DeCipherLogic DeCipher;    
           
    private int radix;
    
    private BigInteger modulusBI;
    
    private BigInteger pubKeyBI;
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert pubKey != null : "fx:id=\"pubKey\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert Modulus != null : "fx:id=\"Modulus\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert originalData != null : "fx:id=\"originalData\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert isTextOriginal != null : "fx:id=\"isTextOriginal\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert cipherData1 != null : "fx:id=\"cipherData1\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert privKeyCombo != null : "fx:id=\"privKeyCombo\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert Modulus1 != null : "fx:id=\"Modulus1\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert cipherData2 != null : "fx:id=\"cipherData2\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert decipherData != null : "fx:id=\"decipherData\" was not injected: check your FXML file 'DeCipher.fxml'.";
        assert isTextCiphered != null : "fx:id=\"isTextCiphered\" was not injected: check your FXML file 'DeCipher.fxml'.";

        DeCipher = new DeCipherLogic (new DeCipherPrint(this));
        
    }
    
    
    
    
    @FXML
    void cipher(ActionEvent event) {
        boolean isText = this.isTextOriginal.isSelected();
        this.DeCipher.setRadix(this.radix);
        
        this.DeCipher.initCipher(this.modulusBI, this.pubKeyBI);
        
        if (this.DeCipher.processData(this.originalData, isText, true)){
            this.DeCipher.encrypt();
        }
    }

    @FXML
    void cipherInfo(ActionEvent event) {

    }

    @FXML
    void clearCipher(ActionEvent event) {

    }


    @FXML
    void decipher(ActionEvent event) {
        boolean isText = this.isTextCiphered.isSelected();
        this.DeCipher.setRadix(this.radix);
        
        if (this.DeCipher.initDecipher(this.modulusBI, this.privKeyCombo.getValue().toString())
                && this.DeCipher.processData(this.cipherData2, isText, false)){
            this.DeCipher.decrypt();
        }

    }

    @FXML
    void decipherInfo(ActionEvent event) {

    }

    @FXML
    void clearDecipher(ActionEvent event) {

    }    
    
    
    
    //Inicializado desde la escena principal
    
    public void setRadix(int radix) {
        this.radix = radix;
    }
    
    public void setModulusBI(BigInteger modulus) {
        this.modulusBI = modulus;
    }
  
    public void setPubKeyBI(BigInteger pubKey) {
        this.pubKeyBI = pubKey;
    }
    
  
    
    
    //parte gráfica
    
    public TextField getPubKey() {
        return this.pubKey;
    }
    
    public TextField getModulus() {
        return this.Modulus;
    }
       
    public ComboBox getPrivKeys() {
        return this.privKeyCombo;
    }
    
    public TextField getModulus1() {
        return this.Modulus1;
    }
    
    public TextArea getOriginalData() {
        return this.originalData;
    }
            
    public TextArea getCipherData1() {
        return this.cipherData1;
    }
    
    public TextArea getCipherData2() {
        return this.cipherData2;
    }
            
    public TextArea getDecipherData() {
        return this.decipherData;
    }
}


