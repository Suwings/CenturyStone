package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.suwings.base.Power;
import top.suwings.main.Tools;

import java.util.Collection;
import java.util.HashMap;

public class AreaInvincible extends Power {
    // 滞留型区域无敌
    public AreaInvincible(Player player) {
        double radius = 4;
        final int effectRange = (int) radius;
        int effectTime = 10;
        final Location location = player.getEyeLocation().clone();
        HashMap<Player, Integer> playerHealthMap = new HashMap<>();
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, (int) radius, 2);
        // 区域粒子效果
        Tools.spawnCircleParticle(location, Particle.END_ROD, radius, 10, effectTime, (Object self) -> {
            Collection<Entity> nearEntity = location.getWorld().getNearbyEntities(location, effectRange, effectRange, effectRange);
            for (Entity entity : nearEntity) {
                if (entity instanceof Player) {
                    Player entityPlayer = (Player) entity;
                    // 虽然是假的无敌，但是很厉害的无敌了
                    if (playerHealthMap.get(entityPlayer) != null) {
                        entityPlayer.setHealth(playerHealthMap.get(entityPlayer));
                    } else {
                        playerHealthMap.put(player,(int)entityPlayer.getHealth());
                        entityPlayer.setHealth(playerHealthMap.get(entityPlayer));
                    }
                    continue;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 1));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3 * 20, 1));
                }
            }
        });
    }
}
