package me.shreyasayyengar.pirateball.GameManagers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.shreyasayyengar.pirateball.PirateBall;
import me.shreyasayyengar.pirateball.Teams.Team;
import me.shreyasayyengar.pirateball.Ultils.Manager;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftSkull;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class GameUtils {

    private ArrayList<Player> currentlyRespawining;

    public static boolean playerIsTakingOwnBalL(CraftSkull skull, Player player) {
        return Manager.getArena(player).getTeam(player).getOwnTeamBallChar() == skull.getOwningPlayer().getUniqueId().toString().charAt(1);
    }

    public static boolean playerIsTakingOwnBallAtOwnBase(CraftSkull block, Player player) {
        return block.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Manager.getArena(player).getTeam(player).getTeamWool()
                && Manager.getArena(player).getTeam(player).getTeamZone(Manager.getArena(player).getTeam(player)).isInRegion(player.getLocation());
    }

    public static void setArmor(Player player, Color colour, Team team) {

        ItemStack[] armor = {
                new ItemStack(Material.LEATHER_BOOTS),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_HELMET)
        };

        LeatherArmorMeta teamArmor = (LeatherArmorMeta) (new ItemStack(Material.LEATHER_HELMET)).getItemMeta();
        teamArmor.setColor(colour);
        teamArmor.setDisplayName(team.getDisplayName() + " Team");

        for (ItemStack piece : armor) {
            piece.setItemMeta(teamArmor);
        }

        player.getInventory().setArmorContents(armor);

    }

    public static void dropBallNaturally(Block block, Team team) {
        switch (team) {
            case RED -> {
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
                block.getWorld().dropItemNaturally(block.getLocation(), skull);
            }
            case BLUE -> {
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
                block.getWorld().dropItemNaturally(block.getLocation(), skull);
            }
            case YELLOW -> {
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
                block.getWorld().dropItemNaturally(block.getLocation(), skull);
            }
            case GREEN -> {
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
                block.getWorld().dropItemNaturally(block.getLocation(), skull);
            }
        }
    }

    public static Location getPlayerLocationPlus1(Player player) {
        return new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ());
    }

    public static void setDeath(Player player) {

        final int[] seconds = {5};

        new BukkitRunnable() {
            @Override
            public void run() {

                if (seconds[0] == 0) {
                    player.sendMessage(ChatColor.GREEN + "Respawned");
                    player.setGameMode(GameMode.SURVIVAL);
                    GameUtils.setArmor(player, Manager.getArena(player).getTeam(player).getColor(), Manager.getArena(player).getTeam(player));
                    EntityPlayer NMSPlayer = ((CraftPlayer) player).getHandle();

                    for (Player loopedPlayer : Bukkit.getOnlinePlayers()) {
                        loopedPlayer.showPlayer(PirateBall.getInstance(), player);

                    }
                    cancel();
                } else if (seconds[0] == 1) {
                    player.sendTitle(ChatColor.RED + "You've been hit by a ball!", ChatColor.GRAY + "Respawning in " + ChatColor.YELLOW + seconds[0] + ChatColor.GRAY + " second", 0, 25, 0);
                } else {
                    player.sendTitle(ChatColor.RED + "You've been hit by a ball!", ChatColor.GRAY + "Respawning in " + ChatColor.YELLOW + seconds[0] + ChatColor.GRAY + " seconds", 0, 25, 0);
                    player.getInventory().clear();
                }

                seconds[0]--;
            }
        }.runTaskTimer(PirateBall.getInstance(), 0, 20);
    }
}
