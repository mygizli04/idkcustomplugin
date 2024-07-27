package sb.customplugin.customplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import sb.customplugin.data.PlayerMemory;
import sb.customplugin.utility.PlayerUtility;

/**
 * This class contains methods that relate to the progression system in the game.
 */
public class LevelUpSystem {

    /**
     * Add experience to a player. They will be leveled up if necessary.
     * 
     * @param player The player to add experience to.
     * @param exp The amount of experience to give to the player.
     */
    public static void addExperience(Player player, int exp) throws PlayerUtility.NoPlayerMemoryError {
        PlayerMemory memory;

        try {
            memory = PlayerUtility.getPlayerMemory(player);
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            Bukkit.getLogger().info("This occured while trying to add experience to the player. This data has been lost.");
            throw err;
        }
        
        memory.currentExp += exp;
        
        // Check if the player has enough experience to level up
        while (memory.currentExp >= memory.maxExp) {
            memory.currentExp -= memory.maxExp;
            memory.level++;
            memory.maxExp = calculateMaxExpForLevel(memory.level); // Update maxExp for the new level

            // Optionally, increase player stats or handle other level-up logic here
            
            // Send a message to the player
            player.sendMessage("Congratulations! You leveled up to level " + memory.level);
        }
        
        // Save updated memory
        PlayerUtility.setPlayerMemory(player, memory);
    }

    /**
     * Calculate the amount of experience a player needs to level up given their current level.
     * 
     * @param level The player's current level
     * @return The amount of experience the player needs to level up.
     */
    private static int calculateMaxExpForLevel(int level) {
        // Example logic: exponential growth
        return (int) (20 * Math.pow(1.5, level - 1));
    }

    /**
     * Set a player's current experience. They will be leveled up if necessary.
     * 
     * @param player The player to set the experience of.
     * @param xp The amount of experience to set for the player.
     */
    public static void setPlayerCurrentExp(Player player, int xp) throws PlayerUtility.NoPlayerMemoryError {
        PlayerMemory memory;

        try {
            memory = PlayerUtility.getPlayerMemory(player);
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            Bukkit.getLogger().info("This error occured while trying to set the player's current xp. This data has been lost.");
            throw err;
        }

        if (xp < 0) throw new Error("Cannot set xp below 0.");

        memory.currentExp = xp;

        // Making sure the proper level-up logic is run in case XP is set above the max xp
        addExperience(player, 0);
    }

    /**
     * Set a player's required xp in order for them to level up. This will not modify their current xp but will level them up if necessary.
     * 
     * @param player The player to set the max xp of.
     * @param maxXp The amount to set the player's max xp to.
     */
    public static void setPlayerMaxExp(Player player, int maxXp) throws PlayerUtility.NoPlayerMemoryError {
        PlayerMemory memory;

        try {
            memory = PlayerUtility.getPlayerMemory(player);
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            Bukkit.getLogger().info("This error occured while trying to set the player's max xp. This data has been lost.");
            throw err;
        }

        if (maxXp < 0) throw new Error("Cannot set max xp below 0.");

        memory.maxExp = maxXp;

        // Making sure the proper level-up logic is run in case current XP is set above the max xp
        addExperience(player, 0);
    }

    /**
     * Set a player's level. Will reset their current xp to 0 and their max xp will be set to the required value given by {@link #calculateMaxExpForLevel(int)}.
     * 
     * @param player The player to set the level of.
     * @param level The level to set the player's level.
     */
    public static void setPlayerLevel(Player player, int level) throws PlayerUtility.NoPlayerMemoryError {
        PlayerMemory memory;

        try {
            memory = PlayerUtility.getPlayerMemory(player);
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            Bukkit.getLogger().info("This error occured while trying to set the player's level. This data has been lost.");
            throw err;
        }

        if (level < 1) throw new Error("Cannot set level to below 1.");

        memory.level = level;
        memory.currentExp = 0;
        memory.maxExp = calculateMaxExpForLevel(level);
    }
}
