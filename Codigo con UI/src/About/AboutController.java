/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package About;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author rdiazarr
 */
public class AboutController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="imageAbout"
    private ImageView imageAbout; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert imageAbout != null : "fx:id=\"imageAbout\" was not injected: check your FXML file 'about.fxml'.";

        
        imageAbout.setImage(new Image(AboutController.class.getResourceAsStream("/allImages/UPM.png")));
    }
    
    
    @FXML
    void mailTo(ActionEvent event) throws URISyntaxException, IOException {
        
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:rodrigo.diaza@alumnos.upm.com?subject=genRSA");
                desktop.mail(mailto);
            }
        }

    }
    

    
    
}