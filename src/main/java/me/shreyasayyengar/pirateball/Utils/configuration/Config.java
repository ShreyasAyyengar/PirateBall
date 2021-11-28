package me.shreyasayyengar.pirateball.Utils.configuration;

import me.shreyasayyengar.pirateball.PirateBall;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Config {

    private static PirateBall main;

    public Config(PirateBall main) {
        Config.main = main;
        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    public static int getRequiredPlayers() {
        return main.getConfig().getInt("required-players");
    }

    public static int getCountdownSeconds() {
        return main.getConfig().getInt("countdown-seconds");
    }

    public static Location getLobbySpawn() {
        return new Location(
                Bukkit.getWorld(main.getConfig().getString("lobby-spawn.world")),
                main.getConfig().getDouble("lobby-spawn.x"),
                main.getConfig().getDouble("lobby-spawn.y"),
                main.getConfig().getDouble("lobby-spawn.z"),
                main.getConfig().getInt("lobby-spawn.yaw"),
                main.getConfig().getInt("lobby-spawn.pitch")
        );
    }

    public static Location getArenaSpawn(int id) {
        return new Location(
                Bukkit.getWorld(main.getConfig().getString("arenas." + id + ".spawn.world")),
                main.getConfig().getDouble("arenas." + id + ".spawn.x"),
                main.getConfig().getDouble("arenas." + id + ".spawn.y"),
                main.getConfig().getDouble("arenas." + id + ".spawn.z"),
                main.getConfig().getInt("arenas." + id + ".spawn.yaw"),
                main.getConfig().getInt("arenas." + id + ".spawn.pitch")
        );
    }

    public static int getArenaAmount() {
       return main.getConfig().getConfigurationSection("arenas.").getKeys(false).size();
    }
}

