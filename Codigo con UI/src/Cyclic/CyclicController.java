/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cyclic;

import Imprimir.CyclicPrint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author rdiazarr
 */
public class CyclicController {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="NumCiphers"
    private TextField NumCiphers; // Value injected by FXMLLoader
    
    @FXML // fx:id="Complete"
    private CheckBox Complete; // Value injected by FXMLLoader   
    
    @FXML // fx:id="Modulus"
    private TextField Modulus; // Value injected by FXMLLoader
    
    @FXML // fx:id="Exponent"
    private TextField Exponent; // Value injected by FXMLLoader

    @FXML // fx:id="Message"
    private TextField Message; // Value injected by FXMLLoader
        
    @FXML // fx:id="CypherMessage"
    private TextField CypherMessage; // Value injected by FXMLLoader    

    @FXML // fx:id="startBttn"
    private Button startBttn; // Value injected by FXMLLoader

    @FXML // fx:id="continueBttn"
    private Button continueBttn; // Value injected by FXMLLoader

    @FXML // fx:id="clearBttn"
    private Button clearBttn; // Value injected by FXMLLoader
    
    @FXML // fx:id="progress"
    private ProgressIndicator progress; // Value injected by FXMLLoader
    
    @FXML // fx:id="Results"
    private TextArea Results; // Value injected by FXMLLoader

    @FXML // fx:id="mRecovered"
    private TextField mRecovered; // Value injected by FXMLLoader

    @FXML // fx:id="Time"
    private TextField Time; // Value injected by FXMLLoader
      
    
    private CyclicAttack cyclicAttack;    
           
    private int radix;    
    
    private boolean firstTime;
    
    private Boolean start;
    
        
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert NumCiphers != null : "fx:id=\"NumCiphers\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert Complete != null : "fx:id=\"Complete\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert Message != null : "fx:id=\"Message\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert Modulus != null : "fx:id=\"Modulus\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert Exponent != null : "fx:id=\"Exponent\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert CypherMessage != null : "fx:id=\"CypherMessage\" was not injected: check your FXML file 'Cyclic.fxml'.";        
        assert startBttn != null : "fx:id=\"startBttn\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert continueBttn != null : "fx:id=\"continueBttn\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert clearBttn != null : "fx:id=\"clearBttn\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert Results != null : "fx:id=\"Results\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert progress != null : "fx:id=\"progress\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert mRecovered != null : "fx:id=\"mRecovered\" was not injected: check your FXML file 'Cyclic.fxml'.";
        assert Time != null : "fx:id=\"Time\" was not injected: check your FXML file 'Cyclic.fxml'.";

        
        firstTime = true;
        start = true;
        cyclicAttack = new CyclicAttack(new CyclicPrint(this));
        continueBttn.setDisable(true);  
        
        Platform.runLater(Message::requestFocus);
    }
    
    @FXML
    public void startStop(ActionEvent event) {
        
        if (start){
            Task CAstart= new Task() {
                @Override
                protected Object call() throws Exception {
                    start = false;
                    String message = Message.getText();
                    String modulus = Modulus.getText();
                    String exponent = Exponent.getText();
                                        
                    cyclicAttack.setRadix(radix); 
                    Platform.runLater(() ->progress.setVisible(true));                    

                    if (cyclicAttack.init(message, modulus, exponent)){

                        if(Complete.isSelected()){
                            cyclicAttack.complete();
                            
                        } else {
                            String numOfCyphers = NumCiphers.getText();            
                            cyclicAttack.start(numOfCyphers);                            
                        }                        
                    } 
                    
                    start=true;
                    cyclicAttack.setIsCancelled(false);

                    Platform.runLater(() ->progress.setVisible(false));
                    return null;
                }
            };
            
            new Thread(CAstart).start(); 
            
        }else {
            cyclicAttack.setIsCancelled(true);
        }              
    }

    @FXML
    public void Continue(ActionEvent event) {
                
        Task CAcontinue = new Task() {
            @Override
            protected Object call() throws Exception {
                start = false;
                String numOfCyphers = NumCiphers.getText();    
                Platform.runLater(() ->progress.setVisible(true));
                cyclicAttack.Continue(numOfCyphers);
                Platform.runLater(() ->progress.setVisible(false));
                start = true;
                cyclicAttack.setIsCancelled(false);
                return null;
            }
        };
        
        new Thread(CAcontinue).start();        
    }
    

    @FXML
    void info(ActionEvent event) {
        this.cyclicAttack.putInfo();
    }
    
    
    @FXML
    void clear(ActionEvent event) {
        
        this.Complete.setDisable(false);
        this.NumCiphers.setDisable(false);
        this.NumCiphers.setBlendMode(BlendMode.SRC_OVER);
        this.NumCiphers.setEditable(true);
        this.NumCiphers.setText("10");
        this.Message.setEditable(true);
        this.Message.clear();
        this.Modulus.setEditable(true);
        this.Exponent.setEditable(true);
        this.CypherMessage.clear();
        this.Results.clear();     
        this.mRecovered.clear();
        this.Time.clear();
        this.startBttn.setDisable(false);
        this.startBttn.setText("Comenzar");
        this.continueBttn.setDisable(true);

    }

    @FXML
    /**
     * Para prevenir que se modifique tanto el módulo como el exponente
     */
    public void warningModify (KeyEvent keyEvent){
        if (this.firstTime){
            this.firstTime = false;
            this.cyclicAttack.warning();   
        }  
        
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
     * Metodo que activará o desactivará el numero de cifrados
     */
    public void checkSelected(ActionEvent event){
        if (this.Complete.isSelected()){
            this.NumCiphers.clear();
            this.NumCiphers.setDisable(true);
            this.NumCiphers.setBlendMode(BlendMode.DARKEN);
        }
        
        if (!this.Complete.isSelected()){
            this.NumCiphers.setText("10");
            this.NumCiphers.setDisable(false);
            this.NumCiphers.setBlendMode(BlendMode.SRC_OVER);
        }
    }   

    
    
    
    public void setRadix(int radix) {
        this.radix = radix;
    }
    
    public void setFirstTime(boolean firstTime){
        this.firstTime = firstTime;
    }
    
      
    
    //parte gráfica -----------------------------------------------------------
    public TextField getModulus() {
        return this.Modulus;
    }
    
    public TextField getExponent() {
        return this.Exponent;
    }
    
    public TextField getMessage() {
        return this.Message;
    }
    
    public TextField getCypherMessage() {
        return this.CypherMessage;
    }
    
    public TextField getTime() {
        return this.Time;
    }
    
    public TextArea getResults() {
        return this.Results;
    }
    
    public TextField getMRecovered() {
        return this.mRecovered;
    }
    
    public CheckBox getComplete() {
        return this.Complete;
    }
    
    public TextField getNumCiphers() {
        return this.NumCiphers;
    }
    
    public Button getContinueBttn() {
        return this.continueBttn;
    }
     
    public Button getStartBttn() {
        return this.startBttn;
    }
            
    public Button getClearBttn() {
        return this.clearBttn;
    }


}
