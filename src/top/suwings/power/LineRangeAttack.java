package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.suwings.base.MineCallback;
import top.suwings.base.SimpleBukkitRunnable;
import top.suwings.main.Main;
import top.suwings.main.Tools;

import javax.tools.Tool;
import java.util.Collection;
import java.util.HashMap;

public class LineRangeAttack extends Power {
    // 世纪之石技能效果
    // 燧石效果
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        // 释放
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 2);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2 * 20, 1));
        final World playerWorld = player.getWorld();
        int effectTime = 2;
        // 释放直线范围攻击效果
        this.releaseLineRangeAttack(player.getEyeLocation(), Particle.DRAGON_BREATH, 0.15, false, (currentLocation) -> {
            Location location = (Location) currentLocation;
            Collection<Entity> entities = playerWorld.getNearbyEntities(location, 1, 1, 1);
            for (Entity entity : entities) {
                if (entity instanceof Player) {
                    continue;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectTime * 20, 1));
                    // 对怪物造成5%的比例伤害，但是最大伤害不可超过30血，最小不低于3
//                    double damageHealth = Tools.damageMultipleConversion(Tools.random(2, 8));
                    double currentHealth = livingEntity.getHealth();
                    double damageHealth = (int) currentHealth * 0.05;
                    if (damageHealth >= Tools.damageMultipleConversion(20))
                        damageHealth = Tools.damageMultipleConversion(20);
                    if (damageHealth <= 2) damageHealth = 2;
                    Tools.damageEntity(livingEntity, damageHealth, player);
                }
            }
        });
    }

    @Override
    public int getCoolDownTick() {
        return 20;
    }

    @Override
    public int getDurableValue() {
        return 3;
    }

    @Override
    public int getUseSpendValue() {
        return 1;
    }

    public void releaseLineRangeAttack(Player player, Particle particle, double attackTime, MineCallback tickCallback) {
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
        }).runTaskTimer(Main.self, 0, 1);
    }

    public void releaseLineRangeAttack(Location location, Particle particle, double attackTime, boolean isOtherLine, MineCallback tickCallback) {
        final World playerWorld = location.getWorld();
        double processMultiplyInitValue = 2;
        double[] processMultiply = {processMultiplyInitValue};
        final Location location1 = location.clone();
        new SimpleBukkitRunnable((self) -> {
            Vector directionVector = location1.getDirection();
            Location location2 = location1.add(directionVector.multiply(processMultiply[0]));
            playerWorld.spawnParticle(
                    particle,
                    location2,
                    40, 0, 0, 0, 0
            );
            if (isOtherLine) {
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
            }
            tickCallback.run(location1);
            processMultiply[0] += 0.005;
            // attackTime 推荐是 0.2~0.4 大概0.4是80格
            if (processMultiply[0] >= processMultiplyInitValue + attackTime) {
                ((BukkitRunnable) self).cancel();
            }
        }).runTaskTimer(Main.self, 0, 1);
    }
}

//            if (directionVector.getX() > 0 && directionVector.getZ() > 0 || directionVector.getX() < 0 && directionVector.getZ() < 0) {
//                vectorLeft = new Vector(-directionVector.getX(), 0, directionVector.getZ());
//                vectorRight = new Vector(directionVector.getX(), 0, -directionVector.getZ()));
//            } else {
//                vectorLeft = new Vector(directionVector.getX(), 0, -directionVector.getZ());
//                vectorRight = new Vector(-directionVector.getX(), 0, directionVector.getZ());
//            }