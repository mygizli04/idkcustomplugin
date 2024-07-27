package sb.customplugin.customplugin;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class FirstTimeJoin implements Listener {

    private CustomPlugin plugin;

    public FirstTimeJoin(CustomPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getLogger().info("Player joined");

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