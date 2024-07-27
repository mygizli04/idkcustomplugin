package sb.customplugin.ChestGui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import sb.customplugin.utility.ItemUtility;
import sb.customplugin.utility.PermissionUtility;
import sb.customplugin.utility.PlayerUtility;

/**
 * GUI for managing playervaults.
 */
public class EquipmentPage extends ChestGui {

    /**
     * Get the cost of purchasing a specific vault.
     * 
     * @param vaultNumber The vault number to calculate the cost of.
     * @return The cost of the vault.
     */
    private int getCostOfVault(int vaultNumber) {
        return (int) Math.ceil(1000 * (Math.pow(1.5, vaultNumber - 1)));
    }

    // private int getCostOfNextVault(Player player) {
        // var memory = PlayerUtility.getPlayerMemory(player);

        // return getCostOfVault(memory.unlockedPlayerVaults + 1);
    // }

    @Override
    public Inventory create(Player player) throws PlayerUtility.NoPlayerMemoryError {
        Inventory inventory = Bukkit.createInventory(null, 54, "Equipment");

        int unlockedPlayerVaults = PlayerUtility.getPlayerMemory(player).unlockedPlayerVaults;

        inventory.setItem(0, ItemUtility.createItemWithName(Material.NETHERITE_SWORD, "Unlocked playervaults: " + unlockedPlayerVaults));
        inventory.setItem(2, ItemUtility.createItemWithName(Material.GOLD_BLOCK, "Buy new playervault for " + getCostOfVault(unlockedPlayerVaults + 1) + " coins."));

        
        if (unlockedPlayerVaults > 36) unlockedPlayerVaults = 36;

        for (int i = 0; i < unlockedPlayerVaults; i++) {
            inventory.setItem((i + 9), ItemUtility.createItemWithName(Material.ENDER_CHEST, "Open playervault " + (i + 1)));
        }

        inventory.setItem(53, ItemUtility.createItemWithName(Material.COMMAND_BLOCK_MINECART, "/pv <number> to use vaults"));

        return inventory;
    }

    @Override
    public void click(InventoryClickEvent event) throws PlayerUtility.NoPlayerMemoryError {
        int slot = event.getRawSlot();
        int vault = slot - 8;

        Player player = (Player) event.getWhoClicked();
        var memory = PlayerUtility.getPlayerMemory(player);

        if ((slot > 8) && (slot < 45)) {
            if (vault > memory.unlockedPlayerVaults) {
                return;
            }
            else {
                player.chat("/pv " + vault);
            }
        }

        if (slot == 2) {
            int cost = getCostOfVault(memory.unlockedPlayerVaults + 1);

            boolean success = PlayerUtility.executePurhcase(player, cost);

            if (!success) {
                player.sendMessage("You do not have enough money to purchase another vault!");
                return;
            }

            User user = PermissionUtility.luckpermsApi.getPlayerAdapter(Player.class).getUser(player);

            user.data().remove(Node.builder("playervaults.amount." + memory.unlockedPlayerVaults).build());

            memory.unlockedPlayerVaults += 1;

            user.data().add(Node.builder("playervaults.amount." + memory.unlockedPlayerVaults).build());

            PermissionUtility.luckpermsApi.getUserManager().saveUser(user);
            
            player.sendMessage("Success!");
            player.closeInventory();
        }
    }
}
