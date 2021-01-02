package me.okexox.hades.data;

import com.google.common.primitives.UnsignedInteger;
import org.bukkit.util.Vector;

public class PlayerData {
    //A player shouldn't have speed for 10 moves before it's considered as not having speed.
    private int speedTick = 0;
    //The amplification level of the speed effect
    private int speedAmplifier = 0;
    //A player shouldn't have jump for 10 moves before it's considered as not having jump.
    private int jumpTick = 0;
    //The amplification level of the jump effect
    private int jumpAmplifier = 0;
    //Last time they healed
    private long lastHeal = -4000;
    //If they are in combat
    private int combatTick = 0;
    //A player shouldn't have fly for 5 moves before it's considered as not having speed.
    private int flyTick = 0;
    //Checks for how many packets a player has been jumping
    private int jumpTime = 0;
    //A player shouldn't be near a boat for 5 moves for it to be save;
    private int boatTick = 0;
    //If a player is eating or not
    private boolean isSlowedDown = false;
    //If a player recently broke a block under them
    private boolean brokeUnderBlock = false;
    //Give the player 10 ticks to fully slow down
    private int slowdownTransition = 8;
    //Blocks broken in the last second
    private int blocksBroken = 0;
    //Speed in the last move
    private int wasNearSlimeBlock = 0;
    //Checks if a player was swimming in the last 3 moves
    private int wasSwimming = 0;
    //Certain jumps are very different, and as such should not flag certain detections
    //E.G: Piston + slimeblock launch
    private boolean badJump = false;
    //How many moves in a row the player has been on the ground
    private int onGroundMoves = 0;
    //The y difference in the previous move
    private double lastVerticalSpeed = 0;
    //Checks whether the player was recently in flying mode
    private int flewRecently = 0;
    //Stores if arm was swung
    private boolean armSwung = false;
    //Checks whether the newest move was an edge teleport.
    private boolean isEdgeTeleport = false;
    //y difference last tick
    private double yDiff = 0;
    //A block place swing animation comes after the block place
    //Set to true when swinging, and false when placing a block. As such, this should always be true when you place a block.
    private boolean maintainsBlockSwing = true;
    //Jesus needs to check the next move due to a move delay of the onGround
    private boolean isJesusFlagged = false;
    //Need to use the pitch of the move after the blockplace, so need to store the expected value for the next move, -180 means that it doesn't need to check.
    private double scaffoldPitchFlag = -180;
    //Need to use the yaw of the move after the blockplace, so need to store the expected value for the next move, -1 means that it doesn't need to check.
    private double scaffoldYawFlag = -1;
    //Check horizontal distance a tick later due to the onground delay
    private double hDistance = -1;
    //Used by the ExpectedJump
    private double lastYMotion = 0;
    //The amount of ticks that a player has not had blocks under them
    private int noFloorTicks = 0;



    public int getSpeed() {
        return speedAmplifier;
    }

    public void updateSpeed(int speed) {
        if(speed >= speedAmplifier) {
            speedAmplifier = speed;
            speedTick = 10;
        }
        if(speed < speedAmplifier) {
            if(--speedTick == 0) {
                speedAmplifier = speed;
            }
        }
    }

    public int getJumpEffect() {
        return jumpAmplifier;
    }

    public void updateJumpEffect(int jumpEffect) {
        if(jumpEffect > jumpAmplifier) {
            jumpAmplifier = jumpEffect;
            jumpTick = 10;
        }
        if(jumpEffect < jumpAmplifier) {
            if(--jumpTick == 0) {
                jumpAmplifier = jumpEffect;
            }
        }
    }

    public void updateFly(boolean isFlying) {
        if(isFlying) {
            flyTick = 20;
        } else if (flyTick > 0) {
            flyTick--;
        }
    }

    public boolean isFlying() {
        return !(flyTick == 0);
    }

    public void updateBoat(boolean boatNearby) {
        if(boatNearby) {
            boatTick = 5;
        } else if(boatTick > 0) {
            boatTick--;
        }
    }

    public boolean getBoatNearby() {
        return (boatTick != 0);
    }

    public long getLastHeal() {
        return lastHeal;
    }

    public void setLastHeal(long lastHeal) {
        this.lastHeal = lastHeal;
    }

    public void setCombat(int value) {
        combatTick = value;
    }

    public void updateCombat() {
        if(combatTick > 0) {
            combatTick--;
        }
    }

    public boolean inCombat() {
        return combatTick > 0;
    }

    public void setJump(boolean isJumping) {
        if(isJumping) {
            jumpTime++;
        } else {
            jumpTime = 0;
        }
    }

    public int getJumpTime() {
        return jumpTime;
    }

    public boolean isSlowedDown() {
        return isSlowedDown;
    }

    public void setSlowedDown(boolean slowdown) {
        isSlowedDown = slowdown;
        slowdownTransition = 10;
    }

    public boolean brokeBlockUnder() {
        return brokeUnderBlock;
    }

    public void setBrokeUnderBlock(boolean brokeUnderBlock) {
        this.brokeUnderBlock = brokeUnderBlock;
    }

    public boolean slowdownTransitioned() {
        if(slowdownTransition == 0) {
            return true;
        } else {
            slowdownTransition--;
            return false;
        }
    }

    public int checkBlocksBroken() {
        return ++blocksBroken;
    }

    public void resetBlocksBroken() {
        blocksBroken = 0;
    }

    public void setNearSlimeBlock(boolean value) {
        if(value) {
            wasNearSlimeBlock = 5;
        } else if(wasNearSlimeBlock > 0) {
            wasNearSlimeBlock--;
        }
    }

    public boolean wasNearSlimeBlock() {
        return wasNearSlimeBlock != 0;
    }

    public boolean wasSwimming() {
        return !(wasSwimming == 0);
    }

    public void updateSwimming(boolean isSwimming) {
        if(isSwimming) {
            wasSwimming = 3;
        } else if(wasSwimming > 0) {
            wasSwimming--;
        }
    }

    public boolean isBadJump() {
        return badJump;
    }

    public void setBadJump(boolean badJump) {
        this.badJump = badJump;
    }

    public void setOnGround(boolean onGround) {
        if(onGround) {
            onGroundMoves++;
        } else {
            onGroundMoves = 0;
        }
    }

    public int getOnGroundMoves() {
        return onGroundMoves;
    }

    public double getLastVerticalSpeed() {
        return lastVerticalSpeed;
    }

    public void setLastVerticalSpeed(double lastVerticalSpeed) {
        this.lastVerticalSpeed = lastVerticalSpeed;
    }

    public void setFlewRecently(boolean isFlying) {
        if(isFlying) {
            flewRecently = 5;
        } else if(flewRecently > 0) {
            flewRecently--;
        }
    }

    public boolean flewRecently() {
        return  (flewRecently > 0);
    }

    public boolean isArmSwung() {
        return armSwung;
    }

    public void setArmSwung(boolean armSwung) {
        this.armSwung = armSwung;
    }

    public boolean isEdgeTeleport() {
        return isEdgeTeleport;
    }

    public void setEdgeTeleport(boolean edgeTeleport) {
        isEdgeTeleport = edgeTeleport;
    }

    public double getyDiff() {
        return yDiff;
    }

    public void setyDiff(double yDiff) {
        this.yDiff = yDiff;
    }

    public boolean maintainsBlockSwing() {
        return maintainsBlockSwing;
    }

    public void setBlockSwing(boolean maintainsBlockSwing) {
        this.maintainsBlockSwing = maintainsBlockSwing;
    }

    public boolean isJesusFlagged() {
        return isJesusFlagged;
    }

    public void setJesusFlagged(boolean jesusFlagged) {
        isJesusFlagged = jesusFlagged;
    }

    public double getScaffoldPitchFlag() {
        return scaffoldPitchFlag;
    }

    public void setScaffoldPitchFlag(double scaffoldPitchFlag) {
        this.scaffoldPitchFlag = scaffoldPitchFlag;
    }

    public double getScaffoldYawFlag() {
        return scaffoldYawFlag;
    }

    public void setScaffoldYawFlag(double scaffoldYawFlag) {
        this.scaffoldYawFlag = scaffoldYawFlag;
    }

    public double gethDistance() {
        return hDistance;
    }

    public void sethDistance(double hDistance) {
        this.hDistance = hDistance;
    }

    public double getLastYMotion() {
        return lastYMotion;
    }

    public void setLastYMotion(double lastYMotion) {
        this.lastYMotion = lastYMotion;
    }

    public int getNoFloorTicks() {
        return noFloorTicks;
    }

    public void setNoFloorTicks(boolean isFloor) {
        if(isFloor) {
            noFloorTicks = 0;
        } else {
            noFloorTicks++;
        }
    }
}
