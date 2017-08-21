package anticlash;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 *
 * @author bruno
 */
public class LandingPageController implements Initializable {

    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane background;
    @FXML
    private ImageView clockImage;
    @FXML
    private Text midText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1500), clockImage);
        RotateTransition rotateTransition1 = new RotateTransition(Duration.millis(1500), clockImage);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2900), midText);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(3);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
        rotateTransition.setByAngle(30);
        rotateTransition1.setByAngle(-30);
        rotateTransition.setCycleCount(2);
        rotateTransition1.setCycleCount(2);
        rotateTransition.setAutoReverse(true);
        rotateTransition1.setAutoReverse(true);
        rotateTransition.setRate(1.2);
        rotateTransition1.setRate(1.2);
        rotateTransition.play();
        rotateTransition.setOnFinished((e) -> rotateTransition1.play());
        rotateTransition1.setOnFinished((e) -> next());
    }

    // open control window
    private void next() {
        try {
            Parent secondPage = FXMLLoader.load(getClass().getResource("ControlPane.fxml"));
            Window here = sidePane.getParent().getScene().getWindow();

            Scene scene = new Scene(secondPage);
            scene.setFill(Color.TRANSPARENT);
            Stage window = (Stage) here;

            secondPage.setOnMouseDragged((event) -> {
                window.setX(event.getSceneX());
                window.setY(event.getSceneY());
            });

            window.setScene(scene);
        } catch (IOException ex) {
        } // error        

    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
