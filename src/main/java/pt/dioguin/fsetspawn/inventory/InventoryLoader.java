package pt.dioguin.fsetspawn.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pt.dioguin.fsetspawn.SetSpawnPlugin;
import pt.dioguin.fsetspawn.utils.ItemBuilder;

public class InventoryLoader {

    private final Inventory setspawn;

    public InventoryLoader(){

        FileConfiguration config = SetSpawnPlugin.getInstance().getConfig();
        this.setspawn = Bukkit.createInventory(null, config.getInt("size"), config.getString("title").replace("&", "§"));

        for (String key : config.getConfigurationSection("mobs").getKeys(false)){

            ItemStack item = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                    .setSkull(config.getString("mobs." + key + ".skull"))
                    .name(config.getString("mobs." + key + ".name"))
                    .setLore(config.getStringList("mobs." + key + ".lore"))
                    .build();

            this.setspawn.setItem(config.getInt("mobs." + key + ".slot"), item);
        }

    }

    public Inventory getSetspawn() {
        return setspawn;
    }
}
