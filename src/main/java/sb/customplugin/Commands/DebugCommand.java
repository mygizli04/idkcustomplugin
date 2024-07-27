package sb.customplugin.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import sb.customplugin.customplugin.CustomPlugin;
import sb.customplugin.customplugin.LevelUpSystem;
import sb.customplugin.utility.PermissionUtility;
import sb.customplugin.utility.PlayerUtility;

public class DebugCommand implements CommandExecutor {

    CustomPlugin plugin;

    public DebugCommand(CustomPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (!PermissionUtility.checkPermission(sender, "custom.debug")) return false;

        if (!command.getName().toLowerCase().equals("debug")) return false;

        Player player = (Player) sender;
        var memory = PlayerUtility.getPlayerMemory(player);

        switch (args[0]) {
            case "memory":
                switch (args[2]) {
                    case "get":
                        switch (args[1]) {
                            case "xp":
                                player.sendMessage("Your current xp is: " + memory.currentExp);
                                return true;
                            case "maxxp":
                                player.sendMessage("Your max xp is: " + memory.maxExp);
                                return true;
                            case "level":
                                player.sendMessage("Your level is: " + memory.level);
                                return true;
                            case "balance":
                                player.sendMessage("Your balance is: " + memory.balance);
                                return true;
                            case "vaults":
                                player.sendMessage("Unlocked vault count: " + memory.unlockedPlayerVaults);
                                return true;
                            default:
                                return false;
                        }
                    case "set":
                        int value = Integer.parseInt(args[3]);

                        switch (args[1]) {
                            case "xp":
                                LevelUpSystem.setPlayerCurrentExp(player, value);
                                player.sendMessage("Your new current xp is: " + memory.currentExp);
                                return true;
                            case "maxxp":
                                LevelUpSystem.setPlayerMaxExp(player, value);
                                player.sendMessage("Your new max xp is: " + memory.maxExp);
                                return true;
                            case "level":
                                LevelUpSystem.setPlayerLevel(player, value);
                                player.sendMessage("Your new level is: " + memory.level + ". Your xp has been reset.");
                                return true;
                            case "balance":
                                memory.balance = value;
                                player.sendMessage("Your new balance is: " + memory.balance);
                                return true;
                            case "vaults":
                                PermissionUtility.removePermission(player, "playervaults.amount." + memory.unlockedPlayerVaults);
                                memory.unlockedPlayerVaults = value;
                                PermissionUtility.addPermission(player, "playervaults.amount." + memory.unlockedPlayerVaults);
                                player.sendMessage("Your new vault count is: " + memory.unlockedPlayerVaults);
                                return true;

                            default:
                                return false;
                        }

                }
            default:
                return false;
        }
    }
}