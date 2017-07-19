package anticlash;

import java.time.LocalTime;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author bruno
 */
public class Course {

    private final SimpleStringProperty courseTitle;
    private final SimpleStringProperty courseId;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final SimpleBooleanProperty required;

    public Course(SimpleStringProperty courseTitle, SimpleStringProperty courseId, LocalTime startTime, LocalTime endTime, SimpleBooleanProperty required) {
        this.courseTitle = courseTitle;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.required = required;
    }

}
