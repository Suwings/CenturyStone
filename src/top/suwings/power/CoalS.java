package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class CoalS extends Power {
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        // 创建粒子效果
        player.getWorld().spawnParticle(
                Particle.TOTEM, player.getEyeLocation(),
                400, 4, 4, 4
        );
        // 群体周围实体加成效果
        final int effectRange = 8;
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 8, 1);
        final int effectTime = 10;
        List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
        for (Entity entity : nearEntity) {
            // 跳过对玩家的 Buff 加成
            if (entity instanceof Player) continue;
            // 对周围生命实体进行效果修正
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.getWorld().spawnParticle(
                        Particle.SPELL, livingEntity.getEyeLocation(),
                        40, 0, 0, 0, 0
                );
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectTime * 20, 1));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, effectTime * 20, 1));
            }
        }
    }

    @Override
    public int getCoolDownTick() {
        return 15 * TICK;
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
