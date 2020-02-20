package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import top.suwings.main.Main;
import top.suwings.main.Tools;

import java.util.HashMap;

public class Gunpowder extends Power {
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        // 创造使用粒子效果
        player.spawnParticle(
                Particle.FLAME, player.getLocation(),
                100, 0, 0, 0, 1
        );
        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, 2, 1);
        // 临时给予玩家无敌并四周开始爆炸
        Tools.setPlayerGod(player.getUniqueId().toString(), true);
        // 创建连环爆炸效果
        Tools.createContinuousExplosion(player, 3f, 5, 0, 1);
        // 玩家无敌结束
        new BukkitRunnable() {
            @Override
            public void run() {
                Tools.setPlayerGod(player.getUniqueId().toString(), false);
            }
        }.runTaskLater(Main.self, 30);
    }

    @Override
    public int getCoolDownTick() {
        return 40 * TICK;
    }

    @Override
    public int getDurableValue() {
        return 1;
    }

    @Override
    public int getUseSpendValue() {
        return 6;
    }
}
