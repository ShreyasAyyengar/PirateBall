package me.shreyasayyengar.pirateball.ArenaManagers;

import com.google.common.collect.TreeMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.shreyasayyengar.pirateball.GameManagers.Game;
import me.shreyasayyengar.pirateball.GameManagers.GameState;
import me.shreyasayyengar.pirateball.Teams.Team;
import me.shreyasayyengar.pirateball.Utils.Countdown;
import me.shreyasayyengar.pirateball.Utils.configuration.Config;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Arena {

    private final int id;
    private final ArrayList<UUID> players;
    private final ArrayList<Team> totalTeams;
    private final HashMap<UUID, Team> teams;
    private final ArrayList<me.shreyasayyengar.pirateball.GameManagers.Team> team = new ArrayList<>();
    private final Location spawn;
    private final Location lbySpawn;
    private Game game;
    private GameState state;
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

        initTeams();
    }

    public int getId() {
        return id;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    private void initTeams() {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile gameProfile = null;
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "This ball is 1 of 4 balls that ");

        {
            skullMeta.setDisplayName(ChatColor.RED + "Red Ball");
            gameProfile = new GameProfile(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), null);
            gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMzYTViZmM4YTJhM2ExNTJkNjQ2YTViZWE2OTRhNDI1YWI3OWRiNjk0YjIxNGYxNTZjMzdjNzE4M2FhIn19fQ"));
            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.RED + "red " + ChatColor.GRAY + "team.");

            applySkullTexture(skull, skullMeta, gameProfile, lore);


            team.add(new me.shreyasayyengar.pirateball.GameManagers.Team("Red",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMzYTViZmM4YTJhM2ExNTJkNjQ2YTViZWE2OTRhNDI1YWI3OWRiNjk0YjIxNGYxNTZjMzdjNzE4M2FhIn19fQ",
                    ChatColor.RED, Color.RED, "&c", 'a',
                    Material.RED_BANNER, Material.RED_WOOL, Material.RED_STAINED_GLASS,
                    new CuboidRegion("world", new Location(Bukkit.getWorld("world"), 58, 16, 58), new Location(Bukkit.getWorld("world"), 9, 0, 9)),
                    UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), skull, new ArrayList<>()));
        }

        {
            skullMeta.setDisplayName(ChatColor.BLUE + "Blue Ball");
            gameProfile = new GameProfile(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"), null);
            gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDExMzdiOWJmNDM1YzRiNmI4OGZhZWFmMmU0MWQ4ZmQwNGUxZDk2NjNkNmY2M2VkM2M2OGNjMTZmYzcyNCJ9fX0"));
            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.BLUE + "blue " + ChatColor.GRAY + "team.");

            applySkullTexture(skull, skullMeta, gameProfile, lore);

            team.add(new me.shreyasayyengar.pirateball.GameManagers.Team("Blue",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDExMzdiOWJmNDM1YzRiNmI4OGZhZWFmMmU0MWQ4ZmQwNGUxZDk2NjNkNmY2M2VkM2M2OGNjMTZmYzcyNCJ9fX0",
                    ChatColor.BLUE, Color.BLUE, "&9", 'b',
                    Material.BLUE_BANNER, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS,
                    new CuboidRegion("world", new Location(Bukkit.getWorld("world"), 58, 16, -41), new Location(Bukkit.getWorld("world"), 9, 0, 7)),
                    UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"), skull, new ArrayList<>()));
        }

        {
            gameProfile = new GameProfile(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"), null);
            gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDExMzliM2VmMmU0YzQ0YTRjOTgzZjExNGNiZTk0OGQ4YWI1ZDRmODc5YTVjNjY1YmI4MjBlNzM4NmFjMmYifX19"));
            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.YELLOW + "yellow " + ChatColor.GRAY + "team.");

            applySkullTexture(skull, skullMeta, gameProfile, lore);

            team.add(new me.shreyasayyengar.pirateball.GameManagers.Team("Yellow",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDExMzliM2VmMmU0YzQ0YTRjOTgzZjExNGNiZTk0OGQ4YWI1ZDRmODc5YTVjNjY1YmI4MjBlNzM4NmFjMmYifX19",
                    ChatColor.YELLOW, Color.YELLOW, "&e", 'c',
                    Material.YELLOW_BANNER, Material.YELLOW_WOOL, Material.YELLOW_STAINED_GLASS,
                    new CuboidRegion("world", new Location(Bukkit.getWorld("world"), 41, 16, 58), new Location(Bukkit.getWorld("world"), 7, 0, 9)),
                    UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"), skull, new ArrayList<>()));
        }

        {
            skullMeta.setDisplayName(ChatColor.GREEN + "Green Ball");
            gameProfile = new GameProfile(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"), null);
            gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU0ODRmNGI2MzY3Yjk1YmIxNjI4ODM5OGYxYzhkZDZjNjFkZTk4OGYzYTgzNTZkNGMzYWU3M2VhMzhhNDIifX19"));
            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.GREEN + "green " + ChatColor.GRAY + "team.");

            applySkullTexture(skull, skullMeta, gameProfile, lore);

            team.add(new me.shreyasayyengar.pirateball.GameManagers.Team("Yellow",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU0ODRmNGI2MzY3Yjk1YmIxNjI4ODM5OGYxYzhkZDZjNjFkZTk4OGYzYTgzNTZkNGMzYWU3M2VhMzhhNDIifX19",
                    ChatColor.GREEN, Color.GREEN, "&a", 'd',
                    Material.GREEN_BANNER, Material.GREEN_BANNER, Material.GREEN_STAINED_GLASS,
                    new CuboidRegion("world", new Location(Bukkit.getWorld("world"), 41, 16, 58), new Location(Bukkit.getWorld("world"), 7, 0, 9)),
                    UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"), skull, new ArrayList<>()));
        }
    }

    public static void applySkullTexture(ItemStack skull, SkullMeta skullMeta, GameProfile gameProfile, ArrayList<String> lore) {
        try {
            Field field;
            field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
            x.printStackTrace();
        }

        skullMeta.setLore(lore);
        skull.setItemMeta(skullMeta);
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
        teams.remove(player.getUniqueId());
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
}

