package pers.ariy.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pers.ariy.Main;
import pers.ariy.dao.SignDao;
import pers.ariy.entity.Sign;
import pers.ariy.inventory.Inventorys;

/**
 * @Author Ariy
 * @Since 2019/3/7
 */
public class Commands implements CommandExecutor {
    private final Main plugin;

    public Commands(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equals("ts")){
            if(strings.length == 0){
                return false;
            }
            switch (strings[0]){
                case "menu":{
                    if(commandSender instanceof Player){
                        if(!Sign.signs.containsKey(commandSender.getName())){
                            SignDao.get().select(commandSender.getName());
                        }
                        Inventorys.openMenu((Player) commandSender);
                    }else {
                        Main.INSTANCE.getLogger().info("控制台无法执行这个指令");
                    }
                    return true;
                }
                case "give":{
                    if(commandSender.hasPermission("ts.give")){
                        if(strings.length == 2){
                            Player player = Bukkit.getPlayer(strings[1]);
                            player.getInventory().addItem(Inventorys.getCard());
                        }else {
                            if(commandSender instanceof Player){
                                commandSender.sendMessage("不合法的参数");
                            }else {
                                Main.INSTANCE.getLogger().info("不合法的参数");
                            }
                        }
                    }else {
                        commandSender.sendMessage("§4你没有权限这样做");
                    }
                    return true;
                }
                case "reload":{
                    if(commandSender.hasPermission("ts.reload")){
                        Main.INSTANCE.mkdirPluginFolder();
                    }else {
                        commandSender.sendMessage("§4你没有权限这样做");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
