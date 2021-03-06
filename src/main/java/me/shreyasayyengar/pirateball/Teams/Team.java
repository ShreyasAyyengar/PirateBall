package me.shreyasayyengar.pirateball.Teams;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.shreyasayyengar.pirateball.ArenaManagers.Arena;
import me.shreyasayyengar.pirateball.ArenaManagers.CuboidRegion;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public enum Team {

    RED(ChatColor.RED + "Red", ChatColor.RED, "&c", Color.RED, Material.RED_BANNER, Material.RED_WOOL, Material.RED_STAINED_GLASS, 'a'),
    BLUE(ChatColor.BLUE + "Blue", ChatColor.BLUE, "&9", Color.BLUE, Material.BLUE_BANNER, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS, 'b'),
    YELLOW(ChatColor.YELLOW + "Yellow", ChatColor.YELLOW, "&e", Color.YELLOW, Material.YELLOW_BANNER, Material.YELLOW_WOOL, Material.YELLOW_STAINED_GLASS, 'c'),
    GREEN(ChatColor.GREEN + "Green", ChatColor.GREEN, "&2", Color.LIME, Material.LIME_BANNER, Material.LIME_WOOL, Material.LIME_STAINED_GLASS, 'd');

    private final String displayName;
    private final ChatColor chatColor;
    private final String chatColorChar;
    private final Color color;
    private final Material banner;
    private final Material wool;
    private final Material glass;
    private final char ballChar;

    Team(String displayName, ChatColor chatColor, String chatColorChar, Color color, Material banner, Material wool, Material glass, char ballChar) {
        this.displayName = displayName;
        this.chatColor = chatColor;
        this.chatColorChar = chatColorChar;
        this.color = color;
        this.banner = banner;
        this.wool = wool;
        this.glass = glass;
        this.ballChar = ballChar;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Material getBannerMaterial() {
        return this.banner;
    }

    public Material getTeamWool() {
        return this.wool;
    }

    public Material getTeamGlass() {
        return this.glass;
    }

    public Color getColor() {
        return this.color;
    }

    public ChatColor getChatColor() {
        return this.chatColor;
    }

    public String getChatColorChar() {
        return this.chatColorChar;
    }

    public char getOwnTeamBallChar() {
        return this.ballChar;
    }

    public CuboidRegion getTeamZone(Team team) {
        switch (team) {
            case RED -> {
                return new CuboidRegion("Red",
                        new Location(Bukkit.getWorld("world"), 58, 16, 58),
                        new Location(Bukkit.getWorld("world"), 9, 4, 9));
            }
            case BLUE -> {
                return new CuboidRegion("Blue",
                        new Location(Bukkit.getWorld("world"), 58, 16, -41),
                        new Location(Bukkit.getWorld("world"), 9, 4, 7));
            }
            case YELLOW -> {
                return new CuboidRegion("Yellow",
                        new Location(Bukkit.getWorld("world"), -41, 16, 58),
                        new Location(Bukkit.getWorld("world"), 7, 4, 9));
            }
            case GREEN -> {
                return new CuboidRegion("Green",
                        new Location(Bukkit.getWorld("world"), -41, 16, -41),
                        new Location(Bukkit.getWorld("world"), 7, 4, 7));
            }
        }
        return null;
    }

    public ItemStack getTeamBall() {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile gameProfile = null;
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "This ball is 1 of 4 balls that ");

        switch (this) {
            case RED -> {
                skullMeta.setDisplayName(ChatColor.RED + "Red Ball");
                gameProfile = new GameProfile(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), null);
                gameProfile.getProperties().put("textures", new Property("textures", this.getTeamBallTexture(Team.RED)));
                lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.RED + "red " + ChatColor.GRAY + "team.");
            }
            case BLUE -> {
                skullMeta.setDisplayName(this.getChatColor() + "Blue Ball");
                gameProfile = new GameProfile(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"), null);
                gameProfile.getProperties().put("textures", new Property("textures", this.getTeamBallTexture(Team.BLUE)));
                lore.add(ChatColor.GRAY + "belongs to the " + this.getChatColor() + "blue " + ChatColor.GRAY + "team.");
            }
            case YELLOW -> {
                skullMeta.setDisplayName(this.getChatColor() + "Yellow Ball");
                gameProfile = new GameProfile(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"), null);
                gameProfile.getProperties().put("textures", new Property("textures", this.getTeamBallTexture(Team.YELLOW)));
                lore.add(ChatColor.GRAY + "belongs to the " + this.getChatColor() + "yellow " + ChatColor.GRAY + "team.");
            }
            case GREEN -> {
                skullMeta.setDisplayName(this.getChatColor() + "Green Ball");
                gameProfile = new GameProfile(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"), null);
                gameProfile.getProperties().put("textures", new Property("textures", this.getTeamBallTexture(Team.GREEN)));
                lore.add(ChatColor.GRAY + "belongs to the " + this.getChatColor() + "green " + ChatColor.GRAY + "team.");
            }
        }

        Arena.applySkullTexture(skull, skullMeta, gameProfile, lore);
        return skull;
    }

    public String getTeamBallTexture(Team team) {

        switch (team) {
            case RED -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMzYTViZmM4YTJhM2ExNTJkNjQ2YTViZWE2OTRhNDI1YWI3OWRiNjk0YjIxNGYxNTZjMzdjNzE4M2FhIn19fQ";
            }
            case BLUE -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDExMzdiOWJmNDM1YzRiNmI4OGZhZWFmMmU0MWQ4ZmQwNGUxZDk2NjNkNmY2M2VkM2M2OGNjMTZmYzcyNCJ9fX0";
            }
            case YELLOW -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDExMzliM2VmMmU0YzQ0YTRjOTgzZjExNGNiZTk0OGQ4YWI1ZDRmODc5YTVjNjY1YmI4MjBlNzM4NmFjMmYifX19";
            }
            case GREEN -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU0ODRmNGI2MzY3Yjk1YmIxNjI4ODM5OGYxYzhkZDZjNjFkZTk4OGYzYTgzNTZkNGMzYWU3M2VhMzhhNDIifX19";
            }
        }
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2FmMDM5YmVjMWZjMWZiNzUxOTYwOTJiMjZlNjMxZjM3YTg3ZGZmMTQzZmMxODI5Nzc5OGQ0N2M1ZWFhZiJ9fX0="; // white
    }

    public UUID getBallUUID(Team team) {
        switch (team) {
            case RED -> {
                return UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
            }

            case BLUE -> {
                return UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
            }

            case YELLOW -> {
                return UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
            }

            case GREEN -> {
                return UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");
            }
        }
        return null;
    }

    public boolean isSkullInTeamZone(CraftSkull block) {
        return getTeamZone(this).isInRegion(block.getLocation());
    }
}
