package sb.customplugin.data;

/**
 * The data the server remembers across restarts for a given player.
 */
public class PlayerMemory {

    /**
     * The amount of playervault's a player has access to. May not reflect their actual amount since that is stored in luckperms but this <i>should</i> be accurate.
     */
    public int unlockedPlayerVaults;

    //Basic Stats
    public double maxHealth;
    public double health;

    public double maxMana;
    public double mana;

    /**
     * A measure of how many years the player could stay alive if they were sent to a deserted island alone only with their money as food then they had to eat the (gold) coins and (gold) paper money. We assume the gold is perfectly edible and contains all nutrients a human needs to survive (though tbh steve can perfectly eat gold since he can eat gold carrots). So the only limiting factor here would be the mass.
     */
    public int balance;

    /**
     * Units of love the player gets in a year (none but we'll pretend they get some)
     */
    public int level;

    /**
     * Maximum execution points
     */
    public int maxExp;

    /**
     * current execution points
     */
    public int currentExp;

    public int attack;
    public int defence;

    public double effectHitRate;
    public double critChance;
    public double critDMG;

    public double dropChance;

}

