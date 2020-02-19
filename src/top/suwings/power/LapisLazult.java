package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import top.suwings.main.Main;

import java.util.HashMap;
import java.util.List;

public class LapisLazult extends Power {
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        // 创造使用粒子效果
        player.getWorld().spawnParticle(
                Particle.END_ROD, player.getEyeLocation(),
                800, 3, 3, 3
        );
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 16, 1);
        // 延时发动技能
        new BukkitRunnable() {
            @Override
            public void run() {
                // 群体周围实体加成效果
                final int effectRange = 10;
                final int effectTime = 8;
                List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
                // 每个实体赋值
                for (Entity entity : nearEntity) {
                    if (entity instanceof Player) continue;
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, effectTime * 20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, effectTime * 20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, effectTime * 20, 1));
                    }
                }
            }
        }.runTaskLater(Main.self, 30);
    }

    @Override
    public int getCoolDownTick() {
        return 25 * TICK;
    }

    @Override
    public int getDurableValue() {
        return 4;
    }

    @Override
    public int getUseSpendValue() {
        return 1;
    }
}
