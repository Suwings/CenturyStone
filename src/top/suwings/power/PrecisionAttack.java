package top.suwings.power;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PrecisionAttack extends Power {

    @Override
    public void release(Player player, HashMap<Object, Object> config) {

    }

    @Override
    public int getCoolDownTick() {
        return 0;
    }

    @Override
    public int getDurableValue() {
        return 0;
    }

    @Override
    public int getUseSpendValue() {
        return 0;
    }
}
