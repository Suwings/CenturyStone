package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import top.suwings.main.CenturyStone;
import top.suwings.main.Tools;

import java.util.HashMap;

public class Diamond extends Power {
    private Player player;

    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        this.player = player;
        player.getWorld().spawnParticle(
                Particle.TOTEM, player.getLocation().add(player.getEyeLocation().getDirection()),
                1000, 3, 3, 3
        );
        // 播放图腾使用声音
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 8, 2);
        this.addPlayerHealth(10);
        Tools.setPlayerPotionEffect(player, 60, PotionEffectType.SPEED);
        Tools.setPlayerPotionEffect(player, 30, PotionEffectType.SATURATION);
        Tools.setPlayerPotionEffect(player, 30, PotionEffectType.REGENERATION);
        Tools.setPlayerPotionEffect(player, 30, PotionEffectType.JUMP);
        Tools.setPlayerPotionEffect(player, 30, PotionEffectType.FIRE_RESISTANCE);
        Tools.setPlayerPotionEffect(player, 30, PotionEffectType.ABSORPTION);
        // 再触发绿宝石效果
        new BukkitRunnable() {
            @Override
            public void run() {
                new Emerald().release(player, null);
            }
        }.runTaskLater(CenturyStone.centuryStone, 40);
    }

    // 玩家血量设置方法
    private void addPlayerHealth(double h) {
        final double MAX_H = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (player.getHealth() + h >= MAX_H) {
            player.setHealth(MAX_H);
        } else {
            player.setHealth(player.getHealth() + h);
        }
    }

    @Override
    public int getCoolDownTick() {
        return 60 * TICK;
    }

    @Override
    public int getDurableValue() {
        return 1;
    }

    @Override
    public int getUseSpendValue() {
        return 1;
    }
}
