/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sign;


import Imprimir.SignPrint;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 *
 * @author rdiazarr
 */
public class SignController {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="privKeyCombo"
    private ComboBox privKeyCombo; // Value injected by FXMLLoader
    
    @FXML // fx:id="modulus"
    private TextField modulus; // Value injected by FXMLLoader

    @FXML // fx:id="originalData"
    private TextArea originalData; // Value injected by FXMLLoader

    @FXML // fx:id="isTextOriginal"
    private CheckBox isTextOriginal; // Value injected by FXMLLoader

    @FXML // fx:id="signedData1"
    private TextArea signedData1; // Value injected by FXMLLoader

    @FXML // fx:id="pubKey"
    private TextField pubKey; // Value injected by FXMLLoader
    
    @FXML // fx:id="modulus1"
    private TextField modulus1; // Value injected by FXMLLoader

    @FXML // fx:id="signedData2"
    private TextArea signedData2; // Value injected by FXMLLoader

    @FXML // fx:id="validatedData"
    private TextArea validatedData; // Value injected by FXMLLoader

    @FXML // fx:id="isTextSigned"
    private CheckBox isTextSigned; // Value injected by FXMLLoader
    
    
    
    private SignLogic Sign;    
           
    private int radix;
    
    private BigInteger modulusBI;
    
    private BigInteger pubKeyBI;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert privKeyCombo != null : "fx:id=\"privKeyCombo\" was not injected: check your FXML file 'Sign.fxml'.";
        assert modulus != null : "fx:id=\"modulus\" was not injected: check your FXML file 'Sign.fxml'.";
        assert originalData != null : "fx:id=\"originalData\" was not injected: check your FXML file 'Sign.fxml'.";
        assert isTextOriginal != null : "fx:id=\"isTextOriginal\" was not injected: check your FXML file 'Sign.fxml'.";
        assert signedData1 != null : "fx:id=\"signedData1\" was not injected: check your FXML file 'Sign.fxml'.";
        assert pubKey != null : "fx:id=\"pubKey\" was not injected: check your FXML file 'Sign.fxml'.";
        assert modulus1 != null : "fx:id=\"modulus1\" was not injected: check your FXML file 'Sign.fxml'.";
        assert signedData2 != null : "fx:id=\"signedData2\" was not injected: check your FXML file 'Sign.fxml'.";
        assert validatedData != null : "fx:id=\"validatedData\" was not injected: check your FXML file 'Sign.fxml'.";
        assert isTextSigned != null : "fx:id=\"isTextSigned\" was not injected: check your FXML file 'Sign.fxml'.";
        
        Sign = new SignLogic(new SignPrint(this));
        
        //para poner el foco en originalData
        Platform.runLater(originalData::requestFocus);
    }
    
    
    @FXML
    void sign(ActionEvent event) {
        boolean isText = this.isTextOriginal.isSelected();
        this.Sign.setRadix(this.radix);
        
        if (this.Sign.initSign(this.modulusBI, this.privKeyCombo.getValue().toString())
                && this.Sign.processData(this.originalData, isText, true)){
            this.Sign.sign();
        }

    }
    
    
    @FXML
    void clearSign(ActionEvent event) {
        this.originalData.clear();
        this.signedData1.clear();
        this.isTextOriginal.setSelected(false);
    }  

    @FXML
    void signInfo(ActionEvent event) {
        this.Sign.putSignInfo();
    }

    @FXML
    void validateSign(ActionEvent event) {        
        boolean isText = this.isTextSigned.isSelected();
        this.Sign.setRadix(this.radix);
        
        this.Sign.initValidateSign(this.modulusBI, this.pubKeyBI);
        
        if (this.Sign.processData(this.signedData2, isText, false)){
            this.Sign.validateSign(isText);
        }

    }
    
    @FXML
    void clearValidateSign(ActionEvent event) {
        this.signedData2.clear();
        this.validatedData.clear();
        this.isTextSigned.setSelected(false);
    }

    @FXML
    void validateSignInfo(ActionEvent event) {
        this.Sign.putValidateSignInfo();
    }
    
    /**
     * Método usado para pasar el foco a isTextSigned   
     * @param keyEvent
     */
    public void tabCiphData(KeyEvent keyEvent) {    
        if (keyEvent.getCode() == KeyCode.TAB) {
            this.isTextSigned.requestFocus();
        }
               
    }
    
    /**
     * Método usado para pasar el foco a isTextOriginal
     * @param keyEvent
     */
    public void tabOrigData(KeyEvent keyEvent) {    
        if (keyEvent.getCode() == KeyCode.TAB) {
            this.isTextOriginal.requestFocus();
        }
               
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
        return this.modulus;
    }
       
    public ComboBox getPrivKeys() {
        return this.privKeyCombo;
    }
    
    public TextField getModulus1() {
        return this.modulus1;
    }
    
    public TextArea getOriginalData() {
        return this.originalData;
    }
            
    public TextArea getSignedData1() {
        return this.signedData1;
    }
    
    public TextArea getSignedData2() {
        return this.signedData2;
    }
            
    public TextArea getValidatedData() {
        return this.validatedData;
    }
}