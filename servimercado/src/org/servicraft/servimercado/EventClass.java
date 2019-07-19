package org.servicraft.servimercado;

import java.io.File;
import java.io.IOException;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class EventClass extends JavaPlugin {
    
    public static EventClass plugin3;
    public static Economy econ = null;
    
    public FileConfiguration orgcfg;
    public File orgfile;
    
    @Override
    public void onEnable() {
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Desactivado debido a que no se ha encontrado Vault!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        PluginManager pm = getServer().getPluginManager();
        plugin3 = this;
        
        this.getCommand("mercado").setExecutor(new Commands());
        
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        
        orgfile = new File(this.getDataFolder(), "organizations.yml");
        
        if (!orgfile.exists()) {
            try {
                orgfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No se pudo crear organizations.yml");
            }
        }
        
        orgcfg = YamlConfiguration.loadConfiguration(orgfile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha creado exitósamente organizations.yml");
        
    }
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    @Override
    public void onDisable() {
        
        try {
        orgcfg.save(orgfile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
        }
        
    }
    
}
