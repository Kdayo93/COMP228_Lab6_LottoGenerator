import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lab6_lotto";
    private static final String DB_USER = "root";
    private static final String CONFIG_FILE = "config.properties";

    private static String getPassword() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(CONFIG_FILE));
            return props.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("❌ Could not load database password.");
            e.printStackTrace();
            return "";
        }
    }

    public static void saveResult(List<Integer> numbers) {
        String sql = "INSERT INTO lotto_results (numbers) VALUES (?)";
        String numberStr = numbers.stream()
                                  .map(String::valueOf)
                                  .collect(Collectors.joining(", "));

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numberStr);
            stmt.executeUpdate();
            System.out.println("✅ Numbers saved to database: " + numberStr);

        } catch (SQLException e) {
            System.out.println("❌ Database error:");
            e.printStackTrace();
        }
    }
}



