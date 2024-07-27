package sb.customplugin.utility;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

/**
 * Utilities regarding permissions (luckperms)
 */
public class PermissionUtility {
    static public RegisteredServiceProvider<LuckPerms> lpProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    static public LuckPerms luckpermsApi = lpProvider.getProvider();

    /**
     * Check if a luckperms user passes a permission check.
     * 
     * @param user The luckperms user to check.
     * @param permission The permission to test the user on.
     * @return The result of the check.
     */
    static public boolean checkPermission(User user, String permission) {
        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    /**
     * Check if a player passes a permission check.
     * 
     * @param player The player to check.
     * @param permission The permission to test the player on.
     * @return The result of the check.
     */
    static public boolean checkPermission(Player player, String permission) {
        return checkPermission(luckpermsApi.getPlayerAdapter(Player.class).getUser(player), permission);
    }

    /**
     * Check if a command sender passes a permission check. Will always return true for console.
     * 
     * @param sender The sender to check.
     * @param permission The permission to test the sender on.
     * @return The result of the check.
     */
    static public boolean checkPermission(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            return checkPermission((Player) sender, permission);
        }
        else {
            return true;
        }
    }

    /**
     * Add a permission to a user.
     * 
     * @param player The player to add the permission to.
     * @param permission The permission to add to the player.
     */
    static public void addPermission(Player player, String permission) {
        User user = PermissionUtility.luckpermsApi.getPlayerAdapter(Player.class).getUser(player);

        user.data().add(Node.builder(permission).build());

        PermissionUtility.luckpermsApi.getUserManager().saveUser(user);
    }

    /**
     * Remove a permission from a user.
     * 
     * @param player The player to remove the permission from.
     * @param permission The permission to remove from the player.
     */
    static public void removePermission(Player player, String permission) {
        User user = PermissionUtility.luckpermsApi.getPlayerAdapter(Player.class).getUser(player);

        user.data().remove(Node.builder(permission).build());

        PermissionUtility.luckpermsApi.getUserManager().saveUser(user);
    }
}
