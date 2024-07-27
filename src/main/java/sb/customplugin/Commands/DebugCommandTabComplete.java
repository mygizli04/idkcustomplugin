package sb.customplugin.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import sb.customplugin.utility.PermissionUtility;

/**
 * Tab complete for {@link DebugCommand}
 */
public class DebugCommandTabComplete implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) { 
        List<String> completions = new ArrayList<>();
        
        if (!PermissionUtility.checkPermission(sender, "custom.debug")) return completions;

        if (!command.getName().toLowerCase().equals("debug")) {
            sender.sendMessage("command name not debug, detected " + command.getName().toLowerCase());
        };

        switch (args[0]) {
            case "memory":
                if (args.length > 3) {
                    return completions;
                }
                else if (args.length == 3) {
                    completions.add("get");
                    completions.add("set");
                }
                else {
                    completions.add("xp");
                    completions.add("maxxp");
                    completions.add("level");
                    completions.add("balance");
                    completions.add("vaults");
                }
                break;
            default:
                completions.add("memory");
        }

        completions.removeIf(completion -> !completion.startsWith(args[args.length - 1]));

        return completions;
    }

}
