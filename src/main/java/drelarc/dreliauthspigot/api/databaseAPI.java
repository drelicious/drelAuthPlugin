package drelarc.dreliauthspigot.api;
import drelarc.dreliauthspigot.DreliAuthSpigot;
import org.bukkit.Bukkit;

import java.sql.*;

public class databaseAPI {
    static DreliAuthSpigot plugin;

    public databaseAPI(DreliAuthSpigot instance) {
        plugin = instance;
    }

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * checkConnection()
     *
     * @return boolean
     * @params
     */
    public static String checkConnection(String DB_URL, String USER, String PASS) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM `player`");
            while (rs.next()) {
                return "Connected to database!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failed to connect to database!";
    }

    /**
     * checkIfTableExist
     *
     * @return boolean
     * @params
     */
    public static boolean checkIfTableExist(String DB_URL, String USER, String PASS) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM `player`");
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        createTable(DB_URL, USER, PASS);
        return false;
    }

    /**
     * createTable()
     *
     * @return boolean
     * @params
     */
    public static boolean createTable(String DB_URL, String USER, String PASS) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE player " +
                    "(playerName VARCHAR(255), " +
                    " code VARCHAR(255), " +
                    " PRIMARY KEY ( playerName ))";
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * generateCode()
     *
     * @param
     * @return String code
     */
    public static String generateCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += (int) (Math.random() * 10);
        }
        return code;
    }

    /**
     * storeCode()
     * Store code on the MYSQL
     *
     * @param playerName
     * @return String
     */
    public static String storeCode(String playerName, String DB_URL, String USER, String PASS) {
        String code = databaseAPI.generateCode();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO player " +
                    "VALUES ('" + playerName + "', '" + code + "')";
            stmt.executeUpdate(sql);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILED";
    }

    /**
     * clearEverythingOnTable()
     *
     * @return boolean
     * @params
     */
    public static boolean clearEverythingOnTable(String DB_URL, String USER, String PASS) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "DELETE FROM player";
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
