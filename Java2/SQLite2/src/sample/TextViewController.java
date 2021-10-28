package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TextViewController {

    /*
     * Даты в БД записаны в Integer в формате Millies from Epoch
     * заметка: из БД вытаскивается Long а не int
     * Длительность также millis, обозначает кол-во дней
     */



    private static final String URL = "jdbc:sqlite:db_one.db";

    private final DBDriver dbdriver = new DBDriver();

    private static final String DB_DATE_FORMAT = "yyyyMMdd";
    private static final String DATE_FORMAT = "yyyy MM dd";
    private static final String BD_TASK_START_TIME = "StartTime";
    private static final String BD_TASK_DURATION = "Duration";

    public TextArea textArea;

    public TextField textField;

    public void initialize() {
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT * FROM Projects
                            """
                    );

            final ResultSet resultSet = st.executeQuery();
            if (resultSet == null) {
                textArea.setText("База данных не существует либо не доступна");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void writeToScreen(ResultSet resultSet) throws SQLException {
        textArea.clear();
        int i = resultSet.getMetaData().getColumnCount();
        String s = "";
        while (resultSet.next()) {
            for (int j = 1; j <= i; j++) {
                s = resultSet.getString(j);
                if (s == null) {
                    s = "null";
                }
                textArea.appendText(s);
                textArea.appendText("\t");
            }
            textArea.appendText("\n");
        }
    }

    /*
     * 1. Какие проекты в работе (по которым есть хотя бы одна незавершенная задача).
     */
    public void firstStory(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Projects.Id, Projects.PrjName, Tasks.TaskName FROM Projects
                            INNER JOIN Tasks ON (Tasks.ProjectID = Projects.Id)
                            WHERE (Tasks.Finished = 0)
                            GROUP BY Projects.Id;
                            """
                    );

            final ResultSet resultSet = st.executeQuery();
            writeToScreen(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /*
     * 2. Сколько по данному проекту незавершенных задач.
     */
    public void secondStory(ActionEvent actionEvent) {
        if (textField.getText().equalsIgnoreCase("")) {
            textArea.clear();
            textArea.setText("Поле для ввода пустое");
            return;
        }
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Projects.Id, Projects.PrjName, Tasks.TaskName, COUNT(Projects.PrjName) FROM Tasks
                            JOIN Projects ON (Tasks.ProjectID = Projects.Id)
                            WHERE (Tasks.Finished = 0) AND Projects.PrjName LIKE ?
                            """
                    );
            st.setString(1, "%" + textField.getText() + "%");
            final ResultSet resultSet = st.executeQuery();

            writeToScreen(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
     * 3. Какие незавершенные задачи есть у некоторого ответственного, имя которого, я хочу ввести в программу.
     */
    public void thirdStory(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Tasks.id, TaskName, Tasks.idMaster, names.name, Projects.PrjName FROM Tasks
                            JOIN (SELECT id AS id, (FirstName || ' ' || SecondName || ' ' || LastName) AS name FROM Masters) AS names
                            ON (Tasks.idMaster = names.id)
                            JOIN Projects ON (Tasks.ProjectID = Projects.Id)
                            WHERE (Tasks.Finished = 0) AND names.name LIKE ?
                            """
                    );

            st.setString(1, "%" + textField.getText() + "%");
            final ResultSet resultSet = st.executeQuery();

            writeToScreen(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /*
     * 4. Какие задачи есть на сегодня и кто за них отвечает.
     */
    public void fourthStory(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Projects.Id, Projects.PrjName, Tasks.TaskName, Tasks.StartTime, Tasks.Duration FROM Projects
                            INNER JOIN Tasks ON (Tasks.ProjectID = Projects.Id)
                            WHERE (Tasks.Finished = 0) AND (Tasks.StartTime + Tasks.Duration > ?)
                            """
                    );
            st.setLong(1, System.currentTimeMillis());

            final ResultSet resultSet = st.executeQuery();
            writeTasksWithDate(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void writeTasksWithDate(ResultSet resultSet) {
        textArea.clear();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    if (metaData.getColumnLabel(i).equalsIgnoreCase(BD_TASK_START_TIME)) {
                        Instant instant = Instant.ofEpochMilli(resultSet.getLong(i));
                        var zd = LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
                        var formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                        textArea.appendText(zd.format(formatter));
                    } else {
                        if (metaData.getColumnLabel(i).equalsIgnoreCase(BD_TASK_DURATION)) {
                            var dur = Duration.ofMillis(resultSet.getLong(i));
                            textArea.appendText(dur.toHours() + " часа");
                        } else {
                            textArea.appendText(resultSet.getString(i));
                        }
                    }
                    textArea.appendText(" \t");
                }
                textArea.appendText("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * 5. Имена и контакты тех, кто просрочил свои задачи (текущая дата больше, чем дата старта задачи + ее длительность)
     */
    public void fifthStory(ActionEvent actionEvent) {
        if (textField.getText().equalsIgnoreCase("")) {
            textArea.clear();
            textArea.setText("Поле для ввода пустое");
            return;
        }
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Tasks.id, TaskName, Tasks.StartTime, Tasks.Duration, Tasks.idMaster, names.name, Masters.Phone, Masters.email, Projects.PrjName FROM Tasks
                            JOIN (SELECT id AS id, (FirstName || ' ' || SecondName || ' ' || LastName) AS name FROM Masters) AS names
                            ON (Tasks.idMaster = names.id)
                            JOIN Projects ON (Tasks.ProjectID = Projects.Id)
                            JOIN Masters ON (Tasks.idMaster = Masters.id)
                            WHERE (Tasks.Finished = 0) AND (Tasks.StartTime + Tasks.duration < ?)
                            """
                    );

            st.setLong(1, System.currentTimeMillis());
            final ResultSet resultSet = st.executeQuery();

            writeTasksWithDate(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
