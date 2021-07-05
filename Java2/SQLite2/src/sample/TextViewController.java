package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.sql.*;

public class TextViewController {
    private static final String URL = "jdbc:sqlite:db_one.db";

    private final DBDriver dbdriver = new DBDriver();

    public TextArea textArea;

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

    /*
     * 1. Какие проекты в работе (по которым есть хотя бы одна незавершенная задача).
     */

    public void firstStory(ActionEvent actionEvent) {
//        final ResultSet resultSet = dbdriver.method1("SELECT * FROM Projects;");
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement
                    ("""
                            SELECT
                              Projects.PrjName,
                              Tasks.TaskName,
                              Masters.FirstName,
                              Masters.SecondName
                            FROM
                              Tasks
                              INNER JOIN Masters ON (Tasks.IdMaster = Masters.Id)
                              INNER JOIN Projects ON (Tasks.Id = Projects.IdTask)
                              AND (Projects.IdMaster = Masters.Id)
                              AND (Tasks.IdPrjName = Projects.Id)
                                                        
                            """);
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
            int i = resultSet.getMetaData().getColumnCount();
            System.err.println(i);
            String s = "";
            while (resultSet.next()) {
//                System.err.println(resultSet.getString(i));
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


//        writeToTextArea(resultSet);
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
