package me.shreyasayyengar.pirateball.Utils.commands;

import me.shreyasayyengar.pirateball.PirateBall;
import me.shreyasayyengar.pirateball.Teams.Team;
import me.shreyasayyengar.pirateball.Utils.FloatingItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            switch (args[0]) {
                case "1" -> FloatingItem.deleteAll();
                case "2" -> {
                    player.getWorld().dropItemNaturally(player.getLocation(), Team.getTeamBall(Team.RED));
                    player.getWorld().dropItemNaturally(player.getLocation(), Team.getTeamBall(Team.BLUE));
                    player.getWorld().dropItemNaturally(player.getLocation(), Team.getTeamBall(Team.YELLOW));
                    player.getWorld().dropItemNaturally(player.getLocation(), Team.getTeamBall(Team.GREEN));
                }
                case "3" -> {
                    player.sendMessage("t");
                    ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            as.setRotation(as.getLocation().getYaw() + 5, 0);
                        }
                    }.runTaskTimerAsynchronously(PirateBall.getInstance(), 0, 20);
                }
            }

        }
        return false;
    }
}
