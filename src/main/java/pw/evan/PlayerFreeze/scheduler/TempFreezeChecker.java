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

public class TempFreezeChecker extends BukkitRunnable
{
    private Main plugin;
    public TempFreezeChecker(Main plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public void run()
    {
        for(Player current: plugin.getServer().getOnlinePlayers())
        {
            User currentUser = UserManager.get().getUser(current);
            if(currentUser.getFrozenUntil() > 0 && currentUser.getFrozenUntil() < TimeUtil.time())
            {
                currentUser.setFrozen(false);
                UserManager.get().updateUser(currentUser);
                if(ActionBarAPIUtil.hasActionBarAPI(plugin))
                {
                    ActionBarAPI.sendActionBar(current, ChatUtil.colorize("&aYou have been unfrozen!"),40);
                }
            }
        }
    }
}
