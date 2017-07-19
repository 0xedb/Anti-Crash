package anticlash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;

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
    @FXML
    private JFXTextField courseID;

    private HashMap<String, Course> courseMap;
    @FXML
    private JFXTextField courseTitle;
    @FXML
    private JFXToggleButton required;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chb.add(mon);
        chb.add(tue);
        chb.add(wed);
        chb.add(thur);
        chb.add(fri);
        chb.add(sat);
        chb.add(sun);

        courseID.setTooltip(new Tooltip("Each course MUST have a UNIQUE ID"));
    }

    @FXML
    private void selectAll(ActionEvent event) {
        if (weekday.isSelected() || weekend.isSelected()) {
            weekday.setSelected(false);
            weekend.setSelected(false);
        }
        if (daily.isSelected()) {
            checkeAll();
            daily.setSelected(false);
        } else {
            uncheckAll();
        }
    }

    @FXML
    private void selectWeekday(ActionEvent event) {
        if (daily.isSelected() || weekend.isSelected()) {
            daily.setSelected(false);
            weekend.setSelected(false);
        }
        if (weekday.isSelected()) {
            checkeAll();
            weekday.setSelected(false);
            sat.setSelected(false);
            sun.setSelected(false);
        } else {
            uncheckAll();
            sat.setSelected(false);
            sun.setSelected(false);
        }
    }

    @FXML
    private void selectWeekend(ActionEvent event) {
        if (daily.isSelected() || weekday.isSelected()) {
            daily.setSelected(false);
            weekday.setSelected(false);
        }
        if (weekend.isSelected()) {
            uncheckAll();
            weekend.setSelected(false);
            sat.setSelected(true);
            sun.setSelected(true);
        } else {
            uncheckAll();
        }

    }

    @FXML
    private void add(ActionEvent event) {
        //System.out.println(courseTitle.getText().length());
        System.out.println(checkRequiredFields());
    }

    private void checkeAll() {
        for (JFXCheckBox a : chb) {
            a.setSelected(true);
        }
    }

    private void uncheckAll() {
        for (JFXCheckBox a : chb) {
            a.setSelected(false);
        }
    }

    private boolean checkRequiredFields() {
        int count = 0;
        String course = courseTitle.getText().trim();
        if (course.length() > 0) {
            count++;
        }
        String id = courseID.getText().trim();
        if (id.length() > 0 && (courseMap == null || !courseMap.containsKey(id))) {
            count++;
        }

        if (mon.isSelected() || tue.isSelected() || wed.isSelected() || thur.isSelected()
                || fri.isSelected() || sat.isSelected() || sun.isSelected()) {
            count++;
        }
        LocalTime sTime = startTime.getValue();
        LocalTime eTime = endTime.getValue();
        if (!(sTime == null)) {
            if (!(eTime == null)) {
                if (sTime.isBefore(eTime)) {
                    count += 2;
                }
            }
        }

        return count == 5;
    }

}
