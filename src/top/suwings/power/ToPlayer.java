package top.suwings.power;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import top.suwings.base.SimpleBukkitRunnable;
import top.suwings.main.Main;

import java.util.HashMap;

public class ToPlayer extends Power {
    @Override
    public void release(Player player, HashMap<Object, Object> config) {
        Player toPlayer = (Player) config.get("tp");
        player.sendMessage("§a[传送水晶] §3正在准备传送....");
        player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation(), 800, 1, 1, 1);
        new SimpleBukkitRunnable((o) -> {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 4, 1);
            player.teleport(toPlayer);
            player.sendMessage("§a[传送水晶] §3传送完成，");
        }).runTaskLater(Main.self, 3 * 20);
    }

    @Override
    public int getCoolDownTick() {
        return 60 * 20;
    }

    @Override
    public int getDurableValue() {
        return 1;
    }

    @Override
    public int getUseSpendValue() {
        return 2;
    }
}
