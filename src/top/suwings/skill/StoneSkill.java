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
import top.suwings.power.AreaInvincible;
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
        if (itemStack.getType() == Material.EMERALD) {

        }
        // 钻石
        if (itemStack.getType() == Material.DIAMOND) {

        }
        // 燧石
        if (itemStack.getType() == Material.FLINT) {

        }
        // 青金石
        if (itemStack.getType() == Material.LAPIS_LAZULI) {

        }
        // 碳
        if (itemStack.getType() == Material.COAL) {

        }
        // 萤石
        if (itemStack.getType() == Material.GLOWSTONE_DUST) {

        }
        // 火药
        if (itemStack.getType() == Material.GUNPOWDER) {

        }
        // 下届石英水晶
        if (itemStack.getType() == Material.QUARTZ) {

        }
        // 海晶沙粒
        if (itemStack.getType() == Material.PRISMARINE_CRYSTALS) {

        }
        // 海晶碎片
        if (itemStack.getType() == Material.PRISMARINE_SHARD) {

        }
        // 金锭
        if (itemStack.getType() == Material.GOLD_INGOT) {

        }
        // 铁锭
        if (itemStack.getType() == Material.IRON_INGOT) {

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
    }


}
