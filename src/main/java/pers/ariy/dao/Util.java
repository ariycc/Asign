package pers.ariy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author Ariy
 * @Since 2019/3/7
 */
public abstract class Util {
    Connection connection;

    public void enable(String createTable){
        connect();
        try{
            PreparedStatement ps = connection.prepareStatement(createTable);
            doCommand(ps);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public abstract void connect();

    void doCommand(PreparedStatement ps) {
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection(){
        return connection;
    }
}
