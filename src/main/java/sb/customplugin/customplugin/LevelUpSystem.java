package sb.customplugin.customplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import sb.customplugin.data.PlayerMemory;
import sb.customplugin.utility.PlayerUtility;

public class LevelUpSystem {

    // This method is used to add experience to a player and handle leveling up
    public static void addExperience(Player player, int exp) {
        PlayerMemory memory = PlayerUtility.getPlayerMemory(player);
        
        if (memory == null) {
            player.sendMessage("An error occured while trying to add xp to your profile. Please contact an admin about this issue.");
            Bukkit.getLogger().info("Failed to increase user's xp as PlayerUtility.getPlayerMemory returned null.");
            return;
        }
        
        memory.currentExp += exp;
        
        // Check if the player has enough experience to level up
        while (memory.currentExp >= memory.maxExp) {
            memory.currentExp -= memory.maxExp;
            memory.level++;
            memory.maxExp = calculateNewMaxExp(memory.level); // Update maxExp for the new level

            // Optionally, increase player stats or handle other level-up logic here
            
            // Send a message to the player
            player.sendMessage("Congratulations! You leveled up to level " + memory.level);
        }
        
        // Save updated memory
        PlayerUtility.setPlayerMemory(player, memory);
    }

    // Example method to calculate new maxExp required for the next level
    private static int calculateNewMaxExp(int level) {
        // Example logic: exponential growth
        return (int) (20 * Math.pow(1.5, level - 1));
    }

    public static void setPlayerCurrentExp(Player player, int xp) {
        PlayerMemory memory = PlayerUtility.getPlayerMemory(player);

        if (xp < 0) throw new Error("Cannot set xp below 0.");

        memory.currentExp = xp;

        // Making sure the proper level-up logic is run in case XP is set above the max xp
        addExperience(player, 0);
    }

    public static void setPlayerMaxExp(Player player, int maxXp) {
        PlayerMemory memory = PlayerUtility.getPlayerMemory(player);

        if (maxXp < 0) throw new Error("Cannot set max xp below 0.");

        memory.maxExp = maxXp;

        // Making sure the proper level-up logic is run in case current XP is set above the max xp
        addExperience(player, 0);
    }

    public static void setPlayerLevel(Player player, int level) {
        PlayerMemory memory = PlayerUtility.getPlayerMemory(player);

        if (level < 1) throw new Error("Cannot set level to below 1.");

        memory.level = level;
        memory.currentExp = 0;
        memory.maxExp = calculateNewMaxExp(level);
    }
}
