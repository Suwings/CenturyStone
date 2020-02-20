package top.suwings.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import top.suwings.power.ToPlayer;

import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {

    public static JavaPlugin self = null;
    public static double AttackMultiple = 1.0d;

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        getLogger().info("CenturyStone enable!");
        self = this;
    }

    public void onDisable() {
        getLogger().info("CenturyStone disable!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpz"))//command获得的指令为Hi时输出“你们好”。
        {
            if (sender instanceof Player) {
                if (args.length >= 1) {
                    final int USE_ONCE_SPEND = 2;
                    Player player = (Player) sender;
                    // 判断玩家手上是否拿着传送水晶
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType() == Material.EMERALD && itemStack.getAmount() >= USE_ONCE_SPEND) {
                        HashMap config = new HashMap<>();
                        Player toPlayer = Bukkit.getServer().getPlayer(args[0]);
                        if (toPlayer == null) {
                            sender.sendMessage("传送失败：此玩家并不存在.");
                            return true;
                        }
                        // 扣除并开始使用
                        itemStack.setAmount(itemStack.getAmount() - USE_ONCE_SPEND);
                        config.put("tp", toPlayer);
                        new ToPlayer().release(player, config);
                    }
                    sender.sendMessage("传送失败：传送水晶条件不足，需要手持3个绿宝石.");
                    return true;
                }
            }
            getLogger().info("Command sender must a player.");
        }
        return false;
    }
}