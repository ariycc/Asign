package pers.ariy.dao;

import pers.ariy.Main;

import java.sql.DriverManager;

/**
 * @Author Ariy
 * @Since 2019/3/7
 */
public class MysqlUtil extends Util {
    private String ip = Main.INSTANCE.getConfig().getString("mysql.ip");
    private String database = Main.INSTANCE.getConfig().getString("mysql.database");
    private String name = Main.INSTANCE.getConfig().getString("mysql.name");
    private String password = Main.INSTANCE.getConfig().getString("mysql.password");
    private String port = Main.INSTANCE.getConfig().getString("mysql.port");

    private static MysqlUtil INSTANCE = null;

    public static MysqlUtil get(){
        return INSTANCE == null ? INSTANCE = new MysqlUtil() : INSTANCE;
    }

    @Override
    public void connect() {
        String driver = "com.mysql.jdbc.Driver";
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql://");
        url.append(ip);
        url.append(":");
        url.append(port);
        url.append("/");
        url.append(database);
        url.append("?useSSL=false");
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url.toString(), name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableMysql(){
        String createTable = "CREATE TABLE IF NOT EXISTS ASIGN(PLAYERNAME VARCHAR(100) NOT NULL, YEAR INT NOT NULL, " +
                "MONTH INT NOT NULL, DATE INT NOT NULL, CAROM INT NOT NULL, NUM INT NOT NULL)";
        enable(createTable);
    }
}
