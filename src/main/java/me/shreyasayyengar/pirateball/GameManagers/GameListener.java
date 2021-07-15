package me.shreyasayyengar.pirateball.GameManagers;

import io.papermc.paper.event.entity.EntityMoveEvent;
import me.shreyasayyengar.pirateball.ArenaManagers.Arena;
import me.shreyasayyengar.pirateball.Teams.Team;
import me.shreyasayyengar.pirateball.Ultils.Config;
import me.shreyasayyengar.pirateball.Ultils.FloatingItem;
import me.shreyasayyengar.pirateball.Ultils.Manager;
import me.shreyasayyengar.pirateball.Ultils.NMS.ActionBar;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Jukebox;
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
            if (Manager.isPlaying(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBreak(BlockBreakEvent e) {

        if (Manager.isPlaying(e.getPlayer()) && Manager.getArena(e.getPlayer()).getState().equals(GameState.LIVE)) {

            Block block = e.getBlock();

            if (block.getType().equals(Material.PLAYER_HEAD)) {
                Skull skull = (Skull) block.getState();
                Player player = e.getPlayer();
                Team playerTeam = Manager.getArena(player).getTeam(player);

                if (playerTeam.getOwnTeamBallChar() == skull.getOwningPlayer().getUniqueId().toString().charAt(1)) { // picking up their own ball
                    if (block.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == playerTeam.getTeamWool() &&
                            playerTeam.getTeamZone(playerTeam).isInRegion(player.getLocation())) { // picking their own base
                        e.setCancelled(true);
                        ActionBar.sendActionBar(player, ChatColor.RED + "You cannot pickup your own ball!");
                        player.sendMessage(ChatColor.RED + "You cannot pickup your own ball!");
                    } else if (!playerTeam.getTeamZone(playerTeam).isInRegion(player.getLocation())) {
                        e.setDropItems(false);
                        Manager.getArena(player).dropBallNaturally(e.getBlock(), playerTeam);

                        if (Manager.getArena(player).isInTeamZone((CraftSkull) skull, Team.RED)) {
                            player.sendTitle(ChatColor.GRAY + "Ball Stolen!", ChatColor.DARK_GRAY + "You stole " + playerTeam.getChatColor() + "your ball " + ChatColor.GRAY + "from " + ChatColor.RED + "red " + ChatColor.DARK_GRAY + "team!", 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 1, 1);
                            Manager.getArena(player).sendTeamMessage(Team.RED, "some1 stole you're ball kek");

                        } else if (Manager.getArena(player).isInTeamZone((CraftSkull) skull, Team.BLUE)) {
                            Manager.getArena(player).sendTeamMessage(Team.RED, "bol stoeln");
                            player.sendTitle(ChatColor.GRAY + "Ball Stolen!", ChatColor.DARK_GRAY + "You stole " + playerTeam.getChatColor() + "your ball " + ChatColor.GRAY + "from " + ChatColor.DARK_BLUE + "blue " + ChatColor.DARK_GRAY + "team!", 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 10, 1);
                            Manager.getArena(player).sendTeamMessage(Team.BLUE, "some1 stole you're ball kek");

                        } else if (Manager.getArena(player).isInTeamZone((CraftSkull) skull, Team.YELLOW)) {
                            player.sendTitle(ChatColor.GRAY + "Ball Stolen!", ChatColor.DARK_GRAY + "You stole " + playerTeam.getChatColor() + "your ball " + ChatColor.GRAY + "from " + ChatColor.YELLOW + "yellow " + ChatColor.DARK_GRAY + "team!", 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 10, 1);
                            Manager.getArena(player).sendTeamMessage(Team.YELLOW, "some1 stole you're ball kek");

                        } else if (Manager.getArena(player).isInTeamZone((CraftSkull) skull, Team.GREEN)) {
                            player.sendTitle(ChatColor.GRAY + "Ball Stolen!", ChatColor.DARK_GRAY + "You stole " + playerTeam.getChatColor() + "your ball " + ChatColor.GRAY + "from " + ChatColor.GREEN + "green " + ChatColor.DARK_GRAY + "team!", 10, 100, 20);
                            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 10, 1);
                            Manager.getArena(player).sendTeamMessage(Team.GREEN, "some1 stole you're ball kek");
                        }
                    }
                } else if (playerTeam.getOwnTeamBallChar() != skull.getOwningPlayer().getUniqueId().toString().charAt(1)) {
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

        if (!Manager.isPlaying(player) && !player.isOp()) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }

        if (Manager.isPlaying(e.getPlayer()) && Manager.getArena(e.getPlayer()).getState().equals(GameState.LIVE)) { // if live game

            if (block.getType() == Material.PLAYER_HEAD) { // if skull (ball) is placed
                Skull skull = (Skull) block.getState();

                if (playerTeam.getOwnTeamBallChar() == skull.getOwningPlayer().getUniqueId().toString().charAt(1)) { // placing their own colour ball

                    if (block.getRelative(BlockFace.DOWN).getType() == Material.END_PORTAL_FRAME) {
                        if (playerTeam.getTeamZone(playerTeam).isBlockInTeamZone(block, playerTeam)) {
                            block.getRelative(BlockFace.DOWN, 2).setType(playerTeam.getTeamGlass());
                            FloatingItem floatingBall = new FloatingItem(block.getLocation().add(0.5, -1, 0.5));
                            floatingBall.spawn(new ItemStack(playerTeam.getTeamBall(playerTeam)), true);
                            e.setCancelled(true);
                            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                        } else {
                            e.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "That's not where that goes!");
                        }
                    } else if (block.getRelative(BlockFace.DOWN).getType() != Material.END_PORTAL_FRAME || (!playerTeam.getTeamZone(playerTeam).isBlockInTeamZone(block, playerTeam))) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You can only place your ball in your " + ChatColor.BOLD + "team pod.");
                    }
                } else {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.BLACK + "IDK how you even got the other team's ball " + ChatColor.RED + "but no!!");
                }

            }

            if (block.getType() == Material.PLAYER_WALL_HEAD) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You cannot place a ball on a wall.");

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
                        if (
                                Math.round(armorStand.getLocation().getX()) == Math.round(player.getLocation().getX()) &&
                                        Math.round(armorStand.getLocation().getZ()) == Math.round(player.getLocation().getZ()) &&
                                        Math.round(armorStand.getLocation().getY()) == Math.round(player.getLocation().getY())) {
                            player.teleport(Config.getArenaSpawn(Manager.getArena(player).getId()));
                        } else if (
                                Math.round(armorStand.getLocation().getX()) == Math.round(player.getLocation().getX()) &&
                                        Math.round(armorStand.getLocation().getZ()) == Math.round(player.getLocation().getZ()) &&
                                        Math.round(armorStand.getLocation().getY()) == Math.round(player.getLocation().getY() + 1)) {
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Hit by armor stand L");
                            player.teleport(Config.getArenaSpawn(Manager.getArena(player).getId()));

                        } else if (
                                Math.round(armorStand.getLocation().getX()) == Math.round(player.getLocation().getX()) &&
                                        Math.round(armorStand.getLocation().getZ()) == Math.round(player.getLocation().getZ()) &&
                                        Math.round(armorStand.getLocation().getY()) == Math.round(player.getLocation().getY() - 1)) {
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Hit by armor stand L");
                            player.teleport(Config.getArenaSpawn(Manager.getArena(player).getId()));
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

                ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ()), EntityType.ARMOR_STAND);

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
        if (Manager.isPlaying(e.getPlayer())) {
            Manager.getArena(e.getPlayer()).removePlayer(e.getPlayer());
            Manager.getArena(e.getPlayer()).sendMessage(ChatColor.BLACK + "" + e.getPlayer() + "left!");
        }
    }

    @EventHandler
    private void onDamage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player player) {
            if (!Manager.isPlaying(player)) {
                e.setCancelled(true);
            } else if (Manager.isPlaying(player) && !Manager.getArena(player).getState().equals(GameState.LIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onHit(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {

            Player damager = (Player) e.getDamager();
            Player victim = (Player) e.getEntity();

            if (Manager.isPlaying(damager) && Manager.isPlaying(victim)) {

                if (Manager.getArena(damager) == Manager.getArena(victim)) {
                    if (Manager.getArena(damager).getTeam(damager) == Manager.getArena(victim).getTeam(victim)) {
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
}
