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
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

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

        //////////////
        System.out.println(isCourseValid.get());

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

            // clear fields
            courseTitle.clear();
            courseID.clear();
            uncheckAll();
            required.setSelected(false);

            // make course label for listview 
            String lbText = course.toString();

            // add to respective day listview
            if (course.getDays().contains("Mon")) {
                monList.getItems().add(makeLabel(lbText));
            }
            if (course.getDays().contains("Tue")) {
                tueList.getItems().add(makeLabel(lbText));
            }
            if (course.getDays().contains("Wed")) {
                wedList.getItems().add(makeLabel(lbText));
            }
            if (course.getDays().contains("Thur")) {
                thurList.getItems().add(makeLabel(lbText));
            }
            if (course.getDays().contains("Fri")) {
                friList.getItems().add(makeLabel(lbText));
            }
            if (course.getDays().contains("Sat")) {
                satList.getItems().add(makeLabel(lbText));
            }
            if (course.getDays().contains("Sun")) {
                sunList.getItems().add(makeLabel(lbText));
            }
        } ///////////////
        else {
            System.out.println("Noooooooooooooooooooooooooooooooooooooooooooooo!!!!!");
        }
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

    public Label makeLabel(String lb) {
        Label butt = new Label(lb);
        butt.setGraphic(new ImageView(getClass().getResource("resources/course.png").toString()));
        butt.setRotate(-180);
        butt.setAlignment(Pos.CENTER);
        butt.setMaxWidth(110);
        butt.setMaxHeight(40);
        butt.setTextAlignment(TextAlignment.CENTER);
        butt.setTextOverrun(OverrunStyle.ELLIPSIS);
        butt.setContentDisplay(ContentDisplay.LEFT);
        butt.setStyle("-fx-font-weight: bold;");
        return butt;
    }

    @FXML
    private void tueExpand(ActionEvent event) {
        if (!tueList.isExpanded()) {
            tueList.setExpanded(true);
            tueList.depthProperty().set(1);
        } else {
            tueList.setExpanded(false);
            tueList.depthProperty().set(0);

        }
    }

    @FXML
    private void wedExpand(ActionEvent event) {
        if (!wedList.isExpanded()) {
            wedList.setExpanded(true);
            wedList.depthProperty().set(1);
        } else {
            wedList.setExpanded(false);
            wedList.depthProperty().set(0);

        }
    }

    @FXML
    private void thurExpand(ActionEvent event) {
        if (!thurList.isExpanded()) {
            thurList.setExpanded(true);
            thurList.depthProperty().set(1);
        } else {
            thurList.setExpanded(false);
            thurList.depthProperty().set(0);

        }
    }

    @FXML
    private void friExpand(ActionEvent event) {
        if (!friList.isExpanded()) {
            friList.setExpanded(true);
            friList.depthProperty().set(1);
        } else {
            friList.setExpanded(false);
            friList.depthProperty().set(0);

        }
    }

    @FXML
    private void satExpand(ActionEvent event) {
        if (!satList.isExpanded()) {
            satList.setExpanded(true);
            satList.depthProperty().set(1);
        } else {
            satList.setExpanded(false);
            satList.depthProperty().set(0);

        }
    }

    @FXML
    private void sunExpand(ActionEvent event) {
        if (!sunList.isExpanded()) {
            sunList.setExpanded(true);
            sunList.depthProperty().set(1);
        } else {
            sunList.setExpanded(false);
            sunList.depthProperty().set(0);

        }
    }
}

//

//        monList.getItems().add(new Label("Bruno"));
