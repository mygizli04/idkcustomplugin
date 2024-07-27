package sb.customplugin.ChestGui;

import org.bukkit.inventory.Inventory;

import sb.customplugin.utility.PlayerUtility;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

/**
 * A chest gui that can be presented to a user.
 */
public abstract class ChestGui {
    /**
     * Creates the chest that will be presented to the player.
     * <br> <br>
     * If you are implementing this feature do <i>not</i> make the player open the gui, that will be done by the caller of this function.
     * 
     * @param player The player which will be presented the gui.
     * @return The inventory that will be presented.
     */
    public abstract Inventory create(Player player) throws PlayerUtility.NoPlayerMemoryError;

    /**
     * Handles the click events that occur while the inventory is open. It may include items that are not in the chest gui such as a player's inventory.
     * 
     * @param event The {@link InventoryClickEvent} concerned.
     */
    public abstract void click(InventoryClickEvent event) throws PlayerUtility.NoPlayerMemoryError;
}