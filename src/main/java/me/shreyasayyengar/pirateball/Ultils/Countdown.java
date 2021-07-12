package me.shreyasayyengar.pirateball.Ultils;

import me.shreyasayyengar.pirateball.ArenaManagers.Arena;
import me.shreyasayyengar.pirateball.GameManagers.GameState;
import me.shreyasayyengar.pirateball.PirateBall;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private final Arena arena;
    private int seconds;

    public Countdown(Arena arena) {
        this.arena = arena;
        this.seconds = Config.getCountdownSeconds();
    }

    public void begin() {
        arena.setState(GameState.COUNTDOWN);
        this.runTaskTimer(PirateBall.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds == 0) {
            cancel();
            arena.start();
            return;
        }

        if (seconds % 30 == 0 || seconds <= 10) {
            if (seconds == 1) {
                arena.sendMessage(ChatColor.YELLOW + "The game will start in " + ChatColor.RED + "1" + ChatColor.YELLOW + " second!");
            } else {
                arena.sendMessage(ChatColor.YELLOW + "The game will start in " + ChatColor.RED + seconds + ChatColor.YELLOW + " seconds!");

            }
        }

        if (arena.getPlayers().size() < Config.getRequiredPlayers()) {
            cancel();
            arena.setState(GameState.WAITING);
            arena.sendMessage(ChatColor.RED + "Too little players, start cancelled");
            arena.sendTitle(ChatColor.RED + "Waiting for more players...", null, 0, 200, 20);
            return;
        }

        seconds--;
    }
}
