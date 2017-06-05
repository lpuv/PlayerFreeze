package pw.evan.PlayerFreeze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.model.User;
import pw.evan.PlayerFreeze.util.TimeUtil;

public class PlayerConnectionListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        User user = UserManager.get().getUser(e.getPlayer()); //will create and save the user if it is not found
        if(user.getFrozenUntil()< TimeUtil.time())
        {
            user.setFrozen(false);
            UserManager.get().updateUser(user);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        UserManager.get().uncacheUser(e.getPlayer().getUniqueId());
    }
}
