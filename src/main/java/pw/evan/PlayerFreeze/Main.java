package pw.evan.PlayerFreeze;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pw.evan.PlayerFreeze.command.Freeze;
import pw.evan.PlayerFreeze.command.FreezeAll;
import pw.evan.PlayerFreeze.command.Unfreeze;
import pw.evan.PlayerFreeze.command.UnfreezeAll;
import pw.evan.PlayerFreeze.listener.PlayerBlockListener;
import pw.evan.PlayerFreeze.listener.PlayerMoveListener;
import pw.evan.PlayerFreeze.manager.UserManager;
import pw.evan.PlayerFreeze.scheduler.MessageBroadcaster;
import pw.evan.PlayerFreeze.scheduler.TempFreezeChecker;
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
        ChatUtil.console(this,getPluginPrefix()+"&aVersion &bv"+getDescription().getVersion()+" &aenabling...");
        UserManager.get(this); //create a UserManager

        saveDefaultConfig();

        getCommand("freeze").setExecutor(new Freeze(this));
        getCommand("freezeall").setExecutor(new FreezeAll(this));
        getCommand("unfreeze").setExecutor(new Unfreeze(this));
        getCommand("unfreezeall").setExecutor(new UnfreezeAll(this));

        getServer().getPluginManager().registerEvents(new PlayerBlockListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(),this);

        new TempFreezeChecker(this).runTaskTimer(this,0,20);
        new MessageBroadcaster(this).runTaskTimer(this,0,20);

        ChatUtil.console(this,getPluginPrefix()+"&aVersion &bv"+getDescription().getVersion()+" &aenabled!");
    }

    public void onDisable()
    {
        ChatUtil.console(this,getPluginPrefix()+"&cVersion &bv"+getDescription().getVersion()+" &cdisabled!");
    }
}
