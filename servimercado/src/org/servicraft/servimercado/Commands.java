package org.servicraft.servimercado;

import java.io.IOException;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class Commands implements CommandExecutor {
    
    private Economy economy = EventClass.econ;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(command.getName().equalsIgnoreCase("mercado")) {
            
            if(sender instanceof Player) {
                
                Player player = (Player) sender;
                
                if(args.length == 0) {
                    
                    List<String> pr = EventClass.plugin3.getConfig().getStringList("comando-help");
                    
                    for (String se: pr) {
                        player.sendMessage(se);
                    }
                    
                    return true;
                } else if(args[0].equalsIgnoreCase("nuevo")) {
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        
                    } else if(args.length == 2) {
                        
                        if(!EventClass.plugin3.orgcfg.contains("organizaciones." + args[1])) {
                            
                            int precio = EventClass.plugin3.getConfig().getInt("precio");
                            EconomyResponse r = economy.withdrawPlayer(player, precio);
                            
                            if(r.transactionSuccess()) {
                                
                                player.sendMessage(ChatColor.GREEN + "Se han removido $" + precio + " de tu cuenta.");
                                player.sendMessage(ChatColor.AQUA + "Has creado la organización " + ChatColor.GOLD + args[1]);
                                
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".head", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".players", player.getName());
                                try {
                                    EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                } catch (IOException e) {
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                }
                                
                            } else {
                                
                                player.sendMessage(ChatColor.RED + "No tienes dinero suficiente!");
                                
                            }
                            
                            return true;
                            
                    } else {
                            
                            player.sendMessage(ChatColor.RED + "Este nombre ya está en uso!");
                            return true;
                            
                        }
                    
                    } else {
                        
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        
                    }
                    
                    return true;
                    
                } else {
                    
                    player.sendMessage(ChatColor.RED + "Subcomando inválido!");
                    return true;
                    
                }
            
        } else {
                
                sender.sendMessage(ChatColor.RED + "Solo jugadores pueden usar este comando.");
                return true;
                
        }
            
        }
        
        return true;
    }
    
}
