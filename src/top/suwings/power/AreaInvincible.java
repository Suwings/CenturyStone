package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.suwings.base.NullCallback;
import top.suwings.main.Tools;

import java.util.Collection;
import java.util.HashMap;

public class AreaInvincible extends Power {

    private final int CD_TIME = 60 * TICK;
    private final int SPEND = 1;
    private final int DURABLE = 2;

    // 滞留型区域无敌
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        double radius = 6;
        final int effectRange = (int) radius;
        int effectTime = 14;
        final Location location = player.getEyeLocation().clone();
        HashMap<Player, Integer> playerHealthMap = new HashMap<>();
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 6, 2);
        // 区域粒子效果
        Tools.spawnCircleParticle(location, Particle.CLOUD, radius, 10, effectTime * 2, (Object self) -> {
            Collection<Entity> nearEntity = location.getWorld().getNearbyEntities(location, effectRange, effectRange, effectRange);
            for (Entity entity : nearEntity) {
                if (entity instanceof Player) {
//                    过于变态，现已删除
//                    Player entityPlayer = (Player) entity;
//                    if (!entityPlayer.isDead() || entityPlayer.getHealth() > 0) {
//                        if (playerHealthMap.get(entityPlayer) != null) {
//                            entityPlayer.setHealth(playerHealthMap.get(entityPlayer));
//                        } else {
//                            playerHealthMap.put(entityPlayer, (int) entityPlayer.getHealth());
//                            entityPlayer.setHealth(playerHealthMap.get(entityPlayer));
//                        }
//                    }
                    continue;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 1));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3 * 20, 1));
                    livingEntity.damage(Tools.damageMultipleConversion(1), player);
                }
            }
        });
        Tools.spawnCircleParticle(location, Particle.ENCHANTMENT_TABLE, radius - 1, 20, effectTime, new NullCallback());
    }

    @Override
    public int getCoolDownTick() {
        return CD_TIME;
    }

    @Override
    public int getDurableValue() {
        return DURABLE;
    }

    @Override
    public int getUseSpendValue() {
        return SPEND;
    }
}
