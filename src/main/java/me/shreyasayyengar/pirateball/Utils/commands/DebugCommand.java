package me.shreyasayyengar.pirateball.Utils.commands;

import me.shreyasayyengar.pirateball.Utils.FloatingItem;
import me.shreyasayyengar.pirateball.Utils.Manager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            switch (args[0]) {
                case "1" -> FloatingItem.deleteAll();
                case "2" -> {
                    player.getWorld().dropItemNaturally(player.getLocation(), Manager.getArena(player).getTeam(player).getTeamBall());
//                    player.getWorld().dropItemNaturally(player.getLocation(), Team.getTeamBall(Team.BLUE));
//                    player.getWorld().dropItemNaturally(player.getLocation(), Team.getTeamBall(Team.YELLOW));
//                    player.getWorld().dropItemNaturally(player.getLocation(), Team.getTeamBall(Team.GREEN));
                }
                case "3" -> {
//                    Manager.getArena(player).getTeam(player).debug(player);
                }
            }

        }
        return false;
    }
}
