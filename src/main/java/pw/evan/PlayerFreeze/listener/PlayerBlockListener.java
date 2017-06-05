package pw.evan.PlayerFreeze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.model.User;

public class PlayerBlockListener implements Listener
{
    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent event)
    {
        User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent event)
    {
        User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event)
    {
        User user = UserManager.get().getUser(event.getPlayer());
        if(user.isFrozen()){
            event.setCancelled(true);
        }
    }
}
