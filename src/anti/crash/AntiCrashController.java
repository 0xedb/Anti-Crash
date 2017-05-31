
package anti.crash;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author bruno
 */
public class AntiCrashController implements Initializable {

    @FXML
    private AnchorPane landingPage;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startSplashScreen();
    }    
    
    
    private void startSplashScreen() {
        try {
            AnchorPane splashPane = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
            landingPage.getChildren().setAll(splashPane);
            
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(7), splashPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            fadeIn.play();
            
            
        } catch (IOException ex) {
            Logger.getLogger(AntiCrash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
