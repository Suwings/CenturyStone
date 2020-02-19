package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;

// 石英水晶效果
public class Quartz extends Power {

    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        // 播放使用音乐
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_2, 4, 2);
        // 给予速度矢量
        player.setVelocity(player.getLocation().getDirection().multiply(3));
        player.getWorld().spawnParticle(
                Particle.END_ROD, player.getLocation(),
                40, 0, 0, 0
        );
    }

    @Override
    public int getCoolDownTick() {
        return 4 * TICK;
    }

    @Override
    public int getDurableValue() {
        return 6;
    }

    @Override
    public int getUseSpendValue() {
        return 1;
    }
}
