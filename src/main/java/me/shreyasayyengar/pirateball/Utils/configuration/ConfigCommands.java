package me.shreyasayyengar.pirateball.Utils.configuration;

import me.shreyasayyengar.pirateball.PirateBall;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConfigCommands implements CommandExecutor {

    private final PirateBall main;

    public ConfigCommands(PirateBall main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("change")) {
                try {
                    int number = Integer.parseInt(args[1]);
                    main.getConfig().set("required-players", number);
                    commandSender.sendMessage(ChatColor.GREEN + "Changed!");
                    commandSender.sendMessage(String.valueOf(Config.getRequiredPlayers()));
                } catch (NumberFormatException e) {
                    commandSender.sendMessage(ChatColor.RED + "That is not a valid number!");
                }
            }
        }
        return false;
    }
}
