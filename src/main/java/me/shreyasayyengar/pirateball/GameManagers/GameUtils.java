package me.shreyasayyengar.pirateball.GameManagers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.shreyasayyengar.pirateball.PirateBall;
import me.shreyasayyengar.pirateball.Teams.Team;
import me.shreyasayyengar.pirateball.Utils.Manager;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftSkull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GameUtils {

    public ArrayList<Player> currentlyRespawning = new ArrayList<>();

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
        block.getWorld().dropItemNaturally(block.getLocation(), Team.getTeamBall(team));
    }

    public static Location getPlayerLocationPlus1(Player player) {
        return new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ());
    }


    public static GameProfile getSkullGameProfile(Team team) {
        GameProfile texture = new GameProfile(team.getBallUUID(team), null);
        texture.getProperties().put("textures", new Property("textures", team.getTeamBallTexture(team)));
        return texture;

    }

    public static void setSkullSkin(Team team, Block block) {
        block.setType(Material.PLAYER_HEAD);

        TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        skullTile.setGameProfile(getSkullGameProfile(team));
        block.getState().update(true);
    }


    public void setDeath(Player player, RespawnReason reason) {
        currentlyRespawning.add(player);

        String title = null;
        String subtitle = ChatColor.GRAY + "Moving to jail in ";

        switch (reason) {
            case KILLED -> title = ChatColor.RED + "You died to " + player.getLastDamageCause().getEntity().getName();

            case HIT_BY_BALL -> title = ChatColor.RED + "You've been hit by a ball!";

            case REMOVED_FROM_JAIL -> {
                title = ChatColor.GREEN + "You teammate has saved you!";
                subtitle = ChatColor.GRAY + "Returning to base in ";
            }

        } // Title Factory

        final int[] seconds = {5};
        String finalTitle = title;
        String finalSubtitle = subtitle;
        new BukkitRunnable() {
            @Override
            public void run() {

                if (seconds[0] == 0) {
                    player.sendMessage(ChatColor.GREEN + "Respawned");
                    player.setGameMode(GameMode.SURVIVAL);
                    setArmor(player, Manager.getArena(player).getTeam(player).getColor(), Manager.getArena(player).getTeam(player));
                    currentlyRespawning.remove(player);

                    for (Player loopedPlayer : Bukkit.getOnlinePlayers()) {
                        loopedPlayer.showPlayer(PirateBall.getInstance(), player);
                    }
                    cancel();

                } else if (seconds[0] == 1) {
                    player.sendTitle(finalTitle, finalSubtitle + ChatColor.YELLOW + seconds[0] + ChatColor.GRAY + " second", 0, 25, 0);
                } else {
                    player.sendTitle(finalTitle, finalSubtitle + ChatColor.YELLOW + seconds[0] + ChatColor.GRAY + " seconds", 0, 25, 0);
                    player.getInventory().clear();
                }

                seconds[0]--;
            }
        }.runTaskTimer(PirateBall.getInstance(), 0, 20);
    }

    public boolean isPlayerRespawning(Player player) {
        return this.currentlyRespawning.contains(player);
    }
}
