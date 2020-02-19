package top.suwings.power;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.suwings.skill.SkillCoolDown;
import top.suwings.skill.StoneSpend;
import top.suwings.skill.StoneWearManager;

import java.util.HashMap;

public abstract class Power {

    protected final int TICK = 20;

    public Power releasePower(Player player, ItemStack itemStack) {
        if (itemStack.getAmount() >= this.getUseSpendValue()) {
            this.doCoolDownAndIsBroken(player,itemStack);
            this.release(player, null);
        }
        return this;
    }

    public Power doCoolDownAndIsBroken(Player player, ItemStack itemStack) {
        SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), this.getCoolDownTick());
        StoneWearManager.autoStoneBroken(player, itemStack.getType(), this.getDurableValue(), itemStack, this.getUseSpendValue());
        return this;
    }

    public abstract void release(Player player, HashMap<Object, Object> config);

    public abstract int getCoolDownTick();

    public abstract int getDurableValue();

    public abstract int getUseSpendValue();


}



