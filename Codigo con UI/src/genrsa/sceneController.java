/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genrsa;

import Cyclic.CyclicController;
import DeCipher.DeCipherController;
import Factorize.FactorizeController;
import Imprimir.MainWindow;
import Metodos.CheckPrimes;
import Metodos.GenerarClaves;
import Metodos.ManageKey;
import Model.ComponentesRSA;
import Paradox.ParadoxController;
import Sign.SignController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;


/**
 *
 * @author rdiazarr
 */
public class SceneController {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="genManBttn"
    private Button genManBttn; // Value injected by FXMLLoader

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

    @FXML // fx:id="sameSizePrimes"
    private CheckBox sameSizePrimes; // Value injected by FXMLLoader

    @FXML // fx:id="num_mensajes_noCifrables"
    private TextField num_mensajes_noCifrables; // Value injected by FXMLLoader
    
    @FXML // fx:id="unitsP"
    private Label unitsP; // Value injected by FXMLLoader
    
    @FXML // fx:id="unitsQ"
    private Label unitsQ; // Value injected by FXMLLoader
    
    @FXML // fx:id="unitsD"
    private Label unitsD; // Value injected by FXMLLoader
    
    @FXML // fx:id="unitsN"
    private Label unitsN; // Value injected by FXMLLoader
    
    @FXML // fx:id="unitsE"
    private Label unitsE; // Value injected by FXMLLoader    
    
    @FXML // fx:id="logNNCbttn"
    private Button logNNCbttn; // Value injected by FXMLLoader
    
    @FXML // fx:id="saveKeyMenuI"
    private MenuItem saveKeyMenuI; // Value injected by FXMLLoader
    
    @FXML // fx:id="DeCipherMenuI"
    private MenuItem DeCipherMenuI; // Value injected by FXMLLoader
        
    @FXML // fx:id="SingMenuI"
    private MenuItem SingMenuI; // Value injected by FXMLLoader
         
    private int radix;
        
    private ComponentesRSA RSA;
    
    private GenerarClaves generate;
        
    private MainWindow mainWindow;
    
    private CheckPrimes checkPrimes;
    
    private ManageKey manageKey;

    
    
    
    /**
     * Initializes the controller class.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //algo asi para controlar que no haya errores
                
        assert genManBttn != null : "fx:id=\"genManBttn\" was not injected: check your FXML file 'scene.fxml'.";
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
        assert iteraciones_primalidad != null : "fx:id=\"iteraciones_primalidad\" was not injected: check your FXML file 'scene.fxml'.";
        assert esPrimo_P != null : "fx:id=\"esPrimo_P\" was not injected: check your FXML file 'scene.fxml'.";
        assert esPrimo_Q != null : "fx:id=\"esPrimo_Q\" was not injected: check your FXML file 'scene.fxml'.";
        assert tiempo_primalidad != null : "fx:id=\"tiempo_primalidad\" was not injected: check your FXML file 'scene.fxml'.";
        assert bits_clave_automatica != null : "fx:id=\"bits_clave_automatica\" was not injected: check your FXML file 'scene.fxml'.";
        assert tiempo_clave_automatica != null : "fx:id=\"tiempo_clave_automatica\" was not injected: check your FXML file 'scene.fxml'.";
        assert sameSizePrimes != null : "fx:id=\"sameSizePrimes\" was not injected: check your FXML file 'scene.fxml'.";
        assert num_mensajes_noCifrables != null : "fx:id=\"num_mensajes_noCifrables\" was not injected: check your FXML file 'scene.fxml'.";
        assert unitsP != null : "fx:id=\"unitsP\" was not injected: check your FXML file 'scene.fxml'.";
        assert unitsQ != null : "fx:id=\"unitsQ\" was not injected: check your FXML file 'scene.fxml'.";
        assert unitsD != null : "fx:id=\"unitsD\" was not injected: check your FXML file 'scene.fxml'.";
        assert unitsN != null : "fx:id=\"unitsN\" was not injected: check your FXML file 'scene.fxml'.";
        assert unitsE != null : "fx:id=\"unitsE\" was not injected: check your FXML file 'scene.fxml'.";
        assert logNNCbttn != null : "fx:id=\"logNNCbttn\" was not injected: check your FXML file 'escena.fxml'.";        
        assert saveKeyMenuI != null : "fx:id=\"saveKeyMenuI\" was not injected: check your FXML file 'escena.fxml'.";
        assert DeCipherMenuI != null : "fx:id=\"DeCipherMenuI\" was not injected: check your FXML file 'escena.fxml'.";
        assert SingMenuI != null : "fx:id=\"SingMenuI\" was not injected: check your FXML file 'escena.fxml'.";

        
        radix = 10;
        
        generate = new GenerarClaves(this);
        mainWindow = new MainWindow(this);
        checkPrimes = new CheckPrimes(this);
        manageKey = new ManageKey();
        
        this.disableButtons();      
        this.configureFocus();
        //para poner el foco en originalData
        Platform.runLater(primo_P::requestFocus);
    }    
    
    /**
     * Método usado cuando se pulsa el boton de generar de manera automática una clave   
     * @param event 
     */
    public void processAutomaticGeneration(ActionEvent event) {       
        String keySize = this.bits_clave_automatica.getText(); 
        boolean isSameSize = this.sameSizePrimes.isSelected();
         
        this.RSA = this.generate.autoRSAkeys(keySize, isSameSize);     
        this.disableButtons();
        
    }
    
    /**
     * Método usado cuando se pulsa enter al meter los bits de generar de manera automática una clave   
     * @param keyEvent
     */
    public void processAutomaticGeneration2(KeyEvent keyEvent) {    
        if (keyEvent.getCode() == KeyCode.ENTER) {            
            processAutomaticGeneration(new ActionEvent());
        }
               
    }
    
    /**
     * Método usado cuando se pulsa el boton de generar de manera manual una clave   
     * @param event 
     */
    public void processManualGeneration(ActionEvent event) {       
        String primeP = this.primo_P.getText(); 
        String primeQ = this.primo_Q.getText(); 
        String publicKey = this.clave_Publica.getText(); 
        
        this.RSA = this.generate.manualRSAkeys(primeP, primeQ, publicKey);
        this.disableButtons();
    }
    
    /**
     * Método usado cuando se pulsa enter al meter los primos p y q o la clave pública   
     * @param keyEvent
     */
    public void processManualGeneration2(KeyEvent keyEvent) {    
        if (keyEvent.getCode() == KeyCode.ENTER) {            
            this.processManualGeneration(new ActionEvent());
        }
               
    }
    
    
    /**
     * Método usado cuando se pulsa el boton de generarLog de NNC
     * @param event 
     */
    public void generateNNC(ActionEvent event) {
       this.manageKey.saveLogNNC(this.unitsP, this.RSA);
    }
    
    /**
     * Método usado para borrar toda la informacion de la pantalla principal
     * @param event 
     */
    public void delete(ActionEvent event) {
        this.mainWindow.delete();
        this.RSA = null;
        this.disableButtons();
    }   
    
    /**
     * Método usado para abrir una clave previamente guardada
     * @param event 
     */
    public void openKey(ActionEvent event)  {
        String[] keys;
        
        keys = this.manageKey.open(this.unitsP);
        
        if (keys != null){
            
            if (StringUtils.equals(keys[3], "Decimal")){
                this.unitsDecimal(event);
            } else {
                this.unitsHexadecimal(event);
            }
            
            this.RSA = this.generate.manualRSAkeys(keys[0], keys[1], keys[2]);
        }//el else ya se ha tenido en cuenta en el interior de manageKey.open   
        
        this.disableButtons();
    }
    
    /**
     * Método usado para guardar una clave previamente generada
     * @param event 
     */
    public void saveKey(ActionEvent event)  {                
        this.manageKey.saveKey(this.unitsP, this.RSA);        
    }
    
    
    /**
     * Establece el formato de unidades a Decimal
     * @param event 
     */
    public void unitsDecimal(ActionEvent event) {
        this.radix = 10;
        this.generate.setUnits(10);  
        this.manageKey.setUnits(10);
        this.checkPrimes.setUnits(10);
        
        this.mainWindow.changeUnits("dec");
        this.mainWindow.delete();
        this.RSA = null;        
        this.disableButtons();
    }
    
    /**
     * Establece el formato de unidades a Hexadecimal
     * @param event 
     */
    public void unitsHexadecimal(ActionEvent event) {
        this.radix = 16;
        this.generate.setUnits(16);
        this.manageKey.setUnits(16);
        this.checkPrimes.setUnits(16);
        this.mainWindow.changeUnits("hex");
        this.mainWindow.delete();
        this.RSA = null;
        this.disableButtons();
    }
     
     
    /**
     * Comprueba la primalidad de P y Q por el metodo de Miller Rabin
     * @param event 
     */
    public void primalityMiller(ActionEvent event) {
        boolean isMiller = true;
        this.checkPrimes.check(this.primo_P.getText(), this.primo_Q.getText(), 
                                this.iteraciones_primalidad.getText(), isMiller);
    }
     
    /**
     * Comprueba la primalidad de P y Q por el metodo de Fermat
     * @param event 
     */
    public void primalityFermat(ActionEvent event) {
        boolean isMiller = false;
        this.checkPrimes.check(this.primo_P.getText(), this.primo_Q.getText(), 
                                this.iteraciones_primalidad.getText(), isMiller);
    }
     
    
 
    
    /**
     * 
     * @param event 
     */
    public void Factorize(ActionEvent event) {          
        Stage stage;
        FXMLLoader fxmlLoader;
        Parent root;
        
        try{              
            stage= new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/Factorize/Factorize.fxml"));
            root = fxmlLoader.load();
        
            FactorizeController factorController = fxmlLoader.<FactorizeController>getController();
            factorController.setRadix(this.radix);
            
            if (this.RSA != null){
                 factorController.getModulus().setText(this.RSA.getN().toString(this.radix).toUpperCase());
            }          
            
            
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.unitsD.getScene().getWindow());
            stage.setScene(scene);
            stage.show();            
        
        } catch (IOException ex) {
            //poner mensaje de error;
        }
                
    }
    
    /**
     * 
     * @param event 
     */
    public void Cyclic(ActionEvent event) {          
        Stage stage;
        FXMLLoader fxmlLoader;
        Parent root;
        
        try{              
            stage= new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/Cyclic/Cyclic.fxml"));
            root = fxmlLoader.load();
        
            CyclicController cyclicController = fxmlLoader.<CyclicController>getController();
            cyclicController.setRadix(this.radix);
            
            if (this.RSA != null){
                            
                cyclicController.getModulus().setText(this.RSA.getN().toString(this.radix).toUpperCase());            
                cyclicController.getExponent().setText(this.RSA.getE().toString(this.radix).toUpperCase());
            } else {
                cyclicController.setFirstTime(false);
            }
           
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.unitsD.getScene().getWindow());
            stage.setScene(scene);
            stage.show();            
        
        } catch (IOException ex) {
            //poner mensaje de error;
        }
                
    }
    
    /**
     * 
     * @param event 
     */
    public void Paradox (ActionEvent event) {          
        Stage stage;
        FXMLLoader fxmlLoader;
        Parent root;
        
        try{              
            stage= new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/Paradox/Paradox.fxml"));
            root = fxmlLoader.load();
        
            ParadoxController paradoxController = fxmlLoader.<ParadoxController>getController();
            paradoxController.setRadix(this.radix);
            
            
            if (this.RSA != null){
                paradoxController.getModulus().setText(this.RSA.getN().toString(this.radix).toUpperCase());
                paradoxController.getExponent().setText(this.RSA.getE().toString(this.radix).toUpperCase());
            } else {
                paradoxController.setFirstTime(false);
            }      
            
            
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.unitsD.getScene().getWindow());
            stage.setScene(scene);
            stage.show();            
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //poner mensaje de error;
        }
                
    }
    
    
    /**
     * 
     * @param event 
     */
    public void DeCipher (ActionEvent event) {          
        Stage stage;
        FXMLLoader fxmlLoader;
        Parent root;
        int iterator;
        
        try{      
            if (this.RSA != null){ //quitar esto y deshabilitar el boton
            stage= new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/DeCipher/DeCipher.fxml"));
            root = fxmlLoader.load();
        
            DeCipherController DeCipherCtr = fxmlLoader.<DeCipherController>getController();
            
            DeCipherCtr.setPubKeyBI(this.RSA.getE());
            DeCipherCtr.setModulusBI(this.RSA.getN());
            DeCipherCtr.setRadix(this.radix);
            
            //parte gráfica
            DeCipherCtr.getModulus().setText(this.RSA.getN().toString(this.radix).toUpperCase());
            DeCipherCtr.getPubKey().setText(this.RSA.getE().toString(this.radix).toUpperCase());
            
            DeCipherCtr.getModulus1().setText(this.RSA.getN().toString(this.radix).toUpperCase());
            //obtengo todas las claves privadas parejas
            String[] PPK = this.claves_parejas.getText().split("\n");
                        
            //las meto en el comboBox
            ComboBox comboBox = DeCipherCtr.getPrivKeys();
            comboBox.getItems().add("Clave Privada");
            comboBox.getItems().add(this.RSA.getD().toString(this.radix).toUpperCase());
            comboBox.getItems().add("Claves Privadas Parejas");
            for (iterator=0; iterator< PPK.length; iterator++){
                comboBox.getItems().add( PPK[iterator]);
            }            
            comboBox.setValue(this.RSA.getD().toString(this.radix).toUpperCase());
                                    
            Scene scene = new Scene(root);            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.unitsD.getScene().getWindow());
            stage.setScene(scene);
            stage.show();       
            }
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //poner mensaje de error;
        }
                
    }
    
    
    /**
     * 
     * @param event 
     */
    public void Sign (ActionEvent event) {          
        Stage stage;
        FXMLLoader fxmlLoader;
        Parent root;
        int iterator;
        
        try{      
            if (this.RSA != null){ //quitar esto y deshabilitar el boton
            stage= new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/Sign/Sign.fxml"));
            root = fxmlLoader.load();
        
            SignController SignCtr = fxmlLoader.<SignController>getController();
            
            SignCtr.setPubKeyBI(this.RSA.getE());
            SignCtr.setModulusBI(this.RSA.getN());
            SignCtr.setRadix(this.radix);
            
            //parte gráfica
            SignCtr.getModulus().setText(this.RSA.getN().toString(this.radix).toUpperCase());
            SignCtr.getPubKey().setText(this.RSA.getE().toString(this.radix).toUpperCase());
            
            SignCtr.getModulus1().setText(this.RSA.getN().toString(this.radix).toUpperCase());
            //obtengo todas las claves privadas parejas
            String[] PPK = this.claves_parejas.getText().split("\n");
                        
            //las meto en el comboBox
            ComboBox comboBox = SignCtr.getPrivKeys();
            comboBox.getItems().add("Clave Privada");
            comboBox.getItems().add(this.RSA.getD().toString(this.radix).toUpperCase());
            comboBox.getItems().add("Claves Privadas Parejas");
            for (iterator=0; iterator< PPK.length; iterator++){
                comboBox.getItems().add( PPK[iterator]);
            }            
            comboBox.setValue(this.RSA.getD().toString(this.radix).toUpperCase());
            
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.unitsD.getScene().getWindow());
            stage.setScene(scene);
            stage.show();       
            }
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //poner mensaje de error;
        }
                
    }
        
    /**
     * Cierra todo el programa
     * @param event 
     */
    public void exitApplication(ActionEvent event) {
        System.exit(0);
    }
    
    
    /**
     * Método usado para deshabilitar ciertos botones cuando
     * no hay una clave RSA generada
     */
    public void disableButtons() {   
        if (this.RSA == null) {
            this.logNNCbttn.setDisable(true);
            this.saveKeyMenuI.setDisable(true);
            this.DeCipherMenuI.setDisable(true);
            this.SingMenuI.setDisable(true);
        } else {
             this.logNNCbttn.setDisable(false);
             this.saveKeyMenuI.setDisable(false);
             this.DeCipherMenuI.setDisable(false);
             this.SingMenuI.setDisable(false);
        }
        
    }
    
    /**
     * Método usado para evitar que se puedan focalizar
     * ciertos nodos de la ventana principal
     */
    private void configureFocus() {
        this.bits_primo_P.setFocusTraversable(false);
        this.bits_primo_Q.setFocusTraversable(false);
        this.clave_Privada.setFocusTraversable(false);
        this.bits_clave_Privada.setFocusTraversable(false);
        this.modulo_N.setFocusTraversable(false);
        this.bits_modulo_N.setFocusTraversable(false);
        this.bits_clave_Publica.setFocusTraversable(false);
        this.num_claves_parejas.setFocusTraversable(false);
        this.claves_parejas.setFocusTraversable(false);
        this.esPrimo_P.setFocusTraversable(false);
        this.esPrimo_Q.setFocusTraversable(false);
        this.tiempo_primalidad.setFocusTraversable(false);
        this.tiempo_clave_automatica.setFocusTraversable(false);
        this.num_mensajes_noCifrables.setFocusTraversable(false);
    }
    
    
    
    /**
     * Método usado cuando se introduce el primo P de forma manual,
     * para mostrar su numero de bits
     * @param keyEvent
     */
    public void bitsP(KeyEvent keyEvent) {    
        String primeP = this.primo_P.getText(); 
        
        this.generate.numberToBits(primeP, this.bits_primo_P);                
    }
    
    /**
     * Método usado cuando se introduce el primo Q de forma manual,
     * para mostrar su numero de bits
     * @param keyEvent
     */
    public void bitsQ(KeyEvent keyEvent) {    
        String primeQ = this.primo_Q.getText(); 
        
        this.generate.numberToBits(primeQ, this.bits_primo_Q);
    }
    
    /**
     * Método usado cuando se introduce la clave publica de forma manual,
     * para mostrar su numero de bits
     * @param keyEvent
     */
    public void bitsPublicKey(KeyEvent keyEvent) {  
        String publicKey = this.clave_Publica.getText(); 
        
        this.generate.numberToBits(publicKey, this.bits_clave_Publica);
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

    public CheckBox getSameSizePrimes() {
        return sameSizePrimes;
    }

    public TextField getNum_mensajes_noCifrables() {
        return num_mensajes_noCifrables;
    }

    public Label getUnitsP() {
        return unitsP;
    }

    public Label getUnitsQ() {
        return unitsQ;
    }    

    public Label getUnitsD() {
        return unitsD;
    }    

    public Label getUnitsN() {
        return unitsN;
    }     

    public Label getUnitsE() {
        return unitsE;
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