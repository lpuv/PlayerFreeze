package pw.evan.PlayerFreeze.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.evan.PlayerFreeze.Main;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.model.User;
import pw.evan.PlayerFreeze.util.ChatUtil;
import pw.evan.PlayerFreeze.util.TimeUtil;

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
                User user = UserManager.get().getUser(playerName);
                if (user != null)
                {
                    if (args.length >= 2)
                    { //we're going to be attempting to temp freeze
                        try
                        {
                            long seconds = TimeUtil.parseTimeString(args[1]);
                            long frozenUntil = TimeUtil.time()+seconds;
                            user.setFrozenUntil(frozenUntil);
                            UserManager.get().updateUser(user);
                            sender.sendMessage(plugin.getShortPrefix()+ChatUtil.colorize("&bPlayer &d "+playerName+" &bhas been frozen for &6"+TimeUtil.makeFormatString(seconds)+"&b!"));
                        }
                        catch (IllegalArgumentException e)
                        {
                            String errorMessage = plugin.getShortPrefix()+ChatUtil.colorize("&c"+e.getMessage());
                            sender.sendMessage(errorMessage);
                        }
                    }
                    else
                    {
                        if(user.isFrozen())
                        {
                            sender.sendMessage(plugin.getShortPrefix()+ChatUtil.colorize("&cPlayer &d "+ playerName + " &cis already frozen!"));
                        }
                        else
                        {
                            user.setFrozen(true);
                            UserManager.get().updateUser(user);
                            sender.sendMessage(plugin.getShortPrefix()+ChatUtil.colorize("&bPlayer &d "+playerName+" &bhas been frozen!"));
                        }
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
