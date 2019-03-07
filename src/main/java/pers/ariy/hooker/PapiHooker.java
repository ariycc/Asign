package pers.ariy.hooker;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import pers.ariy.entity.Sign;

import java.util.Calendar;

/**
 * @Author Ariy
 * @Since 2019/3/6
 */
public class PapiHooker extends PlaceholderExpansion {
    public String getIdentifier() {
        return "Asign";
    }

    public String getAuthor() {
        return "Ariy";
    }

    public String getVersion() {
        return "V1.0.0";
    }

    public String onPlaceholderRequest(Player player, String identifier) {
        if(player==null){
            return "";
        }
        if(identifier.equalsIgnoreCase("sign")){
            if(!Sign.signs.containsKey(player.getName())){
                return "";
            }
            Sign sign = Sign.signs.get(player.getName());
            Calendar now = Calendar.getInstance();
            if(sign.isSign(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DATE))){
                return "已签到";
            }else {
                return "未签到";
            }

        }
        return "";
    }

}
