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
    private LocalTime startTime;
    private LocalTime endTime;
    private BooleanProperty required;
    private ObservableList<String> days;

    public Course(String courseTitle, String courseId, LocalTime startTime,
            LocalTime endTime, boolean required, ObservableList<String> days) {
        this.courseTitle = new SimpleStringProperty(courseTitle);
        this.courseId = new SimpleStringProperty(courseId);
        this.startTime = startTime;
        this.endTime = endTime;
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

    public void setStartTime(LocalTime value) {
        startTime = value;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalTime value) {
        endTime = value;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("%s%n %s%n%s ⇾ %s", getCourseId(),
                getCourseTitle(), getStartTime(), getEndTime());
    }

}
