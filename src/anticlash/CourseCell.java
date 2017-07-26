package anticlash;

import javafx.scene.control.ListCell;

/**
 *
 * @author bruno
 */
public class CourseCell extends ListCell<Course> {

    public CourseCell() {
    }

    @Override
    public void updateItem(Course item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.toString());
        }
    }

}
