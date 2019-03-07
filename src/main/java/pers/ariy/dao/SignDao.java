package pers.ariy.dao;

import pers.ariy.Main;
import pers.ariy.entity.Sign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Ariy
 * @Since 2019/3/7
 */
public class SignDao {
    private static Util util;

    static {
        if (Main.INSTANCE.getConfig().getBoolean("mysql.enable")) {
            util = MysqlUtil.get();
        } else {
            util = SqliteUtil.get();
        }
    }

    private static SignDao INSTANCE = null;

    public static SignDao get() {
        return INSTANCE == null ? INSTANCE = new SignDao() : INSTANCE;
    }

    public void save(Sign sign) {
        try {
            String cmd = "INSERT INTO ASIGN (PLAYERNAME, YEAR, MONTH, DATE, CAROM, NUM) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = util.getConnection().prepareStatement(cmd);
            ps.setString(1, sign.getPlayerName());
            ps.setInt(2, sign.getYear());
            ps.setInt(3, sign.getMonth());
            ps.setInt(4, sign.getDate());
            ps.setInt(5, sign.getCarom());
            ps.setInt(6, sign.getNum());
            util.doCommand(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Sign sign) {
        try {
            String cmd = "UPDATE ASIGN SET YEAR=?,MONTH=?,DATE=?,CAROM=?,NUM=? WHERE PLAYERNAME=?";
            PreparedStatement ps = util.getConnection().prepareStatement(cmd);
            ps.setInt(1, sign.getYear());
            ps.setInt(2, sign.getMonth());
            ps.setInt(3, sign.getDate());
            ps.setInt(4, sign.getCarom());
            ps.setInt(5, sign.getNum());
            ps.setString(6, sign.getPlayerName());
            util.doCommand(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void select(String playerName) {
        Sign sign;
        try {
            String cmd = "SELECT * FROM ASIGN WHERE PLAYERNAME=?";
            PreparedStatement ps = util.getConnection().prepareStatement(cmd);
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            //查无此人，则保存
            if (!rs.next()) {
                sign = new Sign(playerName);
                save(sign);
            } else {
                int year = rs.getInt("YEAR");
                int month = rs.getInt("MONTH");
                int date = rs.getInt("DATE");
                int carom = rs.getInt("CAROM");
                int num = rs.getInt("NUM");
                sign = new Sign(playerName, year, month, date, carom, num);
            }
            Sign.signs.put(playerName, sign);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
