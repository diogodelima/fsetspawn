package pt.dioguin.fsetspawn.setspawn;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import pt.dioguin.fsetspawn.SetSpawnPlugin;

import java.util.HashMap;

public class FSetSpawn {

    private final Faction faction;
    private final HashMap<EntityType, Location> spawn;

    public FSetSpawn(Faction faction){
        this.faction = faction;
        this.spawn = new HashMap<>();
        SetSpawnPlugin.getInstance().getfSetSpawnManager().addFSetSpawn(this);
    }

    public Faction getFaction() {
        return faction;
    }

    public HashMap<EntityType, Location> getSpawn() {
        return spawn;
    }
}
