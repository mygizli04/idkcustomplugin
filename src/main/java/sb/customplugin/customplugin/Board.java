package sb.customplugin.customplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;
import sb.customplugin.data.PlayerMemory;
import sb.customplugin.utility.PlayerUtility;

/**
 * Handles the creating and updating of the scoreboard sidebar.
 */
public class Board implements Runnable {


    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){

            if (player.getScoreboard() != null && player.getScoreboard().getObjective("TestName") != null){
                updateScoreboard(player);
            }
            else{
                createNewScoreboard(player);
            }
        }
    }
    


    /**
     * Creates and displays a new scoreboard for the player if one does not already exist.
     * 
     * @param player The player the scoreboard will be created for.
     */
    private void createNewScoreboard(Player player){

        PlayerMemory memory;

        try {
            memory = PlayerUtility.getPlayerMemory(player);
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            return; // Player's been kicked at this point.
        }


        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("TestName", Criteria.DUMMY, ChatColor.YELLOW +"Testing");
        
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //objective.getScore(ChatColor.GREEN+"Level: "+ChatColor.WHITE + memory.level).setScore(3);


        Team team2 = scoreboard.registerNewTeam("team2");
        String team2Key = ChatColor.GOLD.toString() + "";

        team2.addEntry(team2Key);
        team2.setPrefix(ChatColor.GREEN + "Level: ");
        team2.setSuffix(memory.level+"");
        
        objective.getScore(team2Key).setScore(3);


        Team team1 = scoreboard.registerNewTeam("team1");
        String teamKey = ChatColor.GOLD.toString() + " ";


        team1.addEntry(teamKey);
        team1.setPrefix("[][][][][][][][][][]");
        team1.setSuffix(" ");

        objective.getScore(teamKey).setScore(2);

        objective.getScore(ChatColor.WHITE+" ").setScore(1);


        Team team3 = scoreboard.registerNewTeam("team3");
        String team3Key = "§r";

        team3.addEntry(team3Key);
        team3.setPrefix(ChatColor.GREEN + "Balance: ");
        team3.setSuffix(memory.balance + "");

        objective.getScore(team3Key).setScore(0);


        player.setScoreboard(scoreboard);
        
    }

    /**
     * Updates the scoreboard of the player with the appropriate memory values.
     * 
     * @param player The player the scoreboard will be updated for.
     */
    private void updateScoreboard(Player player){
        PlayerMemory memory;

        try {
            memory = PlayerUtility.getPlayerMemory(player);
        }
        catch (PlayerUtility.NoPlayerMemoryError err) {
            return; // Player's been kicked at this point.
        }

        Scoreboard scoreboard = player.getScoreboard();
        Team team1 = scoreboard.getTeam("team1");
        Team team2 = scoreboard.getTeam("team2");
        Team team3 = scoreboard.getTeam("team3");
        team2.setSuffix(memory.level+"");

        var partOne = ChatColor.GREEN+ "";
        var partTwo = ChatColor.WHITE+"";

        int result = (int) Math.round(((double) memory.currentExp/ (double) memory.maxExp)*10);


        // Bukkit.broadcastMessage(memory.currentExp+"");
        for (int index = 0; index < result; index++) {
            partOne = partOne+"[]";
        }

        for (int index = 0; index < (10 - result); index++) {
            partTwo = partTwo+"[]";
            
        }

        team3.setSuffix(memory.balance + "");

        var finalresult = partOne + partTwo;

        team1.setPrefix(finalresult);
        

    }
}
