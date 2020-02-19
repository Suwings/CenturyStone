package top.suwings.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import top.suwings.main.Main;

import java.util.HashMap;
import java.util.LinkedList;

public class SkillCoolDown {

    private static HashMap<String, LinkedList<Material>> stoneCoolDownMap = new HashMap<>();

    public static void setSkillCoolDown(Player player, Material material, int cdTime) {
        final String UUID = player.getUniqueId().toString();
        if (cdTime > 0) {
            LinkedList<Material> linkedList = stoneCoolDownMap.get(UUID);
            if (linkedList == null) {
                LinkedList<Material> materialList = new LinkedList<>();
                materialList.add(material);
                stoneCoolDownMap.put(UUID, materialList);
            } else {
                linkedList.add(material);
            }
        }
        // 冷却完毕
        new BukkitRunnable(){

            @Override
            public void run() {
                LinkedList<Material> linkedList = stoneCoolDownMap.get(UUID);
                if (linkedList != null) {
                    linkedList.remove(material);
                }
            }
        }.runTaskLater(Main.self,cdTime);
    }

    public static boolean isSkillCooling(Player player, Material material) {
        final String UUID = player.getUniqueId().toString();
        LinkedList<Material> linkedList = stoneCoolDownMap.get(UUID);
        if (linkedList != null) {
            for (Material m : linkedList) {
                if (m == material) {
                    return true;
                }
            }
        }
        return false;
    }
}
