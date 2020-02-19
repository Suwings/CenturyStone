package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import top.suwings.main.Tools;

import java.util.HashMap;
import java.util.List;

public class GlowStone extends Power {
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        // 创造使用粒子效果
        player.getWorld().spawnParticle(
                Particle.TOTEM, player.getLocation(),
                60, 2, 2, 2
        );
        player.spawnParticle(
                Particle.FLAME, player.getLocation(),
                500, 0, 0, 0, 1
        );
        // 群体玩家加成效果
        final int effectRange = 8;
        final int effectTime = 10;
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, effectRange, 1);
        // 给自己
        Tools.setPlayerPotionEffect(player, effectTime, PotionEffectType.SPEED);
        Tools.setPlayerPotionEffect(player, effectTime, PotionEffectType.INCREASE_DAMAGE);
        Tools.setPlayerPotionEffect(player, 8, PotionEffectType.REGENERATION);
        Tools.setPlayerPotionEffect(player, 3, PotionEffectType.ABSORPTION);
        // 循环给队友
        List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
        for (Entity entity : nearEntity) {
            if (entity instanceof Player) {
                Player entityPlayer = (Player) entity;
                entityPlayer.getWorld().spawnParticle(
                        Particle.SPELL, entityPlayer.getEyeLocation(),
                        40, 0, 0, 0, 1
                );
                Tools.setPlayerPotionEffect(entityPlayer, effectTime, PotionEffectType.SPEED);
                Tools.setPlayerPotionEffect(entityPlayer, effectTime, PotionEffectType.INCREASE_DAMAGE);
                Tools.setPlayerPotionEffect(entityPlayer, 8, PotionEffectType.REGENERATION);
                Tools.setPlayerPotionEffect(entityPlayer, 3, PotionEffectType.ABSORPTION);
            }
        }
    }

    @Override
    public int getCoolDownTick() {
        return 15 * TICK;
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
