package me.shreyasayyengar.pirateball.Utils.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class SkullCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player player) {

            if (player.getName().startsWith("Shreyas")) {
                if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "red" -> {
                            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                            skullMeta.setDisplayName(ChatColor.RED + "Red Ball");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.GRAY + "This ball is 1 of 4 balls that ");
                            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.RED + "red " + ChatColor.GRAY + "team.");
                            skullMeta.setLore(lore);

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
                            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), skull);
                        }
                        case "blue" -> {
                            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                            skullMeta.setDisplayName(ChatColor.DARK_BLUE + "Blue Ball");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.GRAY + "This ball is 1 of 4 balls that ");
                            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.DARK_BLUE + "blue " + ChatColor.GRAY + "team.");

                            skullMeta.setLore(lore);

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
                            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), skull);
                        }
                        case "yellow" -> {
                            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                            skullMeta.setDisplayName(ChatColor.YELLOW + "Yellow Ball");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.GRAY + "This ball is 1 of 4 balls that ");
                            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.YELLOW + "yellow " + ChatColor.GRAY + "team.");


                            skullMeta.setLore(lore);


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
                            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), skull);
                        }
                        case "green" -> {
                            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                            skullMeta.setDisplayName(ChatColor.DARK_GREEN + "Green Ball");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.GRAY + "This ball is 1 of 4 balls that ");
                            lore.add(ChatColor.GRAY + "belongs to the " + ChatColor.DARK_GREEN + "green " + ChatColor.GRAY + "team.");
                            skullMeta.setLore(lore);


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
                            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), skull);
                        }
                    }
                }
            }
        }
        return false;
    }
}
