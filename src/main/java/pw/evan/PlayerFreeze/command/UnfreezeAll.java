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

public class UnfreezeAll implements CommandExecutor
{
    private Main plugin;

    public UnfreezeAll(Main plugin)
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
        if (!(sender instanceof Player) || sender.hasPermission("playerfreeze.unfreezeall"))
        {
            for (Player currentPlayer : plugin.getServer().getOnlinePlayers())
            {
                User user = UserManager.get().getUser(currentPlayer);
                if(user.isFrozen())
                {
                    user.setFrozen(false);
                    UserManager.get().updateUser(user);
                }
            }
            sender.sendMessage(plugin.getShortPrefix() + ChatUtil.colorize("&bAll players have been unfrozen!"));
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this!");
        }

        return true;
    }
}
