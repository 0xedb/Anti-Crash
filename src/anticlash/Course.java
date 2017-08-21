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

    public Course(StringProperty courseTitle, StringProperty courseId, ObjectProperty<LocalTime> startTime, ObjectProperty<LocalTime> endTime, BooleanProperty required, ObservableList<String> days) {
        this.courseTitle = courseTitle;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.required = required;
        this.days = days;
    }

    public final void setCourseTitle(String value) {
        courseTitle.set(value);
    }

    public final String getCourseTitle() {
        return courseTitle.get();
    }

    public final StringProperty courseTitleProperty() {
        return courseTitle;
    }

    public final void setCourseId(String value) {
        courseId.set(value);
    }

    public final String getCourseId() {
        return courseId.get();
    }

    public final StringProperty courseIdProperty() {
        return courseId;
    }

    public final void setRequired(Boolean value) {
        required.set(value);
    }

    public final Boolean getRequired() {
        return required.get();
    }

    public final BooleanProperty requiredProperty() {
        return required;
    }

}
