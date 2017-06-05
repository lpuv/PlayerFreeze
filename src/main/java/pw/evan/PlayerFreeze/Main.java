package pw.evan.PlayerFreeze;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.util.ChatUtil;

public class Main extends JavaPlugin
{
    public String getPluginPrefix()
    {
        return ChatUtil.colorize("&8[&aPlayer&bFreeze&8]&r ");
    }

    public String getShortPrefix()
    {
        return ChatUtil.colorize("&b❄ &r");
    }

    public void onEnable()
    {
        
        UserManager.get(this); //create a UserManager
    }

    public void onDisable()
    {

    }
}
