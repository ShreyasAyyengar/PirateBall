package me.shreyasayyengar.pirateball.GameManagers;

import me.shreyasayyengar.pirateball.ArenaManagers.Arena;
import me.shreyasayyengar.pirateball.Teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static me.shreyasayyengar.pirateball.GameManagers.GameUtils.setArmor;

public class Game {

    private final Arena arena;
    private final HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "The game has started. Be the first to break 20 blocks to win!");

        arena.setTeamTabTags(Team.RED);
        arena.setTeamTabTags(Team.BLUE);
        arena.setTeamTabTags(Team.YELLOW);
        arena.setTeamTabTags(Team.GREEN);

        for (UUID uuid : arena.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            points.put(uuid, 0);
            Bukkit.getPlayer(uuid).closeInventory();
            setArmor(player, arena.getTeam(player).getColor(), arena.getTeam(player));
        }
    }


    public void addPoint(Player player) {
        int p = points.get(player.getUniqueId()) + 1;

        if (p == 20) {
            arena.sendTitle(ChatColor.GOLD + player.getName() + " Wins!", null, 20, 100, 20);

            arena.reset();
            return;
        }
        points.replace(player.getUniqueId(), p);
    }
}
