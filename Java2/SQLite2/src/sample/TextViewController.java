package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TextViewController {

    /*
     * Даты в БД записаны в Integer в формате YYYYMMDD
     * заметка: из БД вытаскивается Long а не int
     * Длительность также число, обозначает кол-во дней
     */



    private static final String URL = "jdbc:sqlite:db_one.db";

    private final DBDriver dbdriver = new DBDriver();

    private static final String DB_DATE_FORMAT = "yyyMMdd";
    private static final String BD_TASK_START_TIME = "StartTime";
    private static final String BD_TASK_DURATION = "Duration";

    public TextArea textArea;

    public TextField textField;

    public void initialize() {
    }

    private void writeToTextArea(ResultSet resultSet) {
        textArea.clear();
        int i = 0;
        if (resultSet != null) {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    System.err.println(resultSet.getString( + 1));
                    i++;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    return;
                }
            }
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
//        final ResultSet resultSet = dbdriver.method1("SELECT * FROM Projects;");
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Projects.Id, Projects.PrjName, Tasks.TaskName FROM Projects
                            INNER JOIN Tasks ON (Tasks.ProjectID = Projects.Id)
                            WHERE (Tasks.Finished = 0)
                            GROUP BY Projects.Id;
                            """
                    );
//                    ("""
//                            SELECT
//                              Projects.PrjName,
//                              Tasks.TaskName,
//                              Masters.FirstName,
//                              Masters.SecondName
//                            FROM
//                              Tasks
//                              INNER JOIN Masters ON (Tasks.IdMaster = Masters.Id)
//                              INNER JOIN Projects ON (Tasks.Id = Projects.IdTask)
//                              AND (Projects.IdMaster = Masters.Id)
//                              AND (Tasks.IdPrjName = Projects.Id)
//                            WHERE (Tasks.finished = 0)
//                            """);
//                    ("""
//                            SELECT
//                              Projects.PrjName,
//                              Projects.IdTask,
//                              Projects.IdMaster,
//                              Masters.FirstName,
//                              Masters.SecondName,
//                              Tasks.TaskName
//                            FROM
//                              Tasks
//                              INNER JOIN Masters ON (Tasks.IdMaster = Masters.Id)
//                              INNER JOIN Projects ON (Tasks.Id = Projects.IdTask)
//                              AND (Projects.IdMaster = Masters.Id)
//                              AND (Tasks.IdPrjName = Projects.Id)
//                              """);

//                    ("SELECT " +
//                            "Projects.PrjName, Masters.FirstName " +
//                            "FROM Projects " +
//                            "JOIN Masters ON Projects.IdMaster = Masters.Id " +
//                            "JOIN Tasks ON Projects.IdTask = Tasks.Id;");
            final ResultSet resultSet = st.executeQuery();
            writeToScreen(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


//        writeToTextArea(resultSet);
    }
    /*
     * 2. Сколько по данному проекту незавершенных задач.
     */

    public void secondStory(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Projects.Id, Projects.PrjName, Tasks.TaskName FROM Projects
                            INNER JOIN Tasks ON (Tasks.ProjectID = Projects.Id)
                            WHERE (Tasks.Finished = 0)
                            """
                    );
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
                            WHERE (Tasks.Finished = 0) AND names.name LIKE '%?%'
                            """
                    );
//                    ("""
//                            SELECT DISTINCT Projects.id, Projects.PrjName, Projects.idMaster, Projects.idTask FROM Projects
//                            JOIN (SELECT id AS id, (FirstName || ' ' || SecondName || ' ' || LastName) AS name FROM Masters) AS names
//                            ON (Projects.idMaster = names.id)
//                            JOIN Tasks ON (Projects.idTask = Tasks.id)
//                            """
//                    );

            st.setString(1, textField.getText());
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

        LocalDateTime lt = LocalDateTime.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern(DB_DATE_FORMAT);
        int date = Integer.parseInt(f.format(lt));

        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT Projects.Id, Projects.PrjName, Tasks.TaskName, Tasks.StartTime, Tasks.Duration FROM Projects
                            INNER JOIN Tasks ON (Tasks.ProjectID = Projects.Id)
                            WHERE (Tasks.Finished = 0) AND (Tasks.StartTime < ?)
                            """
                    );
            st.setInt(1, date);

            final ResultSet resultSet = st.executeQuery();
            writeTasksForToday(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void writeTasksForToday(ResultSet resultSet) {
        textArea.clear();
        try {
//            String s = resultSet.getString("StartTime");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                if (taskForToday(resultSet, columnCount)) {
                    for (int i = 1; i <= columnCount; i++) {
                        textArea.appendText(resultSet.getString(i));
                        textArea.appendText(" \t");
                    }
                    textArea.appendText("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean taskForToday(ResultSet resultSet, int columnCount) {
        ResultSetMetaData metaData;
        try {
            metaData = resultSet.getMetaData();
            LocalDate date = null;
            Period taskDuration = null;
            for (int i = 1; i <= columnCount; i++) {
                if (metaData.getColumnLabel(i).equalsIgnoreCase(BD_TASK_START_TIME)) {
                    date = convertToLocalDate(resultSet.getString(i));
                }

                if (metaData.getColumnLabel(i).equalsIgnoreCase(BD_TASK_DURATION)) {
                    taskDuration = Period.ofDays(resultSet.getInt(i));
                }

                if (date != null && taskDuration != null) {
//                    Period p = Period.between(date, date.plus(taskDuration));
                    LocalDate now = LocalDate.now();

                    return now.isAfter(date) && now.isBefore(date.plus(taskDuration));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private LocalDate convertToLocalDate(String s) {
        int year = Integer.parseInt(s.substring(0, 4));
        int month = Integer.parseInt(s.substring(4, 6));
        int day = Integer.parseInt(s.substring(6));
        return LocalDate.of(year, month, day);
    }

    /*
     * 5. Имена и контакты тех, кто просрочил свои задачи (текущая дата больше, чем дата старта задачи + ее длительность)
     */
    public void fifthStory(ActionEvent actionEvent) {

    }
}
