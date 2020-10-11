package top.suwings.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import top.suwings.skill.StoneSkill;

public class Events implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final ItemStack current = event.getItem();
            if (current != null) {
                new StoneSkill().onStoneSkill(event.getPlayer(), current);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String uuid = player.getUniqueId().toString();
            // 如果需要玩家无敌，则让其无敌
            if (Tools.godPlayerMap.get(uuid) != null) {
                event.setCancelled(true);
            }
        }
    }


//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
//        Tools.playerLevelHealth(event.getPlayer());
//    }
//
//
//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onPlayerRespawn(PlayerRespawnEvent event) {
//        Tools.playerLevelHealth(event.getPlayer());
//    }
//
//

//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onPlayerJoin(PlayerJoinEvent event) {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Player player = event.getPlayer();
//                double BASE_HEALTH = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
//                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(BASE_HEALTH + 20);
//            }
//        }.runTaskLater(CenturyStone.centuryStone, 60);
//    }


}
