package pw.evan.PlayerFreeze.scheduler;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pw.evan.PlayerFreeze.Main;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.model.User;
import pw.evan.PlayerFreeze.util.ActionBarAPIUtil;
import pw.evan.PlayerFreeze.util.ChatUtil;
import pw.evan.PlayerFreeze.util.TimeUtil;

public class MessageBroadcaster extends BukkitRunnable
{
    private Main plugin;
    public MessageBroadcaster(Main plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public void run()
    {
        for(Player current: plugin.getServer().getOnlinePlayers())
        {
            User currentUser = UserManager.get().getUser(current);
            if(currentUser.getFrozenUntil() > 0)
            {
                if(ActionBarAPIUtil.hasActionBarAPI(plugin))
                {
                    long secondsLeft = currentUser.getFrozenUntil()-TimeUtil.time();
                    ActionBarAPI.sendActionBar(current, ChatUtil.colorize("&aYou are frozen for &6"+TimeUtil.makeFormatString(secondsLeft)+"&b!"));
                }
            }
            else if(currentUser.isFrozen())
            {
                if(ActionBarAPIUtil.hasActionBarAPI(plugin))
                {
                    ActionBarAPI.sendActionBar(current, ChatUtil.colorize("&bYou are currently frozen!"));
                }
            }
        }
    }
}
