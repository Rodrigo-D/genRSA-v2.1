/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genrsa;

import Imprimir.MainWindow;
import Metodos.CalculateNNC;
import Metodos.GenerarClaves;
import Model.ComponentesRSA;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


/**
 *
 * @author rdiazarr
 */
public class sceneController {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="primo_P"
    private TextField primo_P; // Value injected by FXMLLoader

    @FXML // fx:id="bits_primo_P"
    private TextField bits_primo_P; // Value injected by FXMLLoader

    @FXML // fx:id="primo_Q"
    private TextField primo_Q; // Value injected by FXMLLoader

    @FXML // fx:id="bits_primo_Q"
    private TextField bits_primo_Q; // Value injected by FXMLLoader

    @FXML // fx:id="clave_Privada"
    private TextField clave_Privada; // Value injected by FXMLLoader

    @FXML // fx:id="bits_clave_Privada"
    private TextField bits_clave_Privada; // Value injected by FXMLLoader

    @FXML // fx:id="modulo_N"
    private TextField modulo_N; // Value injected by FXMLLoader

    @FXML // fx:id="bits_modulo_N"
    private TextField bits_modulo_N; // Value injected by FXMLLoader

    @FXML // fx:id="clave_Publica"
    private TextField clave_Publica; // Value injected by FXMLLoader

    @FXML // fx:id="bits_clave_Publica"
    private TextField bits_clave_Publica; // Value injected by FXMLLoader

    @FXML // fx:id="num_claves_parejas"
    private TextField num_claves_parejas; // Value injected by FXMLLoader

    @FXML // fx:id="claves_parejas"
    private TextArea claves_parejas; // Value injected by FXMLLoader

    @FXML // fx:id="estado"
    private Label estado; // Value injected by FXMLLoader

    @FXML // fx:id="iteraciones_primalidad"
    private TextField iteraciones_primalidad; // Value injected by FXMLLoader

    @FXML // fx:id="esPrimo_P"
    private TextField esPrimo_P; // Value injected by FXMLLoader

    @FXML // fx:id="esPrimo_Q"
    private TextField esPrimo_Q; // Value injected by FXMLLoader

    @FXML // fx:id="tiempo_primalidad"
    private TextField tiempo_primalidad; // Value injected by FXMLLoader

    @FXML // fx:id="bits_clave_automatica"
    private TextField bits_clave_automatica; // Value injected by FXMLLoader

    @FXML // fx:id="tiempo_clave_automatica"
    private TextField tiempo_clave_automatica; // Value injected by FXMLLoader

    @FXML // fx:id="generar_automatica"
    private Button generar_automatica; // Value injected by FXMLLoader

    @FXML // fx:id="sameSizePrimes"
    private CheckBox sameSizePrimes; // Value injected by FXMLLoader

    @FXML // fx:id="num_mensajes_noCifrables"
    private TextField num_mensajes_noCifrables; // Value injected by FXMLLoader

    @FXML // fx:id="generar_log_nnc"
    private Button generar_log_nnc; // Value injected by FXMLLoader

    @FXML // fx:id="borrar"
    private Button borrar; // Value injected by FXMLLoader   
    
    @FXML // fx:id="borrar"
    private MenuItem Decimal; // Value injected by FXMLLoader 

    @FXML // fx:id="borrar"
    private MenuItem Hexadecimal; // Value injected by FXMLLoader 
    
    @FXML // fx:id="units"
    private Label units; // Value injected by FXMLLoader
        
    private ComponentesRSA RSA;
    
    private GenerarClaves generate;
    
    private CalculateNNC calculate;
    
    private MainWindow mainWindow;
    
    
   

    
  /*  @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
     //   label.setText("Hello World!");
    }
    */
    
     /**
     * Initializes the controller class.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //algo asi para controlar que no haya errores
                
        assert primo_P != null : "fx:id=\"primo_P\" was not injected: check your FXML file 'scene.fxml'.";
        assert bits_primo_P != null : "fx:id=\"bits_primo_P\" was not injected: check your FXML file 'scene.fxml'.";
        assert primo_Q != null : "fx:id=\"primo_Q\" was not injected: check your FXML file 'scene.fxml'.";
        assert bits_primo_Q != null : "fx:id=\"bits_primo_Q\" was not injected: check your FXML file 'scene.fxml'.";
        assert clave_Privada != null : "fx:id=\"clave_Privada\" was not injected: check your FXML file 'scene.fxml'.";
        assert bits_clave_Privada != null : "fx:id=\"bits_clave_Privada\" was not injected: check your FXML file 'scene.fxml'.";
        assert modulo_N != null : "fx:id=\"modulo_N\" was not injected: check your FXML file 'scene.fxml'.";
        assert bits_modulo_N != null : "fx:id=\"bits_modulo_N\" was not injected: check your FXML file 'scene.fxml'.";
        assert clave_Publica != null : "fx:id=\"clave_Publica\" was not injected: check your FXML file 'scene.fxml'.";
        assert bits_clave_Publica != null : "fx:id=\"bits_clave_Publica\" was not injected: check your FXML file 'scene.fxml'.";
        assert num_claves_parejas != null : "fx:id=\"num_claves_parejas\" was not injected: check your FXML file 'scene.fxml'.";
        assert claves_parejas != null : "fx:id=\"claves_parejas\" was not injected: check your FXML file 'scene.fxml'.";
        assert estado != null : "fx:id=\"estado\" was not injected: check your FXML file 'scene.fxml'.";
        assert iteraciones_primalidad != null : "fx:id=\"iteraciones_primalidad\" was not injected: check your FXML file 'scene.fxml'.";
        assert esPrimo_P != null : "fx:id=\"esPrimo_P\" was not injected: check your FXML file 'scene.fxml'.";
        assert esPrimo_Q != null : "fx:id=\"esPrimo_Q\" was not injected: check your FXML file 'scene.fxml'.";
        assert tiempo_primalidad != null : "fx:id=\"tiempo_primalidad\" was not injected: check your FXML file 'scene.fxml'.";
        assert bits_clave_automatica != null : "fx:id=\"bits_clave_automatica\" was not injected: check your FXML file 'scene.fxml'.";
        assert tiempo_clave_automatica != null : "fx:id=\"tiempo_clave_automatica\" was not injected: check your FXML file 'scene.fxml'.";
        assert generar_automatica != null : "fx:id=\"generar_automatica\" was not injected: check your FXML file 'scene.fxml'.";
        assert sameSizePrimes != null : "fx:id=\"sameSizePrimes\" was not injected: check your FXML file 'scene.fxml'.";
        assert num_mensajes_noCifrables != null : "fx:id=\"num_mensajes_noCifrables\" was not injected: check your FXML file 'scene.fxml'.";
        assert generar_log_nnc != null : "fx:id=\"generar_log_nnc\" was not injected: check your FXML file 'scene.fxml'.";
        assert borrar != null : "fx:id=\"borrar\" was not injected: check your FXML file 'scene.fxml'.";
        assert Decimal != null : "fx:id=\"Decimal\" was not injected: check your FXML file 'scene.fxml'.";
        assert Hexadecimal != null : "fx:id=\"Hexadecimal\" was not injected: check your FXML file 'scene.fxml'.";
        assert units != null : "fx:id=\"units\" was not injected: check your FXML file 'scene.fxml'.";
        
        generate = new GenerarClaves(this);
        mainWindow = new MainWindow(this);
        calculate = new CalculateNNC();
    }    
    
    /**
     * Método usado cuando se pulsa el boton de generar de manera automática una clave   
     * @param event 
     */
    //poner un tooltip encima de bits_clave_automatica que diga que se van a quitar puntos comas y espacios
    public void processAutomaticGeneration(ActionEvent event) {       
        String keySize = this.bits_clave_automatica.getText(); 
        boolean isSameSize = this.sameSizePrimes.isSelected();
         
        this.RSA = this.generate.autoRSAkeys(keySize, isSameSize);        
    }
    /**
     * Método usado cuando se pulsa el boton de generarLog de NNC
     * @param event 
     */
    public void generateNNC(ActionEvent event) {
        calculate.setRSA(this.RSA);
        
        calculate.calculateNNC();        
    }
    
    /**
     * Método usado para borrar toda la informacion de la pantalla principal
     * @param event 
     */
    public void delete(ActionEvent event) {
        this.mainWindow.delete();
    }   
    
    
    /**
     * Establece el formato de unidades a Decimal
     * @param event 
     */
    //hacer que ponga por pantalla dec
    public void unitsDecimal(ActionEvent event) {
        //this.Decimal.;  este y el de hexadecimal ponerlo a negrita
        this.generate.setUnits(10);  
        this.calculate.setUnits(10);
        //cambiarlo y hacerlo con un CSS
        this.mainWindow.changeUnits("dec", units);
    }
    
    /**
     * Establece el formato de unidades a Hexadecimal
     * @param event 
     */
    //hacer que ponga por pantalla hex
    public void unitsHexadecimal(ActionEvent event) {
        this.generate.setUnits(16);
        this.calculate.setUnits(16);
        this.mainWindow.changeUnits("hex", units);
    }
     
     
     
     

    public TextField getPrimo_P() {
        return primo_P;
    }

    public TextField getBits_primo_P() {
        return bits_primo_P;
    }

    public TextField getPrimo_Q() {
        return primo_Q;
    }

    public TextField getBits_primo_Q() {
        return bits_primo_Q;
    }

    public TextField getClave_Privada() {
        return clave_Privada;
    }

    public TextField getBits_clave_Privada() {
        return bits_clave_Privada;
    }

    public TextField getModulo_N() {
        return modulo_N;
    }

    public TextField getBits_modulo_N() {
        return bits_modulo_N;
    }

    public TextField getClave_Publica() {
        return clave_Publica;
    }

    public TextField getBits_clave_Publica() {
        return bits_clave_Publica;
    }

    public TextField getNum_claves_parejas() {
        return num_claves_parejas;
    }

    public TextArea getClaves_parejas() {
        return claves_parejas;
    }

    public Label getEstado() {
        return estado;
    }

    public TextField getIteraciones_primalidad() {
        return iteraciones_primalidad;
    }

    public TextField getEsPrimo_P() {
        return esPrimo_P;
    }

    public TextField getEsPrimo_Q() {
        return esPrimo_Q;
    }

    public TextField getTiempo_primalidad() {
        return tiempo_primalidad;
    }

    public TextField getBits_clave_automatica() {
        return bits_clave_automatica;
    }

    public TextField getTiempo_clave_automatica() {
        return tiempo_clave_automatica;
    }

    public Button getGenerar_automatica() {
        return generar_automatica;
    }

    public CheckBox getSameSizePrimes() {
        return sameSizePrimes;
    }

    public TextField getNum_mensajes_noCifrables() {
        return num_mensajes_noCifrables;
    }

    public Button getGenerar_log_nnc() {
        return generar_log_nnc;
    }

    public Button getBorrar() {
        return borrar;
    }

      
     
     
     
}
/*


  fxButton.setDisable(true);

    
     * Called when the FX Button is fired. T
     *
     * @param event the action event
     
    @FXML
    void handleButtonAction(ActionEvent event) {
        if (enabled) {
        
            enabled = false;
            fxButton.setText("Enable Swing Button");
            fxButton.getTooltip().setText("Click this button to enable the Swing button");
        } else {
     
            enabled = true;
            fxButton.setText("Disable Swing Button");
            fxButton.getTooltip().setText("Click this button to disable the Swing button");
        }
    }


    @FXML 
    private Label success;

    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(3000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }

en el scen builder el tooltip es para cuando dejas el raton encima te sale un texto
*/