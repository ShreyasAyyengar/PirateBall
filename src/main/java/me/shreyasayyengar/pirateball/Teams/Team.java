package me.shreyasayyengar.pirateball.Teams;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.shreyasayyengar.pirateball.ArenaManagers.CuboidRegion;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
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
        return banner;
    }

    public Material getTeamWool() {
        return wool;
    }

    public Material getTeamGlass() {
        return glass;
    }

    public Color getColor() {
        return color;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public String getChatColorChar() {
        return chatColorChar;
    }

    public char getOwnTeamBallChar() {
        return ballChar;
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

    public ItemStack getTeamBall(Team team) {

        switch (team) {
            case RED -> {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setDisplayName(ChatColor.RED + "Red Ball");

                GameProfile gameProfile = new GameProfile(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), null);
                gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMzYTViZmM4YTJhM2ExNTJkNjQ2YTViZWE2OTRhNDI1YWI3OWRiNjk0YjIxNGYxNTZjMzdjNzE4M2FhIn19fQ=="));
                Field field;
                try {
                    field = skullMeta.getClass().getDeclaredField("profile");
                    field.setAccessible(true);
                    field.set(skullMeta, gameProfile);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
                    x.printStackTrace();
                }

                skull.setItemMeta(skullMeta);
                return skull;
            }
            case BLUE -> {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setDisplayName(ChatColor.DARK_BLUE + "Red Ball");

                GameProfile gameProfile = new GameProfile(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"), null);
                gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDExMzdiOWJmNDM1YzRiNmI4OGZhZWFmMmU0MWQ4ZmQwNGUxZDk2NjNkNmY2M2VkM2M2OGNjMTZmYzcyNCJ9fX0="));
                Field field;
                try {
                    field = skullMeta.getClass().getDeclaredField("profile");
                    field.setAccessible(true);
                    field.set(skullMeta, gameProfile);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
                    x.printStackTrace();
                }

                skull.setItemMeta(skullMeta);
                return skull;
            }
            case YELLOW -> {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setDisplayName(ChatColor.YELLOW + "Red Ball");

                GameProfile gameProfile = new GameProfile(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"), null);
                gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDExMzliM2VmMmU0YzQ0YTRjOTgzZjExNGNiZTk0OGQ4YWI1ZDRmODc5YTVjNjY1YmI4MjBlNzM4NmFjMmYifX19"));
                Field field;
                try {
                    field = skullMeta.getClass().getDeclaredField("profile");
                    field.setAccessible(true);
                    field.set(skullMeta, gameProfile);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
                    x.printStackTrace();
                }

                skull.setItemMeta(skullMeta);
                return skull;
            }
            case GREEN -> {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setDisplayName(ChatColor.GREEN + "Red Ball");

                GameProfile gameProfile = new GameProfile(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"), null);
                gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU0ODRmNGI2MzY3Yjk1YmIxNjI4ODM5OGYxYzhkZDZjNjFkZTk4OGYzYTgzNTZkNGMzYWU3M2VhMzhhNDIifX19="));
                Field field;
                try {
                    field = skullMeta.getClass().getDeclaredField("profile");
                    field.setAccessible(true);
                    field.set(skullMeta, gameProfile);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
                    x.printStackTrace();
                }

                skull.setItemMeta(skullMeta);
                return skull;
            }
            default -> {
                return null;
            }
        }
    }
}
