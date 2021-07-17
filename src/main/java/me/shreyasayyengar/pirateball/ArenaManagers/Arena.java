package me.shreyasayyengar.pirateball.ArenaManagers;

import com.google.common.collect.TreeMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import me.shreyasayyengar.pirateball.GameManagers.Game;
import me.shreyasayyengar.pirateball.GameManagers.GameState;
import me.shreyasayyengar.pirateball.Teams.Team;
import me.shreyasayyengar.pirateball.Ultils.Config;
import me.shreyasayyengar.pirateball.Ultils.Countdown;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftSkull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Arena {

    @Getter
    private final int id;
    @Getter
    private final ArrayList<UUID> players;
    @Getter
    private final ArrayList<Team> totalTeams;
    @Getter
    private final HashMap<UUID, Team> teams;
    @Getter
    private Game game;


    @Getter
    @Setter
    private GameState state;
    private final Location spawn;
    private final Location lbySpawn;
    private Countdown countdown;

    public Arena(int id) {
        this.id = id;
        totalTeams = new ArrayList<>();
        players = new ArrayList<>();
        teams = new HashMap<>();
        countdown = new Countdown(this);
        spawn = Config.getArenaSpawn(id);
        lbySpawn = Config.getLobbySpawn();
        state = GameState.WAITING;
        game = new Game(this);
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        TreeMultimap<Integer, Team> count = TreeMultimap.create();

        for (Team team : Team.values()) {
            count.put(getTeamCount(team), team);

        }

        Team selected = (Team) count.values().toArray()[0];
        setTeam(player, selected);

        player.sendMessage(ChatColor.DARK_AQUA + "You have been put on the " + selected.getDisplayName() + ChatColor.DARK_AQUA + " team!");


        if (players.size() >= Config.getRequiredPlayers()) {
            countdown.begin();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(lbySpawn);
        removeTeam(player);

        if (players.size() <= Config.getRequiredPlayers() && state.equals(GameState.COUNTDOWN)) {
            reset();
        }

        if (players.size() == 0 && state.equals(GameState.LIVE)) {
            reset();
        }
    }

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle, int in, int on, int out) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle, in, on, out);
        }
    }

    public void start() {
        game.start();
    }

    public void reset() {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).teleport(lbySpawn);
        }

        state = GameState.WAITING;
        teams.clear();
        players.clear();
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void setTeam(Player player, Team team) {
        removeTeam(player);
        teams.put(player.getUniqueId(), team);
    }

    public void removeTeam(Player player) {
        if (teams.containsKey(player.getUniqueId())) {
            teams.remove(player.getUniqueId());
        }
    }

    public Team getTeam(Player player) {
        return teams.get(player.getUniqueId());
    }

    public int getTeamCount(Team team) {
        int amount = 0;

        for (Team t : teams.values()) {
            if (t.equals(team)) {
                amount++;
            }
        }
        return amount;
    }

    public void sendTeamMessage(Team team, String message) {
        for (UUID uuid : teams.keySet()) {
            if (teams.get(uuid) == team) {
                Bukkit.getPlayer(uuid).sendMessage(message);
            }
        }
    }

    public void sendTeamTitle(Team team, String title, String subtitle, int in, int stay, int out) {
        for (UUID uuid : teams.keySet()) {
            if (teams.get(uuid) == team) {
                Bukkit.getPlayer(uuid).sendTitle(title, subtitle, in, stay, out);

            }
        }
    }

    public void setTeamTabTags(Team team) {
        for (UUID uuid : teams.keySet()) {
            if (teams.get(uuid) == team) {
                Bukkit.getPlayer(uuid).setPlayerListName(team.getChatColor() + Bukkit.getPlayer(uuid).getName());
                Bukkit.getPlayer(uuid).setDisplayName(team.getChatColor() + Bukkit.getPlayer(uuid).getName());
            }
        }
    }

    public boolean isInTeamZone(CraftSkull block, Team team) {
        return team.getTeamZone(team).isInRegion(block.getLocation());
    }

}

