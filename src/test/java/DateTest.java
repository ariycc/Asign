import org.junit.Test;
import pers.ariy.Main;

import java.sql.*;
import java.util.Calendar;

/**
 * @Author Ariy
 * @Since 2019/3/6
 */
public class DateTest {
    @Test
    public void intToBoolean() {
        int i = 0;
        /*
        Calendar now = Calendar.getInstance();
        i = i | (int) Math.pow(2, now.get(Calendar.DATE));
        */
        i = i & 2;
        System.out.println(i | 2);

    }

    @Test
    public void timeTest() {
        Calendar now = Calendar.getInstance();
        System.out.println(now.get(Calendar.YEAR));
        System.out.println(now.get(Calendar.MONTH));
        System.out.println(now.get(Calendar.DATE));
    }

    @Test
    public void jdbcTest() {

    }

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:D:/minecraft.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS employees (\n" + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n" + " capacity real\n" + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Create table finished.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
