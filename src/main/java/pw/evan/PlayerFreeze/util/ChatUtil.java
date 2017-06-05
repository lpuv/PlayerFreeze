package pw.evan.PlayerFreeze.util;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import pw.evan.PlayerFreeze.Main;

public class ChatUtil
{
    public static String colorize(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    public static void console(Main plugin, String message){
        plugin.getServer().getConsoleSender().sendMessage(colorize(message));
    }
}
