package pers.ariy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pers.ariy.command.Commands;
import pers.ariy.dao.MysqlUtil;
import pers.ariy.dao.SqliteUtil;
import pers.ariy.hooker.PapiHooker;
import pers.ariy.listener.Listeners;

import java.io.File;

/**
 * @Author Ariy
 * @Since 2019/3/6
 */
public class Main extends JavaPlugin {
    public static Main INSTANCE;

    public Main() {
        INSTANCE = this;
    }

    @Override
    public void onEnable(){
        getLogger().info("Asign已启用，Author：Ariy，Email：i@ariy.cc");
        mkdirPluginFolder();
        regPapi();
        if(getConfig().getBoolean("mysql.enable")){
            MysqlUtil.get().enableMysql();
        }else {
            SqliteUtil.get().enableSqlite();
        }
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        this.getCommand("ts").setExecutor(new Commands(this));
    }

    @Override
    public void onDisable(){
        getLogger().info("正在断开连接");
        if(getConfig().getBoolean("mysql.enable")){
            MysqlUtil.get().shutdown();
        }else {
            SqliteUtil.get().shutdown();
        }
        getLogger().info("已断开连接");
    }

    @Override
    public void onLoad(){

    }

    //建立插件文件夹，加载配置文件
    public void mkdirPluginFolder(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!(file.exists())) {
            saveDefaultConfig();
        }
        reloadConfig();
    }

    //注册Papi变量
    private void regPapi() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin != null) {
            getLogger().info("连接PAPI成功");
            new PapiHooker().register();
        }
    }
}
