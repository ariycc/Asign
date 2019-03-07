package pers.ariy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import pers.ariy.Main;
import pers.ariy.dao.SignDao;
import pers.ariy.entity.Sign;
import pers.ariy.inventory.Inventorys;

import java.util.Calendar;


/**
 * @Author Ariy
 * @Since 2019/3/7
 */
public class Listeners implements Listener {
    private Main plugin;

    public Listeners(Main plugin) {
        this.plugin = plugin;
    }

    //登录时载入用户数据
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        if (Sign.signs.containsKey(playerName)) {
            return;
        }
        //载入签到数据
        SignDao.get().select(playerName);
    }

    //退出时
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        if (!Sign.signs.containsKey(playerName)) {
            return;
        }
        Sign.signs.remove(playerName);
    }

    //gui点击事件
    @EventHandler
    public void onClickGui(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (Inventorys.inventorys.containsKey(player.getName()) &&
                Inventorys.inventorys.get(player.getName()).equals(event.getClickedInventory())) {
            event.setCancelled(true);
            Calendar now = Calendar.getInstance();
            Sign sign = Sign.signs.get(player.getName());
            int slot = event.getSlot() + 1;
            int date = now.get(Calendar.DATE);
            //防止补签未来日期
            if (slot > date) {
                player.sendMessage("§d这一天还没有到来哦~");
                return;
            }
            //点击当天旗帜签到
            if (slot == now.get(Calendar.DATE)) {
                player.sendMessage(sign.sign());
                player.closeInventory();
                return;
            }
            //点击未签到旗帜补签
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!sign.isSign(now.get(Calendar.YEAR),now.get(Calendar.MONTH),slot) && item.hasItemMeta() &&
                    item.getItemMeta().getDisplayName().equals("§d补签卡")){
                item.setAmount(item.getAmount() -1);
                player.sendMessage(sign.supplementarySign(slot));
                player.closeInventory();
            }else {
                player.sendMessage("§d补签需要点击未签到的那一天，并且手持补签卡");
            }
        }
    }
}
