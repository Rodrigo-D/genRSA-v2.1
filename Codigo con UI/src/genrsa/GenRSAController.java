/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genrsa;


import Paradox.ParadoxController;
import Cyclic.CyclicController;
import Factorize.FactorizeController;
import DeCipher.DeCipherController;
import Sign.SignController;
import Imprimir.MainWindow;
import Methods.*;
import Model.ComponentesRSA;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.commons.lang3.StringUtils;


/**
 * Controlador de la escena principal
 * @author rdiazarr
 */
public class GenRSAController {
    
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
    
    @FXML // fx:id="cBoxP"
    private ComboBox cBoxP; // Value injected by FXMLLoader

    @FXML // fx:id="primo_Q"
    private TextField primo_Q; // Value injected by FXMLLoader

    @FXML // fx:id="bits_primo_Q"
    private TextField bits_primo_Q; // Value injected by FXMLLoader
    
    @FXML // fx:id="cBoxQ"
    private ComboBox cBoxQ; // Value injected by FXMLLoader

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

    @FXML // fx:id="isPrime_P"
    private ImageView isPrime_P; // Value injected by FXMLLoader

    @FXML // fx:id="isPrime_Q"
    private ImageView isPrime_Q; // Value injected by FXMLLoader

    @FXML // fx:id="tiempo_primalidad"
    private TextField tiempo_primalidad; // Value injected by FXMLLoader

    @FXML // fx:id="bits_clave_automatica"
    private TextField bits_clave_automatica; // Value injected by FXMLLoader

    @FXML // fx:id="tiempo_clave_automatica"
    private TextField tiempo_clave_automatica; // Value injected by FXMLLoader

    @FXML // fx:id="sameSizePrimes"
    private CheckBox sameSizePrimes; // Value injected by FXMLLoader    
    
    @FXML // fx:id="tipicalPubKey"
    private CheckBox tipicalPubKey; // Value injected by FXMLLoader
    
    @FXML // fx:id="labelPubKey"
    private Label labelPubKey; // Value injected by FXMLLoader

    @FXML // fx:id="cantidadNNC"
    private TextField cantidadNNC; // Value injected by FXMLLoader
    
    @FXML // fx:id="progress"
    private ProgressIndicator progress; // Value injected by FXMLLoader
    
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
        
    @FXML // fx:id="SignMenuI"
    private MenuItem SignMenuI; // Value injected by FXMLLoader
    
    @FXML // fx:id="openKeyMenuI"
    private MenuItem openKeyMenuI; // Value injected by FXMLLoader
    
    @FXML // fx:id="manualMenuI"
    private MenuItem manualMenuI; // Value injected by FXMLLoader
    
    @FXML // fx:id="millerMenuI"
    private MenuItem millerMenuI; // Value injected by FXMLLoader
        
    @FXML // fx:id="fermatMenuI"
    private MenuItem fermatMenuI; // Value injected by FXMLLoader
            
    @FXML // fx:id="paradoxMenuI"
    private MenuItem paradoxMenuI; // Value injected by FXMLLoader
                
    @FXML // fx:id="cyclicMenuI"
    private MenuItem cyclicMenuI; // Value injected by FXMLLoader
    
    @FXML // fx:id="factorizeMenuI"
    private MenuItem factorizeMenuI; // Value injected by FXMLLoader
    
    @FXML // fx:id="Decimal"
    private RadioMenuItem Decimal; // Value injected by FXMLLoader
    
    @FXML // fx:id="Hexadecimal"
    private RadioMenuItem Hexadecimal; // Value injected by FXMLLoader
    
    @FXML // fx:id="limpiarDatosBttn"
    private Button limpiarDatosBttn; // Value injected by FXMLLoader
    
    @FXML // fx:id="limpiarDatos1Bttn"
    private Button limpiarDatos1Bttn; // Value injected by FXMLLoader
    
    @FXML // fx:id="autoGenerarBttn"
    private Button autoGenerarBttn; // Value injected by FXMLLoader    
    
    @FXML // fx:id="deleteKeyMenu"
    private MenuItem deleteKeyMenu; // Value injected by FXMLLoader
    
    @FXML // fx:id="autoGenerarMenu"
    private MenuItem autoGenerarMenu; // Value injected by FXMLLoader
    
    
         
    private int radix;
    
    private InitCBox initCboxes;
        
    private ComponentesRSA RSA;
    
    private GenerarClaves generate;
        
    private MainWindow mainWindow;
    
    private CheckPrimes checkPrimes;
    
    private ManageKey manageKey;
    
    private boolean startLogNNC;
    
    private Utilidades utilidades;

    
    
    
    /**
     * Initializes the controller class.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
        assert genManBttn != null : "fx:id=\"genManBttn\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert primo_P != null : "fx:id=\"primo_P\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert bits_primo_P != null : "fx:id=\"bits_primo_P\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert cBoxP != null : "fx:id=\"cBoxP\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert primo_Q != null : "fx:id=\"primo_Q\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert bits_primo_Q != null : "fx:id=\"bits_primo_Q\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert cBoxQ != null : "fx:id=\"cBoxQ\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert clave_Privada != null : "fx:id=\"clave_Privada\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert bits_clave_Privada != null : "fx:id=\"bits_clave_Privada\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert modulo_N != null : "fx:id=\"modulo_N\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert bits_modulo_N != null : "fx:id=\"bits_modulo_N\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert clave_Publica != null : "fx:id=\"clave_Publica\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert bits_clave_Publica != null : "fx:id=\"bits_clave_Publica\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert num_claves_parejas != null : "fx:id=\"num_claves_parejas\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert claves_parejas != null : "fx:id=\"claves_parejas\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert iteraciones_primalidad != null : "fx:id=\"iteraciones_primalidad\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert isPrime_P != null : "fx:id=\"esPrimo_P\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert isPrime_Q != null : "fx:id=\"esPrimo_Q\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert tiempo_primalidad != null : "fx:id=\"tiempo_primalidad\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert bits_clave_automatica != null : "fx:id=\"bits_clave_automatica\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert tiempo_clave_automatica != null : "fx:id=\"tiempo_clave_automatica\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert sameSizePrimes != null : "fx:id=\"sameSizePrimes\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert tipicalPubKey != null : "fx:id=\"tipicalPubKey\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert cantidadNNC != null : "fx:id=\"cantidadNNC\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert progress != null : "fx:id=\"progress\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert unitsP != null : "fx:id=\"unitsP\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert unitsQ != null : "fx:id=\"unitsQ\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert unitsD != null : "fx:id=\"unitsD\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert unitsN != null : "fx:id=\"unitsN\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert unitsE != null : "fx:id=\"unitsE\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert logNNCbttn != null : "fx:id=\"logNNCbttn\" was not injected: check your FXML file 'genRSA.fxml'.";        
        assert saveKeyMenuI != null : "fx:id=\"saveKeyMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert DeCipherMenuI != null : "fx:id=\"DeCipherMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert SignMenuI != null : "fx:id=\"SignMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert openKeyMenuI != null : "fx:id=\"openKeyMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";        
        assert manualMenuI != null : "fx:id=\"manualMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert millerMenuI != null : "fx:id=\"millerMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert fermatMenuI != null : "fx:id=\"fermatMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert paradoxMenuI != null : "fx:id=\"paradoxMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert cyclicMenuI != null : "fx:id=\"cyclicMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert factorizeMenuI != null : "fx:id=\"factorizeMenuI\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert Decimal != null : "fx:id=\"Decimal\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert Hexadecimal != null : "fx:id=\"Hexadecimal\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert limpiarDatosBttn != null : "fx:id=\"limpiarDatosBttn\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert limpiarDatos1Bttn != null : "fx:id=\"limpiarDatos1Bttn\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert autoGenerarBttn != null : "fx:id=\"autoGenerarBttn\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert deleteKeyMenu != null : "fx:id=\"deleteKeyMenu\" was not injected: check your FXML file 'genRSA.fxml'.";
        assert autoGenerarMenu != null : "fx:id=\"autoGenerarMenu\" was not injected: check your FXML file 'genRSA.fxml'.";
        
        isPrime_P.setImage(new Image(GenRSAController.class.getResourceAsStream("/allImages/interrogation.png")));
        isPrime_Q.setImage(new Image(GenRSAController.class.getResourceAsStream("/allImages/interrogation.png")));
        
        radix = 10;
        initCboxes = new InitCBox();
        initCboxes.initCboxDec(cBoxP, cBoxQ);
        
        generate = new GenerarClaves(this);
        mainWindow = new MainWindow(this);
        checkPrimes = new CheckPrimes(this);
        manageKey = new ManageKey();
        
        utilidades = new Utilidades();
        startLogNNC = true;
        
        this.disableButtons();      
        this.configureFocus();
        
        //para poner el foco en originalData
        Platform.runLater(bits_clave_automatica::requestFocus);
    }    
    
    /**
     * Método usado cuando se pulsa el boton de generar de manera automática una clave   
     * @param event 
     */
    public void processAutomaticGeneration(ActionEvent event) {      
        
        Task CAstart= new Task() {
            @Override
            protected Object call() throws Exception {
                String keySize = bits_clave_automatica.getText(); 
                boolean isSameSize = sameSizePrimes.isSelected();
                boolean isTipicalPubKey = tipicalPubKey.isSelected();

                progress.setVisible(true);                
                Platform.runLater(() ->disableOnProgress(true));
                
                RSA = generate.autoRSAkeys(keySize, isSameSize, isTipicalPubKey);
                
                Platform.runLater(() -> disableOnProgress(false));
                progress.setVisible(false);
                
                disableButtons();                
                return null;
            }
        };
        
        new Thread(CAstart).start();    
        
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
        
        if (this.startLogNNC) {
            
            //todo se hace antes del thread porque si no nose podria manejar la ventana
            //para que se decidiera donde se guarda el archivo.
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("HTML files", "*.html");    
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory( new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(extensionFilter);
            fileChooser.setTitle("Seleccionar directorio donde guardar el log");        
            fileChooser.setInitialFileName("LogNNC genRSA");
            File logNNCFile = fileChooser.showSaveDialog(labelPubKey.getScene().getWindow());            
            
            
            Task CAstart= new Task() {
                @Override
                protected Object call() throws Exception {
                    startLogNNC = false;
                    progress.setVisible(true);                
                    Platform.runLater(() ->{
                        disableOnProgress(true);
                        configureLogStop(true);
                    });


                    manageKey.saveLogNNC(unitsP, RSA, logNNCFile);

                    Platform.runLater(() ->{
                        disableOnProgress(false);
                        configureLogStop(false);
                    });
                    progress.setVisible(false);
                    startLogNNC = true;
                    return null;
                }
            };

            new Thread(CAstart).start();  
            
        } else {
            this.manageKey.setLogCancelled();
            this.startLogNNC = true;
        }
    }
    
    /**
     * Método usado para cambiar el boton de pausa/generacion del log de NNC
     * @param stop
     */
    public void configureLogStop(final boolean stop) {   
        
        if (stop) {            
            this.logNNCbttn.setDisable(false);
            this.logNNCbttn.setText("  Parar  Log  ");
        } else {
            this.logNNCbttn.setText("Generar Log");
        }        
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
        this.initCboxes.initCboxDec(this.cBoxP, this.cBoxQ);
        this.labelPubKey.setText("Clave pública = 65537");        
        this.tipicalPubKey.setSelected(false);
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
        this.initCboxes.initCboxHex(this.cBoxP, this.cBoxQ);
        this.labelPubKey.setText("Clave pública = 10001");        
        this.tipicalPubKey.setSelected(false);
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
     * Método usado para cargar la ventana de Factorización
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
            stage.getIcons().add(new Image(GenRSAController.class.getResourceAsStream("/allImages/genRSA.png")));
            stage.setTitle("genRSA - Ataque por Factorización");  
            stage.setScene(scene);
            stage.show();        
            
            stage.setOnCloseRequest(closeEvent -> {
                factorController.getFactorizeAttack().setIsCancelled(true);
            });
        
        } catch (IOException ex) {
            //no pongo mensaje de error, porque no se puede dar el caso
        }
                
    }
    
    /**
     * Método usado para cargar la ventana de ataque ciclico
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
            stage.getIcons().add(new Image(GenRSAController.class.getResourceAsStream("/allImages/genRSA.png")));
            stage.setTitle("genRSA - Ataque Cíclico"); 
            stage.setScene(scene);
            stage.show(); 
            
            stage.setOnCloseRequest(closeEvent -> {
                cyclicController.getCyclicAtack().setIsCancelled(true);
            });
        
        } catch (IOException ex) {
            //no pongo mensaje de error, porque no se puede dar el caso
        }
                
    }
    
    /**
     * Método usado para cargar la ventana de ataque por la paradoja del cumpleaños
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
            stage.getIcons().add(new Image(GenRSAController.class.getResourceAsStream("/allImages/genRSA.png")));
            stage.setTitle("genRSA - Ataque por la Paradoja del Cumpleaños"); 
            stage.setScene(scene);
            stage.show();  
            
            stage.setOnCloseRequest(closeEvent -> {
                paradoxController.getParadoxAttack().setIsCancelled(true);
            });
        
        } catch (IOException ex) {
            //no pongo mensaje de error, porque no se puede dar el caso
        }
                
    }
    
    
    /**
     * Método usado para cargar la ventana de Cifra
     * @param event 
     */
    public void DeCipher (ActionEvent event) {          
        Stage stage;
        FXMLLoader fxmlLoader;
        Parent root;
        int iterator;
        
        try{      
            stage= new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/DeCipher/DeCipher.fxml"));
            root = fxmlLoader.load();
        
            DeCipherController DeCipherCtr = fxmlLoader.<DeCipherController>getController();
            
            DeCipherCtr.setPubKeyBI(this.RSA.getE());
            DeCipherCtr.setModulusBI(this.RSA.getN());
            DeCipherCtr.setRadix(this.radix);
            
            //parte gráfica
            DeCipherCtr.getModulus().setText(this.utilidades.putPoints(this.RSA.getN().toString(this.radix).toUpperCase(), this.radix));
            DeCipherCtr.getPubKey().setText(this.utilidades.putPoints(this.RSA.getE().toString(this.radix).toUpperCase(), this.radix));
            
            DeCipherCtr.getModulus1().setText(this.utilidades.putPoints(this.RSA.getN().toString(this.radix).toUpperCase(), this.radix));
            //obtengo todas las claves privadas parejas
            String[] PPK = this.claves_parejas.getText().split("\n");
                        
            //las meto en el comboBox
            ComboBox comboBox = DeCipherCtr.getPrivKeys();
            comboBox.getItems().add("Clave Privada");
            comboBox.getItems().add(this.utilidades.putPoints(this.RSA.getD().toString(this.radix).toUpperCase(), this.radix));
            comboBox.getItems().add("Claves Privadas Parejas");
            for (iterator=0; iterator< PPK.length; iterator++){
                comboBox.getItems().add( PPK[iterator]);
            }            
            comboBox.setValue(this.utilidades.putPoints(this.RSA.getD().toString(this.radix).toUpperCase(), this.radix));
            comboBox.setVisibleRowCount(7);
                                    
            Scene scene = new Scene(root);            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.unitsD.getScene().getWindow());            
            stage.getIcons().add(new Image(GenRSAController.class.getResourceAsStream("/allImages/genRSA.png")));
            stage.setTitle("genRSA - Cifrado y Descifrado"); 
            stage.setScene(scene);
            stage.show();       
            
        
        } catch (IOException ex) {
            //no pongo mensaje de error, porque no se puede dar el caso
        }
                
    }
    
    
    /**
     * Método usado para cargar la ventana de Firma
     * @param event 
     */
    public void Sign (ActionEvent event) {          
        Stage stage;
        FXMLLoader fxmlLoader;
        Parent root;
        int iterator;
        
        try{      
            stage= new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/Sign/Sign.fxml"));
            root = fxmlLoader.load();
        
            SignController SignCtr = fxmlLoader.<SignController>getController();
            
            SignCtr.setPubKeyBI(this.RSA.getE());
            SignCtr.setModulusBI(this.RSA.getN());
            SignCtr.setRadix(this.radix);
            
            //parte gráfica
            SignCtr.getModulus().setText(this.utilidades.putPoints(this.RSA.getN().toString(this.radix).toUpperCase(), this.radix));
            SignCtr.getPubKey().setText(this.utilidades.putPoints(this.RSA.getE().toString(this.radix).toUpperCase(), this.radix));
            
            SignCtr.getModulus1().setText(this.utilidades.putPoints(this.RSA.getN().toString(this.radix).toUpperCase(), this.radix));
            //obtengo todas las claves privadas parejas
            String[] PPK = this.claves_parejas.getText().split("\n");
            //quito la informacion acerca de los bits "--> XXbits"
                        
            //las meto en el comboBox
            ComboBox comboBox = SignCtr.getPrivKeys();
            comboBox.getItems().add("Clave Privada");
            comboBox.getItems().add(this.utilidades.putPoints(this.RSA.getD().toString(this.radix).toUpperCase(), this.radix));
            comboBox.getItems().add("Claves Privadas Parejas");
            for (iterator=0; iterator< PPK.length; iterator++){
                comboBox.getItems().add( PPK[iterator]);
            }            
            comboBox.setValue(this.utilidades.putPoints(this.RSA.getD().toString(this.radix).toUpperCase(), this.radix));
            comboBox.setVisibleRowCount(7);
            
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.unitsD.getScene().getWindow());            
            stage.getIcons().add(new Image(GenRSAController.class.getResourceAsStream("/allImages/genRSA.png")));
            stage.setTitle("genRSA - Firma y Validación"); 
            stage.setScene(scene);
            stage.show();       
            
        
        } catch (IOException ex) {
            //no pongo mensaje de error, porque no se puede dar el caso
        }
                
    }
        
    
    
    /**
     * Muestra por pantalla la información relativa a genRSA 
     * @param event 
     */
    public void aboutGenRSA(ActionEvent event) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Acerca de genRSA");
        Stage stage = (Stage) info.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(GenRSAController.class.getResourceAsStream("/allImages/info.png")));
        
        info.setHeaderText("genRSA - Generación de claves RSA v2.1");
        
        info.setContentText("GenRSA es un software de laboratorio para la generación \n"
                + "de claves RSA.\n"
                + "Este software tiene como fin que el alumno se familiarize con\n"
                + "el sistema de cifrado asimétrico de clave pública RSA. \n\n\n"
                + "Proyecto de fin de grado realizado por Rodrigo Díaz.\n"
                + "Tutor: Jorge Ramió Aguirre\n"
                + "Año 2017");
        
        info.showAndWait();
    }
    
    
    
    /**
     * Cierra todo el programa
     * @param event 
     */
    public void exitApplication(ActionEvent event) {
        //para pararlo en caso de que no haya terminado
        CalculateNNC.isCancelled=true;
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
            this.SignMenuI.setDisable(true);
        } else {
            this.logNNCbttn.setDisable(false);
            this.saveKeyMenuI.setDisable(false);
            this.DeCipherMenuI.setDisable(false);
            this.SignMenuI.setDisable(false);
        }        
    }
    
    /**
     * Método usado para deshabilitar ciertos botones cuando
     * se esta creando una clave de manera automática
     * @param disable
     */
    public void disableOnProgress(final boolean disable) {   
        
        this.primo_P.setDisable(disable);
        this.primo_Q.setDisable(disable);
        this.clave_Publica.setDisable(disable);
        this.bits_clave_automatica.setDisable(disable);
        this.logNNCbttn.setDisable(disable);
        this.genManBttn.setDisable(disable);
        this.saveKeyMenuI.setDisable(disable);
        this.openKeyMenuI.setDisable(disable);
        this.manualMenuI.setDisable(disable);
        this.DeCipherMenuI.setDisable(disable);
        this.SignMenuI.setDisable(disable);        
        this.millerMenuI.setDisable(disable);
        this.fermatMenuI.setDisable(disable);
        this.paradoxMenuI.setDisable(disable);
        this.cyclicMenuI.setDisable(disable);
        this.factorizeMenuI.setDisable(disable);
        this.Decimal.setDisable(disable);        
        this.Hexadecimal.setDisable(disable);
        this.limpiarDatosBttn.setDisable(disable);        
        this.limpiarDatos1Bttn.setDisable(disable);
        this.autoGenerarBttn.setDisable(disable);
        this.autoGenerarMenu.setDisable(disable);
        this.deleteKeyMenu.setDisable(disable);
        
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
        this.isPrime_P.setFocusTraversable(false);
        this.isPrime_Q.setFocusTraversable(false);
        this.tiempo_primalidad.setFocusTraversable(false);
        this.tiempo_clave_automatica.setFocusTraversable(false);
        this.cantidadNNC.setFocusTraversable(false);
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
    
    
    /**
     * Método usado cuando se selecciona un primo de la CBOX
     * para que se introduzca en el primo P
     * @param event
     */
    public void cBoxPselected(ActionEvent event) {  
        //para que no de errores cuando se esta modificando al pasar de dec to hex y viceversa
        if (!this.cBoxP.isDisable()){
            String prime = this.cBoxP.getValue().toString();
        
            if (!StringUtils.equals(prime, "Primos Seguros")) {
                this.primo_P.setText(prime);
            }
        }              
    }
    
    /**
     * Método usado cuando se selecciona un primo de la CBOX
     * para que se introduzca en el primo Q
     * @param event
     */
    public void cBoxQselected(ActionEvent event) {  
        //para que no de errores cuando se esta modificando al pasar de dec to hex y viceversa
        if (!this.cBoxQ.isDisable()){
            String prime = this.cBoxQ.getValue().toString();

            if (!StringUtils.equals(prime, "Primos Seguros")) {
                this.primo_Q.setText(prime);
            }
        }        
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

    public ImageView getIsPrime_P() {
        return isPrime_P;
    }

    public ImageView getIsPrime_Q() {
        return isPrime_Q;
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
            
    public CheckBox getTipicalPubKey() {
        return tipicalPubKey;
    }

    public TextField getCantidadNNC() {
        return cantidadNNC;
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