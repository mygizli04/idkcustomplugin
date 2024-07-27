package sb.customplugin.utility;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import sb.customplugin.customplugin.CustomPlugin;
import sb.customplugin.data.PlayerMemory;

public class PlayerUtility {
    private static Map<String, PlayerMemory> playerMemory = new HashMap<>();

    public static PlayerMemory getPlayerMemory(Player p){
        return playerMemory.get(p.getUniqueId().toString());
    }

    public static void setPlayerMemory(Player p, PlayerMemory memory){
        if(memory == null) playerMemory.put(p.getUniqueId().toString(), memory);
        else playerMemory.put(p.getUniqueId().toString(), memory);
    }

    public static String getFolderPath(Player p){
        CustomPlugin plugin = (CustomPlugin) Bukkit.getServer().getPluginManager().getPlugin("ExamplePlugin");

        return plugin.getDataFolder().getAbsolutePath() + "/ServerData/player/" + p.getUniqueId();
    }

    public static boolean executePurhcase(Player player, int cost) {
        var memory = getPlayerMemory(player);

        if (cost > memory.balance) {
            return false;
        }

        memory.balance -= cost;
        return true;
    }
}
