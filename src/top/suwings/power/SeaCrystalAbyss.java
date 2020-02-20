package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.suwings.main.Tools;

import java.util.Collection;
import java.util.HashMap;

public class SeaCrystalAbyss extends Power {

    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        int effectTime = 30;
        double radius = 10;
        final int effectRange = (int) radius;
        final Location location = player.getEyeLocation().clone();
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 4, 2);
        // 释放粒子圆
        Tools.spawnCircleParticle(location, Particle.FLAME, radius, 20, effectTime, (Object self) -> {
            Collection<Entity> nearEntity = location.getWorld().getNearbyEntities(location, effectRange, effectRange, effectRange);
            for (Entity entity : nearEntity) {
                if (entity instanceof Player) {
                    Player entityPlayer = (Player) entity;
                    entityPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3 * 20, 1));
                    entityPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 20, 1));
                    continue;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 1));
                }
            }
        });
    }

    @Override
    public int getCoolDownTick() {
        return 30 * TICK;
    }

    @Override
    public int getDurableValue() {
        return 3;
    }

    @Override
    public int getUseSpendValue() {
        return 1;
    }
}
