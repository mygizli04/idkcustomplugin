package sb.customplugin.utility;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class PermissionUtility {
    static public RegisteredServiceProvider<LuckPerms> lpProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    static public LuckPerms luckpermsApi = lpProvider.getProvider();

    static public boolean checkPermission(User user, String permission) {
        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    static public boolean checkPermission(Player player, String permission) {
        return checkPermission(luckpermsApi.getPlayerAdapter(Player.class).getUser(player), permission);
    }

    static public boolean checkPermission(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            return checkPermission((Player) sender, permission);
        }
        else {
            return true;
        }
    }

    static public void addPermission(Player player, String permission) {
        User user = PermissionUtility.luckpermsApi.getPlayerAdapter(Player.class).getUser(player);

        user.data().add(Node.builder(permission).build());

        PermissionUtility.luckpermsApi.getUserManager().saveUser(user);
    }

    static public void removePermission(Player player, String permission) {
        User user = PermissionUtility.luckpermsApi.getPlayerAdapter(Player.class).getUser(player);

        user.data().remove(Node.builder(permission).build());

        PermissionUtility.luckpermsApi.getUserManager().saveUser(user);
    }
}
