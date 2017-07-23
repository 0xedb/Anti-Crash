package anticlash;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author bruno
 */
public class Course {

    private final String courseTitle;
    private final String courseId;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final boolean required;
    private final ArrayList<String> days;

    public Course(String courseTitle, String courseId, LocalTime startTime, LocalTime endTime, boolean required, ArrayList<String> days) {
        this.courseTitle = courseTitle;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.required = required;
        this.days = days;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseId() {
        return courseId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isRequired() {
        return required;
    }

    public ArrayList<String> getDays() {
        return days;
    }

}
