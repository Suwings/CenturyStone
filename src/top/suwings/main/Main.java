package top.suwings.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public static JavaPlugin self = null;
    public static double AttackMultiple = 1.0d;

    public void onEnable(){
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        getLogger().info("CenturyStone enable!");
        self = this;
    }

    public void onDisable(){
        getLogger().info("CenturyStone disable!");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("centurystone"))//command获得的指令为Hi时输出“你们好”。
        {
            sender.sendMessage("暂无使用说明");
            return true;//返回true告诉服务器执行指令
        }
        return false;
    }
}