package sb.customplugin.customplugin;

import org.bukkit.plugin.java.JavaPlugin;

import sb.customplugin.Commands.DebugCommand;
import sb.customplugin.Commands.DebugCommandTabComplete;

/**
 * Created by Levi Muniz on 7/29/20.
 *
 * @author Copyright (c) Levi Muniz. All Rights Reserved.
 */
public class CustomPlugin extends JavaPlugin {

  public LevelUpSystem levelUpSystem;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    FirstTimeJoin firstTimeJoin = new FirstTimeJoin(this);
    CompassListener compassListener = new CompassListener(this);
    PlayerMemoryJoinQuit playerMemoryJoinQuitListener = new PlayerMemoryJoinQuit();

    getServer().getPluginManager().registerEvents(compassListener, this);
    getServer().getPluginManager().registerEvents(firstTimeJoin, this);
    getServer().getPluginManager().registerEvents(playerMemoryJoinQuitListener, this);

    var board = new Board();

    getServer().getScheduler().runTaskTimer(this, board, 0, 1);

    getCommand("debug").setExecutor(new DebugCommand(this));
    getCommand("debug").setTabCompleter(new DebugCommandTabComplete());

    levelUpSystem = new LevelUpSystem();
  }

}
