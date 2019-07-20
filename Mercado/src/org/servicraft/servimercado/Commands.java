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
                    
                    if(args.length < 3){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        
                    } else if(args.length == 3) {
                        
                        
                        if (args[1].equalsIgnoreCase("sociedad")) {
                            
                            if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                        if(!EventClass.plugin3.orgcfg.contains("organizaciones." + args[2])) {
                            
                            int precio = EventClass.plugin3.getConfig().getInt("precio");
                            EconomyResponse r = economy.withdrawPlayer(player, precio);
                            
                            if(r.transactionSuccess()) {
                                
                                player.sendMessage(ChatColor.GREEN + "Se han removido $" + precio + " de tu cuenta.");
                                player.sendMessage(ChatColor.AQUA + "Has creado la organización " + ChatColor.GOLD + args[2]);
                                
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[2] + ".head", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[2] + ".players", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[2] + ".es", "sociedad");
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".sociedad", args[2]);
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
                                player.sendMessage(ChatColor.RED + "Ya perteneces a una sociedad!");
                                return true;
                            }
                        
                        } else if (args[1].equalsIgnoreCase("gremio")) {
                            
                            if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".gremio")) {
                        if(!EventClass.plugin3.orgcfg.contains("organizaciones." + args[2])) {
                            
                            int precio = EventClass.plugin3.getConfig().getInt("precio");
                            EconomyResponse r = economy.withdrawPlayer(player, precio);
                            
                            if(r.transactionSuccess()) {
                                
                                player.sendMessage(ChatColor.GREEN + "Se han removido $" + precio + " de tu cuenta.");
                                player.sendMessage(ChatColor.AQUA + "Has creado la organización " + ChatColor.GOLD + args[2]);
                                
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[2] + ".head", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[2] + ".players", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[2] + ".es", "gremio");
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".gremio", args[2]);
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
                                player.sendMessage(ChatColor.RED + "Ya perteneces a un gremio!");
                                return true;
                            }
                        
                        } else {
                            player.sendMessage(ChatColor.RED + "Organización inválida!");
                            return true;
                        }
                        
                        
                        
                        
                    
                    } else {
                        
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        
                    }
                    
                    return true;
                    
                } else if(args[0].equalsIgnoreCase("invitar")) {
                    
                    if(args.length < 3){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        
                    } else if(args.length == 3) {
                        
                        if (args[1].equalsIgnoreCase("sociedad")) {
                            
                            
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                                
                                if (player.getServer().getPlayer(args[2]) != null) {
                                    if (!args[2].equals(player.getName())) {
                                        
                                        if(!EventClass.plugin3.orgcfg.contains("jugadores." + args[2] + ".sociedad")) {
                                            
                                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + args[2] + ".InvSoc");
                                            String soc = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".sociedad");
                                            cfg.add(soc);
                                            EventClass.plugin3.getConfig().set("List", cfg);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            return true;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Este jugador ya pertenece a una sociedad!");
                                            return true;
                                        }
                                        
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No puedes invitarte a tí mismo!");
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "El jugador no está conectado!");
                                    return true;
                                }
                                
                                
                            } else {
                               player.sendMessage(ChatColor.RED + "No perteneces a una sociedad!");
                               return true;
                            }
                            
                            
                            
                            
                        } else if (args[1].equalsIgnoreCase("gremio")) {
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".gremio")) {
                                
                                if (player.getServer().getPlayer(args[2]) != null) {
                                    if (!args[2].equals(player.getName())) {
                                        
                                        if(!EventClass.plugin3.orgcfg.contains("jugadores." + args[2] + ".gremio")) {
                                            
                                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + args[2] + ".InvGre");
                                            String gre = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".gremio");
                                            cfg.add(gre);
                                            EventClass.plugin3.getConfig().set("List", cfg);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a " + args[2]);
                                            return true;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Este jugador ya pertenece a un gremio!");
                                            return true;
                                        }
                                        
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No puedes invitarte a tí mismo!");
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "El jugador no está conectado!");
                                    return true;
                                }
                                
                                
                            } else {
                               player.sendMessage(ChatColor.RED + "No perteneces a un gremio!");
                               return true;
                            }
                            
                            
                        } else {
                            player.sendMessage(ChatColor.RED + "Organización inválida!");
                            return true;
                        }
                        
                    } else {
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                    }
                    
                } else {
                    
                    player.sendMessage(ChatColor.RED + "Subcomando inválido!");
                    return true;
                    
                }
            
        } else {
                
                sender.sendMessage(ChatColor.RED + "Solo jugadores pueden usar este comando.");
                return true;
                
        }
            
        }
        
        
        if(command.getName().equalsIgnoreCase("sociedad")) {
            
            if(sender instanceof Player) {
                
                Player player = (Player) sender;
                
                if(args[0].equalsIgnoreCase("nuevo")) {
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        
                    } else if(args.length == 2) {
                            
                            if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                        if(!EventClass.plugin3.orgcfg.contains("organizaciones." + args[1])) {
                            
                            int precio = EventClass.plugin3.getConfig().getInt("precio");
                            EconomyResponse r = economy.withdrawPlayer(player, precio);
                            
                            if(r.transactionSuccess()) {
                                
                                player.sendMessage(ChatColor.GREEN + "Se han removido $" + precio + " de tu cuenta.");
                                player.sendMessage(ChatColor.AQUA + "Has creado la organización " + ChatColor.GOLD + args[1]);
                                
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".head", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".players", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".es", "sociedad");
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".sociedad", args[2]);
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
                                player.sendMessage(ChatColor.RED + "Ya perteneces a una sociedad!");
                                return true;
                            }
                        
                    
                    } else {
                        
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        
                    }
                    
                    return true;
                    
                }
                
                if(args[0].equalsIgnoreCase("invitar")) {
                    
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        
                    } else if(args.length == 2) {
                            
                            
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                                
                                if (player.getServer().getPlayer(args[1]) != null) {
                                    if (!args[2].equals(player.getName())) {
                                        
                                        if(!EventClass.plugin3.orgcfg.contains("jugadores." + args[1] + ".sociedad")) {
                                            
                                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + args[1] + ".InvSoc");
                                            String soc = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".sociedad");
                                            cfg.add(soc);
                                            EventClass.plugin3.getConfig().set("List", cfg);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a " + args[1]);
                                            return true;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Este jugador ya pertenece a una sociedad!");
                                            return true;
                                        }
                                        
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No puedes invitarte a tí mismo!");
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "El jugador no está conectado!");
                                    return true;
                                }
                                
                                
                            } else {
                               player.sendMessage(ChatColor.RED + "No perteneces a una sociedad!");
                               return true;
                            }
                    } else {
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                    }
                    
                }
                
                
            } else {
                sender.sendMessage(ChatColor.RED + "Solo jugadores pueden usar este comando.");
                return true;
            }
            
        }
        
        
        if(command.getName().equalsIgnoreCase("gremio")) {
            
            if(sender instanceof Player) {
                
                Player player = (Player) sender;
                
                if(args[0].equalsIgnoreCase("nuevo")) {
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        
                    } else if(args.length == 2) {
                            
                            if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".gremio")) {
                        if(!EventClass.plugin3.orgcfg.contains("organizaciones." + args[1])) {
                            
                            int precio = EventClass.plugin3.getConfig().getInt("precio");
                            EconomyResponse r = economy.withdrawPlayer(player, precio);
                            
                            if(r.transactionSuccess()) {
                                
                                player.sendMessage(ChatColor.GREEN + "Se han removido $" + precio + " de tu cuenta.");
                                player.sendMessage(ChatColor.AQUA + "Has creado la organización " + ChatColor.GOLD + args[1]);
                                
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".head", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".players", player.getName());
                                EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".es", "gremio");
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".gremio", args[2]);
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
                                player.sendMessage(ChatColor.RED + "Ya perteneces a un gremio!");
                                return true;
                            }
                        
                    
                    } else {
                        
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        
                    }
                    
                    return true;
                    
                }
                
                if(args[0].equalsIgnoreCase("invitar")) {
                    
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        
                    } else if(args.length == 2) {
                            
                            
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".gremio")) {
                                
                                if (player.getServer().getPlayer(args[1]) != null) {
                                    if (!args[2].equals(player.getName())) {
                                        
                                        if(!EventClass.plugin3.orgcfg.contains("jugadores." + args[1] + ".gremio")) {
                                            
                                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + args[1] + ".InvGre");
                                            String gre = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".gremio");
                                            cfg.add(gre);
                                            EventClass.plugin3.getConfig().set("List", cfg);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a " + args[1]);
                                            return true;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Este jugador ya pertenece a un gremio!");
                                            return true;
                                        }
                                        
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No puedes invitarte a tí mismo!");
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "El jugador no está conectado!");
                                    return true;
                                }
                                
                                
                            } else {
                               player.sendMessage(ChatColor.RED + "No perteneces a un gremio!");
                               return true;
                            }
                    } else {
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                    }
                    
                }
                
                
            } else {
                sender.sendMessage(ChatColor.RED + "Solo jugadores pueden usar este comando.");
                return true;
            }
            
        }
        
        return true;
        
        
        
    }
    
}
