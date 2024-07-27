package sb.customplugin.customplugin;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import sb.customplugin.ChestGui.ChestGui;
import sb.customplugin.ChestGui.EquipmentPage;
import sb.customplugin.ChestGui.MainPage;
import sb.customplugin.ChestGui.StatsPage;
import sb.customplugin.utility.PlayerUtility;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;

import java.lang.Runnable;

/**
 * Event listener that handles events relating to the main GUI that is accessed via a recovery compass.
 */
public class CompassListener implements Listener {

    private final CustomPlugin plugin;

    final Map<ChestGuiType, ChestGui> chestGuiMap = new HashMap<>();

    public CompassListener(CustomPlugin plugin) {
        this.plugin = plugin;

        chestGuiMap.put(ChestGuiType.MAIN, new MainPage(this));
        chestGuiMap.put(ChestGuiType.STATS, new StatsPage(this));
        chestGuiMap.put(ChestGuiType.EQUIPMENT, new EquipmentPage());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Material itemType = event.getItemDrop().getItemStack().getType();

        if (itemType == Material.RECOVERY_COMPASS) {
            event.setCancelled(true);
        }
    }

    /**
     * Types of GUI that are accessible from the compass GUI.
     */
    public static enum ChestGuiType {
        MAIN,
        STATS,
        EQUIPMENT,
        PLAY,
        SKILLS,
        OTHER
    }

    final Map<Player, ChestGuiType> playersWithChestGuiOpen = new HashMap<>();
    final Map<Player, Boolean> playersWithChestGuiOpening = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType() == Material.RECOVERY_COMPASS) {
            openChestGui(ChestGuiType.MAIN, player);
        }
    }

    /**
     * Open a specific chest gui (from the compass) for a specific player.
     * 
     * @param gui The GUI that will be shown to the player.
     * @param player The player to show the GUI to.
     */
    public void openChestGui(ChestGuiType gui, Player player) {

        var chestGui = chestGuiMap.get(gui);

        if (chestGui == null) {
            Bukkit.broadcastMessage("chest gui not implemented lol");
            return;
        }

        Inventory inventory;

        try {
            inventory = chestGui.create(player);
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            return; // Player's been kicked at this point.
        }

        player.openInventory(inventory);

        playersWithChestGuiOpen.put(player, gui);

        playersWithChestGuiOpening.put(player, true);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                playersWithChestGuiOpening.put(player, false);
            }
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ChestGuiType chestGuiOpen = playersWithChestGuiOpen.get(player);

        if (chestGuiOpen != null) {

            var chestGui = chestGuiMap.get(chestGuiOpen);

            try {
                chestGui.click(event);
            }
            catch (PlayerUtility.NoPlayerMemoryError err) {
                return; // Player's been kicked at this point.
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        Boolean chestGuiOpening = playersWithChestGuiOpening.get(player);

        if ((chestGuiOpening != null) && (chestGuiOpening != false)) {
            return;
        }

        playersWithChestGuiOpen.put(player, null);
    }

}