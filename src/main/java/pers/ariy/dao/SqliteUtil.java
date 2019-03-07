package pers.ariy.dao;

import java.sql.DriverManager;

/**
 * @Author Ariy
 * @Since 2019/3/7
 */
public class SqliteUtil extends Util {
    private static SqliteUtil INSTANCE = null;

    public static SqliteUtil get(){
        return INSTANCE == null ? INSTANCE = new SqliteUtil() : INSTANCE;
    }

    @Override
    public void connect() {
        String driver = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:plugins/Asign/Asign.db";
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enableSqlite(){
        String createTable = "CREATE TABLE IF NOT EXISTS ASIGN (PLAYERNAME TEXT NOT NULL, YEAR INT NOT NULL, " +
                "MONTH INT NOT NULL, DATE INT NOT NULL, CAROM INT NOT NULL, NUM INT NOT NULL)";
        enable(createTable);
    }
}
