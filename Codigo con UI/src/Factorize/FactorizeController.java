/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorize;

import Imprimir.FactorizePrint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyEvent;

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

    @FXML // fx:id="Module"
    private TextField Module; // Value injected by FXMLLoader

    @FXML // fx:id="Prime_p"
    private TextField Prime_p; // Value injected by FXMLLoader

    @FXML // fx:id="Prime_q"
    private TextField Prime_q; // Value injected by FXMLLoader
    
    @FXML // fx:id="Time"
    private TextField Time; // Value injected by FXMLLoader

    @FXML // fx:id="Results"
    private TextArea Results; // Value injected by FXMLLoader
    
    @FXML // fx:id="startBttn"
    private Button startBttn; // Value injected by FXMLLoader

    @FXML // fx:id="continueBttn"
    private Button continueBttn; // Value injected by FXMLLoader
    
    
    
    private FactorizeAttack factorize;
        
    private int radix;
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert NumLaps != null : "fx:id=\"NumLaps\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert ObtainPQ != null : "fx:id=\"ObtainPQ\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Module != null : "fx:id=\"Module\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Prime_p != null : "fx:id=\"Prime_p\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Prime_q != null : "fx:id=\"Prime_q\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Time != null : "fx:id=\"Time\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert Results != null : "fx:id=\"Results\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert startBttn != null : "fx:id=\"startBttn\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        assert continueBttn != null : "fx:id=\"continueBttn\" was not injected: check your FXML file 'Factorizacion.fxml'.";
        
        
        
        factorize = new FactorizeAttack(new FactorizePrint(this));
        continueBttn.setDisable(true);
        
    }
    
        
    
    public void start(ActionEvent event) {
        String moduleStr = this.Module.getText();
        this.factorize.setRadix(this.radix);
            
        
        if(this.ObtainPQ.isSelected()){
            this.factorize.obtainPQ(moduleStr);
        } else {
            String lapsNumStr = this.NumLaps.getText();
            this.factorize.start(moduleStr, lapsNumStr);
        }
        
    } 
    
    public void Continue(ActionEvent event) {
        this.factorize.Continue();
        
    } 
    
    public void info(ActionEvent event) {        
        this.factorize.putInfo();
        
    } 
      
    //creo que esto sobra
   /* public void obtainPQ(ActionEvent event) {
        String moduleStr = this.Module.getText();
        this.factorize.setRadix(this.radix);
        this.factorize.obtainPQ(moduleStr);
        
    }*/
    
    public void clearWhileEditModule(KeyEvent keyEvent){
        this.ObtainPQ.setDisable(false);
        this.NumLaps.setDisable(false);
        this.NumLaps.setBlendMode(BlendMode.SRC_OVER);
        this.NumLaps.setEditable(true);
        this.Prime_p.clear();
        this.Prime_q.clear();
        this.Results.clear();
        this.Time.clear();
        this.startBttn.setDisable(false);
        this.continueBttn.setDisable(true);
       
    }
    
     public void clean(ActionEvent eventx){
        this.ObtainPQ.setDisable(false);
        this.NumLaps.setDisable(false);
        this.NumLaps.setBlendMode(BlendMode.SRC_OVER);
        this.NumLaps.setEditable(true);
        this.NumLaps.setText("10");
        this.Prime_p.clear();
        this.Prime_q.clear();
        this.Results.clear();        
        this.Time.clear();
        this.startBttn.setDisable(false);
        this.continueBttn.setDisable(true);
       
    }
    
    @FXML
    public void checkSelected(ActionEvent event){
        if (this.ObtainPQ.isSelected()){
            this.NumLaps.clear();
            this.NumLaps.setDisable(true);
            this.NumLaps.setBlendMode(BlendMode.DARKEN);
        }
        
        if (!this.ObtainPQ.isSelected()){
            this.NumLaps.setText("10");
            this.NumLaps.setDisable(false);
            this.NumLaps.setBlendMode(BlendMode.SRC_OVER);
        }
    }   
    
  
    
    
    
    
    
    
    public TextField getModule() {
        return Module;
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
    
    public CheckBox getObtainPQ() {
        return ObtainPQ;
    }
    
    public void setRadix(int radix) {
        this.radix = radix;
    }
    
    
    
    
}
