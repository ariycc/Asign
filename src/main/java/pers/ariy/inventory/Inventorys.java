package pers.ariy.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pers.ariy.entity.Sign;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Ariy
 * @Since 2019/3/7
 */
public class Inventorys {
    public static HashMap<String, Inventory> inventorys = new HashMap<String, Inventory>();

    public static void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "§6签到菜单");
        Calendar now = Calendar.getInstance();
        Sign sign = Sign.signs.get(player.getName());
        int maxDate = 30;
        for (int i = 1; i < maxDate + 1; i++) {
            if (sign.isSign(now.get(Calendar.YEAR),now.get(Calendar.MONTH),i)) {
                inventory.setItem(i - 1, getItem(0, i));
            } else {
                inventory.setItem(i - 1, getItem(2, i));
            }
        }
        if(!sign.isSign(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DATE))){
            inventory.setItem(now.get(Calendar.DATE)-1, getItem(1, now.get(Calendar.DATE)));
        }
        inventory.setItem(49, getItem(3,sign.getCarom()));
        player.openInventory(inventory);
        inventorys.put(player.getName(), inventory);
    }

    private static ItemStack getItem(int status, int date) {
        switch (status) {
            case 0: {
                ItemStack item = new ItemStack(Material.LIME_BANNER, 1);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§a第" + date + "天");
                List<String> lores = new ArrayList<String>();
                lores.add("§f这一天你签到了");
                itemMeta.setLore(lores);
                item.setItemMeta(itemMeta);
                return item;
            }
            case 1: {
                ItemStack item = new ItemStack(Material.LIGHT_BLUE_BANNER, 1);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§d第" + date + "天");
                List<String> lores = new ArrayList<String>();
                lores.add("§f点击签到");
                itemMeta.setLore(lores);
                item.setItemMeta(itemMeta);
                return item;
            }
            case 2: {
                ItemStack item = new ItemStack(Material.WHITE_BANNER, 1);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§f第" + date + "天");
                List<String> lores = new ArrayList<String>();
                lores.add("§f这一天你没有签到");
                itemMeta.setLore(lores);
                item.setItemMeta(itemMeta);
                return item;
            }
            case 3: {
                ItemStack item = new ItemStack(Material.CLOCK, 1);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§b本月累计签到" + date + "天");
                List<String> lores = new ArrayList<String>();
                lores.add("§f累计签到每满7天均有额外奖励哦");
                itemMeta.setLore(lores);
                item.setItemMeta(itemMeta);
                return item;
            }
        }
        return null;
    }

    public static ItemStack getCard(){
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§d补签卡");
        List<String> lores = new ArrayList<String>();
        lores.add("§f将这个道具放在主手");
        lores.add("§f点击未签到的那天可以补签哦");
        itemMeta.setLore(lores);
        item.setItemMeta(itemMeta);
        return item;
    }
}
