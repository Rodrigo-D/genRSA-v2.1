/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorize;

import Imprimir.FactorizePrint;
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

/**
 *
 * @author rdiazarr
 */
public class FactorizeController {
    
     @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="NumLaps"
    private TextField NumLaps; // Value injected by FXMLLoader

    @FXML // fx:id="ObtainPQ"
    private CheckBox ObtainPQ; // Value injected by FXMLLoader

    @FXML // fx:id="Modulus"
    private TextField Modulus; // Value injected by FXMLLoader

    @FXML // fx:id="Prime_p"
    private TextField Prime_p; // Value injected by FXMLLoader

    @FXML // fx:id="Prime_q"
    private TextField Prime_q; // Value injected by FXMLLoader
    
    @FXML // fx:id="Time"
    private TextField Time; // Value injected by FXMLLoader

    @FXML // fx:id="Results"
    private TextArea Results; // Value injected by FXMLLoader
    
    @FXML // fx:id="progress"
    private ProgressIndicator progress; // Value injected by FXMLLoader
    
    @FXML // fx:id="startBttn"
    private Button startBttn; // Value injected by FXMLLoader

    @FXML // fx:id="continueBttn"
    private Button continueBttn; // Value injected by FXMLLoader
    
    @FXML // fx:id="clearBttn"
    private Button clearBttn; // Value injected by FXMLLoader
    
    
    
    private FactorizeAttack factorize;
        
    private int radix;
    
    private Boolean start;
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert NumLaps != null : "fx:id=\"NumLaps\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert ObtainPQ != null : "fx:id=\"ObtainPQ\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Modulus != null : "fx:id=\"Modulus\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Prime_p != null : "fx:id=\"Prime_p\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Prime_q != null : "fx:id=\"Prime_q\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Time != null : "fx:id=\"Time\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Results != null : "fx:id=\"Results\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert progress != null : "fx:id=\"progress\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert startBttn != null : "fx:id=\"startBttn\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert continueBttn != null : "fx:id=\"continueBttn\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert clearBttn != null : "fx:id=\"clearBttn\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        
        
        factorize = new FactorizeAttack(new FactorizePrint(this));
        continueBttn.setDisable(true);
        start = true;
        
    }
    
        
    /**
     * Método que gestions la pulsacion del boton Comenzar-Parar
     * @param event 
     */
    public void startStop(ActionEvent event) {
        
        if (start){
            Task PAstart= new Task() {
                @Override
                protected Object call() throws Exception {
                    start = false;
                    String modulusStr = Modulus.getText();
                    factorize.setRadix(radix);

                    Platform.runLater(() ->progress.setVisible(true));

                    if (factorize.init(modulusStr)){
                        
                        if(ObtainPQ.isSelected()){
                            factorize.complete();
                            
                        } else {
                            String lapsNumStr = NumLaps.getText();
                            factorize.start(lapsNumStr);
                        }
                    }
                    start=true;
                    factorize.setIsCancelled(false);
                    
                    Platform.runLater(() ->progress.setVisible(false));
                    return null;
                }
            };
            
            new Thread(PAstart).start(); 
            
        } else {
            factorize.setIsCancelled(true);
        }        
    } 
    
    
    /**
     * Método que gestiona la pulsacion del boton Continuar
     * @param event 
     */
    public void Continue(ActionEvent event) {
        
        Task PAcontinue= new Task() {
            @Override
            protected Object call() throws Exception {
                start = false;
                String lapsNumStr = NumLaps.getText();
                
                Platform.runLater(() ->progress.setVisible(true));

                factorize.Continue(lapsNumStr);
                
                Platform.runLater(() ->progress.setVisible(false));
                start = true;
                factorize.setIsCancelled(false);
                return null;
            }
        };
        
        new Thread(PAcontinue).start(); 
        
    } 
    
    /**
     * Metodo que gestiona la pulsacion del boton Informacion
     * @param event 
     */
    public void info(ActionEvent event) {        
        this.factorize.putInfo();
        
    } 

    /**
     * Metodo que gestiona la pulsacion del boton Limpiar Datos
     * @param eventx 
     */
    public void clear(ActionEvent eventx){
        this.ObtainPQ.setDisable(false);
        this.NumLaps.setDisable(false);
        this.NumLaps.setEditable(true);
        this.NumLaps.setText("10");
        this.Prime_p.clear();
        this.Prime_q.clear();
        this.Results.clear();        
        this.Time.clear();
        this.startBttn.setDisable(false);
        this.startBttn.setText("Comenzar");
        this.continueBttn.setDisable(true);
       
    }
    
    @FXML
    /**
     * Metodo que gestiona si se marca la opcion obtener p y q
     */
    public void checkSelected(ActionEvent event){
        if (this.ObtainPQ.isSelected()){
            this.NumLaps.clear();
            this.NumLaps.setDisable(true);
        }
        
        if (!this.ObtainPQ.isSelected()){
            this.NumLaps.setText("10");
            this.NumLaps.setDisable(false);
        }
    }   
    
  
    
    
    
    public void setRadix(int radix) {
        this.radix = radix;
    }
    
    public FactorizeAttack getFactorizeAttack() {
        return this.factorize;
    }
    
    
    //PARTE GRAFICA
    
    public TextField getModulus() {
        return Modulus;
    }

    public TextField getPrimeP() {
        return Prime_p;
    }
   
    public TextField getPrimeQ() {
        return Prime_q;
    }
    
    public TextField getNumLaps(){
        return NumLaps;
    }
    
    public TextField getTime() {
        return Time;
    }
    public TextArea getResults() {
        return Results;
    }
    
    public Button getStartBttn() {
        return startBttn;
    }
    
    public Button getContinueBttn() {
        return continueBttn;
    }
            
    public Button getClearBttn() {
        return clearBttn;
    }
    
    public CheckBox getObtainPQ() {
        return ObtainPQ;
    }
    
       
}
