package pw.evan.PlayerFreeze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pw.evan.PlayerFreeze.manager.UserManager;

public class PlayerMoveListener implements Listener
{
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        UserManager.User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen())
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        UserManager.User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen())
        {
            event.setCancelled(true);
        }
    }
}
