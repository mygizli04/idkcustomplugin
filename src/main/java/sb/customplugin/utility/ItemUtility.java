package sb.customplugin.utility;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

/**
 * Utilities relating to items and item management.
 */
public class ItemUtility {

    /**
     * Creates an item with a specific name. Adding lore is currently not implemented. Formatting is reset by default.
     * 
     * @param type The type of item to create.
     * @param name The name to give the item.
     * @return The resulting item.
     */
    public static ItemStack createItemWithName(Material type, String name) {

        ItemStack itemStack = new ItemStack(type, 1);
        var meta = itemStack.getItemMeta();

        meta.setDisplayName("§r" + name);

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}