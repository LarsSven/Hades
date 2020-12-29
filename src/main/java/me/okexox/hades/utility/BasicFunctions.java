package me.okexox.hades.utility;

import me.okexox.hades.data.ServerData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import static me.okexox.hades.utility.Settings.GCD;

public class BasicFunctions {
    public static boolean isSwimming(Location loc) {
        for(float x = -0.5f; x <= 0.5f; x += 0.5f) {
            for(float z = -0.5f; z <= 0.5f; z += 0.5f) {
                for(int i = 0; i <= 1; i++) {
                    Location testLoc = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + i, loc.getZ() + z);
                    if(testLoc.getBlock().isLiquid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static double getHorizontalDistance(Location to, Location from) {
        return Math.sqrt(Math.pow((to.getX()-from.getX()),2) + Math.pow((to.getZ()-from.getZ()),2));
    }

    public static double round(double value) {
        return Math.round(value * 1000.)/1000.;
    }


    /**
     * Checks if all blocks around the location is the specified block
     * @param loc The location to be checked
     * @return Whether all blocks are the specified block
     */
    public static boolean checkAllBlockAround(Location loc, int blockID) {
        for(float x = -0.5f; x <= 0.5f; x += 0.5f) {
            for(float z = -0.5f; z <= 0.5f; z += 0.5f) {
                Location newLoc = loc.clone();
                newLoc.setZ(loc.getZ()+z);
                newLoc.setX(loc.getX()+x);
                if(newLoc.getWorld().getBlockTypeIdAt(newLoc) != blockID) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean pistonSafety() {
        //TODO: Only return false for players who have been close to an event
        //TODO: Lag sensitive
        return System.currentTimeMillis()-ServerData.getInstance().isPistonExtended() >= 800;
    }

    /**
     * Checks if any blocks around the location is the specified block
     * @param loc The location to be checked
     * @return Whether any block is the specified block
     */
    public static boolean checkAnyBlockAround(Location loc, int blockID) {
        for(float x = -0.5f; x <= 0.5f; x += 0.5f) {
            for(float z = -0.5f; z <= 0.5f; z += 0.5f) {
                Location newLoc = loc.clone();
                newLoc.setZ(loc.getZ()+z);
                newLoc.setX(loc.getX()+x);
                if(newLoc.getWorld().getBlockTypeIdAt(newLoc) == blockID) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkBlockAround(Location loc, int blockID) {
        Block block = loc.getBlock();
        World world = loc.getWorld();
        int minX = block.getX()-1;
        int maxX = block.getX()+1;
        int minZ = block.getZ()-1;
        int maxZ = block.getZ()+1;
        int minY = block.getY()-1;
        int maxY = block.getY()+1;
        for(int x = minX; x <= maxX; x++) {
            for(int z = minZ; z <= maxZ; z++) {
                for(int y = minY; y <= maxY; y++) {
                    int id = world.getBlockTypeIdAt(x, y, z);
                    if(id == blockID) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Detects whether the move could be due to a teleport up that the server may do when the client hits the edge of a block
     * @param loc The location to be checked, should be the "to" location
     * @return Whether it is an edge teleport or not
     */
    public static boolean edgeTeleport(Location loc) {
        Location newLoc = loc.clone();
        boolean isValidPos = newLoc.getY() % GCD == 0;
        boolean currentAllAir = BasicFunctions.checkAllBlockAround(newLoc, 0);
        newLoc.setY(newLoc.getY()-1);
        boolean belowAllAir = BasicFunctions.checkAllBlockAround(newLoc, 0);
        return isValidPos && (!currentAllAir || !belowAllAir);
    }
}
