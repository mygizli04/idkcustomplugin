package sb.customplugin.utility;

import java.util.HashMap;
import java.util.Map;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import sb.customplugin.customplugin.CustomPlugin;
import sb.customplugin.data.PlayerMemory;

/**
 * The utility to manage saving, loading and manipulating user data.
 */
public class PlayerUtility {
    /**
     * Map of all currently loaded player data.
     */
    private static Map<String, PlayerMemory> playerMemory = new HashMap<>();

    /**
     * The error to be thrown when a method tries accessing player memory when it is not available. All player memory is created upon that player joining so if one does not exists then we just kick the player and error out to prevent data corruption.
     */
    public static class NoPlayerMemoryError extends Throwable {
        public NoPlayerMemoryError() {
            super("No player memory was found when one was expected! The player has been kicked.");
        }
    }

    /**
     * Get a player's data (memory).
     * 
     * @param player The player to get the memory of.
     * @return The memory of the player.
     * 
     * @throws NoPlayerMemoryError If a player's memory is not accessible, an error will be thrown. Player memory is assumed to always be accessible.
     */
    public static PlayerMemory getPlayerMemory(Player player) throws NoPlayerMemoryError {
        var memory = playerMemory.get(player.getUniqueId().toString());
        
        if (memory == null) {
            player.kickPlayer("Error retrieving data for your profile, sorry! Please contact an admin about this error.");
            Bukkit.getLogger().info("Player memory for player " + player.getName() + " is null!");

            try {
                String folderPath = getFolderPath(player);
                Bukkit.getLogger().info("Memory folder path for player: " + folderPath);
            }
            catch (java.lang.NullPointerException err) {
                Bukkit.getLogger().info("This error is caused because getFolderPath() cannot determine the plugin path!");
            }

            throw new NoPlayerMemoryError();
        }

        return memory;
    }

    /**
     * Set a player's memory.
     * 
     * @param player The player to set the memory of.
     * @param memory The memory to set for the player.
     */
    public static void setPlayerMemory(Player player, PlayerMemory memory){
        playerMemory.put(player.getUniqueId().toString(), memory);
    }

    /**
     * Get the file path which stores the player's memory.
     * 
     * @param player The player to retrieve the memory file for.
     * @return The memory file.
     */
    public static String getFolderPath(Player player){
        CustomPlugin plugin = (CustomPlugin) Bukkit.getServer().getPluginManager().getPlugin("CustomPlugin");

        return plugin.getDataFolder().getAbsolutePath() + "/ServerData/player/" + player.getUniqueId();
    }

    public static void loadPlayerMemory(Player player) {
        PlayerMemory memory = new PlayerMemory();
        File f = new File(getFolderPath(player) + "/general.yml");

        if (f.exists()){
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            memory.unlockedPlayerVaults = cfg.getInt("stats.unlockedPlayerVaults");
            memory.maxHealth = cfg.getDouble("stats.maxHealth");
            memory.health = cfg.getDouble("stats.health");
            memory.maxMana = cfg.getDouble("stats.maxMana");
            memory.mana = cfg.getDouble("stats.mana");

            memory.balance = cfg.getInt("stats.balance");


            memory.level = cfg.getInt("stats.level");
            memory.maxExp = cfg.getInt("stats.maxExp");
            memory.currentExp = cfg.getInt("stats.currentExp");

            memory.effectHitRate = cfg.getDouble("stats.effectHitRate");
            memory.critChance = cfg.getDouble("stats.critChance");
            memory.critDMG = cfg.getInt("stats.critDMG");
            
            memory.dropChance = cfg.getInt("stats.dropChance");

        }else{
            memory.maxHealth = 20;
            memory.health = 20;
            memory.maxMana = 20;
            memory.mana = 20;


            memory.balance = 100;

            memory.level = 1;
            memory.maxExp = 20;
            memory.currentExp = 0;

            memory.attack = 10;
            memory.defence = 5;

            memory.effectHitRate = 10;
            memory.critChance = 5;
            memory.critDMG = 50;

            memory.dropChance = 5;

        }

        setPlayerMemory(player, memory);
    }

    /**
     * Execute a purchase for a player. If the player has enough balance to cover the cost their balance will be deducted, else their balance will not be affected.
     * 
     * @param player The plaeyr to execute a purchase for.
     * @param cost The cost to charge the player.
     * @return true if the player had enough money to cover the transaction, false if not.
     * 
     * @throws PlayerUtility.NoPlayerMemoryError If the player memory could not be accessed. They will have been kicked by the time this error is thrown.
     */
    public static boolean executePurhcase(Player player, int cost) throws PlayerUtility.NoPlayerMemoryError {
        PlayerMemory memory;

        try {
            memory = getPlayerMemory(player);
        }
        catch (NoPlayerMemoryError err) {
            Bukkit.getLogger().info("This error occured while trying to execute a purchase for the player. This data has been lost. Their balance (or items) have not been modified.");
            throw err;
        }

        if (cost > memory.balance) {
            return false;
        }

        memory.balance -= cost;
        return true;
    }
}
