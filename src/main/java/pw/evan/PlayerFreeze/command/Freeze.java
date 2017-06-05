package pw.evan.PlayerFreeze.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import pw.evan.PlayerFreeze.Main;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.util.ChatUtil;

public class Freeze implements CommandExecutor
{
    private Main plugin;
    public Freeze(Main plugin){
        if(plugin==null){
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player) || sender.hasPermission("playerfreeze.freeze"))
        {
            if (args.length >= 1)
            {
                String playerName = args[0];
                UserManager.User user = UserManager.get().getUser(playerName);
                if (user != null)
                {
                    if (args.length >= 2)
                    { //we're going to be attempting to temp freeze
                        //lets get temp freezing running later
                    }
                    else
                    {
                        user.setFrozen(true);
                    }
                }
                else
                {
                    sender.sendMessage(ChatUtil.colorize("&cPlayer &d" + playerName + " &cwas not found!"));
                }
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this!");
        }
        return true;
    }
}
