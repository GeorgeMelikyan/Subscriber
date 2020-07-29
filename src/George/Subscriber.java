package George;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Subscriber extends JavaPlugin {
    public static ConsoleCommandSender consoleCommandSender;
    public static String prefix;
    public static File config;

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String[] color(String[] msg) {
        for (int i = 0; i < msg.length; i++) {
            msg[i] = ChatColor.translateAlternateColorCodes('&', msg[i]);
        }
        return msg;
    }

    public void onEnable() {
        consoleCommandSender = getServer().getConsoleSender();
        prefix = color("&8(&cADVENTURES&8) &7");
        config = new File(getDataFolder() + File.separator + "config.yml");

        if (!config.exists()) {
            saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new Login(this), this);
        consoleCommandSender.sendMessage(prefix + "Subscriber enabled.");
    }

    public void onDisable() {
        consoleCommandSender.sendMessage(prefix + "Subscriber disabled.");
    }


}
