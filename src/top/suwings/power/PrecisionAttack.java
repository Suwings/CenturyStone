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
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 4, 1);
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,
                player.getEyeLocation().add(player.getEyeLocation().getDirection()), 50, 0, 0, 0);
        new SimpleBukkitRunnable((o) -> {
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 4, 6);
            new LineRangeAttack().releaseLineRangeAttack(player, Particle.END_ROD, 1.6, false, (currentLocation) -> {
                Location location = (Location) currentLocation;
                Collection<Entity> entities = player.getWorld().getNearbyEntities(location, 1, 1, 1);
                for (Entity entity : entities) {
                    if (entity instanceof Player) continue;
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectTime * 20, 1));
                        // 取随机伤害值
                        int damageHealth = Tools.random(1, Tools.damageMultipleConversion(24));
                        livingEntity.damage(damageHealth, player);
                    }
                }
            });
        }).runTaskLater(Main.self, 60);
    }

    @Override
    public int getCoolDownTick() {
        return 5 * TICK;
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
