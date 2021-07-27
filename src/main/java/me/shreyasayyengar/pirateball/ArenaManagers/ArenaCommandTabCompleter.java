package me.shreyasayyengar.pirateball.ArenaManagers;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class ArenaCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        List<String> arguments = Arrays.asList("join", "pee", "list", "team", "help");
        List<String> flist = Lists.newArrayList();
        if (args.length == 1) {
            for (String s : arguments) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase()))flist.add(s);
            }
            return flist;
        }
        return null;
    }
}
