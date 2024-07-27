package sb.customplugin.customplugin;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

/**
 * Handles events relating to players that join the server for their first time.
 */
public class FirstTimeJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if you joined for the first time.
        if (player.hasPlayedBefore()) {
            // Nope not their first time.
            return;
        }

        // First time joining.

        player.getInventory().addItem(new ItemStack(Material.RECOVERY_COMPASS, 1));
    }
}