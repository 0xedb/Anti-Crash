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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

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
    private ScrollPane scroll;
    @FXML
    private JFXListView<Label> monList;
    @FXML
    private AnchorPane sidePane;

    private final ObservableList<JFXCheckBox> chb = FXCollections
            .observableArrayList(); //stores all checkboxes

    private ObservableMap<String, Course> courseMap = FXCollections
            .observableHashMap();  // will store all courses

    // store courses for clashing to be done
    private static ArrayList<ArrayList<Course>> tmp = new ArrayList<>(7);

    private static BooleanProperty isTimeValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isDayValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isCourseTitleValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isCourseIdValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isCourseValid = new SimpleBooleanProperty(false);

    private static Course cCopy;

    @FXML
    private JFXListView<Label> tueList;
    @FXML
    private JFXListView<Label> wedList;
    @FXML
    private JFXListView<Label> thurList;
    @FXML
    private JFXListView<Label> friList;
    @FXML
    private JFXListView<Label> satList;
    @FXML
    private JFXListView<Label> sunList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // add all checkboxes to checkbox list
        chb.add(mon);
        chb.add(tue);
        chb.add(wed);
        chb.add(thur);
        chb.add(fri);
        chb.add(sat);
        chb.add(sun);

        // validate coursetitle  
        courseTitle.textProperty().addListener((observable, oldV, newV) -> {
            if (newV.trim().length() > 0) {
                courseTitle.setUnFocusColor(Color.GREENYELLOW);
                isCourseTitleValid.set(true);
            } else {
                courseTitle.setUnFocusColor(Color.RED);
                isCourseTitleValid.set(false);
            }

        });

        // validate courseID
        courseID.textProperty().addListener((observable, oldV, newV) -> {
            if (newV.trim().length() > 0 && (courseMap == null
                    || !courseMap.containsKey(newV.trim()))) {
                courseID.setUnFocusColor(Color.GREENYELLOW);
                isCourseIdValid.set(true);
            } else {
                courseID.setUnFocusColor(Color.RED);
                isCourseIdValid.set(false);
            }
        });

        // validate course field
        isCourseValid.bind(isCourseIdValid.and(isTimeValid).and(isCourseTitleValid).and(isDayValid));

        //////////////
        ArrayList<Course> play = new ArrayList<>();
        tmp.add(0, play);
        tmp.add(1, (ArrayList<Course>) play.clone());
        tmp.add(2, (ArrayList<Course>) play.clone());
        tmp.add(3, (ArrayList<Course>) play.clone());
        tmp.add(4, (ArrayList<Course>) play.clone());
        tmp.add(5, (ArrayList<Course>) play.clone());
        tmp.add(6, (ArrayList<Course>) play.clone());
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void add(ActionEvent event) {
        // validate start & end time
        LocalTime sTime = startTime.getValue();
        LocalTime eTime = endTime.getValue();
        isTimeValid.set(((sTime != null) && (eTime != null)) && sTime.isBefore(eTime));

        // validate day
        isDayValid.set(mon.isSelected() || tue.isSelected()
                || wed.isSelected() || thur.isSelected()
                || fri.isSelected() || sat.isSelected()
                || sun.isSelected());

        String cs = courseTitle.getText().trim();
        String cid = courseID.getText().trim();

        if (!courseMap.isEmpty() && courseMap.containsKey(cid)) {
            courseID.setUnFocusColor(Color.RED);
            return;
        }

        if (isCourseValid.get()) {

            ObservableList<String> days = FXCollections.observableArrayList();

            // get all selected days and add to list
            for (JFXCheckBox a : chb) {
                if (a.isSelected()) {
                    days.add(a.getText());
                }
            }

            // create course
            Course course = new Course(cs, cid, sTime, eTime, required.isSelected(), days);

            // add to coursemap
            courseMap.put(cid, course);
            cCopy = course;

            // clear fields
            courseTitle.clear();
            courseID.clear();
            uncheckAll();
            required.setSelected(false);

            // make course label for listview 
            String lbText = course.toString();

            // add to respective day listview
            if (course.getDays().contains("Mon")) {
                tmp.get(0).add(course);
                monList.getItems().add(makeLabel(lbText));
                monList.setExpanded(true);
            }
            if (course.getDays().contains("Tue")) {
                tmp.get(1).add(course);
                tueList.getItems().add(makeLabel(lbText));
                tueList.setExpanded(true);

            }
            if (course.getDays().contains("Wed")) {
                tmp.get(2).add(course);
                wedList.getItems().add(makeLabel(lbText));
                wedList.setExpanded(true);

            }
            if (course.getDays().contains("Thur")) {
                tmp.get(3).add(course);
                thurList.getItems().add(makeLabel(lbText));
                thurList.setExpanded(true);

            }
            if (course.getDays().contains("Fri")) {
                tmp.get(4).add(course);
                friList.getItems().add(makeLabel(lbText));
                friList.setExpanded(true);

            }
            if (course.getDays().contains("Sat")) {
                tmp.get(5).add(course);
                satList.getItems().add(makeLabel(lbText));
                satList.setExpanded(true);

            }
            if (course.getDays().contains("Sun")) {
                tmp.get(6).add(course);
                sunList.getItems().add(makeLabel(lbText));
                sunList.setExpanded(true);

            }
        }
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

    public Label makeLabel(String lb) {
        Label butt = new Label(lb);
        butt.setRotate(-180);
        butt.setAlignment(Pos.CENTER);
        butt.setMaxWidth(100);
        butt.setMaxWidth(100);
        butt.setTextAlignment(TextAlignment.CENTER);
        butt.setContentDisplay(ContentDisplay.CENTER);
        butt.setTextOverrun(OverrunStyle.ELLIPSIS);
        return butt;
    }

    @FXML
    private void monExpand(ActionEvent event) {
        if (monList.isExpanded()) {
            monList.setExpanded(false);
        } else {
            monList.setExpanded(true);

        }

    }

    @FXML
    private void tueExpand(ActionEvent event) {
        if (tueList.isExpanded()) {
            tueList.setExpanded(false);
        } else {
            tueList.setExpanded(true);

        }
    }

    @FXML
    private void wedExpand(ActionEvent event) {
        if (wedList.isExpanded()) {
            wedList.setExpanded(false);
        } else {
            wedList.setExpanded(true);

        }
    }

    @FXML
    private void thurExpand(ActionEvent event) {
        if (thurList.isExpanded()) {
            thurList.setExpanded(false);
        } else {
            thurList.setExpanded(true);

        }
    }

    @FXML
    private void friExpand(ActionEvent event) {
        if (friList.isExpanded()) {
            friList.setExpanded(false);
        } else {
            friList.setExpanded(true);

        }
    }

    @FXML
    private void satExpand(ActionEvent event) {
        if (satList.isExpanded()) {
            satList.setExpanded(false);
        } else {
            satList.setExpanded(true);

        }
    }

    @FXML
    private void sunExpand(ActionEvent event) {
        if (sunList.isExpanded()) {
            sunList.setExpanded(false);
        } else {
            sunList.setExpanded(true);

        }
    }

    @FXML
    private void clash(ActionEvent event) {
        ArrayList<Course> a = tmp.get(0);
        tmp.forEach((c) -> {
            findClash(c);
        });

    }

    private static void findClash(ArrayList<Course> array) {
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.size(); j++) {
                if (i == j) {
                    break;
                }
                String msg = "";
                LocalTime start = array.get(i).getStartTime();
                LocalTime end = array.get(i).getEndTime();

                LocalTime start1 = array.get(j).getStartTime();
                LocalTime end1 = array.get(j).getEndTime();

                if ((!end.isAfter(start1) && start.isBefore(start1))
                        || (!start.isBefore(end1) && end.isAfter(end1))) {
                    // correct -- no clash found
                } else {
                    System.out.println("Clash : \t\n" + array.get(i) + " and "
                            + array.get(j));
                    msg += array.get(i).getCourseTitle() + "("
                            + array.get(i).getCourseId() + ") and " + array.get(i).getCourseTitle() + "("
                            + array.get(i).getCourseId() + ")" + "\n";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
                    alert.initModality(Modality.WINDOW_MODAL);
                    alert.setTitle("Clashes!");
                    alert.showAndWait();
                }

            }
        }
    }

}
