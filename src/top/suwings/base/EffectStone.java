package top.suwings.base;

import org.bukkit.Material;

public class EffectStone {

    private int usedCount;
    private Material material = null;

    public EffectStone(Material material) {
        this.material = material;
    }

    public EffectStone(Material material, int usedCount) {
        this.material = material;
        this.usedCount = usedCount;
    }


    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }


    public void plusUsedCount() {
        this.usedCount += 1;
    }


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

}
