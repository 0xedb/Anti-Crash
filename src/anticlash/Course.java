package anticlash;

import java.time.LocalTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author bruno
 */
public class Course {

    private StringProperty courseTitle;
    private StringProperty courseId;
    private StringProperty startTime;
    private StringProperty endTime;
    private BooleanProperty required;
    private ObservableList<String> days;

    public Course(String courseTitle, String courseId, LocalTime startTime,
            LocalTime endTime, boolean required, ObservableList<String> days) {
        this.courseTitle = new SimpleStringProperty(courseTitle);
        this.courseId = new SimpleStringProperty(courseId);
        this.startTime = new SimpleStringProperty(startTime.toString());
        this.endTime = new SimpleStringProperty(endTime.toString());
        this.required = new SimpleBooleanProperty(required);
        this.days = days;
    }

    public void setCourseTitle(String value) {
        courseTitle.set(value);
    }

    public String getCourseTitle() {
        return courseTitle.get();
    }

    public StringProperty courseTitleProperty() {
        return courseTitle;
    }

    public void setCourseId(String value) {
        courseId.set(value);
    }

    public String getCourseId() {
        return courseId.get();
    }

    public StringProperty courseIdProperty() {
        return courseId;
    }

    public void setRequired(Boolean value) {
        required.set(value);
    }

    public Boolean getRequired() {
        return required.get();
    }

    public BooleanProperty requiredProperty() {
        return required;
    }

    public ObservableList<String> getDays() {
        return days;
    }

    public void setDays(ObservableList<String> days) {
        this.days = days;
    }

    public void setStartTime(String value) {
        startTime.set(value);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    public void setEndTime(String value) {
        endTime.set(value);
    }

    public String getEndTime() {
        return endTime.get();
    }

    public StringProperty endTimeProperty() {
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("%s%n %s%n%s ⇾ %s", getCourseId(),
                getCourseTitle(), getStartTime(), getEndTime());
    }

}
