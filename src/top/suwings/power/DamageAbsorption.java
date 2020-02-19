package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

// 超级伤害吸收
public class DamageAbsorption extends Power {
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        player.getWorld().spawnParticle(
                Particle.SUSPENDED_DEPTH, player.getLocation().add(player.getEyeLocation().getDirection()),
                800, 4, 4, 4
        );
        // 播放声音
        // 群体周围实体加成效果
        final int effectRange = 8;
        final int effectTime = 60;

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, effectRange, 3);
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, effectTime * 20, 4));
        List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
        for (Entity entity : nearEntity) {
            // 跳过对玩家的 Buff 加成
            if (entity instanceof Player) {
                Player nearPlayer = (Player) entity;
                nearPlayer.getWorld().spawnParticle(Particle.SPELL, nearPlayer.getEyeLocation(), 40, 0, 0, 0, 0);
                nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, effectTime * 20, 4));
            }
        }
    }

    @Override
    public int getCoolDownTick() {
        return 30 * TICK;
    }

    @Override
    public int getDurableValue() {
        return 2;
    }

    @Override
    public int getUseSpendValue() {
        return 1;
    }
}
