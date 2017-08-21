package anticlash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.time.LocalTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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

    private static BooleanProperty isTimeValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isDayValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isCourseTitleValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isCourseIdValid = new SimpleBooleanProperty(false);
    private static BooleanProperty isCourseValid = new SimpleBooleanProperty(false);

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

        System.out.println(isCourseValid.get());
    }

    @FXML
    private void clash(ActionEvent event) {
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
    private void monExpand(ActionEvent event) {
        if (!monList.isExpanded()) {
            monList.setExpanded(true);
            monList.depthProperty().set(1);
        } else {
            monList.setExpanded(false);
            monList.depthProperty().set(0);

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
}

//
//Label butt = new Label("Kofi");
//        butt.setRotate(-180);
//        butt.setStyle("-fx-font-weight: bold;");
//        monList.getItems().add(butt);
//        monList.getItems().add(new Label("Bruno"));
