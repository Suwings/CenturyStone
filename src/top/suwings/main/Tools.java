package top.suwings.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import top.suwings.base.MineCallback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Tools {

    public static HashMap<String, Boolean> godPlayerMap = new HashMap<>();

    // 玩家药水效果设置方法
    public static void setPlayerPotionEffect(Player player, int potionTime, PotionEffectType z) {
        player.addPotionEffect(new PotionEffect(z, potionTime * 20, 1));
    }

    public static void setPlayerPotionEffect(Player player, int potionTime, PotionEffectType z, int amplifier) {
        player.addPotionEffect(new PotionEffect(z, potionTime * 20, 1));
    }

    // 玩家无敌设置方法
    public static void setPlayerGod(String uuid, boolean f) {
        if (f) {
            godPlayerMap.put(uuid, true);
        } else {
            godPlayerMap.remove(uuid);
        }
    }

    // 玩家无敌设置方法
    public static void setPlayerGod(Player player, boolean f) {
        if (f) {
            godPlayerMap.put(player.getUniqueId().toString(), true);
        } else {
            godPlayerMap.remove(player.getUniqueId().toString());
        }
    }

    //比较两个list
    //取出存在menuOneList中，但不存在resourceList中的数据，差异数据放入differentList
    public static <T> List<T> listCompare(List<T> menuOneList, List<T> resourceList) {
        List<T> differentList = new ArrayList<T>();
        for (T resource : menuOneList) { //取出每一个menuOneList
            if (!resourceList.contains(resource)) { //不存在于resourceList，则加入
                differentList.add(resource);
            }
        }
        return differentList;
    }

    public static void playerLevelHealth(Player player) {
        int currentLevel = player.getLevel();
        final int BASE_HEALTH = 20;
        int uph = 0;
        int divi = 4;
        if (currentLevel > 0) {
            uph = (((currentLevel) / divi)) * 2;
            if (BASE_HEALTH + uph >= 60) {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60);
            } else {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(BASE_HEALTH + uph);
            }
        } else {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(BASE_HEALTH);
        }
    }

    public static void spawnCircleParticle(Location location, Particle particleEffect,
                                           double radius, int period, int runnableCount,
                                           MineCallback callback) {
        final int[] currentRunCount = {0};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (currentRunCount[0] >= runnableCount)
                    this.cancel();
                if (location.getWorld() == null) {
                    this.cancel();
                    return;
                }
                for (int degree = 0; degree < 360; degree += 6) {
                    double radians = Math.toRadians(degree);
                    double x = radius * Math.cos(radians);
                    double z = radius * Math.sin(radians);
                    location.add(x, 0d, z);
                    location.getWorld().spawnParticle(
                            particleEffect,
                            location,
                            period + 10, 0, 0, 0, 0
                    );
                    location.subtract(x, 0d, z);
                }
                callback.run(this);
                currentRunCount[0] += 1;
            }
        }.runTaskTimer(CenturyStone.centuryStone, 0, period);
    }

    // 爆炸效果
    // 推荐参考值：
    //    float power = 6;
    //    int safeDistance = 3;
    //    int time = 0;
    //    int explosionCount = 3;
    public static void createContinuousExplosion(Player player, float power, int safeDistance, int initTime, int explosionCount) {
        for (int i = 0; i < explosionCount; i++) {
            int finalSafeDistance = safeDistance;
            initTime += 10;
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getWorld().createExplosion(
                            player.getLocation().getX() + finalSafeDistance,
                            player.getLocation().getY(),
                            player.getLocation().getZ() + finalSafeDistance,
                            power, false, false
                    );
                    player.getWorld().createExplosion(
                            player.getLocation().getX() - finalSafeDistance,
                            player.getLocation().getY(),
                            player.getLocation().getZ() + finalSafeDistance,
                            power, false, false
                    );
                    player.getWorld().createExplosion(
                            player.getLocation().getX() - finalSafeDistance,
                            player.getLocation().getY(),
                            player.getLocation().getZ() - finalSafeDistance,
                            power, false, false
                    );
                    player.getWorld().createExplosion(
                            player.getLocation().getX() + finalSafeDistance,
                            player.getLocation().getY(),
                            player.getLocation().getZ() - finalSafeDistance,
                            power, false, false
                    );
                    player.getWorld().createExplosion(
                            player.getLocation().getX() + finalSafeDistance,
                            player.getLocation().getY(),
                            player.getLocation().getZ(),
                            power, false, false
                    );
                    player.getWorld().createExplosion(
                            player.getLocation().getX(),
                            player.getLocation().getY(),
                            player.getLocation().getZ() + finalSafeDistance,
                            power, false, false
                    );
                    player.getWorld().createExplosion(
                            player.getLocation().getX(),
                            player.getLocation().getY(),
                            player.getLocation().getZ() - finalSafeDistance,
                            power, false, false
                    );
                    player.getWorld().createExplosion(
                            player.getLocation().getX() - finalSafeDistance,
                            player.getLocation().getY(),
                            player.getLocation().getZ(),
                            power, false, false
                    );
                }
            }.runTaskLater(CenturyStone.centuryStone, initTime);
            // 爆炸距离递增
            safeDistance += 1;
        }
    }
}
