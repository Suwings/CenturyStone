package top.suwings.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.suwings.base.EffectStone;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// 宝石消耗记录
public class StoneWearManager {

    private static HashMap<Player, List<EffectStone>> stoneDegreeWearMap = new HashMap<>();

    public static void initStoneCount(Player player, Material material) {
        List<EffectStone> effectStoneList = stoneDegreeWearMap.get(player);
        if (effectStoneList != null) {
            for (EffectStone effectStone : effectStoneList) {
                if (effectStone.getMaterial() == material) {
                    effectStone.setUsedCount(0);
                }
            }
        }
    }

    public static void releaseStone(Player player, Material material) {
        if (stoneDegreeWearMap.get(player) == null) {
            List<EffectStone> effectStoneList = new LinkedList<>();
            effectStoneList.add(new EffectStone(material, 1));
            stoneDegreeWearMap.put(player, effectStoneList);
        } else {
            List<EffectStone> effectStoneList = stoneDegreeWearMap.get(player);
            boolean isFind = false;
            for (EffectStone effectStone : effectStoneList) {
                if (effectStone.getMaterial() == material) {
                    isFind = true;
                    effectStone.plusUsedCount();
                    break;
                }
            }
            if (!isFind) {
                effectStoneList.add(new EffectStone(material, 1));
            }
        }
    }

    public static int getStoneUsedCount(Player player, Material material, int defaultValue) {
        List<EffectStone> effectStoneList = stoneDegreeWearMap.get(player);
        if (effectStoneList != null) {
            for (EffectStone effectStone : effectStoneList) {
                if (effectStone.getMaterial() == material) {
                    return effectStone.getUsedCount();
                }
            }
        }
        return defaultValue;
    }

    public static boolean isStoneBroken(Player player, Material material, int durable) {
        int used = StoneWearManager.getStoneUsedCount(player, material, 0);
        // 因为是先判断再释放技能，所以使用次数会变成0开始
        return used >= (durable - 1);
    }


    public static void autoStoneBroken(Player player, Material material, int durable, ItemStack itemStack, int stoneSpendVlaue) {
        boolean result = isStoneBroken(player, material, durable);
        if (result) {
            itemStack.setAmount(itemStack.getAmount() - stoneSpendVlaue);
            initStoneCount(player, material);
        } else {
            releaseStone(player, material);
        }
    }


}
