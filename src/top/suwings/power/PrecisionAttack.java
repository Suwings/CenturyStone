package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.suwings.main.Main;
import top.suwings.main.Tools;

import java.util.Collection;
import java.util.HashMap;

public class PrecisionAttack extends Power {

    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        int effectTime = 10;
        new LineRangeAttack().releaseLineRangeAttack(player, Particle.TOTEM, 0.6, false, (currentLocation) -> {
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
    }

    @Override
    public int getCoolDownTick() {
        return 0;
    }

    @Override
    public int getDurableValue() {
        return 0;
    }

    @Override
    public int getUseSpendValue() {
        return 0;
    }
}
