package pt.dioguin.fsetspawn.setspawn.manager;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import pt.dioguin.fsetspawn.setspawn.FSetSpawn;

import java.util.ArrayList;
import java.util.List;

public class FSetSpawnManager {

    private final List<FSetSpawn> spawns;

    public FSetSpawnManager(){
        this.spawns = new ArrayList<>();
    }

    public boolean hasFSetSpawn(Faction faction){
        return this.spawns.stream().anyMatch(fSetSpawn -> fSetSpawn.getFaction() == faction);
    }

    public FSetSpawn getFSetSpawn(Faction faction){
        return this.spawns.stream().filter(fSetSpawn -> fSetSpawn.getFaction() == faction).findAny().orElse(null);
    }

    public boolean containsSpawn(FSetSpawn fSetSpawn, EntityType type){
        return fSetSpawn.getSpawn().containsKey(type);
    }

    public Location getSpawn(FSetSpawn fSetSpawn, EntityType type){
        return fSetSpawn.getSpawn().get(type);
    }

    public void setLocation(FSetSpawn fSetSpawn, EntityType type, Location location){
        if (fSetSpawn.getSpawn().containsKey(type)) fSetSpawn.getSpawn().replace(type, location);
        else fSetSpawn.getSpawn().put(type, location);
    }

    public void addFSetSpawn(FSetSpawn fSetSpawn){
        this.spawns.add(fSetSpawn);
    }

}
