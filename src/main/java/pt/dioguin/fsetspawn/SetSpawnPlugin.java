package pt.dioguin.fsetspawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pt.dioguin.fsetspawn.inventory.InventoryLoader;
import pt.dioguin.fsetspawn.listener.MobListener;
import pt.dioguin.fsetspawn.listener.PlayerListener;
import pt.dioguin.fsetspawn.setspawn.manager.FSetSpawnManager;

public class SetSpawnPlugin extends JavaPlugin {

    private InventoryLoader inventoryLoader;
    private FSetSpawnManager fSetSpawnManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        inventoryLoader = new InventoryLoader();
        fSetSpawnManager = new FSetSpawnManager();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MobListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static SetSpawnPlugin getInstance(){
        return getPlugin(SetSpawnPlugin.class);
    }

    public InventoryLoader getInventoryLoader() {
        return inventoryLoader;
    }

    public FSetSpawnManager getfSetSpawnManager() {
        return fSetSpawnManager;
    }
}
