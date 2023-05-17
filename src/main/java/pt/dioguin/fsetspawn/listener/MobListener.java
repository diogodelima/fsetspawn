package pt.dioguin.fsetspawn.listener;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import pt.dioguin.fsetspawn.SetSpawnPlugin;
import pt.dioguin.fsetspawn.setspawn.FSetSpawn;

public class MobListener implements Listener {

    @EventHandler
    void onEntitySpawn(EntitySpawnEvent event){

        FileConfiguration config = SetSpawnPlugin.getInstance().getConfig();
        Entity entity = event.getEntity();
        Faction faction = BoardColl.get().getFactionAt(PS.valueOf(entity.getLocation()));
        FSetSpawn fSetSpawn = SetSpawnPlugin.getInstance().getfSetSpawnManager().getFSetSpawn(faction);

        if (config.contains("mobs." + entity.getType().name()) && SetSpawnPlugin.getInstance().getfSetSpawnManager().hasFSetSpawn(faction) && SetSpawnPlugin.getInstance().getfSetSpawnManager().containsSpawn(fSetSpawn, entity.getType()))
            entity.teleport(SetSpawnPlugin.getInstance().getfSetSpawnManager().getSpawn(fSetSpawn, entity.getType()));

    }

}
