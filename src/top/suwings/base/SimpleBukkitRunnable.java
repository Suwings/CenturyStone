package top.suwings.base;

import org.bukkit.scheduler.BukkitRunnable;

public class SimpleBukkitRunnable extends BukkitRunnable {

    private MineCallback mineCallback;

    public SimpleBukkitRunnable(MineCallback mineCallback) {
        this.mineCallback = mineCallback;
    }

    @Override
    public void run() {
        this.mineCallback.run(this);
    }

}
