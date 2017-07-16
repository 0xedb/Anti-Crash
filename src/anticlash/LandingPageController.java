 
package anticlash;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private JFXButton next;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void next(ActionEvent event) throws IOException {
        Parent secondPage = FXMLLoader.load(getClass().getResource("ControlPane.fxml"));
        Scene scene = new Scene(secondPage);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

}
