package sb.customplugin.ChestGui;

import org.bukkit.inventory.Inventory;

import sb.customplugin.customplugin.CompassListener;
import sb.customplugin.utility.ItemUtility;
import sb.customplugin.utility.PlayerUtility;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.entity.Player;

public class StatsPage extends ChestGui {

    CompassListener compassListener;

    public StatsPage(CompassListener compassListener) {
        this.compassListener = compassListener;
    }

    @Override
    public Inventory create(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Stats");

        var memory = PlayerUtility.getPlayerMemory(player);

        inventory.setItem(0, ItemUtility.createItemWithName(Material.GLASS_BOTTLE, "Mana: " + memory.mana));
        inventory.setItem(1, ItemUtility.createItemWithName(Material.GOLD_BLOCK, "Balance: " + memory.balance));
        inventory.setItem(2, ItemUtility.createItemWithName(Material.DIAMOND_SWORD, "Level: " + memory.level));
        inventory.setItem(3, ItemUtility.createItemWithName(Material.ENCHANTED_GOLDEN_APPLE, "Max Health: " + memory.maxHealth));
        inventory.setItem(4, ItemUtility.createItemWithName(Material.GOLDEN_APPLE, "Health: " + memory.health));
        inventory.setItem(5, ItemUtility.createItemWithName(Material.WATER_BUCKET, "Max Mana: " + memory.maxMana));
        inventory.setItem(6, ItemUtility.createItemWithName(Material.EXPERIENCE_BOTTLE, "Max Exp: " + memory.maxExp));
        inventory.setItem(7, ItemUtility.createItemWithName(Material.OMINOUS_BOTTLE, "Current Exp: " + memory.currentExp));
        inventory.setItem(8, ItemUtility.createItemWithName(Material.WOODEN_SWORD, "Attack: " + memory.attack));
        inventory.setItem(9, ItemUtility.createItemWithName(Material.SHIELD, "Defence: " + memory.defence));
        inventory.setItem(10, ItemUtility.createItemWithName(Material.PISTON, "Effect Hit Rate: " + memory.effectHitRate));
        inventory.setItem(11, ItemUtility.createItemWithName(Material.END_ROD, "Crit. chance: " + memory.critChance));
        inventory.setItem(12, ItemUtility.createItemWithName(Material.SHEEP_SPAWN_EGG, "Crit. damage: " + memory.critDMG));
        inventory.setItem(13, ItemUtility.createItemWithName(Material.TNT, "Drop chance: " + memory.dropChance));

        return inventory;
    }

    @Override
    public void click(InventoryClickEvent event) {}
}