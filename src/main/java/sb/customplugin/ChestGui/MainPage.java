package sb.customplugin.ChestGui;

import org.bukkit.inventory.Inventory;

import sb.customplugin.customplugin.CompassListener;
import sb.customplugin.utility.ItemUtility;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.entity.Player;

public class MainPage extends ChestGui {

    CompassListener compassListener;

    public MainPage(CompassListener compassListener) {
        this.compassListener = compassListener;
    }

    @Override
    public Inventory create(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Menu");

        inventory.setItem(19, ItemUtility.createItemWithName(Material.OAK_LOG, "Stats"));
        inventory.setItem(11, ItemUtility.createItemWithName(Material.DIAMOND_SWORD, "Equipment"));
        inventory.setItem(13, ItemUtility.createItemWithName(Material.ENDER_PEARL, "Play"));
        inventory.setItem(15, ItemUtility.createItemWithName(Material.SHIELD, "Skills"));
        inventory.setItem(25, ItemUtility.createItemWithName(Material.COMMAND_BLOCK, "Other"));

        return inventory;
    }

    @Override
    public void click(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();

        switch (slot) {
            case 19:
                compassListener.openChestGui(CompassListener.ChestGuiType.STATS, player);
                return;
            case 11:
                compassListener.openChestGui(CompassListener.ChestGuiType.EQUIPMENT, player);
                return;
            case 13:
                compassListener.openChestGui(CompassListener.ChestGuiType.PLAY, player);
                return;
            case 15:
                compassListener.openChestGui(CompassListener.ChestGuiType.SKILLS, player);
                return;
            case 25:
                compassListener.openChestGui(CompassListener.ChestGuiType.OTHER, player);
        }
    }
}