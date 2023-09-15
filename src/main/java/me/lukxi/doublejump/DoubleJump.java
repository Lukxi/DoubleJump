package me.lukxi.doublejump;


import me.lukxi.doublejump.listeners.JumpListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleJump extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new JumpListener(), this);
    }
}
