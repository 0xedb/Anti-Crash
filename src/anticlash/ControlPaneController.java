package anticlash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
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

    private ArrayList<JFXCheckBox> chb = new ArrayList<>();
    private HashMap<String, Course> courseMap = new HashMap<>();
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private JFXButton add;
    @FXML
    private JFXTextField courseID;

    @FXML
    private JFXTextField courseTitle;
    @FXML
    private JFXToggleButton required;

    String course, id;
    LocalTime sTime, eTime;

    ArrayList<Course> mondayList = new ArrayList<>();
    ArrayList<Course> tuesdayList = new ArrayList<>();
    ArrayList<Course> wednesdayList = new ArrayList<>();
    ArrayList<Course> thursdayList = new ArrayList<>();
    ArrayList<Course> fridayList = new ArrayList<>();
    ArrayList<Course> saturdayList = new ArrayList<>();
    ArrayList<Course> sundayList = new ArrayList<>();
    private ObservableList<Course> courseList = FXCollections.observableArrayList();
    @FXML
    private JFXButton clash;
    @FXML
    private JFXListView<Course> lv;
    @FXML
    private JFXButton remove;

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

        lv.setCellFactory((ListView<Course> param) -> new CourseCell());

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
        ArrayList<String> days = days();
        if (isInfoValid()) {
            Course value = new Course(course, id, sTime, eTime, required.isSelected(), days);
            courseMap.put(id, value);
            putCourse(value, days);
            courseList.add(value);
            lv.setItems(courseList);
            courseTitle.clear();
            courseID.clear();

        }
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

    private boolean isInfoValid() {
        return isCourseValid() && isTimeValid() && isDayValid();
    }

    private boolean isCourseValid() {
        id = courseID.getText().trim();
        course = courseTitle.getText().trim();
        return course.length() > 0
                && id.length() > 0
                && (courseMap == null || !courseMap.containsKey(id));

    }

    private boolean isTimeValid() {
        sTime = startTime.getValue();
        eTime = endTime.getValue();
        return ((sTime != null) && (eTime != null)) && sTime.isBefore(eTime);
    }

    private boolean isDayValid() {
        return mon.isSelected() || tue.isSelected()
                || wed.isSelected() || thur.isSelected()
                || fri.isSelected() || sat.isSelected()
                || sun.isSelected();
    }

    private ArrayList<String> days() {
        ArrayList<String> days = new ArrayList<>();
        for (JFXCheckBox a : chb) {
            if (a.isSelected()) {
                days.add(a.getText());
            }
        }
        return days;
    }

    private void putCourse(Course subject, ArrayList<String> array) {
        for (String w : array) {
            switch (w) {
                case "Mon":
                    mondayList.add(subject);
                    break;
                case "Tue":
                    tuesdayList.add(subject);
                    break;
                case "Wed":
                    tuesdayList.add(subject);
                    break;
                case "Thur":
                    tuesdayList.add(subject);
                    break;
                case "Fri":
                    tuesdayList.add(subject);
                    break;
                case "Sat":
                    tuesdayList.add(subject);
                    break;
                case "Sun":
                    tuesdayList.add(subject);
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        Course selected = lv.getSelectionModel().getSelectedItem();
        courseMap.remove(selected.getCourseId());
        courseList.remove(selected);
        uncheckAll();
    }

    @FXML
    private void clash(ActionEvent event) {

    }

}
