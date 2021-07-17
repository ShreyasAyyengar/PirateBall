package me.shreyasayyengar.pirateball.GameManagers;

import io.papermc.paper.event.entity.EntityMoveEvent;
import me.shreyasayyengar.pirateball.ArenaManagers.Arena;
import me.shreyasayyengar.pirateball.Teams.Team;
import me.shreyasayyengar.pirateball.Ultils.Config;
import me.shreyasayyengar.pirateball.Ultils.FloatingItem;
import me.shreyasayyengar.pirateball.Ultils.Manager;

import static me.shreyasayyengar.pirateball.Ultils.Util.*;
import static me.shreyasayyengar.pirateball.Ultils.Manager.*;
import static me.shreyasayyengar.pirateball.GameManagers.GameUtils.*;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftSkull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


public class GameListener implements Listener {

    private final Arena arena;

    public GameListener(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    private void onGUIClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Team playerTeam = Manager.getArena(player).getTeam(player);

        if (e.getCurrentItem() != null && e.getView().getTitle().contains("Team")) {
            Team team = Team.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());

            if (playerTeam.equals(team)) {
                player.sendMessage(ChatColor.DARK_RED + "You are already on " + team.getDisplayName() + ChatColor.DARK_RED + "team!");
                player.closeInventory();
            } else {
                Manager.getArena(player).setTeam(player, team);
                player.sendMessage(ChatColor.GREEN + "You are now on " + team.getDisplayName() + " team!");
                player.closeInventory();
            }
            e.setCancelled(true);
        }

        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            if (isPlaying(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBreak(BlockBreakEvent e) {

        if (isPlaying(e.getPlayer()) && getArena(e.getPlayer()).getState().equals(GameState.LIVE)) {

            Block block = e.getBlock();

            if (block.getType().equals(Material.PLAYER_HEAD)) {

                Skull skull = (Skull) block.getState();
                Player player = e.getPlayer();
                Team playerTeam = Manager.getArena(player).getTeam(player);

                if (playerIsTakingOwnBalL((CraftSkull) skull, player)) { // picking up their own ball
                    if (playerIsTakingOwnBallAtOwnBase(block, player)) { // picking their own base ball
                        e.setCancelled(true);
                        sendActionBar(player, colourise("&cYou cannot pick up your own ball!"), 100);
                    } else if (!playerTeam.getTeamZone(playerTeam).isInRegion(player.getLocation())) {
                        e.setDropItems(false);
                        dropBallNaturally(e.getBlock(), playerTeam);

                        if (getArena(player).isInTeamZone((CraftSkull) skull, Team.RED)) {
                            player.sendTitle(colourise("&7Ball Stolen!"), colourise("&8You stole " + playerTeam.getChatColorChar() + "your ball &7from &cred &8 team!"), 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 1, 1);
                            getArena(player).sendTeamMessage(Team.RED, "some1 stole you're ball kek");

                        } else if (getArena(player).isInTeamZone((CraftSkull) skull, Team.BLUE)) {
                            player.sendTitle(colourise("&7Ball Stolen!"), colourise("&8You stole " + playerTeam.getChatColorChar() + "your ball &7from &9blue &8 team!"), 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 10, 1);
                            getArena(player).sendTeamMessage(Team.BLUE, "some1 stole you're ball kek");

                        } else if (getArena(player).isInTeamZone((CraftSkull) skull, Team.YELLOW)) {
                            player.sendTitle(colourise("&7Ball Stolen!"), colourise("&8You stole " + playerTeam.getChatColorChar() + "your ball &7from &eyellow &8 team!"), 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 10, 1);
                            getArena(player).sendTeamMessage(Team.YELLOW, "some1 stole you're ball kek");

                        } else if (getArena(player).isInTeamZone((CraftSkull) skull, Team.GREEN)) {
                            player.sendTitle(colourise("&7Ball Stolen!"), colourise("&8You stole " + playerTeam.getChatColorChar() + "your ball &7from &2green &8 team!"), 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 10, 1);
                            getArena(player).sendTeamMessage(Team.GREEN, "some1 stole you're ball kek");
                        }
                    }
                } else if (playerIsTakingOwnBalL((CraftSkull) skull, player)) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You cannot pick up another ball's team!");
                }

            } else if (!e.getPlayer().isOp()) {
                e.setCancelled(true);
            } else if (block.getType().toString().toLowerCase().endsWith("wool")) {
                if (block.getRelative(BlockFace.UP).getType() == Material.PLAYER_HEAD) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "You cannot break the wool base!");
                }
            }
        }
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Team playerTeam = Manager.getArena(player).getTeam(player);

        if (!isPlaying(player) && !player.isOp()) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }

        if (isPlaying(e.getPlayer()) && getArena(e.getPlayer()).getState().equals(GameState.LIVE)) { // if live game

            if (block.getType() == Material.PLAYER_HEAD) { // if skull (ball) is placed
                Skull skull = (Skull) block.getState();

//                if (playerTeam.getOwnTeamBallChar() == skull.getOwningPlayer().getUniqueId().toString().charAt(1)) { // placing their own colour ball
                if (playerIsTakingOwnBalL((CraftSkull) skull, player)) {

                    if (block.getRelative(BlockFace.DOWN).getType() == Material.END_PORTAL_FRAME) {
                        if (playerTeam.getTeamZone(playerTeam).isBlockInTeamZone(block, playerTeam)) {
                            block.getRelative(BlockFace.DOWN, 2).setType(playerTeam.getTeamGlass());
                            FloatingItem floatingBall = new FloatingItem(block.getLocation().add(0.5, -1, 0.5));
                            floatingBall.spawn(new ItemStack(playerTeam.getTeamBall(playerTeam)), true);
                            e.setCancelled(true);
                            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                        } else {
                            e.setCancelled(true);
                            sendActionBar(player, colourise("&4That's not where that goes!"), 100);
//                            player.sendMessage(ChatColor.RED + "That's not where that goes!");
                        }
                    } else if (block.getRelative(BlockFace.DOWN).getType() != Material.END_PORTAL_FRAME || (!playerTeam.getTeamZone(playerTeam).isBlockInTeamZone(block, playerTeam))) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You can only place your ball in your " + ChatColor.BOLD + "team pod.");
                        sendActionBar(player, colourise("&cYou can only place your ball in " + playerTeam.getChatColorChar() + "your team pod"));
                    }
                } else {
                    e.setCancelled(true);
                    player.sendMessage(colourise("&0IDK how you even got the other team's ball &cbut no!!"));
                }
            }

            if (block.getType() == Material.PLAYER_WALL_HEAD) {
                e.setCancelled(true);
                sendActionBar(player, colourise("&cYou cannot place a ball on a wall"), 100);
//                player.sendMessage(ChatColor.RED + "You cannot place a ball on a wall.");
            }
        }
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent e) {
        if (e.getEntity().getType() == EntityType.ARMOR_STAND) {
            ArmorStand armorStand = (ArmorStand) e.getEntity();
            if (armorStand.getEquipment().getHelmet().getType() == Material.PLAYER_HEAD) {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Team playerTeam = Manager.getArena(player).getTeam(player);


                    if (!armorStand.getEquipment().getHelmet().getItemMeta().getDisplayName().equals(playerTeam.getTeamBall(playerTeam).getItemMeta().getDisplayName())) {
                        if (Math.round(armorStand.getLocation().getX()) == Math.round(player.getLocation().getX()) &&
                                Math.round(armorStand.getLocation().getZ()) == Math.round(player.getLocation().getZ()) &&
                                Math.round(armorStand.getLocation().getY()) == Math.round(player.getLocation().getY())) {
                            player.sendTitle(colourise("&cYou have been hit!"), colourise("&7You will repspawn shortly"), 10, 100, 10);
                            player.teleport(Config.getArenaSpawn(getArena(player).getId()));
                        } else if (
                                Math.round(armorStand.getLocation().getX()) == Math.round(player.getLocation().getX()) &&
                                        Math.round(armorStand.getLocation().getZ()) == Math.round(player.getLocation().getZ()) &&
                                        Math.round(armorStand.getLocation().getY()) == Math.round(player.getLocation().getY() + 1)) {
                            player.sendTitle(colourise("&cYou have been hit!"), colourise("&7You will repspawn shortly"), 10, 100, 10);
                            player.teleport(Config.getArenaSpawn(getArena(player).getId()));

                        } else if (
                                Math.round(armorStand.getLocation().getX()) == Math.round(player.getLocation().getX()) &&
                                        Math.round(armorStand.getLocation().getZ()) == Math.round(player.getLocation().getZ()) &&
                                        Math.round(armorStand.getLocation().getY()) == Math.round(player.getLocation().getY() - 1)) {
                            player.sendTitle(colourise("&cYou have been hit!"), colourise("&7You will repspawn shortly"), 10, 100, 10);
                            player.teleport(Config.getArenaSpawn(getArena(player).getId()));
                        }
                    }
                }
            }

            if (armorStand.getLocation().getY() == 5.0) {
                armorStand.getLocation().getBlock().setType(armorStand.getEquipment().getHelmet().getType());
                armorStand.remove();
            }
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Vector playerHead = player.getEyeLocation().getDirection();
        Team playerTeam = Manager.getArena(player).getTeam(player);


        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getItem().getType() == Material.PLAYER_HEAD) {

                ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(getPlayerLocationPlus1(player), EntityType.ARMOR_STAND);

                as.setVelocity(new Vector(playerHead.getX() * 3.5, playerHead.getY() * 2, playerHead.getZ() * 3.5));
                as.setInvulnerable(true);
                as.setBasePlate(false);
                as.setSmall(true);
                as.setGliding(false);
                as.setVisible(false);
                as.setCollidable(false);
                as.setHelmet(playerTeam.getTeamBall(playerTeam));
            }
        }
    }

    @EventHandler
    private void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (isPlaying(player)) {
            getArena(player).removePlayer(e.getPlayer());
            getArena(player).sendMessage(colourise("&0" + player.getName() + " left!"));
//            getArena(e.getPlayer()).sendMessage(ChatColor.BLACK + "" + e.getPlayer() + "left!");
        }
    }

    @EventHandler
    private void onDamage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player player) {
            if (!isPlaying(player)) {
                e.setCancelled(true);
            } else if (isPlaying(player) && !getArena(player).getState().equals(GameState.LIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onHit(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof Player victim) {

            if (isPlaying(damager) && isPlaying(victim)) {

                if (getArena(damager) == getArena(victim)) {
                    if (getArena(damager).getTeam(damager) == getArena(victim).getTeam(victim)) {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onFoodLoss(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    private void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    private void onPlayerHandSwitch(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
    }
}
