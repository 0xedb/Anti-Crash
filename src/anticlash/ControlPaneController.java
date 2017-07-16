 
package anticlash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author bruno
 */
public class ControlPaneController implements Initializable {

    @FXML
    private JFXCheckBox daily;
    @FXML
    private JFXCheckBox weekday;
    @FXML
    private JFXCheckBox weekend;
    @FXML
    private JFXCheckBox mon;
    @FXML
    private JFXCheckBox tue;
    @FXML
    private JFXCheckBox wed;
    @FXML
    private JFXCheckBox thur;
    @FXML
    private JFXCheckBox fri;
    @FXML
    private JFXCheckBox sat;
    @FXML
    private JFXCheckBox sun;
    
    ArrayList<JFXCheckBox> chb = new ArrayList<>();
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private JFXButton add;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chb.add(mon); chb.add(tue); chb.add(wed); chb.add(thur);
        chb.add(fri); chb.add(sat); chb.add(sun);
        
        
         
    }    

    @FXML
    private void selectAll(ActionEvent event) {
        if(daily.isSelected())
            disableAll(chb);
        else
            enableAll(chb);
    }
    
    private void disableAll(ArrayList<JFXCheckBox> a) {
        for(JFXCheckBox b : a) {
            b.setDisable(true);
            b.setSelected(false);
            weekday.setDisable(true);
            weekend.setDisable(true);
            
            weekday.setSelected(false);
            weekend.setSelected(false);
        }
    }
    
    private void enableAll(ArrayList<JFXCheckBox> a) {
        for(JFXCheckBox b : a) 
            b.setDisable(false);
        weekday.setDisable(false);
            weekend.setDisable(false);
    }

    @FXML
    private void selectWeekday(ActionEvent event) {
        
    }

    @FXML
    private void selectWeekend(ActionEvent event) {
        
    }

    @FXML
    private void add(ActionEvent event) {
        System.out.println(startTime.getValue());
    }
    
}
