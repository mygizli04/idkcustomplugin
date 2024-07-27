package sb.customplugin.ChestGui;

import org.bukkit.inventory.Inventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public abstract class ChestGui {
    public abstract Inventory create(Player player);

    public abstract void click(InventoryClickEvent event);
}