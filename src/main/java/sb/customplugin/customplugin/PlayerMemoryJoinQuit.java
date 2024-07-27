package sb.customplugin.customplugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import sb.customplugin.data.PlayerMemory;
import sb.customplugin.utility.PlayerUtility;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerMemoryJoinQuit implements Listener{
    
    @EventHandler
    private void onJoin(PlayerJoinEvent event){
        PlayerMemory memory = new PlayerMemory();
        File f = new File(PlayerUtility.getFolderPath(event.getPlayer()) + "/general.yml");

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

        PlayerUtility.setPlayerMemory(event.getPlayer(), memory);
    }


    @EventHandler
    private void onQuit(PlayerQuitEvent event){
        PlayerMemory memory = PlayerUtility.getPlayerMemory(event.getPlayer());
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


