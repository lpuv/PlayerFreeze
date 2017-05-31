package pw.evan.PlayerFreeze.util;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.plugin.Plugin;

public class ActionBarAPIUtil
{
    public static boolean hasActionBarAPI(Plugin plugin){
        return getActionBarAPI(plugin)!=null;
    }

    public static ActionBarAPI getActionBarAPI(Plugin plugin){
        Plugin actionBarAPI = plugin.getServer().getPluginManager().getPlugin("ActionBarAPI");
        if(actionBarAPI != null && actionBarAPI instanceof ActionBarAPI){
            return (ActionBarAPI) actionBarAPI;
        }
        else
        {
            return null;
        }
    }
}
