package top.suwings.power;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.suwings.skill.SkillCoolDown;
import top.suwings.skill.StoneSpend;
import top.suwings.skill.StoneWearManager;

public abstract class Power {

    public void setCoolDown(Player player, ItemStack itemStack){
        SkillCoolDown.setSkillCoolDown(player, itemStack.getType(), this.getCoolDownTick());
        StoneWearManager.autoStoneBroken(player, itemStack.getType(), this.getDurableValue(), itemStack, this.getUseSpendValue());
    }

    public abstract int getCoolDownTick();

    public abstract int getDurableValue();

    public abstract int getUseSpendValue();
}
