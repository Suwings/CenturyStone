package top.suwings.skill;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import top.suwings.base.NullCallback;
import top.suwings.base.SimpleBukkitRunnable;
import top.suwings.book.CenturyBooks;
import top.suwings.main.CenturyStone;
import top.suwings.main.Tools;
import top.suwings.power.LineRangeAttack;

import javax.tools.Tool;
import java.util.*;


public class StoneSkill {
    //EMERALD 绿宝石 DIAMOND 钻石 FLINT 燧石  LAPIS_LAZULI 青金石

    private Player player;
    private ItemStack itemStack;

    // 所有石头的效果触发事件
    public void onStoneSkill(final Player player, final ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack;
        Material material = itemStack.getType();
        final int itemAmount = itemStack.getAmount();
        // 技能冷却检测
        if (SkillCoolDown.isSkillCooling(player, itemStack.getType())) {
            // 玩家技能冷却粒子提示ITEM ARMOR EQUIP GENERIC
            // Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title '" + player.getName() + "' actionbar \"无法使用,正在冷却中..\"");
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);
            player.spawnParticle(Particle.CLOUD, player.getLocation().add(player.getEyeLocation().getDirection()), 20, 1, 1, 1, 1);
            return;
        }
        // 绿宝石
        if (itemStack.getType() == Material.EMERALD && itemAmount >= StoneSpend.EMERALD) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.EMERALD_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.EMERALD_DURABLE, itemStack, StoneSpend.EMERALD);
            this.emeraldSkill();
        }
        // 钻石
        if (itemStack.getType() == Material.DIAMOND && itemAmount >= StoneSpend.DIAMOND) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.DIAMOND_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.DIAMOND_DURABLE, itemStack, StoneSpend.DIAMOND);
            this.diamondSkill();
        }
        // 燧石
        if (itemStack.getType() == Material.FLINT && itemAmount >= StoneSpend.FLINT) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.FLINT_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.FLINT_DURABLE, itemStack, StoneSpend.FLINT);
            this.flintSkill();
        }
        // 青金石
        if (itemStack.getType() == Material.LAPIS_LAZULI && itemAmount >= StoneSpend.LAPIS_LAZULI) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.LAPIS_LAZULI_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.LAPIS_LAZULI_DURABLE, itemStack, StoneSpend.LAPIS_LAZULI);
            this.lapisSkill();
        }
        // 碳
        if (itemStack.getType() == Material.COAL && itemAmount >= StoneSpend.COAL) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.COAL_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.COAL_DURABLE, itemStack, StoneSpend.COAL);
            this.coalSkill();
        }
        // 萤石
        if (itemStack.getType() == Material.GLOWSTONE_DUST && itemAmount >= StoneSpend.GLOWSTONE_DUST) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.GLOWSTONE_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.GLOWSTONE_DUST_DURABLE, itemStack, StoneSpend.GLOWSTONE_DUST);
            this.glowStoneSkill();
        }
        // 火药
        if (itemStack.getType() == Material.GUNPOWDER && itemAmount >= StoneSpend.GUNPOWDER) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.GUNPOWDER_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.GUNPOWDER_DURABLE, itemStack, StoneSpend.GUNPOWDER);
            this.gunpowderSkill();

        }
        // 下届石英水晶
        if (itemStack.getType() == Material.QUARTZ && itemAmount >= StoneSpend.QUARTZ) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.QUARTZ_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.QUARTZ_DURABLE, itemStack, StoneSpend.QUARTZ);
            this.quartzSkill();
        }
        // 海晶沙粒
        if (itemStack.getType() == Material.PRISMARINE_CRYSTALS && itemAmount >= StoneSpend.PRISMARINE_CRYSTALS) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.PRISMARINE_CRYSTALS_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.PRISMARINE_CRYSTALS_DURABLE, itemStack, StoneSpend.PRISMARINE_CRYSTALS);
            this.damageAbsorption();
        }
        // 海晶碎片
        if (itemStack.getType() == Material.PRISMARINE_SHARD && itemAmount >= StoneSpend.PRISMARINE_SHARD) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.PRISMARINE_SHARD_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.PRISMARINE_SHARD_DURABLE, itemStack, StoneSpend.PRISMARINE_SHARD);
            this.seaCrystalAbyss();
        }
        // 附魔书
        if (itemStack.getType() == Material.ENCHANTED_BOOK) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.PRISMARINE_SHARD_CD_TIME);
            Map<Enchantment, Integer> enchantmentMap = itemStack.getEnchantments();
            for (Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()) {
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                CenturyBooks.onUseCenturyBook(player, enchantment, level);
                // 有且只有一个效果能够被触发
                break;
            }
        }
        // 金锭
        if (itemStack.getType() == Material.GOLD_INGOT && itemAmount >= StoneSpend.GOLD_INGOT) {
            SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), StoneSpend.GOLD_INGOT_CD_TIME);
            StoneWearManager.autoStoneBroken(player, material, StoneSpend.GOLD_INGOT_DURABLE, itemStack, StoneSpend.GOLD_INGOT);
            this.aeraInvincible();
        }
    }

    // 技能效果
    // 滞留型区域无敌
    private void aeraInvincible() {
        double radius = 4;
        final int effectRange = (int) radius;
        int effectTime = 10;
        final Location location = player.getEyeLocation().clone();
        HashMap<Player, Integer> playerHealthMap = new HashMap<>();
        Collection<Entity> nearEntityInit = location.getWorld().getNearbyEntities(location, effectRange, effectRange, effectRange);
        for (Entity entity : nearEntityInit) {
            if (entity instanceof Player) {
                Player entityPlayer = (Player) entity;
                playerHealthMap.put(entityPlayer, (int) player.getHealth());
            }
        }
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, (int) radius, 2);
        // 区域粒子效果
        Tools.spawnCircleParticle(location, Particle.END_ROD, radius, 10, effectTime, (Object self) -> {
            Collection<Entity> nearEntity = location.getWorld().getNearbyEntities(location, effectRange, effectRange, effectRange);
            for (Entity entity : nearEntity) {
                if (entity instanceof Player) {
                    Player entityPlayer = (Player) entity;
                    entityPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3 * 20, 1));
                    // 虽然是假的无敌，但是很厉害的无敌了
                    player.setHealth(playerHealthMap.get(player));
                    continue;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 1));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3 * 20, 1));
                }
            }
        });
    }

    // 技能效果
    // 持久性区域
    private void seaCrystalAbyss() {
        int effectTime = 30;
        double radius = 10;
        final int effectRange = (int) radius;
        final Location location = player.getEyeLocation().clone();
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, effectRange, 2);
        // 释放粒子圆
        Tools.spawnCircleParticle(location, Particle.FLAME, radius, 20, effectTime, (Object self) -> {
            Collection<Entity> nearEntity = location.getWorld().getNearbyEntities(location, effectRange, effectRange, effectRange);
            for (Entity entity : nearEntity) {
                if (entity instanceof Player) {
                    Player entityPlayer = (Player) entity;
                    entityPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3 * 20, 1));
                    entityPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 20, 1));
                    continue;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 1));
                }
            }
        });
    }

    // 技能效果
    // 超级伤害吸收
    private void damageAbsorption() {
        player.getWorld().spawnParticle(
                Particle.SUSPENDED_DEPTH, player.getLocation().add(player.getEyeLocation().getDirection()),
                800, 4, 4, 4
        );
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 8, 3);
        // 群体周围实体加成效果
        final int effectRange = 8;
        final int effectTime = 60;
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


    // 世纪之石技能效果
    // 青金石效果
    private void lapisSkill() {
        // 创造使用粒子效果
        player.getWorld().spawnParticle(
                Particle.END_ROD, player.getEyeLocation(),
                800, 3, 3, 3
        );
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 16, 1);
        // 延时发动技能
        new BukkitRunnable() {
            @Override
            public void run() {
                // 群体周围实体加成效果
                final int effectRange = 10;
                final int effectTime = 8;
//                int entityMax = 4;
//                int entityCount = 0;
                List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
                // 每个实体赋值
                for (Entity entity : nearEntity) {
                    if (entity instanceof Player) continue;
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, effectTime * 20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, effectTime * 20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, effectTime * 20, 1));
                    }
                }
            }
        }.runTaskLater(CenturyStone.centuryStone, 30);
    }


    // 世纪之石技能效果
    // 火药
    private void gunpowderSkill() {
        // 创造使用粒子效果
        player.spawnParticle(
                Particle.FLAME, player.getLocation(),
                100, 0, 0, 0, 1
        );
        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, 6, 1);
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
        }.runTaskLater(CenturyStone.centuryStone, 30);
    }


    // 世纪之石技能效果
    // 碳石效果
    private void coalSkill() {
        // 创建粒子效果
        player.getWorld().spawnParticle(
                Particle.TOTEM, player.getEyeLocation(),
                400, 4, 4, 4
        );
        // 群体周围实体加成效果
        final int effectRange = 8;
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 12, 1);
        final int effectTime = 10;
        List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
        for (Entity entity : nearEntity) {
            // 跳过对玩家的 Buff 加成
            if (entity instanceof Player) continue;
            // 对周围生命实体进行效果修正
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.getWorld().spawnParticle(
                        Particle.SPELL, livingEntity.getEyeLocation(),
                        40, 0, 0, 0, 0
                );
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectTime * 20, 1));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, effectTime * 20, 1));
            }
        }
    }


    // 世纪之石技能效果
    // 萤石
    private void glowStoneSkill() {
        // 创造使用粒子效果
        player.getWorld().spawnParticle(
                Particle.TOTEM, player.getLocation(),
                60, 2, 2, 2
        );
        player.spawnParticle(
                Particle.FLAME, player.getLocation(),
                500, 0, 0, 0, 1
        );
        // 群体玩家加成效果
        final int effectRange = 8;
        final int effectTime = 10;
        // 播放声音
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, effectRange, 1);
        // 给自己
        Tools.setPlayerPotionEffect(player, effectTime, PotionEffectType.SPEED);
        Tools.setPlayerPotionEffect(player, effectTime, PotionEffectType.INCREASE_DAMAGE);
        Tools.setPlayerPotionEffect(player, 8, PotionEffectType.REGENERATION);
        Tools.setPlayerPotionEffect(player, 3, PotionEffectType.ABSORPTION);
        // 循环给队友
        List<Entity> nearEntity = player.getNearbyEntities(effectRange, effectRange, effectRange);
        for (Entity entity : nearEntity) {
            if (entity instanceof Player) {
                Player entityPlayer = (Player) entity;
                entityPlayer.getWorld().spawnParticle(
                        Particle.SPELL, entityPlayer.getEyeLocation(),
                        40, 0, 0, 0, 1
                );
                Tools.setPlayerPotionEffect(entityPlayer, effectTime, PotionEffectType.SPEED);
                Tools.setPlayerPotionEffect(entityPlayer, effectTime, PotionEffectType.INCREASE_DAMAGE);
                Tools.setPlayerPotionEffect(entityPlayer, 7, PotionEffectType.REGENERATION);
                Tools.setPlayerPotionEffect(entityPlayer, 3, PotionEffectType.ABSORPTION);
            }
        }
    }

    // 世纪之石技能效果
    // 绿宝石效果
    private void emeraldSkill() {
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


    // 世纪之石技能效果
    // 钻石效果
    private void diamondSkill() {
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
                emeraldSkill();
            }
        }.runTaskLater(CenturyStone.centuryStone, 40);
    }


    // 世纪之石技能效果
    // 燧石效果
    private void flintSkill() {
        // 射出粒子效果
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 2);
        final World playerWorld = player.getWorld();
        int effectTime = 2;
        // 释放直线范围攻击效果
        new LineRangeAttack(player, Particle.END_ROD, 0.35, (currentLocation) -> {
            Location location = (Location) currentLocation;
            Collection<Entity> entities = playerWorld.getNearbyEntities(location, 2, 2, 2);
            for (Entity entity : entities) {
                if (entity instanceof Player) continue;
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectTime * 20, 1));
                    // 对怪物造成10%的比例伤害，但是最大伤害不可超过20血，最小不低于10
                    double currentHealth = livingEntity.getHealth();
                    double damageHealth = (int) currentHealth * 0.1;
                    if (damageHealth >= 20) damageHealth = 20;
                    if (damageHealth <= 4) damageHealth = 4;
                    livingEntity.damage(damageHealth, player);
                }
            }
        });
    }


    // 世纪之石技能效果
    // 石英水晶效果
    private void quartzSkill() {
        // 播放使用音乐
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_2, 4, 2);
        // 给予速度矢量
        player.setVelocity(player.getLocation().getDirection().multiply(3));
        player.getWorld().spawnParticle(
                Particle.END_ROD, player.getLocation(),
                40, 0, 0, 0
        );
    }


    // 玩家血量设置方法
    private void addPlayerHealth(double h) {
        final double ADD_HEALTH = h;
        final double MAX_H = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (player.getHealth() + ADD_HEALTH >= MAX_H) {
            player.setHealth(MAX_H);
        } else {
            player.setHealth(player.getHealth() + ADD_HEALTH);
        }
    }

}
