package pers.ariy.entity;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import pers.ariy.Main;
import pers.ariy.dao.SignDao;

import java.util.*;

/**
 * @Author Ariy
 * @Since 2019/3/6
 */
public class Sign {
    private String playerName;
    private int year;
    private int month;
    private int date;
    private int carom;
    private int num;

    public String getPlayerName() {
        return playerName;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getCarom() {
        return this.carom;
    }


    public int getNum() {
        return num;
    }

    private static int[] probability = {5, 15, 30, 60, 100}; //概率数组
    private static String[] draws = {"draw.s", "draw.a", "draw.b", "draw.c", "draw.d"};//映射config.yml节点

    public static HashMap<String, Sign> signs = new HashMap<String, Sign>();

    //抽签
    private static String draw() {
        Random r = new Random();
        int i = r.nextInt(100);
        String draw = "draw.d";
        for (int p = 0; p < 5; p++) {
            if (i < probability[p]) {
                draw = draws[p];
                break;
            }
        }
        List<String> strings = Main.INSTANCE.getConfig().getStringList(draw);
        return strings.get(r.nextInt(strings.size()));
    }

    //获取礼包种类
    private void kit() {
        Player player = Bukkit.getPlayer(this.playerName);
        Set<String> permissions = Main.INSTANCE.getConfig().getConfigurationSection("sign").getKeys(false);
        String type = "sign.default";
        for (String permission : permissions) {
            if (player.hasPermission("ts." + permission)) {
                type = permission;
            }
        }
        doCommands("sign." + type + ".day");
        switch (carom) {
            case 7:
                doCommands("sign." + type + ".week");
                break;
            case 14:
                doCommands("sign." + type + ".twoweek");
                break;
            case 21:
                doCommands("sign." + type + ".threeweek");
                break;
            case 28:
                doCommands("sign." + type + ".month");
                break;
        }
    }

    //执行礼包指令
    private void doCommands(String type) {
        List<String> commands = Main.INSTANCE.getConfig().getStringList(type);
        for (String command : commands) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", playerName));
        }
    }

    //签到
    public String sign() {
        Calendar now = Calendar.getInstance();
        if (isSign(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DATE))) {
            return "你今天已经签到过了";
        }
        if (this.year == now.get(Calendar.YEAR) && this.month == now.get(Calendar.MONTH)) {
            this.carom++;
            this.num++;
            this.date = this.date | (int) Math.pow(2, now.get(Calendar.DATE));
        } else {
            this.year = now.get(Calendar.YEAR);
            this.month = now.get(Calendar.MONTH);
            this.carom = 1;
            this.num++;
            this.date = (int) Math.pow(2, now.get(Calendar.DATE));
        }
        SignDao.get().update(this);
        kit();
        String draw = draw();
        return "[第" + num + "签]" + draw;
    }

    //补签
    public String supplementarySign(int date) {
        this.date = this.date | (int) Math.pow(2, date);
        this.carom++;
        this.num++;
        SignDao.get().update(this);
        kit();
        String draw = draw();
        return "[第" + num + "签]" + draw;
    }

    public boolean isSign(int year, int month, int date) {
        boolean flag1 = this.year == year;
        boolean flag2 = this.month == month;
        boolean flag3 = (this.date & (int) Math.pow(2, date)) != 0;
        return flag1 && flag2 && flag3;
    }

    public Sign(String playerName) {
        this.playerName = playerName;
        this.year = 0;
        this.month = 0;
        this.date = 0;
        this.carom = 0;
        this.num = 0;
    }

    public Sign(String playerName, int year, int month, int date, int carom, int num) {
        this.playerName = playerName;
        this.year = year;
        this.month = month;
        this.date = date;
        this.carom = carom;
        this.num = num;
    }

}
