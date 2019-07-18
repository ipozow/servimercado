package me.sc.mercado;

import org.bukkit.plugin.java.JavaPlugin;

public class EventClass extends JavaPlugin {
    
    @Override
    public void onEnable() {
        
        this.getCommand("mercado").setExecutor(new Commands());
        
    }
    
    @Override
    public void onDisable() {
        
    }
    
}
