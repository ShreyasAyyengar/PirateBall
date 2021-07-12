package me.shreyasayyengar.pirateball.ArenaManagers;

import lombok.Getter;
import me.shreyasayyengar.pirateball.Teams.Team;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class CuboidRegion {

    private String world;
    private final Vector minV, maxV;
    @Getter
    private final String name;

    public CuboidRegion(String name, Location min, Location max) {
        this.name = name;
        world = min.getWorld().getName();

        double xPos1= Math.min(min.getX(), max.getX());
        double yPos1= Math.min(min.getY(), max.getY());
        double zPos1= Math.min(min.getZ(), max.getZ());

        double xPos2= Math.max(min.getX(), max.getX());
        double yPos2= Math.max(min.getY(), max.getY());
        double zPos2= Math.max(min.getZ(), max.getZ());

        minV = new Vector(xPos1, yPos1, zPos1);
        maxV = new Vector(xPos2, yPos2, zPos2);
    }

    public boolean isInRegion(Location location) {
        return location.toVector().isInAABB(minV, maxV);
    }

    public boolean isBlockInTeamZone(Block block, Team team) {
        return team.getTeamZone(team).isInRegion(block.getLocation());
    }
}
