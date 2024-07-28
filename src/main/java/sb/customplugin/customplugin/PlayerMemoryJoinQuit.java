package sb.customplugin.customplugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import sb.customplugin.data.PlayerMemory;
import sb.customplugin.utility.PlayerUtility;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Handles the loading and saving of player's data.
 */
public class PlayerMemoryJoinQuit implements Listener{
    
    @EventHandler
    private void onJoin(PlayerJoinEvent event){
        PlayerUtility.loadPlayerMemory(event.getPlayer());
    }


    @EventHandler
    private void onQuit(PlayerQuitEvent event) throws PlayerUtility.NoPlayerMemoryError {
        PlayerMemory memory;

        try {
            memory = PlayerUtility.getPlayerMemory(event.getPlayer());
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            Bukkit.getLogger().info("This error occured while trying to process the player quiting (saving). This means there was no data to be saved in the first place. Either it got deleted somehow (bad) or the player just didn't happen to do anything that saved to PlayerMemory (good). I sure do hope it is the latter.");
            throw err;
        }

        File f = new File(PlayerUtility.getFolderPath(event.getPlayer()) + "/general.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.set("stats.unlockedPlayerVaults", memory.unlockedPlayerVaults);
        cfg.set("stats.maxHealth", memory.maxHealth);
        cfg.set("stats.health", memory.health);
        cfg.set("stats.maxMana", memory.maxMana);
        cfg.set("stats.mana", memory.mana);

        cfg.set("stats.balance", memory.balance);

        cfg.set("stats.level", memory.level);
        cfg.set("stats.maxExp", memory.maxExp);
        cfg.set("stats.currentExp", memory.currentExp);

        cfg.set("stats.attack", memory.attack);
        cfg.set("stats.defence", memory.defence);

        cfg.set("stats.effectHitRate", memory.effectHitRate);
        cfg.set("stats.critChance", memory.critChance);
        cfg.set("stats.critDMG", memory.critDMG);

        cfg.set("stats.dropChance", memory.dropChance);

        
        try{cfg.save(f);} catch (IOException e){ e.printStackTrace();}
        PlayerUtility.setPlayerMemory(event.getPlayer(),null);
    }
}


