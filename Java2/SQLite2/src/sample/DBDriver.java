package sample;

import java.sql.*;

public class DBDriver {

    private static final String URL = "jdbc:sqlite:db_one.db";

    public DBDriver() {

    }

    public ResultSet method1(String statement) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement(statement);
            return st.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } ;
    }
}
