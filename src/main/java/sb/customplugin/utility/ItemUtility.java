package sb.customplugin.utility;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class ItemUtility {

    public static ItemStack createItemWithName(Material type, String name) {

        ItemStack itemStack = new ItemStack(type, 1);
        var meta = itemStack.getItemMeta();

        meta.setDisplayName(name);

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}