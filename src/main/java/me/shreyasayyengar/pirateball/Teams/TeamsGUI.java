package me.shreyasayyengar.pirateball.Teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamsGUI {
    public TeamsGUI(Player player) {

        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Team Selection GUI");

        for (Team team : Team.values()) {
            ItemStack item = new ItemStack(team.getBannerMaterial());
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(team.getDisplayName());
            itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
            itemMeta.setLocalizedName(team.name());
            item.setItemMeta(itemMeta);

            gui.addItem(item);

        }
        player.openInventory(gui);
    }
}
