package pw.evan.PlayerFreeze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pw.evan.PlayerFreeze.manager.UserManager;

public class PlayerBlockListener implements Listener
{
    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent event)
    {
        UserManager.User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent event)
    {
        UserManager.User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event)
    {
        UserManager.User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen()){
            event.setCancelled(true);
        }
    }
}
