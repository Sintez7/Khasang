package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TextViewController {

    private final DBDriver dbdriver = new DBDriver();

    public TextArea textArea;

    public void initialize() {

    }

    private void appendToTextArea(ResultSet resultSet) {
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resultSet.
        }
    }

    /*
     * 1. Какие проекты в работе (по которым есть хотя бы одна незавершенная задача).
     */

    public void firstStory(ActionEvent actionEvent) {
        final ResultSet resultSet = dbdriver.method1("SELECT * FROM Projects");
        appendToTextArea(resultSet);
    }

    /*
     * 2. Сколько по данному проекту незавершенных задач.
     */
    public void secondStory(ActionEvent actionEvent) {

    }

    /*
     * 3. Какие незавершенные задачи есть у некоторого ответственного, имя которого, я хочу ввести в программу.
     */
    public void thirdStory(ActionEvent actionEvent) {

    }

    /*
     * 4. Какие задачи есть на сегодня и кто за них отвечает.
     */
    public void fourthStory(ActionEvent actionEvent) {

    }

    /*
     * 5. Имена и контакты тех, кто просрочил свои задачи (текущая дата больше, чем дата старта задачи + ее длительность)
     */
    public void fifthStory(ActionEvent actionEvent) {

    }
}
