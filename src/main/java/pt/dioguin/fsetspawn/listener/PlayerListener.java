package pt.dioguin.fsetspawn.listener;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pt.dioguin.fsetspawn.SetSpawnPlugin;
import pt.dioguin.fsetspawn.setspawn.FSetSpawn;
import pt.dioguin.fsetspawn.utils.ActionBarAPI;

public class PlayerListener implements Listener {

    @EventHandler
    void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){

        Player player = event.getPlayer();
        MPlayer mPlayer = MPlayer.get(player);

        if (event.getMessage().equalsIgnoreCase("/f setspawn") && mPlayer.hasFaction() && mPlayer.getFaction() == BoardColl.get().getFactionAt(PS.valueOf(player.getLocation())) && (mPlayer.getRole() == Rel.LEADER || mPlayer.getRole() == Rel.OFFICER)){
            event.setCancelled(true);
            player.openInventory(SetSpawnPlugin.getInstance().getInventoryLoader().getSetspawn());
        }

    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event){

        FileConfiguration config = SetSpawnPlugin.getInstance().getConfig();
        Player player = (Player) event.getWhoClicked();
        MPlayer mPlayer = MPlayer.get(player);
        Faction faction = mPlayer.getFaction();

        if (!event.getInventory().getTitle().equals(SetSpawnPlugin.getInstance().getInventoryLoader().getSetspawn().getTitle())) return;

        event.setCancelled(true);

        for (String key : config.getConfigurationSection("mobs").getKeys(false)){

            if (event.getSlot() == config.getInt("mobs." + key + ".slot")){

                FSetSpawn fSetSpawn = SetSpawnPlugin.getInstance().getfSetSpawnManager().hasFSetSpawn(faction) ? SetSpawnPlugin.getInstance().getfSetSpawnManager().getFSetSpawn(faction) : new FSetSpawn(faction);
                SetSpawnPlugin.getInstance().getfSetSpawnManager().setLocation(fSetSpawn, EntityType.valueOf(key), player.getLocation());
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.valueOf(config.getString("mobs." + key + ".sound")), 1, 1);
                ActionBarAPI.sendMessage(player, config.getString("mobs." + key + ".action-bar").replace("&", "§"));
                break;
            }

        }

    }

}
