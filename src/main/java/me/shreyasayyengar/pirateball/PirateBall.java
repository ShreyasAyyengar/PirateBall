package me.shreyasayyengar.pirateball;

import me.shreyasayyengar.pirateball.ArenaManagers.Arena;
import me.shreyasayyengar.pirateball.ArenaManagers.ArenaCommand;
import me.shreyasayyengar.pirateball.ArenaManagers.ArenaCommandTabCompleter;
import me.shreyasayyengar.pirateball.GameManagers.GameListener;
import me.shreyasayyengar.pirateball.Ultils.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PirateBall extends JavaPlugin implements Listener {

    private static PirateBall instance;
    private Arena arena;

    public static PirateBall getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        PirateBall.instance = this;
        getLogger().info("Starting Up...");

        new Config(this);
        getLogger().config("Configuration Initialisation Starting...");

        new Manager();
        getLogger().info("Manager Initialisation...");
        getLogger().info("Creating Arenas...");

        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("arena").setTabCompleter(new ArenaCommandTabCompleter());
        getCommand("removeholo").setExecutor(new DebugCommand());
        getCommand("skullball").setExecutor(new SkullCommand());
        getLogger().info("Registering Arena Commands:");
        Bukkit.getPluginManager().registerEvents(new GameListener(arena), this);
        Bukkit.getPluginManager().registerEvents(this, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                FloatingItem.getFloatingItems().stream().filter(i -> i.getArmorStand() != null).forEach(FloatingItem::update);
            }
        }.runTaskTimerAsynchronously(this, 0, 1);
    }

    public void onDisable() {
        getLogger().warning("Shutting down...");
    }

}

