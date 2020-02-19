package top.suwings.skill;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.suwings.book.CenturyBooks;
import top.suwings.power.*;

import java.util.*;


public class StoneSkill {
    // 所有石头与附魔书的效果触发事件
    public void onStoneSkill(final Player player, final ItemStack itemStack) {
        // 技能冷却检测
        if (SkillCoolDown.isSkillCooling(player, itemStack.getType())) {
            // 玩家技能冷却粒子提示ITEM ARMOR EQUIP GENERIC
            // Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title '" + player.getName() + "' actionbar \"无法使用,正在冷却中..\"");
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);
            player.spawnParticle(Particle.CLOUD, player.getLocation().add(player.getEyeLocation().getDirection()), 20, 1, 1, 1, 1);
            return;
        }
        // 绿宝石
        if (itemStack.getType() == Material.EMERALD) {
            new Emerald().releasePower(player, itemStack);
        }
        // 钻石
        if (itemStack.getType() == Material.DIAMOND) {
            new Diamond().releasePower(player, itemStack);
        }
        // 燧石
        if (itemStack.getType() == Material.FLINT) {
            new LineRangeAttack().releasePower(player, itemStack);
        }
        // 青金石
        if (itemStack.getType() == Material.LAPIS_LAZULI) {
            new LapisLazult().releasePower(player, itemStack);
        }
        // 碳
        if (itemStack.getType() == Material.COAL) {
            new CoalS().releasePower(player, itemStack);
        }
        // 萤石
        if (itemStack.getType() == Material.GLOWSTONE_DUST) {
            new GlowStone().releasePower(player, itemStack);
        }
        // 火药
        if (itemStack.getType() == Material.GUNPOWDER) {
            new Gunpowder().releasePower(player, itemStack);
        }
        // 下届石英水晶
        if (itemStack.getType() == Material.QUARTZ) {
            new Quartz().releasePower(player, itemStack);
        }
        // 海晶沙粒
        if (itemStack.getType() == Material.PRISMARINE_CRYSTALS) {
            new DamageAbsorption().releasePower(player, itemStack);
        }
        // 海晶碎片
        if (itemStack.getType() == Material.PRISMARINE_SHARD) {
            new SeaCrystalAbyss().releasePower(player, itemStack);
        }
        // 金锭
        if (itemStack.getType() == Material.GOLD_INGOT) {
            new AreaInvincible().releasePower(player, itemStack);
        }
        // 铁锭
        if (itemStack.getType() == Material.IRON_INGOT) {
            new PrecisionAttack().releasePower(player, itemStack);
        }

        // 附魔书 - 全新的方案
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

    }
}
