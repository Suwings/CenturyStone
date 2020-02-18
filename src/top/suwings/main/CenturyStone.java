package top.suwings.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CenturyStone extends JavaPlugin implements Listener {

    public static JavaPlugin centuryStone = null;

    public void onEnable(){
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        getLogger().info("CenturyStone enable!");
        centuryStone = this;
    }

    public void onDisable(){
        getLogger().info("CenturyStone disable!");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("Hi"))//command获得的指令为Hi时输出“你们好”。
        {
            sender.sendMessage("Hi~");
            return true;//返回true告诉服务器执行指令
        }
        return false;
    }
}