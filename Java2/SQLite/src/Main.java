import java.sql.*;

public class Main {

    private static final String URL = "jdbc:sqlite:firstdb.db";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        method1();
    }

    private static void method1() {
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM Clients_Names");
            final ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String sName = resultSet.getString("SECOND_NAME");
                String o = resultSet.getString("OTCHESTVO");

                System.err.printf("%s %s %s\n", name, sName, o);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } ;
    }
}
