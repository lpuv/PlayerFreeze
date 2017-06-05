package pw.evan.PlayerFreeze.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.evan.PlayerFreeze.Main;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.util.ChatUtil;
import pw.evan.PlayerFreeze.util.TimeUtil;

public class FreezeAll implements CommandExecutor
{
    private Main plugin;

    public FreezeAll(Main plugin)
    {
        if (plugin == null)
        {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player) || sender.hasPermission("playerfreeze.freezeall"))
        {
            long frozenUntil = -1;
            long seconds = -1;
            if (args.length >= 1)
            { //we're going to be attempting to temp freeze
                try
                {
                    seconds = TimeUtil.parseTimeString(args[1]);
                    frozenUntil = TimeUtil.time() + seconds;
                }
                catch (IllegalArgumentException e)
                {
                    String errorMessage = plugin.getShortPrefix() + ChatUtil.colorize("&c" + e.getMessage());
                    sender.sendMessage(errorMessage);
                    return true;
                }
            }
            for (Player currentPlayer : plugin.getServer().getOnlinePlayers())
            {
                if(!currentPlayer.hasPermission("playerfreeze.exempt"))
                {
                    UserManager.User user = UserManager.get().getUser(currentPlayer);
                    user.setFrozenUntil(frozenUntil);
                    user.setFrozen(true);
                    UserManager.get().updateUser(user);
                }
            }
            if(frozenUntil>0)
            {
                sender.sendMessage(plugin.getShortPrefix() + ChatUtil.colorize("&bAll players have been frozen for &6" + TimeUtil.makeFormatString(seconds) + "&b!"));

            }
            else
            {
                sender.sendMessage(plugin.getShortPrefix() + ChatUtil.colorize("&bAll players have been frozen!"));
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this!");
        }

        return true;
    }
}