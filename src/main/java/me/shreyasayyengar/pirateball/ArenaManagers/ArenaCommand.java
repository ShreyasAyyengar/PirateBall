package me.shreyasayyengar.pirateball.ArenaManagers;

import me.shreyasayyengar.pirateball.GameManagers.GameState;
import me.shreyasayyengar.pirateball.Teams.TeamsGUI;
import me.shreyasayyengar.pirateball.Utils.configuration.Config;
import me.shreyasayyengar.pirateball.Utils.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {

        if (commandSender instanceof Player player) {

            if (args.length == 1 && args[0].equalsIgnoreCase("team")) {
                if (Manager.isPlaying(player)) {
                    if (Manager.getArena(player).getState().equals(GameState.COUNTDOWN) ||
                            Manager.getArena(player).getState().equals(GameState.WAITING)) {
                        new TeamsGUI(player);

                    } else {
                        player.sendMessage(ChatColor.RED + "You cannot change teams right now!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + " You are not in an arena");
                }

            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.GOLD + "These are the list of arenas");
                for (Arena arena : Manager.getArenas()) {
                    player.sendMessage(ChatColor.GREEN + "- " + arena.getId());
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {

                if (Manager.isPlaying(player)) {
                    Manager.getArena(player).removePlayer(player);

                    player.sendMessage(ChatColor.AQUA + "You left the arena!");
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in an arena right now!");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                try {
                    int id = Integer.parseInt(args[1]);

                    if (id >= 0 && id <= (Config.getArenaAmount() - 1)) {
                        if (Manager.isJoinable(id)) {
                            Manager.getArena(id).addPlayer(player);

                            player.sendMessage(ChatColor.GREEN + "You are now playing in arena " + id);
                        } else {
                            player.sendMessage(ChatColor.RED + "You cannot join that game right now");
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + "Invalid Arena, see /arena list");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.DARK_RED + "Invalid Arena, see /arena list");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid Usage! These are the commands:");
                player.sendMessage(ChatColor.YELLOW + "/arena list");
                player.sendMessage(ChatColor.GOLD + "/arena join [id]");
                player.sendMessage(ChatColor.GREEN + "/arena leave");
            }

        } else {
            Bukkit.getLogger().warning(ChatColor.RED + "You cannot use this from the console!");
        }

        return false;
    }
}
