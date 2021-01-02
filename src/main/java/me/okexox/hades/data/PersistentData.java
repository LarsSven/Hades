package me.okexox.hades.data;

import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.combat.CombatNoSwing;
import me.okexox.hades.modules.combat.WallHit;
import me.okexox.hades.modules.exploits.FastHeal;
import me.okexox.hades.modules.exploits.NoWeb;
import me.okexox.hades.modules.exploits.Nuker;
import me.okexox.hades.modules.exploits.SelfDamage;
import me.okexox.hades.modules.integrity.CanFlyIntegrity;
import me.okexox.hades.modules.integrity.OnGroundIntegrity;
import me.okexox.hades.modules.integrity.PitchIntegrity;
import me.okexox.hades.modules.integrity.VehicleIntegrity;
import me.okexox.hades.modules.movement.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class PersistentData implements Serializable {
    private long serialVersionUID = 1;
    public static final String HADES = ChatColor.RED + "[Hades] ";
    private static PersistentData instance;

    private transient ArrayList<Detection> checks;
    private static transient HashSet<Material> blocks; //Blocks that can be used for scaffold checks
    private ArrayList<String> admins;

    private boolean punishPlayers = false;

    public PersistentData() {
        admins = new ArrayList<>();
        setupData();
    }

    public void setupData() {
        addChecks();
        addScaffoldBlocks();
    }

    private void addChecks() {
        checks = new ArrayList<>();
        //Combat
        checks.add(new CombatNoSwing());
        checks.add(new WallHit());
        //Exploits
        checks.add(new FastHeal());
        checks.add(new Nuker());
        checks.add(new SelfDamage());
        //Integrity
        checks.add(new OnGroundIntegrity());
        checks.add(new PitchIntegrity());
        checks.add(new VehicleIntegrity());
        checks.add(new CanFlyIntegrity());
        //Movement
        checks.add(new ExcessiveJump());
        checks.add(new HorizontalSpeed());
        checks.add(new Jesus());
        checks.add(new NoGravity());
        checks.add(new NoSlowDown());
        checks.add(new VerticalAcceleration());
        checks.add(new VerticalSpeed());
        checks.add(new ScaffoldHand());
        checks.add(new ScaffoldPitch());
        checks.add(new ScaffoldYaw());
        checks.add(new BlockNoSwing());
        checks.add(new ScaffoldBlockFace());
        checks.add(new NoWeb());
        checks.add(new ExpectedJump());
    }

    private void addScaffoldBlocks() {
        blocks = new HashSet<>();
        blocks.add(Material.STONE);
        blocks.add(Material.GRASS);
        blocks.add(Material.DIRT);
        blocks.add(Material.COBBLESTONE);
        blocks.add(Material.WOOD);
        blocks.add(Material.BEDROCK);
        blocks.add(Material.SAND);
        blocks.add(Material.GRAVEL);
        blocks.add(Material.GOLD_ORE);
        blocks.add(Material.IRON_ORE);
        blocks.add(Material.COAL_ORE);
        blocks.add(Material.LOG);
        blocks.add(Material.LEAVES);
        blocks.add(Material.SPONGE);
        blocks.add(Material.GLASS);
        blocks.add(Material.LAPIS_ORE);
        blocks.add(Material.LAPIS_BLOCK);
        blocks.add(Material.DISPENSER);
        blocks.add(Material.SANDSTONE);
        blocks.add(Material.NOTE_BLOCK);
        blocks.add(Material.POWERED_RAIL);
        blocks.add(Material.DETECTOR_RAIL);
        blocks.add(Material.PISTON_STICKY_BASE);
        blocks.add(Material.WEB);
        blocks.add(Material.PISTON_BASE);
        blocks.add(Material.WOOL);
        blocks.add(Material.GOLD_BLOCK);
        blocks.add(Material.IRON_BLOCK);
        blocks.add(Material.STEP);
        blocks.add(Material.BRICK);
        blocks.add(Material.TNT);
        blocks.add(Material.BOOKSHELF);
        blocks.add(Material.MOSSY_COBBLESTONE);
        blocks.add(Material.OBSIDIAN);
        blocks.add(Material.REDSTONE_ORE);
        blocks.add(Material.ICE);
        blocks.add(Material.SNOW_BLOCK);
        blocks.add(Material.CLAY);
        blocks.add(Material.JUKEBOX);
        blocks.add(Material.PUMPKIN);
        blocks.add(Material.NETHERRACK);
        blocks.add(Material.SOUL_SAND);
        blocks.add(Material.GLOWSTONE);
        blocks.add(Material.JACK_O_LANTERN);
        blocks.add(Material.STAINED_GLASS);
        blocks.add(Material.SMOOTH_BRICK);
        blocks.add(Material.MYCEL);
        blocks.add(Material.QUARTZ_BLOCK);
        blocks.add(Material.STAINED_CLAY);
        blocks.add(Material.LEAVES_2);
        blocks.add(Material.LOG_2);
        blocks.add(Material.SLIME_BLOCK);
        blocks.add(Material.BARRIER);
        blocks.add(Material.PRISMARINE);
        blocks.add(Material.SEA_LANTERN);
        blocks.add(Material.HAY_BLOCK);
        blocks.add(Material.HARD_CLAY);
        blocks.add(Material.COAL_BLOCK);
        blocks.add(Material.PACKED_ICE);
        blocks.add(Material.RED_SANDSTONE);
    }

    public void addAdmin(String name) {
        admins.add(name);
    }

    public void removeAdmin(String name) {
        admins.remove(name);
    }

    public ArrayList<String> getAdmins() {
        return admins;
    }

    public ArrayList<Detection> getChecks() {
        return checks;
    }

    public static HashSet<Material> getScaffoldBlocks() {
        return blocks;
    }

    public boolean punishPlayers() {
        return punishPlayers;
    }

    public void setPunishPlayers(boolean punishPlayers) {
        this.punishPlayers = punishPlayers;
    }

    public static PersistentData getInstance() {
        return instance;
    }

    public static void setInstance(PersistentData instance) {
        PersistentData.instance = instance;
    }
}
