package anticlash;

import java.time.LocalTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author bruno
 */
public class Course {

    private StringProperty courseTitle;
    private StringProperty courseId;
    private ObjectProperty<LocalTime> startTime;
    private ObjectProperty<LocalTime> endTime;
    private BooleanProperty required;
    private ObservableList<String> days;

    public Course(String courseTitle, String courseId, LocalTime startTime,
            LocalTime endTime, boolean required, ObservableList<String> days) {
        this.courseTitle.set(courseTitle);
        this.courseId.set(courseId);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.required.set(required);
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

    @Override
    public String toString() {
        return String.format("%s %n %s", getCourseId(), getCourseTitle());
    }

}
