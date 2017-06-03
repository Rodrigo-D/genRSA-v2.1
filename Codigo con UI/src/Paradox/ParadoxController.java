/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paradox;

import Imprimir.ParadoxPrint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    @FXML // fx:id="Modulus"
    private TextField Modulus; // Value injected by FXMLLoader

    @FXML // fx:id="Exponent"
    private TextField Exponent; // Value injected by FXMLLoader

    @FXML // fx:id="Message"
    private TextField Message; // Value injected by FXMLLoader

    @FXML // fx:id="AvgCiphersStats"
    private TextField AvgCiphersStats; // Value injected by FXMLLoader

    @FXML // fx:id="Results"
    private TextArea Results; // Value injected by FXMLLoader

    @FXML // fx:id="PrivateKey"
    private TextField PrivateKey; // Value injected by FXMLLoader

    @FXML // fx:id="Time"
    private TextField Time; // Value injected by FXMLLoader
    
    @FXML // fx:id="clearBttn"
    private Button clearBttn; // Value injected by FXMLLoader
    
    @FXML // fx:id="startBttn"
    private Button startBttn; // Value injected by FXMLLoader
    
    @FXML // fx:id="progress"
    private ProgressIndicator progress; // Value injected by FXMLLoader

    
    private ParadoxAttack paradoxAttack;
    
    private int radix;
    
    private boolean firstTime;
    
    private Boolean start;
    
    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Modulus != null : "fx:id=\"Modulus\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Exponent != null : "fx:id=\"Exponent\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Message != null : "fx:id=\"Message\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert AvgCiphersStats != null : "fx:id=\"AvgCiphersStats\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Results != null : "fx:id=\"Results\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert PrivateKey != null : "fx:id=\"PrivateKey\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert Time != null : "fx:id=\"Time\" was not injected: check your FXML file 'Paradox.fxml'.";        
        assert startBttn != null : "fx:id=\"startBttn\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert clearBttn != null : "fx:id=\"clearBttn\" was not injected: check your FXML file 'Paradox.fxml'.";
        assert progress != null : "fx:id=\"progress\" was not injected: check your FXML file 'Paradox.fxml'.";
        
        firstTime = true;
        start = true;
        paradoxAttack = new ParadoxAttack(new ParadoxPrint(this));
        
        Platform.runLater(Message::requestFocus);
    }


    @FXML
    public void startStop(ActionEvent event) {
        
        if (start) {  
            Task CAstart= new Task() {
                @Override
                protected Object call() throws Exception {
                    start = false;
                    String message = Message.getText();
                    String modulus = Modulus.getText();
                    String exponent = Exponent.getText();

                    paradoxAttack.setRadix(radix);
                    Platform.runLater(() ->progress.setVisible(true));

                    if (paradoxAttack.init(message, modulus, exponent)){
                        paradoxAttack.start();
                        start=true;
                    }

                    Platform.runLater(() ->progress.setVisible(false));
                    return null;
                }
            };

            new Thread(CAstart).start();  
            
        } else {
            paradoxAttack.setIsCancelled(true);
        }   
    }  
    
    
        
    @FXML
    public void clear(ActionEvent event) {
        this.paradoxAttack.clear();
    } 
    
    @FXML
    public void info(ActionEvent event) {
        this.paradoxAttack.putInfo();
    }
    
    @FXML
    /**
     * Una vez introducido el mensaje si se pulsa enter se lanza el metodo start (igual q pulsar comenzar)
     */
    public void processStart(KeyEvent keyEvent) {
         if (keyEvent.getCode() == KeyCode.ENTER) {            
            this.startStop(new ActionEvent());
        }
    }
        
    @FXML
    /**
     * Para prevenir que se modifique tanto el modulo como el exponente
     */
    public void warningModify (KeyEvent keyEvent){
        if (this.firstTime){
            this.firstTime = false;
            this.paradoxAttack.warning();   
        }  
        
    }  
    
    
    
    
    public void setRadix(int radix) {
        this.radix = radix;
    }
   
    public void setFirstTime(boolean firstTime){
        this.firstTime = firstTime;
    }
    
    public ParadoxAttack getParadoxAttack() {
        return this.paradoxAttack;
    }
    
    //PARTE GRÁFICA
    public TextField getModulus() {
        return this.Modulus;
    }

    public TextField getExponent() {
        return this.Exponent;
    }

    public TextField getMessage() {
        return this.Message;
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
    
    public Button getStartBttn() {
        return this.startBttn;
    }

    public Button getClearBttn() {
        return this.clearBttn;
    }
    
     
}
