package top.suwings.power;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import top.suwings.base.NullCallback;
import top.suwings.main.CenturyStone;
import top.suwings.main.Tools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Emerald extends Power {

    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        // 创造使用粒子效果
        player.getWorld().spawnParticle(
                Particle.FLAME, player.getLocation(),
                400, 0, 0, 0, 1
        );
        player.getWorld().spawnParticle(
                Particle.SMOKE_NORMAL, player.getLocation(),
                200, 0, 0, 0, 1
        );
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 12, 1);
        // 对范围内的怪物生命实体暂时性取消 AI
        final int noAITime = 8;
        final int effectRange = 16;
        // 创造粒子圆
        Tools.spawnCircleParticle(player.getEyeLocation().clone(), Particle.END_ROD, effectRange, 20, 7, new NullCallback());
        // 周围怪物给予效果
        List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
        List<LivingEntity> noAiEntity = new LinkedList<>();
        for (Entity entity : nearEntity) {
            // 跳过对玩家的效果
            if (entity instanceof Player) {
                continue;
            }
            // 暂时性取消周围实体的 AI
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.getWorld().spawnParticle(
                        Particle.SPELL, livingEntity.getEyeLocation(),
                        40, 0, 0, 0, 1
                );
                livingEntity.setAI(false);
                noAiEntity.add(livingEntity);
            }
        }
        // 给缓存列表中的AI定时恢复
        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity livingEntity : noAiEntity) {
                    livingEntity.setAI(true);
                }
            }
        }.runTaskLater(CenturyStone.centuryStone, noAITime * 20);
    }

    @Override
    public int getCoolDownTick() {
        return 36 * TICK;
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
