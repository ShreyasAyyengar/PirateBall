package me.shreyasayyengar.pirateball.GameManagers;

import me.shreyasayyengar.pirateball.ArenaManagers.CuboidRegion;
import me.shreyasayyengar.pirateball.PirateBall;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class Team {

    private final String displayName;
    private final String ballTexture;
    private final String chatColorChar;
    private final char ballChar;

    private final ChatColor chatColor;
    private final Color color;

    private final Material banner;
    private final Material wool;
    private final Material glass;

    private final CuboidRegion zone;
    private final UUID ballUUID;
    private final ItemStack ball;

    private final List<UUID> players;

    public Team(String displayName, String ballTexture, ChatColor chatColor, Color color, String chatColorChar, char ballChar, Material banner, Material wool, Material glass, CuboidRegion zone, UUID ballUUID, ItemStack ball, List<UUID> players) {
        this.displayName = displayName;
        this.ballTexture = ballTexture;
        this.chatColor = chatColor;
        this.color = color;
        this.chatColorChar = chatColorChar;
        this.ballChar = ballChar;
        this.banner = banner;
        this.wool = wool;
        this.glass = glass;
        this.zone = zone;
        this.ballUUID = ballUUID;
        this.ball = ball;
        this.players = players;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBallTexture() {
        return ballTexture;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Color getColor() {
        return color;
    }

    public String getChatColorChar() {
        return chatColorChar;
    }

    public char getBallChar() {
        return ballChar;
    }

    public Material getBanner() {
        return banner;
    }

    public Material getWool() {
        return wool;
    }

    public Material getGlass() {
        return glass;
    }

    public CuboidRegion getZone() {
        return zone;
    }

    public UUID getBallUUID() {
        return ballUUID;
    }

    public ItemStack getBall() {
        return ball;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void sendTeamMessage(String message) {
        players.forEach(player -> Bukkit.getPlayer(player).sendMessage(message));
    }

    public void sendTeamTitle(String title, String subtitle, int in, int stay, int out) {
        players.forEach(player -> Bukkit.getPlayer(player).sendTitle(title, subtitle, in, stay, out));
    }

    public void sendActionBar(String message, int duration) {

        players.forEach(player -> sendActionBar(Bukkit.getPlayer(player), message));

        if (duration >= 0) {
            // Sends empty message at the end of the duration. This allows messages shorter than 3 seconds, ensures precision.
            new BukkitRunnable() {
                @Override
                public void run() {
                    players.forEach(player -> sendActionBar(Bukkit.getPlayer(player), ""));
                }
            }.runTaskLater(PirateBall.getInstance(), duration + 1);
        }

        // Re-sends the messages every 3 seconds, so it doesn't go away from the player's screen.
        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    players.forEach(player -> sendActionBar(Bukkit.getPlayer(player), message));
                }
            }.runTaskLater(PirateBall.getInstance(), duration);
        }
    }

    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}