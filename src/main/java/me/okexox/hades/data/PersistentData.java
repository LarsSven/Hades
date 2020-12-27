package me.okexox.hades.data;

import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.combat.NoSwing;
import me.okexox.hades.modules.combat.WallHit;
import me.okexox.hades.modules.exploits.FastHeal;
import me.okexox.hades.modules.exploits.Nuker;
import me.okexox.hades.modules.exploits.SelfDamage;
import me.okexox.hades.modules.integrity.OnGroundIntegrity;
import me.okexox.hades.modules.integrity.PitchIntegrity;
import me.okexox.hades.modules.integrity.VehicleIntegrity;
import me.okexox.hades.modules.movement.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class PersistentData implements Serializable {
    private long serialVersionUID = 1;
    public static final String HADES = ChatColor.RED + "[Hades] ";

    private ArrayList<Detection> checks;
    private ArrayList<Player> admins;

    public PersistentData() {
        admins = new ArrayList<>();
        checks = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        //Combat
        checks.add(new NoSwing());
        checks.add(new WallHit());
        //Exploits
        checks.add(new FastHeal());
        checks.add(new Nuker());
        checks.add(new SelfDamage());
        //Integrity
        checks.add(new OnGroundIntegrity());
        checks.add(new PitchIntegrity());
        checks.add(new VehicleIntegrity());
        //Movement
        checks.add(new ExcessiveJump());
        checks.add(new HorizontalSpeed());
        checks.add(new Jesus());
        checks.add(new NoGravity());
        checks.add(new NoSlowDown());
        checks.add(new VerticalAcceleration());
        checks.add(new VerticalSpeed());
    }

    public void addAdmin(Player name) {
        admins.add(name);
    }

    public void removeAdmin(Player name) {
        admins.remove(name);
    }

    public ArrayList<Player> getAdmins() {
        return admins;
    }

    public ArrayList<Detection> getChecks() {
        return checks;
    }
}
