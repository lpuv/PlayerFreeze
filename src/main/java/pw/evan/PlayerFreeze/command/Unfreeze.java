package pw.evan.PlayerFreeze.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.evan.PlayerFreeze.Main;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.util.ChatUtil;

public class Unfreeze implements CommandExecutor
{
    private Main plugin;
    public Unfreeze(Main plugin){
        if(plugin==null){
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player) || sender.hasPermission("playerfreeze.unfreeze"))
        {
            if (args.length >= 1)
            {
                String playerName = args[0];
                UserManager.User user = UserManager.get().getUser(playerName);
                if (user != null)
                {
                    if(!user.isFrozen())
                    {
                        sender.sendMessage(plugin.getShortPrefix() + ChatUtil.colorize("&cPlayer &d " + playerName + " &cis not frozen!"));
                    }
                    else
                    {
                        user.setFrozen(false);
                        UserManager.get().updateUser(user);
                        sender.sendMessage(plugin.getShortPrefix() + ChatUtil.colorize("&bPlayer &d " + playerName + " &bhas been unfrozen!"));
                    }
                }
                else
                {
                    sender.sendMessage(ChatUtil.colorize("&cPlayer &d " + playerName + " &cwas not found!"));
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
