package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.suwings.base.MineCallback;
import top.suwings.base.SimpleBukkitRunnable;
import top.suwings.main.CenturyStone;

public class LineRangeAttack extends Power {

    public LineRangeAttack(Player player, Particle particle, double attackTime, MineCallback tickCallback) {
        final World playerWorld = player.getWorld();
        double processMultiplyInitValue = 2;
        double[] processMultiply = {processMultiplyInitValue};
        final Location location1 = player.getEyeLocation().clone();
        new SimpleBukkitRunnable((self) -> {
            Vector directionVector = location1.getDirection();
            Location location2 = location1.add(directionVector.multiply(processMultiply[0]));
            playerWorld.spawnParticle(
                    particle,
                    location2,
                    40, 0, 0, 0, 0
            );
            Vector vectorLeft = directionVector.clone();
            Vector vectorRight = directionVector.clone();
            vectorLeft = vectorLeft.rotateAroundY(90);
            vectorLeft = vectorLeft.multiply(0.8);
            vectorRight = vectorRight.rotateAroundY(-90);
            vectorRight = vectorRight.multiply(0.8);
            Location tmpLocation1 = location2.clone();
            Location tmpLocation2 = location2.clone();
            Location vectorLeftLocation = tmpLocation1.add(vectorLeft);
            Location vectorRightLocation = tmpLocation2.add(vectorRight);
            playerWorld.spawnParticle(
                    particle,
                    vectorLeftLocation,
                    20, 0, 0, 0, 0
            );
            playerWorld.spawnParticle(
                    particle,
                    vectorRightLocation,
                    20, 0, 0, 0, 0
            );

            tickCallback.run(location1);
            processMultiply[0] += 0.01;
            // attackTime 推荐是 0.4~0.8 大概0.4是40格
            if (processMultiply[0] >= processMultiplyInitValue + attackTime) {
                ((BukkitRunnable) self).cancel();
            }
        }).runTaskTimer(CenturyStone.centuryStone, 0, 1);
    }

    @Override
    public int getCoolDownTick() {
        return 2 * 20;
    }

    @Override
    public int getDurableValue() {
        return 6;
    }

    @Override
    public int getUseSpendValue() {
        return 1;
    }
}

//            if (directionVector.getX() > 0 && directionVector.getZ() > 0 || directionVector.getX() < 0 && directionVector.getZ() < 0) {
//                vectorLeft = new Vector(-directionVector.getX(), 0, directionVector.getZ());
//                vectorRight = new Vector(directionVector.getX(), 0, -directionVector.getZ()));
//            } else {
//                vectorLeft = new Vector(directionVector.getX(), 0, -directionVector.getZ());
//                vectorRight = new Vector(-directionVector.getX(), 0, directionVector.getZ());
//            }