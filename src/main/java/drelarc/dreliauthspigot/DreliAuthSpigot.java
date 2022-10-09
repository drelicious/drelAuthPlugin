package drelarc.dreliauthspigot;

import drelarc.dreliauthspigot.api.databaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DreliAuthSpigot extends JavaPlugin {
    @Override
    public void onEnable() {
        final FileConfiguration config = this.getConfig();
       if (!config.getBoolean("doNotRemoveThisCheckKeepItTrueOrElseItWillBeResetted")) {
           config.addDefault("MYSQLIP", "");
           config.addDefault("MYSQLUser", "");
           config.addDefault("MYSQLDB", "");
           config.addDefault("MYSQLPASS", "");
           config.addDefault("doNotRemoveThisCheckKeepItTrueOrElseItWillBeResetted", true);
           config.options().copyDefaults(true);
           saveConfig();
       }

    Bukkit.getLogger().info("drelAuthSpigot is activated!");
        PluginManager pluginmanager = getServer().getPluginManager();
        pluginmanager.registerEvents(new events(this), this);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("test11!");
    }
}
