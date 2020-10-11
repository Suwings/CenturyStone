package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.suwings.base.SimpleBukkitRunnable;
import top.suwings.main.Main;
import top.suwings.main.Tools;

import java.util.Collection;
import java.util.HashMap;

public class PrecisionAttack extends Power {

    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        int effectTime = 10;
        // 蓄力
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 2, 1);
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,
                player.getEyeLocation().add(player.getEyeLocation().getDirection()), 120, 0, 0, 0);
        Location eyeLocation = player.getEyeLocation();
        // 释放
        new SimpleBukkitRunnable((o) -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4 * 20, 1));
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 2, 6);
            new LineRangeAttack().releaseLineRangeAttack(eyeLocation, Particle.END_ROD, 0.3d, false, (currentLocation) -> {
                Location location = (Location) currentLocation;
                player.spawnParticle(Particle.DRAGON_BREATH, location, 10, 0, 0, 0, 2);
                Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 1, 1, 1);
                for (Entity entity : entities) {
                    if (entity instanceof Player) continue;
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectTime * 20, 1));
                        // 取随机伤害值
                        int damageHealth = Tools.random(20, Tools.damageMultipleConversion(120));
                        // livingEntity.damage(damageHealth, player);
                        Tools.damageEntity(livingEntity, damageHealth, player);
                    }
                }
            });
        }).runTaskLater(Main.self, 60);
    }

    @Override
    public int getCoolDownTick() {
        return 6 * TICK;
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
