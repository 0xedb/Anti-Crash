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
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author bruno
 */
public class ControlPaneController implements Initializable {

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
    @FXML
    private JFXButton clash;
    @FXML
    private JFXButton more;
    @FXML
    private TilePane tp;
    @FXML
    private ScrollPane sp;

    private JFXCheckBox daily;
    private JFXCheckBox weekday;
    private JFXCheckBox weekend;
    private ArrayList<JFXCheckBox> chb = new ArrayList<>();
    private HashMap<String, Course> courseMap = new HashMap<>();
    BooleanProperty hasClashed = new SimpleBooleanProperty(false);

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

    ArrayList<Course> clashLedger = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mon.setTooltip(new Tooltip("Monday"));
        tue.setTooltip(new Tooltip("Tuesday"));
        wed.setTooltip(new Tooltip("Wednesday"));
        thur.setTooltip(new Tooltip("Thursday"));
        fri.setTooltip(new Tooltip("Friday"));
        sat.setTooltip(new Tooltip("Saturday"));
        sun.setTooltip(new Tooltip("Sunday"));

        chb.add(mon);
        chb.add(tue);
        chb.add(wed);
        chb.add(thur);
        chb.add(fri);
        chb.add(sat);
        chb.add(sun);

        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @FXML
    private void selectAll(ActionEvent event) {
        checkeAll();
    }

    @FXML
    private void selectWeekday(ActionEvent event) {
        checkeAll();
        sat.setSelected(false);
        sun.setSelected(false);

    }

    @FXML
    private void selectWeekend(ActionEvent event) {
        uncheckAll();
        sat.setSelected(true);
        sun.setSelected(true);

    }

    @FXML
    private void add(ActionEvent event) {
        ArrayList<String> days = days();
        if (isInfoValid()) {
            Course value = new Course(course, id, sTime, eTime, required.isSelected(), days);
            courseMap.put(id, value);
            putCourse(value, days);
            courseList.add(value);
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

    private void delete(ActionEvent event) {
        uncheckAll();
    }

    @FXML
    private void clash(ActionEvent event) {
        for (Course vv : mondayList) {
            System.out.println(vv.getCourseTitle() + "\t" + vv.getCourseId());
        }
    }

    private void addToDayList(ArrayList<Course> list, Course c) {
        if (list.isEmpty()) {
            list.add(c);
            return;
        }
        Course current = list.get(0);
        if (!(c.getEndTime().isAfter(current.getStartTime()))) {
            list.add(0, c);
            return;
        } else if (!(c.getStartTime().isBefore(current.getStartTime()))) {
            list.add(c);
            return;
        } else {
            hasClashed.set(true);
            clashLedger.add(c);

        }

        if (hasClashed.get() == true) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Hellllllllllllllllllo");
            dialog.showAndWait();

        }

    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    static int i = 0;

    @FXML
    private void more(ActionEvent event) {
        Button butt = new Button(Integer.toString(++i * 100));
        butt.setRotate(-90);
        tp.getChildren().add(butt);
    }

}

//        mondayList.clear();wednesdayList.clear();
//        tuesdayList.clear();thursdayList.clear();
//        fridayList.clear();saturdayList.clear();
//        sundayList.clear();
