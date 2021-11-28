package me.shreyasayyengar.pirateball.Utils;

import me.shreyasayyengar.pirateball.ArenaManagers.Arena;
import me.shreyasayyengar.pirateball.GameManagers.GameState;
import me.shreyasayyengar.pirateball.GameManagers.Team;
import me.shreyasayyengar.pirateball.Utils.configuration.Config;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static ArrayList<Arena> arenas;
    private static List<Team> teams;

    public Manager() {
        arenas = new ArrayList<>();
        for (int i = 0; i <= (Config.getArenaAmount() - 1); i++) {
            arenas.add(new Arena(i));
        }
    }

    public static ArrayList<Arena> getArenas() {
        return arenas;
    }

    public static boolean isPlaying(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlayingLive(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId()) && arena.getState() == GameState.LIVE) {
                return true;
            }
        }
        return false;
    }

    public static Arena getArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return arena;
            }
        }
        return null;
    }

    public static Arena getArena(int id) {
        for (Arena arena : arenas) {
            if (arena.getId() == id)    {
                return arena;
            }
        }
        return null;
    }

    public static boolean isJoinable(int id) {
        return getArena(id).getState() == GameState.WAITING;
    }
}
