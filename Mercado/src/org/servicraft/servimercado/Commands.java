package org.servicraft.servimercado;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Commands implements CommandExecutor {
    
    private Economy economy = EventClass.econ;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(command.getName().equalsIgnoreCase("mercado")) {
            
            if(sender instanceof Player) {
                
                Player player = (Player) sender;
                
                List<String> pr = EventClass.plugin3.getConfig().getStringList("comando-help");
                
                for (String se: pr) {
                    player.sendMessage(se);
                }
                
                return true;
            
            } else {
                
                sender.sendMessage(ChatColor.RED + "Solo jugadores pueden usar este comando.");
                return true;
                
            }
            
        }
        
        
        if(command.getName().equalsIgnoreCase("sociedad")) {
            
            if(sender instanceof Player) {
                
                Player player = (Player) sender;
                
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                    return true;
                } else if(args[0].equalsIgnoreCase("nuevo")) {
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        return true;
                        
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
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".sociedad", args[1]);
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
                        return true;
                        
                    }
                    
                } else if(args[0].equalsIgnoreCase("invitar")) {
                    
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        return true;
                        
                    } else if(args.length == 2) {
                            
                            
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                                Player target = player.getServer().getPlayer(args[1]);
                                if (target == null) {
                                    player.sendMessage(ChatColor.RED + "El jugador no está conectado!");
                                    return true;
                                } else {
                                    if (!target.getName().equals(player.getName())) {
                                    if(!EventClass.plugin3.orgcfg.contains("jugadores." + target.getName() + ".sociedad")) {
                                        
                                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + target.getName())) {
                                            
                                            String orr = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".sociedad");
                                            
                                            List<String> list = new ArrayList<>();
                                            list.add(orr);
                                            EventClass.plugin3.orgcfg.set("jugadores." + target.getName() + "InvSoc", list);
                                            
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a tu sociedad a " + ChatColor.GOLD + target.getName());
                                            target.sendMessage(ChatColor.AQUA + "Has sido invitado a la sociedad " + ChatColor.GOLD + orr);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            return true;
                                            
                                        } else {
                                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + target.getName() + ".InvSoc")) {
                                            
                                            String orr = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".sociedad");
                                            List<String> list = new ArrayList<>();
                                            list.add(orr);
                                            EventClass.plugin3.orgcfg.set("jugadores." + target.getName() + "InvSoc", list);
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a tu sociedad a " + ChatColor.GOLD + target.getName());
                                            target.sendMessage(ChatColor.AQUA + "Has sido invitado a la sociedad " + ChatColor.GOLD + orr);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            return true;
                                            
                                            
                                        } else {
                                            
                                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + target.getName() + ".InvSoc");
                                            int value = 0;
                                            String sss = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".sociedad");
                                            for (String each : cfg) {
                                                if (each.equalsIgnoreCase(sss)) {
                                                    value = 1;
                                                }
                                            }
                                            if (value == 0) {
                                            String soc = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".sociedad");
                                            cfg.add(soc);
                                            EventClass.plugin3.getConfig().set("List", cfg);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a tu sociedad a " + ChatColor.GOLD + target.getName());
                                            target.sendMessage(ChatColor.AQUA + "Has sido invitado a la sociedad " + ChatColor.GOLD + sss);
                                            return true;
                                            } else {
                                                player.sendMessage(ChatColor.RED + "Este jugador ya tiene una invitación para tu sociedad!");
                                                return true;
                                            }
                                        }
                                        
                                    }
                                            
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Este jugador ya pertenece a una sociedad!");
                                            return true;
                                        }
                                    
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No puedes invitarte a ti mismo!");
                                        return true;
                                    }
                                }
                                
                                
                            } else {
                               player.sendMessage(ChatColor.RED + "No perteneces a una sociedad!");
                               return true;
                            }
                    } else {
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        return true;
                    }
                    
                } else if(args[0].equalsIgnoreCase("aceptar")) {
                    
                    if (args.length < 2) {
                        
                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".InvSoc")) {
                            
                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + player.getName() + ".InvSoc");
                            int value = 0;
                            String org = "";
                            for (String each : cfg) {
                                if (value == 0) {
                                    value = 1;
                                    org = each;
                                }
                            }
                            if (value == 0) {
                                player.sendMessage(ChatColor.RED + "No tienes invitaciones de ningúna sociedad!");
                                return true; 
                            } else {
                                
                                EventClass.plugin3.orgcfg.set("organizaciones." + org + ".players", player.getName());
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".sociedad", org);
                                try {
                                    EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                } catch (IOException e) {
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                }
                                
                                return true;
                                
                            }
                            
                        } else {
                                player.sendMessage(ChatColor.RED + "No tienes invitaciones de ningúna sociedad!");
                                return true;
                            }
                            
                        } else {
                           player.sendMessage(ChatColor.RED + "Ya perteneces a una sociedad!");
                           return true; 
                        }
                        
                    } else if (args.length == 2) {
                        
                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                            
                            if (EventClass.plugin3.orgcfg.contains("organizaciones." + args[1])) {
                                
                                if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".InvSoc")) {
                                
                                List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + player.getName() + ".InvSoc");
                                int value = 0;
                                for (String each : cfg) {
                                    if (each.equalsIgnoreCase(args[1])) {
                                        value = 1;
                                        return true;
                                    }
                                }
                                
                                if (value == 0) {
                                    player.sendMessage(ChatColor.RED + "No tienes ningúna invitación de esta sociedad!");
                                    return true;
                                } else {
                                    
                                    EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".players", player.getName());
                                    EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".sociedad", args[1]);
                                    try {
                                        EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                    } catch (IOException e) {
                                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                    }
                                    
                                    return true;
                                }
                                
                            } else {
                                    player.sendMessage(ChatColor.RED + "No tienes invitaciones de ningúna sociedad!");
                                    return true;
                                }
                                
                            } else {
                                
                                player.sendMessage(ChatColor.RED + "No existe está organización!");
                                return true;
                            }
                            
                        } else {
                            player.sendMessage(ChatColor.RED + "Ya perteneces a una sociedad!");
                           return true; 
                        }
                        
                    } else {
                        
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        return true;
                        
                    }
                
                } else if(args[0].equalsIgnoreCase("proyecto")) {
                    
                    if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".sociedad")) {
                        
                        if (args.length < 4) {
                            player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                            return true;
                        } else if (args.length == 4) {
                            
                            String rrr = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".sociedad");
                            
                            if (!EventClass.plugin3.orgcfg.contains("organizaciones." + rrr + ".proyectos")) {
                                
                                if (NumberUtils.isNumber(args[2])) {
                                    
                                    if (NumberUtils.isNumber(args[3])) {
                                        boolean tr = false;
                                        if (player.hasPermission("servimercado.proyectos.10")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.9")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.8")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.7")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.6")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.5")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.4")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.3")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.2")) {
                                            tr = true;
                                        } else if (player.hasPermission("servimercado.proyectos.1")) {
                                            tr = true;
                                        }
                                        
                                        if (tr == true) {
                                        EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                        EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                        EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                        try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                        } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                        }
                                        } else {
                                            player.sendMessage(ChatColor.RED + "No tienes permitido crear proyectos!");
                                            return true;
                                        }
                                        
                                    } else {
                                        player.sendMessage(ChatColor.RED + "El tiempo deber ser un número!");
                                        return true;
                                    }
                                    
                                } else {
                                    player.sendMessage(ChatColor.RED + "El precio debe ser un número!");
                                    return true;
                                }
                                
                            } else {
                                
                                if (NumberUtils.isNumber(args[2])) {
                                    
                                    if (NumberUtils.isNumber(args[3])) {
                                        
                                        if (!EventClass.plugin3.orgcfg.contains("organizaciones." + rrr + ".proyectos." + args[1])) {
                                            
                                            ConfigurationSection sdc = EventClass.plugin3.orgcfg.getConfigurationSection("organizaciones." + rrr + ".proyectos");
                                            int Num = 0;
                                            for (String dt : sdc.getKeys(false)) {
                                                
                                                String name = (String) sdc.get(dt + ".by");
                                                
                                                if (name.equalsIgnoreCase(player.getName())) {
                                                    Num = Num + 1;
                                                }
                                                
                                            }
                                            
                                            if (player.hasPermission("servimercado.proyectos.10")) {
                                                
                                                if (Num < 10) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                    
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.9")) {
                                                
                                                if (Num < 9) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.8")) {
                                                
                                                if (Num < 8) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.7")) {
                                                
                                                if (Num < 7) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.6")) {
                                                
                                                if (Num < 6) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.5")) {
                                                
                                                if (Num < 5) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.4")) {
                                                
                                                if (Num < 4) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.3")) {
                                                
                                                if (Num < 3) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.2")) {
                                                
                                                if (Num < 2) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else if (player.hasPermission("servimercado.proyectos.1")) {
                                                
                                                if (Num < 1) {
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".by", player.getName());
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".precio", args[2]);
                                                    EventClass.plugin3.orgcfg.set("organizaciones." + rrr + ".proyectos." + args[1] + ".plazo", args[3]);
                                                } else {
                                                   player.sendMessage(ChatColor.RED + "Ya has alcanzado el limite de proyectos permitidos!");
                                                   return true; 
                                                }
                                                
                                            } else {
                                                player.sendMessage(ChatColor.RED + "No tienes permitido crear proyectos!");
                                                return true;
                                            }
                                            
                                            
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Este nombre de proyecto ya existe!");
                                            return true;
                                        }
                                        
                                    } else {
                                        player.sendMessage(ChatColor.RED + "El tiempo deber ser un número!");
                                        return true;
                                    }
                                    
                                } else {
                                    player.sendMessage(ChatColor.RED + "El precio debe ser un número!");
                                    return true;
                                }
                                
                            }
                            
                    } else {
                          player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                          return true;
                        }
                        
                    } else {
                        player.sendMessage(ChatColor.RED + "No perteneces a una sociedad!");
                        return true;
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
        
        
        if(command.getName().equalsIgnoreCase("gremio")) {
            
            if(sender instanceof Player) {
                
                Player player = (Player) sender;
                
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                    return true;
                } else if(args[0].equalsIgnoreCase("nuevo")) {
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        return true;
                        
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
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".gremio", args[1]);
                                try {
                                    EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                } catch (IOException e) {
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                }
                                
                            } else {
                                
                                player.sendMessage(ChatColor.RED + "No tienes dinero suficiente!");
                                return true;
                                
                            }
                            
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
                        return true;
                        
                    }
                    
                } else if(args[0].equalsIgnoreCase("invitar")) {
                    
                    
                    if(args.length < 2){
                        
                        player.sendMessage(ChatColor.RED + "Faltan argumentos!");
                        return true;
                        
                    } else if(args.length == 2) {
                            
                            
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".gremio")) {
                                Player target = player.getServer().getPlayer(args[1]);
                                if (target == null) {
                                    player.sendMessage(ChatColor.RED + "El jugador no está conectado!");
                                    return true;
                                } else {
                                    if (!target.getName().equals(player.getName())) {
                                    if(!EventClass.plugin3.orgcfg.contains("jugadores." + target.getName() + ".gremio")) {
                                        
                                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + target.getName())) {
                                            
                                            String orr = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".gremio");
                                            List<String> list = new ArrayList<>();
                                            list.add(orr);
                                            EventClass.plugin3.orgcfg.set("jugadores." + target.getName() + "InvGre", list);
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a tu gremio a " + ChatColor.GOLD + target.getName());
                                            target.sendMessage(ChatColor.AQUA + "Has sido invitado a el gremio " + ChatColor.GOLD + orr);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            return true;
                                            
                                        } else {
                                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + target.getName() + ".InvGre")) {
                                            
                                            String orr = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".gremio");
                                            List<String> list = new ArrayList<>();
                                            list.add(orr);
                                            EventClass.plugin3.orgcfg.set("jugadores." + target.getName() + "InvGre", list);
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a tu gremio a " + ChatColor.GOLD + target.getName());
                                            target.sendMessage(ChatColor.AQUA + "Has sido invitado a el gremio " + ChatColor.GOLD + orr);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            return true;
                                            
                                            
                                        } else {
                                            
                                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + target.getName() + ".InvGre");
                                            int value = 0;
                                            String sss = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".gremio");
                                            for (String each : cfg) {
                                                if (each.equalsIgnoreCase(sss)) {
                                                    value = 1;
                                                }
                                            }
                                            if (value == 0) {
                                            String soc = EventClass.plugin3.orgcfg.getString("jugadores." + player.getName() + ".gremio");
                                            cfg.add(soc);
                                            EventClass.plugin3.getConfig().set("List", cfg);
                                            try {
                                            EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                            } catch (IOException e) {
                                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                            }
                                            player.sendMessage(ChatColor.AQUA + "Has invitado a tu gremio a " + ChatColor.GOLD + target.getName());
                                            target.sendMessage(ChatColor.AQUA + "Has sido invitado a el gremio " + ChatColor.GOLD + sss);
                                            return true;
                                            } else {
                                                player.sendMessage(ChatColor.RED + "Este jugador ya tiene una invitación para tu gremio!");
                                                return true;
                                            }
                                        }
                                        
                                    }
                                            
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Este jugador ya pertenece a un gremio!");
                                            return true;
                                        }
                                    
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No puedes invitarte a ti mismo!");
                                        return true;
                                    }
                                }
                                
                                
                            } else {
                               player.sendMessage(ChatColor.RED + "No perteneces a un gremio!");
                               return true;
                            }
                    } else {
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        return true;
                    }
                    
                } else if(args[0].equalsIgnoreCase("aceptar")) {
                    
                    if (args.length < 2) {
                        
                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".gremio")) {
                            
                            if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".InvGre")) {
                            
                            List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + player.getName() + ".InvGre");
                            int value = 0;
                            String org = "";
                            for (String each : cfg) {
                                if (value == 0) {
                                    value = 1;
                                    org = each;
                                }
                            }
                            if (value == 0) {
                                player.sendMessage(ChatColor.RED + "No tienes invitaciones de ningún gremio!");
                                return true; 
                            } else {
                                
                                EventClass.plugin3.orgcfg.set("organizaciones." + org + ".players", player.getName());
                                EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".gremio", org);
                                try {
                                    EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                } catch (IOException e) {
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                }
                                
                                return true;
                                
                            }
                            
                        } else {
                                player.sendMessage(ChatColor.RED + "No tienes invitaciones de ningún gremio!");
                                return true;
                            }
                            
                        } else {
                           player.sendMessage(ChatColor.RED + "Ya perteneces a un gremio!");
                           return true; 
                        }
                        
                    } else if (args.length == 2) {
                        
                        if (!EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".gremio")) {
                            
                            if (EventClass.plugin3.orgcfg.contains("organizaciones." + args[1])) {
                                
                                if (EventClass.plugin3.orgcfg.contains("jugadores." + player.getName() + ".InvGre")) {
                                
                                List<String> cfg = (List<String>)EventClass.plugin3.orgcfg.getList("jugadores." + player.getName() + ".InvGre");
                                int value = 0;
                                for (String each : cfg) {
                                    if (each.equalsIgnoreCase(args[1])) {
                                        value = 1;
                                        return true;
                                    }
                                }
                                
                                if (value == 0) {
                                    player.sendMessage(ChatColor.RED + "No tienes ningúna invitación de este gremio!");
                                    return true;
                                } else {
                                    
                                    EventClass.plugin3.orgcfg.set("organizaciones." + args[1] + ".players", player.getName());
                                    EventClass.plugin3.orgcfg.set("jugadores." + player.getName() + ".gremio", args[1]);
                                    try {
                                        EventClass.plugin3.orgcfg.save(EventClass.plugin3.orgfile);
                                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Se ha guardado exitósamente organizations.yml");
                                    } catch (IOException e) {
                                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No ha podido guardar organizations.yml");
                                    }
                                    
                                    return true;
                                }
                                
                            } else {
                                    player.sendMessage(ChatColor.RED + "No tienes invitaciones de ningún gremio!");
                                    return true;
                                }
                                
                            } else {
                                
                                player.sendMessage(ChatColor.RED + "No existe está organización!");
                                return true;
                            }
                            
                        } else {
                            player.sendMessage(ChatColor.RED + "Ya perteneces a un gremio!");
                           return true; 
                        }
                        
                    } else {
                        
                        player.sendMessage(ChatColor.RED + "Sobran argumentos!");
                        return true;
                        
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
        
        return true;
        
        
        
    }
    
}
