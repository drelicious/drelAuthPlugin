package drelarc.dreliauthspigot;
import drelarc.dreliauthspigot.api.databaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class events implements Listener {
    DreliAuthSpigot plugin;
    public events(DreliAuthSpigot instance) {
        plugin = instance;
    }

    public String DB_URL() {
        String DB_URL = "jdbc:mysql://" + plugin.getConfig().getString("MYSQLIP") + "/" + plugin.getConfig().getString("MYSQLDB");
        return DB_URL;
    }
    public String USER() {
        String USER = (String) plugin.getConfig().getString("MYSQLUser");
        return USER;
    }
    public String PASS() {
        String PASS = (String) plugin.getConfig().getString("MYSQLPASS");
        return PASS;
    }

// kalau nggak work, masuknya kedalam playerJoin - drelicious signing off!
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        String DB_URL = "jdbc:mysql://" + plugin.getConfig().getString("MYSQLIP") + "/" + plugin.getConfig().getString("MYSQLDB");
        String USER = (String) plugin.getConfig().getString("MYSQLUser");
        String PASS = (String) plugin.getConfig().getString("MYSQLPASS");


        Player player = event.getPlayer();
        if (!plugin.getConfig().getString("MYSQLIP").equals("")) {
            if (databaseAPI.checkIfTableExist(DB_URL, USER, PASS)) {
                player.kickPlayer(databaseAPI.storeCode(player.getName(), DB_URL, USER, PASS));
            } else {
                player.kickPlayer(databaseAPI.storeCode(player.getName(), DB_URL, USER, PASS));
            }
    } else {
        player.kickPlayer(ChatColor.RED + "MYSQL Database is not set, please contact server admin!");
        Bukkit.getLogger().severe("MYSQL DATABASE IS NOT SET, ALL PLAYER AUTH DATA CAN'T BE SAVED!!!");
        }
    }
@EventHandler
    public void serverOn(PluginEnableEvent e) {
        Bukkit.getLogger().info("MYSQL Auto-Delete is active! EVERY 30 MINUTES!");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                databaseAPI.clearEverythingOnTable(DB_URL(), USER(), PASS());
            }
        }, 0L, 18000L);
    }

}
